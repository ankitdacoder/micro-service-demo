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
import com.webdacoder.VaccinationCenter.feignClient.Citizen_Feign;
import com.webdacoder.VaccinationCenter.model.CitizenEntity;
import com.webdacoder.VaccinationCenter.model.ReponseModel;
import com.webdacoder.VaccinationCenter.repo.VaccinationCenterRepo;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/vaccinationcenter")
public class VaccinationCenterController {

  @Autowired
  private VaccinationCenterRepo repo;
  
  @Autowired
  private RestTemplate restTemplate;
  
  @Autowired
  private Citizen_Feign citizenClient;
	
	@PostMapping("/add")
	public ResponseEntity<VaccinationCentre> addCitizen(@RequestBody VaccinationCentre requst){
		VaccinationCentre vcenter=  repo.save(requst);
		return new ResponseEntity<>(vcenter,HttpStatus.OK);
	}
	
	@GetMapping("/id/{id}")
	//@HystrixCommand(fallbackMethod = "handleCitizenDownTime")
	@CircuitBreaker(name="circuit-breaker-for-citizen",fallbackMethod = "getCitizenFallback")
	public ResponseEntity<ReponseModel> getCitizensById(@PathVariable Integer id) {
		ReponseModel response = new ReponseModel();
		Optional<VaccinationCentre> vaccinationCentre = repo.findById(id);
		if (vaccinationCentre.isPresent()) {
			response.setCentre(vaccinationCentre.get());
			/*
			 * @SuppressWarnings("unchecked") List<CitizenEntity> citizenList =
			 * restTemplate.getForObject("http://CITIZEN-SERVICE/citizen/id/" + id,
			 * List.class);
			 */

			ResponseEntity<List<CitizenEntity>> citizenList = citizenClient.getCitizenListByVaccinationCenterId(id);
			if (citizenList.getStatusCode().equals(HttpStatus.OK)) {
				response.setCitizens(citizenList.getBody());
			}
		}
		return new ResponseEntity<>(response, HttpStatus.OK);

	}
	
	public ResponseEntity<ReponseModel> getCitizenFallback(@PathVariable Integer id) {
		ReponseModel response = new ReponseModel();
		Optional<VaccinationCentre> vaccinationCentre = repo.findById(id);
		if(vaccinationCentre.isPresent()) {
			response.setCentre(vaccinationCentre.get());
		}
		return new ResponseEntity<ReponseModel>(HttpStatus.NO_CONTENT);
	}
	
	
	
}
