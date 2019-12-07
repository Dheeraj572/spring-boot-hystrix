package com.projects.springboothystrix.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.projects.springboothystrix.util.StudentResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StudentService implements IStudentService{

	@Autowired
	private RestTemplate restTemplate;
	
	ParameterizedTypeReference<List<StudentResponse>> studentParameterizedResponse = new ParameterizedTypeReference<List<StudentResponse>>() {
	};
	
	@HystrixCommand(fallbackMethod="getStudentsEmptyList")
	public List<StudentResponse> getStudents() {
		
		log.info("Connecting to localhost:5000");
		
		ResponseEntity<List<StudentResponse>> response = restTemplate.exchange("http://localhost:5000/students", HttpMethod.GET
				, null, studentParameterizedResponse);
		
		List<StudentResponse> studentResponseList = Optional.ofNullable(response).map(mapper -> mapper.getBody()).orElse(new ArrayList<>());
		
		log.info("Retrieved student details from  localhost:5000");
		
		return studentResponseList;
	}
	
	public List<StudentResponse> getStudentsEmptyList(){
		
		return new ArrayList<>();
	}

}
