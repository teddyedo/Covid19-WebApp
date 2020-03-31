package allari.edoardo.covidtracker.controllers;

import java.util.Comparator;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import allari.edoardo.covidtracker.models.LocationStats;
import allari.edoardo.covidtracker.services.CoronaVirusDataService;

/**
 * HomeController
 */

@Controller
public class HomeController {

    @Autowired
    CoronaVirusDataService coronaVirusDataService;

    @GetMapping("/")
    public String home(Model model) throws JsonProcessingException {


        List<LocationStats> allStats = coronaVirusDataService.getAllStats();

        allStats.sort(Comparator.comparing(LocationStats::getDeaths).reversed());

        int totalReportedCases = allStats.stream().mapToInt(stat -> stat.getLatestTotalCases()).sum();
        int totalNewCases = allStats.stream().mapToInt(stat -> stat.getDifFromPrevDay()).sum();
        int totalDeaths = allStats.stream().mapToInt(stat -> stat.getDeaths()).sum();
        int totalRecovered = allStats.stream().mapToInt(stat -> stat.getRecovered()).sum();

        ObjectMapper objectMapper = new ObjectMapper();
        model.addAttribute("JSONLocationStats", objectMapper.writeValueAsString(coronaVirusDataService.getAllStats()));
        
        
        model.addAttribute("locationStats", coronaVirusDataService.getAllStats());
        model.addAttribute("totalReportedCases", totalReportedCases);
        model.addAttribute("totalNewCases", totalNewCases);
        model.addAttribute("totalDeaths", totalDeaths);
        model.addAttribute("totalRecovered", totalRecovered);
        
        return "home";
    }

    @GetMapping("/sortBy/{object}")
    public String sortByCountry(@PathVariable(value="object") String object, Model model)  {

        List<LocationStats> allStats = coronaVirusDataService.getAllStats();

            System.out.println(object);

        switch (object) {
            case "Country":
                allStats.sort(Comparator.comparing(LocationStats::getCountry));
                break;

            case "TotalCases":
                allStats.sort(Comparator.comparing(LocationStats::getLatestTotalCases).reversed());
                break;

            case "Recovered":
                allStats.sort(Comparator.comparing(LocationStats::getRecovered).reversed());
                break;
                
            case "Deaths":
                allStats.sort(Comparator.comparing(LocationStats::getDeaths).reversed());
                break;
        }

        int totalReportedCases = allStats.stream().mapToInt(stat -> stat.getLatestTotalCases()).sum();
        int totalNewCases = allStats.stream().mapToInt(stat -> stat.getDifFromPrevDay()).sum();
        int totalDeaths = allStats.stream().mapToInt(stat -> stat.getDeaths()).sum();
        int totalRecovered = allStats.stream().mapToInt(stat -> stat.getRecovered()).sum();

        model.addAttribute("locationStats", coronaVirusDataService.getAllStats());
        model.addAttribute("totalReportedCases", totalReportedCases);
        model.addAttribute("totalNewCases", totalNewCases);
        model.addAttribute("totalDeaths", totalDeaths);
        model.addAttribute("totalRecovered", totalRecovered);

        return "home";
    }
}