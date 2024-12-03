package sg.edu.nus.iss.vttp5_ssf_day16l.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sg.edu.nus.iss.vttp5_ssf_day16l.model.Country;
import sg.edu.nus.iss.vttp5_ssf_day16l.service.CountryService;

@Controller
@RequestMapping("/country")
public class CountryController {
    
    @Autowired
    CountryService countryService;

    @ResponseBody
    @GetMapping("countryList")
    public List<Country> getApiCountryList(Model model){
        
        List<Country> countryList = countryService.getApiCountryList();
        model.addAttribute("countryList", countryList);

        return countryList;
    }

}
