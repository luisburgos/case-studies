import java.io.Serializable;

/**
 * This class implements Serializable, which is required for all objects that you add to a cache because the
 * cache uses serialization to read and write objects to persistent storage.
 *
 * Created by luisburgos on 26/10/15.
 */
public class Book implements Serializable{

    private String title;
    private String author;
    private String isbn;

    public Book() {}

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String toString() {
        return title + " by " + author + ": " + isbn;
    }

}
