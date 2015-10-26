package book;

/**
 * Created by luisburgos on 26/10/15.
 */
public class App {

    public static void main(String[] args) {

        BookManager bookManager = new BookManager();

        bookManager.addBook(new Book("My book.Book", "Steven Haines", "1234"));
        bookManager.addBook(new Book("My Other book.Book", "Linda Haines", "5678"));
        bookManager.addBook(new Book("My Third book.Book", "Michael Haines", "9012"));

        Book stevesBook = bookManager.getBook("1234");
        System.out.println("Steve's book: " + stevesBook);

        bookManager.removeBook("1234");
        System.out.println("Steve's book: " + bookManager.getBook("1234"));

    }

}
