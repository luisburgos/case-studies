package book;

import org.apache.commons.jcs.JCS;
import org.apache.commons.jcs.access.CacheAccess;
import org.apache.commons.jcs.access.exception.CacheException;

/**
 * Created by luisburgos on 26/10/15.
 */
public class BookManager {

    private CacheAccess<String, Book> cache = null;
    private static final String BOOK_CACHE_NAME = "bookCache";

    public BookManager() {
        try {
            cache = JCS.getInstance(BOOK_CACHE_NAME);
            cache.put("1234", new Book("My book.Book", "Steven Haines", "1234"));
            cache.put("5678", new Book("My Other book.Book", "Linda Haines", "5678"));
            cache.put("9012", new Book("My Third book.Book", "Michael Haines", "9012"));
        } catch (CacheException e) {
            e.printStackTrace();
        }
    }

    public void addBook(Book book) {
        try {
            cache.put(book.getIsbn(), book);
        } catch (CacheException e) {
            e.printStackTrace();
        }
    }

    public Book getBook(String isbn) {
        return (Book) cache.get(isbn);
    }

    public void removeBook(String isbn) {
        try {
            cache.remove(isbn);
        } catch (CacheException e) {
            e.printStackTrace();
        }
    }


}
