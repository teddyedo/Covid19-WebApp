package allari.edoardo.covidtracker.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
    public String home(Model model) {

        List<LocationStats> allStats = coronaVirusDataService.getAllStats();

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