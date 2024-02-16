package com.webdacoder.VaccinationCenter.feignClient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.webdacoder.VaccinationCenter.model.CitizenEntity;


@FeignClient(name="CITIZEN-SERVICE",fallback = FeignClient_FallBack.class)
public interface Citizen_Feign {

	@GetMapping("/citizen/id/{id}")
	ResponseEntity<List<CitizenEntity>> getCitizenListByVaccinationCenterId(@PathVariable Integer id);	
}
