package com.example.demo.controller;

import com.example.demo.dto.RequestDto;
import com.example.demo.dto.ResponseDto;
import com.example.demo.service.StudentInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class StudentController {

    @Autowired
    @Qualifier("StudentService")
    private StudentInterface sService;

    @GetMapping("/all_students")
    public ResponseEntity<ResponseDto<List<RequestDto>>> getAllStudent(){
        List<RequestDto> listStudents = sService.getAll();
        ResponseDto<List<RequestDto>> responseDto = new ResponseDto<>();
        responseDto.setSuccess(true);

        responseDto.setMessage("All Students");
        responseDto.setResult(listStudents);

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ResponseDto<RequestDto>> getStudent(@PathVariable("id") Long id){
        RequestDto dataStudent = sService.getStudent(id);
        ResponseDto<RequestDto> responseDto = new ResponseDto<>();
        responseDto.setSuccess(true);

        responseDto.setMessage("id: "+ id);
        responseDto.setResult(dataStudent);

        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/input")
    public ResponseEntity<ResponseDto<Object>> inputStudent(@RequestBody RequestDto bodyInput){
        ResponseDto<Object> response = new ResponseDto<>();
        Boolean status = sService.registerStudent(bodyInput);
        response.setSuccess(status);
        if(status){
            response.setMessage("Success");
        } else {
            response.setMessage("Failed");
        }

        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseDto<Object>> updateStudent(@PathVariable("id") Long id, @RequestBody RequestDto bodyInput){
        ResponseDto<Object> response = new ResponseDto<>();
        RequestDto checkStudent = sService.getStudent(id);
        if(checkStudent == null){
            return ResponseEntity.notFound().build();
        } else {
            checkStudent = sService.updateStudent(id, bodyInput);
            response.setSuccess(true);

            response.setMessage("Updated Data");
            response.setResult(checkStudent);
            return ResponseEntity.ok(response);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDto<Object>> deleteStudent(@PathVariable("id") Long id){
        ResponseDto<Object> responseDto = new ResponseDto<>();
        Boolean status = sService.deleteStudent(id);

        if(status){
            responseDto.setSuccess(true);
            responseDto.setMessage("Data has been deleted");
        } else {
            responseDto.setSuccess(false);
            responseDto.setMessage("No data");
        }

        return ResponseEntity.ok(responseDto);
    }

}
