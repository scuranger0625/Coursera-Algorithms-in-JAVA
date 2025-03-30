public class WeightedQuickUnionUF {
    private int[] id;    // 儲存每個元素的父節點
    private int[] size;  // 儲存每棵樹的大小

    // 初始化陣列
    public WeightedQuickUnionUF(int N) {
        id = new int[N];
        size = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;      // 初始化時每個元素的根節點是自己
            size[i] = 1;    // 每個集合的大小都是 1
        }
    }

    // 找到根節點
    private int root(int i) {
        while (i != id[i]) {
            i = id[i];  // 一直往上尋找，直到找到根節點
        }
        return i;
    }

    // 判斷 p 和 q 是否連通
    public boolean connected(int p, int q) {
        return root(p) == root(q);  // 判斷兩個元素的根是否相同
    }

    // 加權合併 union()：將小樹掛到大樹上
    public void union(int p, int q) {
        int rootP = root(p);  // 找到 p 的根節點
        int rootQ = root(q);  // 找到 q 的根節點

        if (rootP == rootQ) return;  // 如果已經連通，直接返回

        // 比較兩棵樹的大小
        if (size[rootP] < size[rootQ]) {
            id[rootP] = rootQ;       // 將較小的樹掛到較大的樹上
            size[rootQ] += size[rootP];  // 更新新根節點的大小
        } else {
            id[rootQ] = rootP;       // 將 rootQ 掛到 rootP
            size[rootP] += size[rootQ];  // 更新新根節點的大小
        }
    }

    // main() 方法：測試加權合併的功能
    public static void main(String[] args) {
        // 建立一個有 10 個元素的 WeightedQuickUnionUF 物件
        WeightedQuickUnionUF wqu = new WeightedQuickUnionUF(10);

        // 測試 union() 操作
        wqu.union(4, 3);
        wqu.union(3, 8);
        wqu.union(6, 5);
        wqu.union(9, 4);
        wqu.union(2, 1);
        wqu.union(5, 0);
        wqu.union(7, 2);
        wqu.union(6, 1);
        wqu.union(1, 0);
        wqu.union(6, 7);

        // 測試 connected() 操作
        System.out.println("Connected(0, 7)? " + wqu.connected(0, 7));  // true
        System.out.println("Connected(8, 9)? " + wqu.connected(8, 9));  // true
        System.out.println("Connected(5, 4)? " + wqu.connected(5, 4));  // false
    }
}
