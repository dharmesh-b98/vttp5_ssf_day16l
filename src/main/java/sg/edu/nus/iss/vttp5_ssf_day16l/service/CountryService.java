package sg.edu.nus.iss.vttp5_ssf_day16l.service;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import sg.edu.nus.iss.vttp5_ssf_day16l.model.Country;

@Service
public class CountryService {
    
    RestTemplate restTemplate = new RestTemplate();

    public List<Country> getApiCountryList(){
                
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("https://api.first.org/data/v1/countries", String.class);
        String jsonString = responseEntity.getBody();

        StringReader sr = new StringReader(jsonString);
        JsonReader jr = Json.createReader(sr);
        JsonObject jsonObject = jr.readObject();

        List<Country> countryList = new ArrayList<>();
        JsonObject data = jsonObject.getJsonObject("data");
        Set<Entry<String,JsonValue>> entrySet = data.entrySet();
        for (Entry<String,JsonValue> entry : entrySet){
            String code = entry.getKey();
            JsonObject country_region = entry.getValue().asJsonObject();  //IMPT to convert JSON value to JSON object
            String name = country_region.getString("country");
            
            Country country = new Country(code, name);
            countryList.add(country);
        }
        
        return countryList;       
        
    }
}
