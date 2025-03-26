/*定義一個公開的類別（class），名稱為 Helloworld。// 在 Java 中，每個應用程式都必須至少有一個類別。
 類別名稱最好與檔名一致（例如：Helloworld.java），否則編譯會出錯。*/

/*main 方法是 Java 程式的進入點（也就是程式從這裡開始執行）。 所有的 Java 應用程式都必須包含這個 main 方法。*/


// - public: 表示這個方法是公開的，其他類別也可以存取它。
// - static: 表示這個方法是靜態的，無需建立物件就可以直接執行。
// - void: 表示這個方法沒有回傳值。
// - String[] args: 接收來自命令列的參數（可以忽略，但不能刪）。

public class HelloWorld {
    public static void main(String[] args) {
       // System.out.println() 是 Java 的輸出方法，可以將文字輸出到螢幕上。
       System.out.println("Hello, World");
    }
 }
