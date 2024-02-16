package com.webdacoder.VaccinationCenter.feignClient;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.webdacoder.VaccinationCenter.model.CitizenEntity;

@Component
public class FeignClient_FallBack implements Citizen_Feign {

	@Override
	public ResponseEntity<List<CitizenEntity>> getCitizenListByVaccinationCenterId(Integer id) {

		CitizenEntity cObj1 = new CitizenEntity();
		cObj1.setId(10000);
		cObj1.setName("Fake Name");
		cObj1.setVaccinaionCenterId(50002);

		List cList = new ArrayList<>();
		cList.add(cObj1);
		return new ResponseEntity<List<CitizenEntity>>(cList, HttpStatus.OK);

	}

}
