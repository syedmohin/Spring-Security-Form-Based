package com.security.student;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("manage/api/std")
public class StudentManagementController {
    private static final List<Student> STUDENT = Arrays.asList(
            new Student(1, "Syed Mohiuddin"),
            new Student(2, "Mohd Obaid Ahmed"),
            new Student(3, "Mohd Abdul Hakeem"),
            new Student(4, "Mohammad Ahmed")
    );

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MIDDLE')")
    public List<Student> getAllStudent() {
        return STUDENT;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('student:write')")
    public void registerNewStudent(@RequestBody Student student) {
        System.out.println("Post Method to insert student record " + student);
    }

    @DeleteMapping("{studentId}")
    @PreAuthorize("hasAuthority('student:write')")
    public void deleteStudent(@PathVariable("studentId") Integer studentId) {
        System.out.println("Delete Method to delete student record id is " + studentId);
    }

    @PutMapping("{studentId}")
    @PreAuthorize("hasAuthority('student:write')")
    public void updateStudent(@PathVariable("studentId") Integer studentId, @RequestBody Student student) {
        System.out.printf("Update method to update a record  and id is %d and new data is %s\n", studentId, student);
    }


}
