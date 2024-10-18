package com.example.demo.controller;

import com.example.demo.dao.StudentDAO;
import com.example.demo.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {
    private final StudentDAO studentDao;

    @Autowired
    public StudentController(StudentDAO studentDao) {
        this.studentDao = studentDao;
    }

    @GetMapping
    public String listStudents(Model model) {
        List<Student> students = studentDao.findAll();
        model.addAttribute("students", students);
        return "studentList";
    }

    @GetMapping("/new")
    public String createStudentForm(Model model) {
        return "studentForm";
    }

    @PostMapping
    public String createStudent(@ModelAttribute Student student) {
        studentDao.save(student);
        return "redirect:/students";
    }

    @GetMapping("/edit/{id}")
    public String editStudentForm(@PathVariable int id, Model model) {
        Student student = studentDao.findById(id);
        if (student == null) {
            return "redirect:/students";
        }
        model.addAttribute("student", student);
        return "studentEdit";
    }

    @PostMapping("/{id}")
    public String updateStudent(@PathVariable int id, @ModelAttribute Student student) {
        studentDao.update(id, student);
        return "redirect:/students";
    }

    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable int id) {
        studentDao.delete(id);
        return "redirect:/students";
    }

    @GetMapping("/{id}")
    public String viewStudentDetails(@PathVariable int id, Model model) {
        Student student = studentDao.findById(id);
        model.addAttribute("student", student);
        return "studentDetails";
    }
}
