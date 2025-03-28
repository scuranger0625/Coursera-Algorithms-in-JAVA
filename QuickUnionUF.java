public class QuickUnionUF {
    // private 是只有在這個 class 裡面可以使用 int[] id是一個陣列
    private int[] id ;
    // 宣告一個 int 型別的陣列 id，這個陣列用來儲存每個元素的根節點。
    public QuickUnionUF(int N) { 
        // new int[N] 是建立一個大小為 N 的整數陣列，並將其指派給 id 變數。
        id = new int[N];
        // 當 int i從0到N-1時，id[i] = i，這樣 id 陣列的每個元素都指向自己。i++是自動加1。
        for(int i = 0; i<N; i++) id[i] = i;
    }

    private int root(int i) {
        // while迴圈的條件是 i != id[i]，這表示當前元素 i 的根節點不是它自己時，就繼續尋找。
        while ((i !=id[i])) i = id[i];
        // return i; 是返回當前元素 i 的根節點。
        return i ;

        }
        
        // 這個方法的作用是尋找元素 i 的根節點，並返回它。
        public boolean connected(int p, int q) { // connected方法用來檢查兩個元素p和q是否在同一個集合中。
            return root(p) == root(q); // 如果p和q的根節點相同，則返回true，否則返回false。
        }
        
        // union方法用來將兩個元素p和q所在的集合合併。
        public void union(int p, int q) {
            int i = root(p); // 尋找元素p的根節點
            int j = root(q); // 尋找元素q的根節點
            id[i] = j;

    }


}
