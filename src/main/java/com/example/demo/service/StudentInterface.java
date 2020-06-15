package com.example.demo.service;

import com.example.demo.dto.RequestDto;
import java.util.List;

public interface StudentInterface {

    Boolean registerStudent(RequestDto requestDto);
    RequestDto updateStudent(Long id, RequestDto data);
    RequestDto getStudent(Long id);
    Boolean deleteStudent(Long id);
    List<RequestDto> getAll();

}
