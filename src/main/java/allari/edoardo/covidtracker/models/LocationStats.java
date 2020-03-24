package allari.edoardo.covidtracker.models;

/**
 * LocationStats
 */
public class LocationStats {

    private String state;
    private String country;
    private int latestTotalCases;
    private int difFromPrevDay;
    private int deaths;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getLatestTotalCases() {
        return latestTotalCases;
    }

    public void setLatestTotalCases(int latestTotalCases) {
        this.latestTotalCases = latestTotalCases;
    }

    public LocationStats() {
    }

    @Override
    public String toString() {
        return "LocationStats [country=" + country + ", latestTotalCases=" + latestTotalCases + ", state=" + state
                + "]";
    }

    public int getDifFromPrevDay() {
        return difFromPrevDay;
    }

    public void setDifFromPrevDay(int difFromPrevDay) {
        this.difFromPrevDay = difFromPrevDay;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    
    
}