package sg.edu.nus.iss.vttp5_ssf_day16l.restcontroller;

import java.io.StringReader;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    //create
    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody String entity){ //it is looking for a json in string format as the input
        
        StringReader sr = new StringReader(entity);
        JsonReader jr = Json.createReader(sr);
        JsonObject studentJson = jr.readObject();

        Integer id = studentJson.getInt("id");
        String fullName = studentJson.getString("fullName");
        String email = studentJson.getString("email");
        String phoneNumber = studentJson.getString("phoneNumber");

        Student student = new Student(id,fullName,email,phoneNumber);
        studentRestService.addPerson(Constants.studentKey, student);

        return ResponseEntity.ok().body("Completed");
        
    }

    //find all
    @GetMapping("/studentList")
    public ResponseEntity<String> studentList(){
        
        List<Student> studentList = studentRestService.findAll(Constants.studentKey);
        
        JsonArrayBuilder jAb = Json.createArrayBuilder();

        for (Student student : studentList){
            JsonObjectBuilder jOb = Json.createObjectBuilder();
            jOb.add("id",student.getId())
                .add("fullName",student.getFullName())
                .add("email",student.getEmail())
                .add("phoneNumber",student.getPhoneNumber());
            
            jAb.add(jOb);
        }
        JsonArray jsonBuilt = jAb.build(); //important to assign to variable or it wont save    
        
        String JsonString = jsonBuilt.toString();
        
        return  ResponseEntity.ok().body(JsonString);
        
    }

}
