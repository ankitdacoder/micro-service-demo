package com.webdacoder.VaccinationCenter.model;

import java.util.List;

import com.webdacoder.VaccinationCenter.Entity.VaccinationCentre;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReponseModel {

	private VaccinationCentre centre;
	private List<CitizenEntity> citizens;
	
}
