/**
 * Created by luisburgos on 26/10/15.
 */
public class App {

    public static void main(String[] args) {

        BookManager bookManager = new BookManager();

        bookManager.addBook(new Book("My Book", "Steven Haines", "1234"));
        bookManager.addBook(new Book("My Other Book", "Linda Haines", "5678"));
        bookManager.addBook(new Book("My Third Book", "Michael Haines", "9012"));

        Book stevesBook = bookManager.getBook("1234");
        System.out.println("Steve's book: " + stevesBook);

        bookManager.removeBook("1234");

        System.out.println("Steve's book: " + bookManager.getBook("1234"));

    }

        /*CityManager manager = new CityManager();

        manager.addCity(new City("Zürich", "Switzerland", 366765));
        manager.addCity(new City("Berlin", "Germany", 3502000));
        manager.addCity(new City("Johannesburg", "South Africa", 12200000));

        City retrievedCity = manager.getCity("Berlin");
        if (retrievedCity != null) {
            System.out.println(retrievedCity.toString());
        } else {
            System.out.println("No object was found in the cache for the key \"Berlin\"");
        }

        retrievedCity = manager.getCity("New York");
        if (retrievedCity != null) {
            System.out.println(retrievedCity.toString());
        } else {
            System.out.println("No object was found in the cache for the key \"New York\"");
        }*/



}
