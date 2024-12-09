package sg.edu.nus.iss.vttp5_ssf_day16l.restcontroller;

import java.io.StringReader;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;
import sg.edu.nus.iss.vttp5_ssf_day16l.service.StudentRestService;
import sg.edu.nus.iss.vttp5_ssf_day16l.constant.Constants;
import sg.edu.nus.iss.vttp5_ssf_day16l.model.Student;
@RestController
@RequestMapping(path="/api/student", produces="application/json")  //important
public class StudentRestController{

    @Autowired
    StudentRestService studentRestService;

    @PostMapping("/create")
    public ResponseEntity<String> createPerson(@RequestBody String entity){ //looking for json in string format as the input
        
        JsonReader reader = Json.createReader(new StringReader(entity));
        JsonObject rawJsonObject = reader.readObject();
        
        Integer id = rawJsonObject.getInt("id");
        String fullName = rawJsonObject.getString("fullName");
        String email = rawJsonObject.getString("email");
        String phoneNumber = rawJsonObject.getString("phoneNumber");

        Student student = new Student(id,fullName,email,phoneNumber);
        studentRestService.addPerson(Constants.studentKey,student);
        
        ResponseEntity<String> responseEntity = ResponseEntity.status(HttpStatus.CREATED).body("complete");

        return responseEntity;
    }

    //find all
    @GetMapping("/studentList")
    public ResponseEntity<String> studentList(){
        List<Student> studentList = studentRestService.findAll(Constants.studentKey);
        
        JsonArrayBuilder jab = Json.createArrayBuilder();

        for (Student student : studentList){
            JsonObjectBuilder job = Json.createObjectBuilder();
            job = job.add("id", student.getId())
                                        .add("fullName", student.getFullName())
                                        .add("email", student.getEmail())
                                        .add("phoneNumber", student.getPhoneNumber());

            jab = jab.add(job);
        }

        JsonArray studentJsonArray = jab.build();
        String studentJsonArrayString = studentJsonArray.toString();

        ResponseEntity<String> responseEntity = ResponseEntity.status(HttpStatus.FOUND).body(studentJsonArrayString);
        //return  ResponseEntity.ok().body(JsonString);
        return responseEntity;

    }


    //CAN IGNORE BELOW THISS


    // urlencoded example
    @PostMapping("/create_Try_URLencodedForm") //requestbody plus multivalueMap
    public ResponseEntity<String> createPerson_Try_URLencodedForm(@RequestBody MultiValueMap<String,String> entity){ //looking for json in string format as the input
        
        Integer id = Integer.parseInt(entity.getFirst("id")); //getFirst instead of getString etc
        String fullName = entity.getFirst("fullName");
        String email = entity.getFirst("email");
        String phoneNumber = entity.getFirst("phoneNumber");

        Student student = new Student(id,fullName,email,phoneNumber);
        studentRestService.addPerson(Constants.studentKey,student);
        
        ResponseEntity<String> responseEntity = ResponseEntity.status(HttpStatus.CREATED).body("complete");

        return responseEntity;
    }

}
