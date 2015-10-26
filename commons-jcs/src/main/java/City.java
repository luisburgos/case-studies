import java.io.Serializable;

/**
 * Created by luisburgos on 26/10/15.
 */
public class City implements Serializable {

    public String name;
    public String country;
    public int population;
    private static final long serialVersionUID = 6392376146163510146L;

    public City( String name, String country, int population ) {
        this.name = name;
        this.country = country;
        this.population = population;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format( "%s is a city in the country %s with a population of %d", name, country, population );
    }

}
