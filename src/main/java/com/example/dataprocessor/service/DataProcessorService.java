package com.example.dataprocessor.service;

import com.example.dataprocessor.model.Student;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DataProcessorService {
    private static final String DATA_FILE = "students.txt";

    public void saveStudent(Student student) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DATA_FILE, true))) {
            writer.println(String.format("%s,%d,%.2f", student.getName(), student.getAge(), student.getGrade()));
        } catch (IOException e) {
            throw new RuntimeException("Error saving data", e);
        }
    }

    public List<Student> readStudents() {
        List<Student> students = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                students.add(new Student(
                    parts[0],
                    Integer.parseInt(parts[1]),
                    Double.parseDouble(parts[2])
                ));
            }
        } catch (IOException e) {
            // Return empty list if file doesn't exist yet
            return students;
        }
        return students;
    }

    public List<Student> getSortedByGrade() {
        return readStudents().stream()
            .sorted(Comparator.comparing(Student::getGrade).reversed())
            .collect(Collectors.toList());
    }

    public List<Student> getTopStudents() {
        return readStudents().stream()
            .filter(s -> s.getGrade() >= 4.0)
            .collect(Collectors.toList());
    }

    public double getAverageGrade() {
        List<Student> students = readStudents();
        if (students.isEmpty()) return 0.0;
        return students.stream()
            .mapToDouble(Student::getGrade)
            .average()
            .orElse(0.0);
    }
}