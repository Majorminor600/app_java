package com.example.dataprocessor.controller;

import com.example.dataprocessor.model.Student;
import com.example.dataprocessor.service.DataProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class DataController {
    
    @Autowired
    private DataProcessorService dataProcessorService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("students", dataProcessorService.readStudents());
        model.addAttribute("sortedStudents", dataProcessorService.getSortedByGrade());
        model.addAttribute("topStudents", dataProcessorService.getTopStudents());
        model.addAttribute("averageGrade", dataProcessorService.getAverageGrade());
        return "index";
    }

    @PostMapping("/add")
    public String addStudent(@ModelAttribute Student student) {
        dataProcessorService.saveStudent(student);
        return "redirect:/";
    }
}