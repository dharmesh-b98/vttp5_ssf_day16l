package sg.edu.nus.iss.vttp5_ssf_day16l.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.vttp5_ssf_day16l.model.Student;
import sg.edu.nus.iss.vttp5_ssf_day16l.repo.ListRepo;

@Service
public class StudentRestService { //getting info from the redis database into the rest api
    
    @Autowired
    ListRepo studentRepo;

    public void addPerson(String key,Student student){
        String studentString = student.toString();
        studentRepo.rightPush(key,studentString);
    }

    public boolean delete(String key, Integer id){
        return studentRepo.deleteItem(key,id);
    }

    public List<Student> findAll(String key){
        List<String> studentListString = studentRepo.getList(key);
        List<Student> studentList = new ArrayList<>();
        for (String studentString: studentListString){
            String[] studentStringAttributeList = studentString.split(",");
            Integer id = Integer.parseInt(studentStringAttributeList[0]);
            String fullName = studentStringAttributeList[1];
            String email = studentStringAttributeList[2];
            String phoneNumber = studentStringAttributeList[3];
            Student student= new Student(id,fullName,email,phoneNumber);
            studentList.add(student);
        }
        return studentList;
    }
        

}
