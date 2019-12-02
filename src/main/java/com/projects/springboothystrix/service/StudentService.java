package com.projects.springboothystrix.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.projects.springboothystrix.util.StudentResponse;

@Service
public class StudentService implements IStudentService{

	@Autowired
	private RestTemplate restTemplate;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StudentService.class);
	
	ParameterizedTypeReference<List<StudentResponse>> studentParameterizedResponse = new ParameterizedTypeReference<List<StudentResponse>>() {
	};
	
	@HystrixCommand(fallbackMethod="getStudentsEmptyList")
	public List<StudentResponse> getStudents() {
		
		LOGGER.info("Connecting to localhost:5000");
		
		ResponseEntity<List<StudentResponse>> response = restTemplate.exchange("http://localhost:5000/students", HttpMethod.GET
				, null, studentParameterizedResponse);
		
		List<StudentResponse> studentResponseList = Optional.ofNullable(response).map(mapper -> mapper.getBody()).orElse(new ArrayList<>());
		
		LOGGER.info("Retrieved student details from  localhost:5000");
		
		return studentResponseList;
	}
	
	public List<StudentResponse> getStudentsEmptyList(){
		
		return new ArrayList<>();
	}

}
