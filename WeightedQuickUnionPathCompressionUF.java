public class WeightedQuickUnionPathCompressionUF {
    private int[] id;    // 儲存每個元素的父節點
    private int[] size;  // 儲存每棵樹的大小

    // 初始化陣列
    public WeightedQuickUnionPathCompressionUF(int N) {
        id = new int[N];
        size = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;      // 初始化時每個元素的根節點是自己
            size[i] = 1;    // 每個集合的大小都是 1
        }
    }

    // 找到根節點（使用路徑壓縮）
    private int root(int i) {
        while (i != id[i]) {
            id[i] = id[id[i]];  // 將節點 i 直接連接到它的祖父節點
            i = id[i];
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

    // main() 方法：測試加權合併 + 路徑壓縮的功能
    public static void main(String[] args) {
        // 建立一個有 10 個元素的 WeightedQuickUnionPathCompressionUF 物件
        WeightedQuickUnionPathCompressionUF wqupc = new WeightedQuickUnionPathCompressionUF(10);

        // 測試 union() 操作
        wqupc.union(4, 3);
        wqupc.union(3, 8);
        wqupc.union(6, 5);
        wqupc.union(9, 4);
        wqupc.union(2, 1);
        wqupc.union(5, 0);
        wqupc.union(7, 2);
        wqupc.union(6, 1);
        wqupc.union(1, 0);
        wqupc.union(6, 7);

        // 測試 connected() 操作
        System.out.println("Connected(0, 7)? " + wqupc.connected(0, 7));  // true
        System.out.println("Connected(8, 9)? " + wqupc.connected(8, 9));  // true
        System.out.println("Connected(5, 4)? " + wqupc.connected(5, 4));  // false
    }
}
