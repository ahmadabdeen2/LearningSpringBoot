package com.ahmad.restie.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.convert.ReadingConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ahmad.restie.entity.Student;
import com.ahmad.restie.exceptions.StudentErrorResponse;
import com.ahmad.restie.exceptions.StudentNotFoundException;

import jakarta.annotation.PostConstruct;


@RestController
@RequestMapping("/api")
public class StudentRestController {

    List<Student> students = new ArrayList<>();

    @PostConstruct
    public void loadData() {
        students.add(new Student(1,"Ahmad", "Abdeen"));
        students.add(new Student(2,"Mohammad", "Medhi"));
        students.add(new Student(3,"Gadu", "Abdeen"));
        for (Student tempStudent: students){
            System.out.println(tempStudent);
        }

    }
  

    @GetMapping("/students")
    public  List<Student> getStudents() {


        return students;

    }

    @GetMapping("/students/{studentId}")
    public Student getStudent(@PathVariable int studentId) {
        if (studentId >= students.size() || studentId < 0) {
            throw new StudentNotFoundException("Student id not found - " + studentId);
        }

        // if (studentId >= students.size() || studentId < 0) {
        //     throw new StudentNotFoundException("Student id not found - " + studentId);
        // }

        return students.get(studentId-1);
    }

    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handleException(StudentNotFoundException ecx){

        StudentErrorResponse studentErrorResponse = new StudentErrorResponse();
        studentErrorResponse.setStatus(404);
        studentErrorResponse.setMessage(ecx.getMessage());
        studentErrorResponse.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(studentErrorResponse, org.springframework.http.HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handleGeneralException(Exception ecx){

        StudentErrorResponse studentErrorResponse = new StudentErrorResponse();
        studentErrorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        studentErrorResponse.setMessage(ecx.getMessage());
        studentErrorResponse.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(studentErrorResponse, HttpStatus.BAD_REQUEST);
    }
 
    


    
}
