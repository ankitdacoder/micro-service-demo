package com.webdacoder.VaccinationCenter.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webdacoder.VaccinationCenter.Entity.VaccinationCentre;

@Repository
public interface VaccinationCenterRepo extends JpaRepository<VaccinationCentre, Integer> {

	
}
