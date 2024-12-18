package sg.edu.nus.iss.vttp5_ssf_day16l.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.nus.iss.vttp5_ssf_day16l.model.Student;
import sg.edu.nus.iss.vttp5_ssf_day16l.service.StudentService;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;
    
    @GetMapping("/create")
    public String createStudent(Model model){
        Student student = new Student();
        model.addAttribute("student", student);
        return "studentform";

    }

    @PostMapping("/create")
    public String postCreateStudent(@ModelAttribute Student student){
        String response = studentService.createApiStudent(student);
        System.out.println("\n\n\n\n" + response + "\n\n\n\n");
        return "redirect:/student/studentList";
    }

    @GetMapping("/studentList")
    public String studentList(Model model){
        List<Student> studentList = studentService.getApiStudentList();
        model.addAttribute("studentList", studentList);
        return "studentList";
    }

    @GetMapping("delete/{id}")
    public String deleteStudent(@PathVariable("id") String id){
        studentService.deleteStudent(id);
        return "redirect:/student/studentList";
    }

    

    //CAN IGNORE BELOW THIS

    @GetMapping("/create_Try_URLencodedForm") //Getmapping instead of post mapping
    public String postCreateStudent_Try_URLencodedForm(){
        String response = studentService.createApiStudent_Try_URLencodedForm();
        System.out.println("\n\n\n\n" + response + "\n\n\n\n");
        return "redirect:/student/studentList";
    }

    
}
