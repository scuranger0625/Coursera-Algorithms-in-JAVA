
/*示範用 */

// 這是一個動態連通性的程式
public static void main(String[] args) {
    // readInt() 是 StdIn 類別的靜態方法，用來讀入一個整數
    int N = StdIn.readInt();     // 從輸入讀入物件總數 N
    // new是Java的關鍵字，用來建立新的物件
    UF uf = new UF(N);           // 建立 Union-Find 結構
    // !是Java的邏輯運算子，用來取反
    while (!StdIn.isEmpty()) {   // 只要還有輸入資料
        int p = StdIn.readInt(); // 讀入 p
        int q = StdIn.readInt(); // 讀入 q

        // 如果 p 和 q 尚未連接，則進行連接並印出
        if (!uf.connected(p, q)) {
            uf.union(p, q);                      // 合併集合
            StdOut.println(p + " " + q);         // 印出這次合併的數對
        }
    }
}
