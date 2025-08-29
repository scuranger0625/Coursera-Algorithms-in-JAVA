/**
 * Selection.java
 *
 * 目的：示範「選擇排序」（Selection Sort）的標準實作（教材風格）並加上詳細註解。
 * ---------------------------------------------------------------------
 * 核心想法：
 * 第 i 回合，從右邊尚未排序的區間 [i..N-1] 找出「最小值索引 min」，
 * 然後把 a[i] 與 a[min] 交換 → 使得位置 i 成為「正確的最終位置」。
 *
 * 不變量（Loop Invariant）：
 * - 每次外層迴圈開始時，區間 [0..i-1] 皆已「在最終順序」且固定不動。
 * - 區間 [i..N-1] 為未處理區，min 會是該區間的最小元素索引。
 *
 * 時間複雜度：
 * - 比較次數： (N-1) + (N-2) + ... + 1 + 0 = ~ N^2 / 2
 * - 交換次數： N 次（每回合至多 1 次交換；若 min == i 則仍可視為一次「恰好 N 次」的上界）
 * 空間複雜度：O(1)（原地排序）
 * 穩定性：不穩定（相等鍵可能因交換而改變相對次序）
 *
 * 適用場景：
 * - 當「交換成本很高」但「比較成本可以接受」時（因為交換次數只有 ~N）。
 * - 對「幾乎已排序」資料沒有加速效果（仍是 ~N^2/2 次比較）。
 *
 * 編譯執行：
 *   javac Selection.java
 *   // 範例一：用命令列字串當輸入（每個參數一個鍵）
 *   java Selection S O R T E X A M P L E
 *
 *   // 範例二：若不給參數，程式會用內建範例陣列
 *   java Selection
 */
public class Selection {

    // === 統計用計數器（方便你觀察成本） ===
    private static long compares = 0;  // 計算比較次數（less 被呼叫次數）
    private static long exchanges = 0; // 計算交換次數（exch 被呼叫次數）

    // 是否在每次交換後印出陣列狀態（教學追蹤用）
    private static boolean TRACE = true;

    /**
     * 主排序函式：對 a[] 做選擇排序（升冪）。
     * 使用 Comparable 接口，因而可排序 String、Integer、Double、File... 及自定型別（實作 compareTo）。
     */
    public static void sort(Comparable[] a) {
        int N = a.length;
        // 外層：決定「當前要放正確元素」的位置 i
        for (int i = 0; i < N; i++) {
            int min = i; // 目前假設 a[i] 是未排序區間 [i..N-1] 的最小值
            // 內層：在右邊未排序區間中尋找更小者，更新 min
            for (int j = i + 1; j < N; j++) {
                if (less(a[j], a[min])) { // 若 a[j] < a[min]，則更新最小索引
                    min = j;
                }
            }
            // 將最小值放到位置 i（若 min==i 也呼叫 exch，便於統計與示範；你也可加條件避免多餘交換）
            exch(a, i, min);
            if (TRACE) {
                System.out.printf("i=%2d, min=%2d  -> ", i, min);
                show(a);
            }
        }
    }

    /**
     * 比較輔助：回傳 v 是否「小於」 w。
     * 說明：
     *   Comparable.compareTo 的慣例：
     *   - 回傳 < 0 ： v < w
     *   - 回傳 = 0 ： v == w
     *   - 回傳 > 0 ： v > w
     */
    private static boolean less(Comparable v, Comparable w) {
        compares++; // 統計每次比較
        return v.compareTo(w) < 0;
    }

    /**
     * 交換輔助：交換 a[i] 與 a[j]。
     * 注意：選擇排序的特色是「交換次數少」（約 N 次），有利於交換成本高的環境。
     */
    private static void exch(Comparable[] a, int i, int j) {
        exchanges++; // 統計每次交換
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    /**
     * 驗證陣列是否已排序（升冪）。
     * 教學／測試用：排序後可呼叫此函式檢查正確性。
     */
    public static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++) {
            if (less(a[i], a[i - 1])) return false;
        }
        return true;
    }

    /**
     * 輸出陣列內容（同一列、以空白分隔），教學用。
     */
    public static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + (i + 1 == a.length ? "" : " "));
        }
        System.out.println();
    }

    /**
     * 重置統計（方便多次實驗）
     */
    public static void resetStats() {
        compares = 0;
        exchanges = 0;
    }

    /**
     * 列印統計資料（比較次數 / 交換次數）
     */
    public static void printStats() {
        System.out.printf("Comparisons = %d, Exchanges = %d%n", compares, exchanges);
    }

    // === 範例主程式 ===
    public static void main(String[] args) {
        // 1) 準備輸入：
        //    - 若命令列有參數：直接把參數當成要排序的鍵（例如字串）
        //    - 否則使用內建示範資料（和課本一樣 "S O R T E X A M P L E"）
        Comparable[] a;
        if (args.length > 0) {
            a = new Comparable[args.length];
            System.arraycopy(args, 0, a, 0, args.length);
        } else {
            a = new Comparable[] { "S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E" };
            System.out.println("未提供命令列參數，使用預設示範：");
            show(a);
        }

        // 2) 可選：是否顯示每一步交換後的追蹤（教學模式）
        //    若不想顯示過程，把 TRACE 設為 false
        TRACE = true;

        // 3) 排序 + 顯示結果 + 驗證 + 統計
        resetStats();
        sort(a);
        System.out.println("排序結果：");
        show(a);

        System.out.println("是否已排序（升冪）？ " + isSorted(a));
        printStats();

        // 小提醒：
        // - 對幾乎已排序的資料，選擇排序仍舊需要 ~N^2/2 比較；插入排序反而可接近線性。
        // - 但若交換成本遠大於比較成本，選擇排序可能更合適（因交換只有 ~N 次）。
    }
}
