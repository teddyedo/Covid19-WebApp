package allari.edoardo.covidtracker.models;

import java.util.ArrayList;

/**
 * LocationStats
 */
public class LocationStats {

    private String state;
    private String country;
    private int latestTotalCases;
    private int difFromPrevDay;
    private int deaths;
    private int recovered;
    private ArrayList<Integer> casesDayByDay = new ArrayList<>();
    private ArrayList<Integer> recoveredDayByDay = new ArrayList<>();
    private ArrayList<Integer> deathsDayByDay = new ArrayList<>();
    

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

    public int getRecovered() {
        return recovered;
    }

    public void setRecovered(int recovered) {
        this.recovered = recovered;
    }

    public ArrayList<Integer> getCasesDayByDay() {
        return casesDayByDay;
    }

    public void setCasesDayByDay(ArrayList<Integer> casesDayByDay) {
        this.casesDayByDay = casesDayByDay;
    }

    public ArrayList<Integer> getRecoveredDayByDay() {
        return recoveredDayByDay;
    }

    public void setRecoveredDayByDay(ArrayList<Integer> recoveredDayByDay) {
        this.recoveredDayByDay = recoveredDayByDay;
    }

    public ArrayList<Integer> getDeathsDayByDay() {
        return deathsDayByDay;
    }

    public void setDeathsDayByDay(ArrayList<Integer> deathsDayByDay) {
        this.deathsDayByDay = deathsDayByDay;
    }

       
    @Override
    public String toString() {
        return "LocationStats [country=" + country + ", latestTotalCases=" + latestTotalCases + ", state=" + state
                + "]";
    }
}