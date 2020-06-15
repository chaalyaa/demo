package com.example.demo.service;

import com.example.demo.dto.RequestDto;
import com.example.demo.repository.StudentRepository;
import com.example.demo.tabledb.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Qualifier("StudentService")
public class StudentService implements StudentInterface{

    @Autowired
    private StudentRepository sRepo;

    @Override
    public Boolean registerStudent(RequestDto requestDto) {
        Student data = mapStudentDtotoEntity(requestDto);
        Student registeredStudent = sRepo.save(data);

        if (registeredStudent != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public RequestDto updateStudent(Long id, RequestDto data) {
        Optional<Student> s = sRepo.findById(id);

        if(s.isPresent()){
            Student update = mapStudentDtotoEntity(data);
            update.setId(id);

            Student updatedStudent = sRepo.save(update);
            data = mapStudentEntitytoDto(updatedStudent);
        }

        return data;
    }

    @Override
    public RequestDto getStudent(Long id) {
        RequestDto data = new RequestDto();
        Optional<Student> s = sRepo.findById(id);
        if(s.isPresent()){
            data = mapStudentEntitytoDto(s.get());
        }
        return data;
    }

    @Override
    public Boolean deleteStudent(Long id) {
        Optional<Student> s = sRepo.findById(id);
        if(s.isPresent()){
            sRepo.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<RequestDto> getAll() {
        List<Student> students = sRepo.findAll();
        List<RequestDto> studentOutput = new ArrayList<>();

        for (Student value: students) {
            studentOutput.add(mapStudentEntitytoDto(value));
        }

        return studentOutput;
    }

    private Student mapStudentDtotoEntity(RequestDto requestDto){
        Student student = new Student();
        student.setId(requestDto.getId());
        student.setNim(requestDto.getNim());
        student.setFirst_name(requestDto.getFirst_name());
        student.setSurname(requestDto.getSurname());
        student.setGender(requestDto.getGender());
        student.setAddress(requestDto.getAddress());
        student.setDate_of_birth(requestDto.getDate_of_birth());

        return student;
    }

    private RequestDto mapStudentEntitytoDto(Student student){
        RequestDto request = new RequestDto();
        request.setId(student.getId());
        request.setNim(student.getNim());
        request.setFirst_name(student.getFirst_name());
        request.setSurname(student.getSurname());
        request.setGender(student.getGender());
        request.setAddress(student.getAddress());
        request.setDate_of_birth(student.getDate_of_birth());

        return request;
    }
}
