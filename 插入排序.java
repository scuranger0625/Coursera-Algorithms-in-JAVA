/**
 * Insertion.java
 *
 * 插入排序（Insertion Sort）— Java 教材風格 + 詳細註解 + 成本統計 + 步驟追蹤
 * -----------------------------------------------------------------------------
 * 直覺：像整理撲克牌。每次拿到新牌 a[i]，一路往左和已排序區比較，找到位置後「插入」。
 *
 * 兩種實作：
 * (A) 交換版（教材常見）：內圈用 swap 將 a[i] 一步步與左邊較大的元素交換，直到停。
 * (B) 平移版（工程常見）：先暫存 key=a[i]，把左邊較大的元素【右移】，最後一次性放回 key。
 *     => 平移版通常「陣列寫入次數」較少（尤其在大量移動時），較省資料搬動。
 *
 * 複雜度（兩版同）：
 * - 最好情況：已排序 → O(N)（因為每次內圈很快遇到 break）
 * - 平均／最壞：O(N^2)
 * 穩定性：穩定（相等鍵不會改相對順序）
 * 空間：O(1) in-place
 *
 * 執行範例：
 *   javac Insertion.java
 *   // 預設使用交換版 + 開啟追蹤，未給參數時自帶示範資料
 *   java Insertion
 *
 *   // 排序命令列字串
 *   java Insertion S O R T E X A M P L E
 *
 *   // 改用平移版（shift）
 *   java Insertion --shift S O R T E X A M P L E
 *
 *   // 關閉追蹤
 *   java Insertion --trace=false S O R T E X A M P L E
 */
public class Insertion {

    // === 成本統計（方便你觀察比較/交換/寫入次數） ===
    private static long compares  = 0; // 呼叫 less() 的次數（近似比較次數）
    private static long exchanges = 0; // 呼叫 exch() 的次數（交換版專用）
    private static long writes    = 0; // 對陣列 a[] 的寫入次數（平移版著重觀察此數）

    // 是否顯示每次外圈 i 完成後的陣列狀態（教學追蹤用）
    private static boolean TRACE = true;

    // === (A) 交換版：教材經典寫法（與 Princeton 幻燈片一致） ===
    public static void sortSwap(Comparable[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++) {               // 外圈：i 從左到右推進，「已排序區」隨之擴大
            for (int j = i; j > 0; j--) {           // 內圈：把 a[j] 往左「冒泡」到正確位置
                if (less(a[j], a[j-1])) {           // 若 a[j] < a[j-1]，代表還要往左移
                    exch(a, j, j-1);                // 交換 a[j] 與 a[j-1]
                } else break;                       // 一旦左邊不大於我，就到位了，停止
            }
            if (TRACE) {
                System.out.printf("[swap] i=%2d -> ", i);
                show(a);
            }
        }
    }

    // === (B) 平移版（shift）：常見工程優化，減少寫入次數 ===
    public static void sortShift(Comparable[] a) {
        int N = a.length;
        for (int i = 1; i < N; i++) {               // i=0 時左邊空集合，可從 i=1 開始
            Comparable key = a[i];                  // 暫存新牌（要插入的元素）
            // 注意：此處只「讀出」key；真正計數寫入的是陣列 a[] 的賦值
            int j = i - 1;

            // 將左側比 key 大的元素，逐一往右「平移」
            while (j >= 0 && less(key, a[j])) {
                a[j + 1] = a[j];                    // 把較大的元素往右搬一格
                writes++;                           // 計一次陣列寫入
                j--;
            }
            // 找到插入點（j+1）：把 key 放回陣列
            a[j + 1] = key;
            writes++;

            if (TRACE) {
                System.out.printf("[shift] i=%2d -> ", i);
                show(a);
            }
        }
    }

    // === 輔助：比較（包一層方便之後換比較邏輯；同時統計比較次數） ===
    private static boolean less(Comparable v, Comparable w) {
        compares++;
        return v.compareTo(w) < 0;
    }

    // === 輔助：交換（交換版會用到；同時統計交換次數與寫入次數） ===
    private static void exch(Comparable[] a, int i, int j) {
        exchanges++;
        Comparable t = a[i];
        a[i] = a[j];  writes++;     // 計一次寫入
        a[j] = t;     writes++;     // 計一次寫入
    }

    // === 驗證是否已排序（升冪） ===
    public static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++) {
            if (less(a[i], a[i - 1])) return false; // 如有 a[i] < a[i-1] 則非遞增
        }
        return true;
    }

    // === 輸出陣列狀態（單行） ===
    public static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + (i + 1 == a.length ? "" : " "));
        }
        System.out.println();
    }

    // === 重置統計 ===
    public static void resetStats() {
        compares = 0;
        exchanges = 0;
        writes = 0;
    }

    // === 印出統計（根據使用版本看重點：交換版看 exchanges，平移版看 writes） ===
    public static void printStats(String label) {
        System.out.printf("%s -> compares=%d, exchanges=%d, writes=%d%n",
                label, compares, exchanges, writes);
    }

    // === 解析旗標（--shift / --trace=false），回傳過濾後的實際鍵值 ===
    private static Comparable[] parseArgs(String[] args) {
        boolean shift = false;
        TRACE = true;

        // 預先掃旗標
        int countKeys = 0;
        for (String s : args) {
            if (s.equalsIgnoreCase("--shift")) continue;
            if (s.equalsIgnoreCase("--trace=false")) continue;
            countKeys++;
        }
        Comparable[] keys = new Comparable[countKeys];

        // 第二次掃描：同時設定旗標與收集鍵
        int k = 0;
        for (String s : args) {
            if (s.equalsIgnoreCase("--shift")) { shift = true; continue; }
            if (s.equalsIgnoreCase("--trace=false")) { TRACE = false; continue; }
            keys[k++] = s;
        }

        // 把選擇的版本記在系統屬性上（只為了 main 裡判斷）
        System.setProperty("insertion.useShift", Boolean.toString(shift));
        return keys;
    }

    // === 主程式（示範 / 測試） ===
    public static void main(String[] args) {
        // 1) 處理輸入：若無參數，使用教科書示範資料
        Comparable[] a;
        if (args.length == 0) {
            a = new Comparable[] { "S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E" };
            System.out.println("未提供命令列參數，使用預設示範：");
            show(a);
        } else {
            a = parseArgs(args);
            if (a.length == 0) {
                // 只給旗標未給鍵 → 仍用預設示範
                a = new Comparable[] { "S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E" };
                System.out.println("未提供鍵值，使用預設示範：");
                show(a);
            }
        }

        boolean useShift = Boolean.parseBoolean(System.getProperty("insertion.useShift", "false"));

        // 2) 排序 + 追蹤 + 驗證 + 成本
        resetStats();
        if (useShift) {
            System.out.println("使用平移版（shift）插入排序：" + (TRACE ? "TRACE=on" : "TRACE=off"));
            sortShift(a);
            System.out.println("排序結果：");
            show(a);
            System.out.println("是否已排序（升冪）？ " + isSorted(a));
            printStats("shift");
        } else {
            System.out.println("使用交換版（swap）插入排序：" + (TRACE ? "TRACE=on" : "TRACE=off"));
            sortSwap(a);
            System.out.println("排序結果：");
            show(a);
            System.out.println("是否已排序（升冪）？ " + isSorted(a));
            printStats("swap");
        }

        // 小提醒：
        // - 已近乎有序 → 插入排序表現極佳（接近 O(N)）
        // - 大量資料 → 請使用 O(N log N) 排序（例如 Timsort/Merge/Quick）
    }
}
