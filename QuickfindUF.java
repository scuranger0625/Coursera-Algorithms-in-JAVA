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
    
}
