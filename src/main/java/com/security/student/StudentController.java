package com.security.student;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/std")
public class StudentController {
    private static final List<Student> STUDENT = Arrays.asList(
            new Student(1, "Syed Mohiuddin"),
            new Student(2, "Mohd Obaid Ahmed"),
            new Student(3, "Mohd Abdul Hakeem"),
            new Student(4, "Mohammad Ahmed")
    );

    @GetMapping("{studentId}")
    public Student getStudent(@PathVariable("studentId") Integer studentId) {
        return STUDENT.stream()
                .filter(student -> studentId.equals(student.getStudentId()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Student " + studentId + " does not exists"));
    }
}
