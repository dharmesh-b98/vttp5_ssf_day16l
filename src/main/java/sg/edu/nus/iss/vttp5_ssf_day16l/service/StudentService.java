package sg.edu.nus.iss.vttp5_ssf_day16l.service;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;


import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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

    public ResponseEntity<String> createApiStudent(Student student){
        JsonObjectBuilder jab = Json.createObjectBuilder();
        
        jab.add("id",student.getId())
            .add("fullName", student.getFullName())
            .add("email", student.getEmail())
            .add("phoneNumber", student.getPhoneNumber());

        JsonObject jObject = jab.build();

        RequestEntity<String> request = RequestEntity.post("http://localhost:3000/api/student/create")
                                                    .contentType(MediaType.APPLICATION_JSON)
                                                    .body(jObject.toString(), String.class);

        ResponseEntity<String> response = restTemplate.exchange(request, String.class);
        return response;
    }




    public List<Student> getApiStudentList(){
        
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:3000/api/student/studentList", String.class);
        String responsebody = response.getBody();
        
        StringReader sr = new StringReader(responsebody);
        JsonReader jr = Json.createReader(sr);
        JsonArray jsonArray = jr.readArray();

        List<Student> studentList = new ArrayList<>();

        for (int i = 0; i<jsonArray.size(); i++){
            JsonObject jsonObject = jsonArray.getJsonObject(i);
            
            Integer id = jsonObject.getInt("id");
            String fullName = jsonObject.getString("fullName");
            String email = jsonObject.getString("email");
            String phoneNumber = jsonObject.getString("phoneNumber");

            Student student = new Student(id, fullName, email, phoneNumber);
            studentList.add(student);
            
        }

        return studentList;       

    }

    

    
}
