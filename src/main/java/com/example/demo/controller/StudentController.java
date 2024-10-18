package com.example.demo.controller;

import com.example.demo.model.Student;
import com.example.demo.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/students")
public class StudentController {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping
    public String listStudents(Model model) {
        List<Student> students = studentRepository.findAll();
        model.addAttribute("students", students);
        return "studentList";
    }

    @GetMapping("/new")
    public String createStudentForm(Model model) {
        model.addAttribute("student", new Student());
        return "studentForm";
    }

    @GetMapping("/search")
    public String searchStudents(@RequestParam("keyword") String keyword, Model model) {
        List<Student> students = studentRepository.findByNameContaining(keyword);
        model.addAttribute("students", students);
        return "studentList";
    }

    @PostMapping
    public String createStudent(@ModelAttribute Student student) {
        studentRepository.save(student);
        return "redirect:/students";
    }

    @GetMapping("/edit/{id}")
    public String editStudentForm(@PathVariable int id, Model model) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            return "redirect:/students";
        }
        model.addAttribute("student", student.get());
        return "studentEdit";
    }

    @PostMapping("/{id}")
    public String updateStudent(@PathVariable int id, @ModelAttribute Student student) {
        if (!studentRepository.existsById(id)) {
            return "redirect:/students";
        }
        student.setId(id);
        studentRepository.save(student);
        return "redirect:/students";
    }

    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable int id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
        }
        return "redirect:/students";
    }

    @GetMapping("/{id}")
    public String viewStudentDetails(@PathVariable int id, Model model) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            model.addAttribute("student", student.get());
        }
        return "studentDetails";
    }
}
