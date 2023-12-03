package com.webdacoder.VaccinationCenter.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.webdacoder.VaccinationCenter.Entity.VaccinationCentre;
import com.webdacoder.VaccinationCenter.model.CitizenEntity;
import com.webdacoder.VaccinationCenter.model.ReponseModel;
import com.webdacoder.VaccinationCenter.repo.VaccinationCenterRepo;

@RestController
@RequestMapping("/vaccinationcenter")
public class VaccinationCenterController {

  @Autowired
  private VaccinationCenterRepo repo;
  
  @Autowired
  private RestTemplate restTemplate;
	
	@PostMapping("/add")
	public ResponseEntity<VaccinationCentre> addCitizen(@RequestBody VaccinationCentre requst){
		VaccinationCentre vcenter=  repo.save(requst);
		return new ResponseEntity<>(vcenter,HttpStatus.OK);
	}
	
	@GetMapping("/id/{id}")
	@HystrixCommand(fallbackMethod = "handleCitizenDownTime")
	public ResponseEntity<ReponseModel> getCitizensById(@PathVariable Integer id) {
		ReponseModel response = new ReponseModel();
		Optional<VaccinationCentre> vaccinationCentre = repo.findById(id);
		if(vaccinationCentre.isPresent()) {
			response.setCentre(vaccinationCentre.get());
			@SuppressWarnings("unchecked")
			List<CitizenEntity> citizenList = restTemplate
			.getForObject("http://CITIZEN-SERVICE/citizen/id/" + id, List.class);
			response.setCitizens(citizenList);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);

	}
	
	public ResponseEntity<ReponseModel> handleCitizenDownTime(@PathVariable Integer id) {
		ReponseModel response = new ReponseModel();
		Optional<VaccinationCentre> vaccinationCentre = repo.findById(id);
		if(vaccinationCentre.isPresent()) {
			response.setCentre(vaccinationCentre.get());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
	
}
