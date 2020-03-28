package allari.edoardo.covidtracker.services;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import allari.edoardo.covidtracker.models.LocationStats;

/**
 * CoronaVirusDataService
 */

 @Service
public class CoronaVirusDataService {

    private static String INFECTED_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    private static String DEATH_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_deaths_global.csv";

    private static String RECOVERED_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_recovered_global.csv";
    
    private List<LocationStats> allStats = new ArrayList<>();

    public List<LocationStats> getAllStats(){
        return allStats;
    }

    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void fetchVirusData() throws IOException, InterruptedException {

        List<LocationStats> newStats = new ArrayList<>();

        HttpClient client = HttpClient.newHttpClient();
        
        //Request for general data
        HttpRequest genRequest = HttpRequest.newBuilder()
                    .uri(URI.create(INFECTED_URL))
                    .build();

        //Request for deaths data
        HttpRequest deathsRequest = HttpRequest.newBuilder()
                    .uri(URI.create(DEATH_URL))
                    .build();

        //Request for recovered data
        HttpRequest recoveredRequest = HttpRequest.newBuilder()
                    .uri(URI.create(RECOVERED_URL))
                    .build();


        //Response for general data
        HttpResponse<String> genResponse = client.send(genRequest, HttpResponse.BodyHandlers.ofString());

        //Response for deaths data
        HttpResponse<String> deathsResponse = client.send(deathsRequest, HttpResponse.BodyHandlers.ofString());
        
        //Response for recovered data
        HttpResponse<String> recoveredResponse = client.send(recoveredRequest, HttpResponse.BodyHandlers.ofString());
        
        //Responses reader
        StringReader genReader = new StringReader(genResponse.body());
        Iterable<CSVRecord> genRecords = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(genReader);

        StringReader deathsReader = new StringReader(deathsResponse.body());
        Iterable<CSVRecord> deathRecords = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(deathsReader);

        StringReader recoveredReader = new StringReader(recoveredResponse.body());
        Iterable<CSVRecord> recoveredRecords = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(recoveredReader);

        List<CSVRecord> recoveredList = new ArrayList<>();

        for (CSVRecord record : recoveredRecords){
            recoveredList.add(record);
        }

        for(CSVRecord record : genRecords) {
            
            LocationStats locationStat = new LocationStats();
            
            locationStat.setState(record.get("Province/State"));
            locationStat.setCountry(record.get("Country/Region"));
            
            int latestCases = Integer.parseInt(record.get(record.size() - 1));
            int prevDayCases = Integer.parseInt(record.get(record.size() - 2));
            
            locationStat.setLatestTotalCases(latestCases);
            locationStat.setDifFromPrevDay(latestCases - prevDayCases);

            
            newStats.add(locationStat);
        }

        //adding deaths information
        int count = 0;
        for(CSVRecord record : deathRecords){
            newStats.get(count).setDeaths(Integer.parseInt(record.get(record.size() - 1)));
            count ++;
        }


        //adding recovered information
        
        for(LocationStats locationStats : newStats){
            for(CSVRecord record : recoveredList){
                if((locationStats.getState().equals(record.get("Province/State"))) && locationStats.getCountry().equals(record.get("Country/Region"))){ 
                    
                    locationStats.setRecovered(Integer.parseInt(record.get(record.size() - 1)));
                    break;
                }
                else if(record.get("Province/State").equals("") && locationStats.getCountry().equals(record.get("Country/Region"))){
                    
                    locationStats.setRecovered(Integer.parseInt(record.get(record.size() - 1)));
                    break;
                }
            }
        }


        newStats.sort(Comparator.comparing(LocationStats::getDeaths).reversed());

        this.allStats = newStats;


    }
}