package com.microservice.CitizenService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.CitizenService.Entity.CitizenEntity;
import com.microservice.CitizenService.repo.CitizenRepo;

@RestController
@RequestMapping("/citizen")
public class CitizenController {

	@Autowired
	private CitizenRepo citizenRepo;

	@GetMapping("/test")
	public ResponseEntity<String> test() {

		return new ResponseEntity<>("Hello", HttpStatus.OK);
	}

	@GetMapping("/id/{id}")
	public ResponseEntity<List<CitizenEntity>> getCitizensById(@PathVariable Integer id) {
		List<CitizenEntity> listOfCitzens = citizenRepo.findByvaccinaionCenterId(id);
		return new ResponseEntity<>(listOfCitzens, HttpStatus.OK);

	}
	
	@PostMapping("/add")
	public ResponseEntity<CitizenEntity> addCitizen(@RequestBody CitizenEntity requst){
		CitizenEntity citizen=  citizenRepo.save(requst);
		return new ResponseEntity<>(citizen,HttpStatus.OK);
	}
	
	

}
