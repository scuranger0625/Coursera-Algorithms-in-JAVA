/**
 * Shell.java
 *
 * 教材風格的 Shellsort（貝殼排序）
 * ----------------------------------------------------------
 * 直覺：
 *   - 插入排序慢在「元素只能一步一步左移」。
 *   - Shellsort 先讓元素「跨大步」(h-sort)，快速接近正確位置，
 *     然後逐步縮小 h，最後 h=1 就幾乎是有序 → 插入排序快收尾。
 *
 * 特性：
 *   - In-place (O(1) 額外空間)
 *   - 不穩定排序
 *   - 複雜度：最壞情況不確定，但用 3x+1 序列，實務上很快
 *
 * 編譯：
 *   javac Shell.java
 *
 * 執行：
 *   java Shell S H E L L S O R T E X A M P L E
 *   java Shell   // 沒有參數則用預設示範
 */
public class Shell {

    // 是否要印出每次 h-sort 的中間狀態
    private static boolean TRACE = true;

    /**
     * Shellsort 主程式
     * 使用 3x+1 序列 (1,4,13,40,...)
     */
    public static void sort(Comparable[] a) {
        int N = a.length;

        // 1) 建立最大 h
        int h = 1;
        while (h < N/3) h = 3*h + 1;  // 最大 h < N/3

        // 2) 逐步縮小 h，直到 h=1
        while (h >= 1) {
            // 對每個子序列做插入排序（步長為 h）
            for (int i = h; i < N; i++) {
                for (int j = i; j >= h && less(a[j], a[j-h]); j -= h) {
                    exch(a, j, j-h);
                }
            }
            if (TRACE) {
                System.out.printf("h = %d → ", h);
                show(a);
            }
            h = h/3; // 縮小步長
        }
    }

    // === 輔助函式 ===
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + (i == a.length-1 ? "" : " "));
        }
        System.out.println();
    }

    public static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++) {
            if (less(a[i], a[i-1])) return false;
        }
        return true;
    }

    // === 主程式：可直接下參數，或用預設示範 ===
    public static void main(String[] args) {
        Comparable[] a;
        if (args.length == 0) {
            a = new Comparable[] {"S","H","E","L","L","S","O","R","T","E","X","A","M","P","L","E"};
            System.out.println("使用預設示範：");
            show(a);
        } else {
            a = new Comparable[args.length];
            for (int i = 0; i < args.length; i++) a[i] = args[i];
        }

        sort(a);

        System.out.println("排序結果：");
        show(a);
        System.out.println("是否已排序？ " + isSorted(a));
    }
}
