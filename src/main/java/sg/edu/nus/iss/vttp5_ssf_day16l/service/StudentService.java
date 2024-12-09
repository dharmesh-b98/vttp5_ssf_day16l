package sg.edu.nus.iss.vttp5_ssf_day16l.service;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;


import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;
import sg.edu.nus.iss.vttp5_ssf_day16l.model.Student;

@Service
public class StudentService { //getting info from the restcontroller
    
    RestTemplate restTemplate = new RestTemplate();

    public String createApiStudent(Student student){

        JsonObjectBuilder jab = Json.createObjectBuilder();
        jab.add("id", student.getId())
            .add("fullName", student.getFullName())
            .add("email", student.getEmail())
            .add("phoneNumber", student.getPhoneNumber());
        JsonObject studentJsonObject = jab.build();
        
        String studentJsonObjectString = studentJsonObject.toString();

        RequestEntity<String> requestEntity = RequestEntity.post("http://localhost:3000/api/student/create")
                                                    .contentType(MediaType.APPLICATION_JSON)
                                                    .body(studentJsonObjectString);

        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
        
        String response = responseEntity.getBody();
        return response;
    }



    public List<Student> getApiStudentList(){
        
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:3000/api/student/studentList", String.class);
        String response = responseEntity.getBody();

        JsonReader reader = Json.createReader(new StringReader(response));
        JsonArray jsonArray = reader.readArray();

        List<Student> studentList = new ArrayList<>();
        for(int i=0; i<jsonArray.size(); i++){
            JsonObject studentJson = jsonArray.getJsonObject(i);

            Integer id = studentJson.getInt("id");
            String fullName = studentJson.getString("fullName");
            String email = studentJson.getString("email");
            String phoneNumber = studentJson.getString("phoneNumber");

            Student student = new Student(id,fullName,email,phoneNumber);
            studentList.add(student);
        }
        return studentList;

    }

    //CAN IGNORE BELOW

    //get request with exchange trial //NEED HELP WITH THIS

    public List<Student> getApiStudentList_Try_Exchange(){
        //RequestEntity<String> requestEntity = RequestEntity.get("http://localhost:3000/api/student/studentList")
        //                                                    .accept(MediaType.APPLICATION_JSON);
                                                            
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:3000/api/student/studentList", String.class);
        String response = responseEntity.getBody();

        JsonReader reader = Json.createReader(new StringReader(response));
        JsonArray jsonArray = reader.readArray();

        List<Student> studentList = new ArrayList<>();
        for(int i=0; i<jsonArray.size(); i++){
            JsonObject studentJson = jsonArray.getJsonObject(i);

            Integer id = studentJson.getInt("id");
            String fullName = studentJson.getString("fullName");
            String email = studentJson.getString("email");
            String phoneNumber = studentJson.getString("phoneNumber");

            Student student = new Student(id,fullName,email,phoneNumber);
            studentList.add(student);
        }
        return studentList;

    }

    //posting url encoded form example
    public String createApiStudent_Try_URLencodedForm(){
        MultiValueMap<String,String> form = new LinkedMultiValueMap<>();
        form.add("id", "12345");
        form.add("fullName", "Jonah Fella");
        form.add("email", "jonahfella@gmail.com");
        form.add("phoneNumber", "92345667");
        //mutivaluemap intead of string below
        RequestEntity<MultiValueMap<String,String>> requestEntity = RequestEntity.post("http://localhost:3000/api/student/create_Try_URLencodedForm")
                                                    .contentType(MediaType.APPLICATION_FORM_URLENCODED) //urlencoded instead of json
                                                    .body(form);

        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
        
        String response = responseEntity.getBody();
        return response;
    }

    

    
}
