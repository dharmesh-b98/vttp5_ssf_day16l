package sg.edu.nus.iss.vttp5_ssf_day16l.service;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import sg.edu.nus.iss.vttp5_ssf_day16l.constant.Url;
import sg.edu.nus.iss.vttp5_ssf_day16l.model.Country;

@Service
public class CountryService {
    
    RestTemplate restTemplate = new RestTemplate();

    public List<Country> getApiCountryList(){
        ResponseEntity<String> countryRawJsonStringEntity = restTemplate.getForEntity(Url.countryUrl, String.class);
        String countryRawJsonString = countryRawJsonStringEntity.getBody();
        
        JsonReader reader = Json.createReader(new StringReader(countryRawJsonString));
        JsonObject countryRawJsonObject = reader.readObject();

        JsonObject countryDataJsonObject = countryRawJsonObject.getJsonObject("data");

        List<Country> countryList = new ArrayList<>();
        for (Entry<String,JsonValue> countryEntry : countryDataJsonObject.entrySet()){
            String code = countryEntry.getKey();
            JsonObject countrydetails = countryEntry.getValue().asJsonObject(); //IMPT to convert JSON value to JSON object

            String name = countrydetails.getString("country"); //Cannot use getValue() here ****

            Country country = new Country(code, name);
            countryList.add(country);
        }
        return countryList;

    }        
    
}
