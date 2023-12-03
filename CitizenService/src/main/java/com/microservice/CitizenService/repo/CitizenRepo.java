package com.microservice.CitizenService.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservice.CitizenService.Entity.CitizenEntity;

@Repository
public interface CitizenRepo extends JpaRepository<CitizenEntity, Integer> {

	public List<CitizenEntity> findByvaccinaionCenterId(Integer id);
}
