// Quickfind屬於一種貪婪演算法，它是一種簡單的方法，但是效率不高
public class QuickfindUF {
    // private 是只有在這個 class 裡面可以使用 int[] id是一個陣列
    private int[] id;
    // QuickfindUF 是一個建構子
    public QuickfindUF(int N) {
        id = new int[N]; // new是一個陣列[N]是一個大小
        // 這個迴圈是把每個元素都設定為自己
        for (int i = 0; i < N; i++) { // i < N 是一個條件 i++是一個遞增
            id[i] = i; // id[i]是一個陣列
        }
    }
// boolean connected 是一個布林值   
public boolean connected(int p, int q) {
    return id[p] == id[q];
}

// void union 是一個空值
public void union(int p, int q) {
    int pid = id[p];
    int qid = id[q];
    // 這個迴圈是把每個元素都設定為自己 id.length是一個長度 i++是一個遞增
    for (int i = 0; i < id.length; i++) 
        // 如果 id[i] 等於 pid 就把 id[i] 設定為 qid
        if (id[i] == pid) id[i] = qid;
        }
    }

