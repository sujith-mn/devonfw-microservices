package com.devonfw.application.quarkus.sample.animalmanagement.service.impl.rest;

import javax.inject.Inject;
import javax.inject.Qualifier;
import javax.validation.Valid;
import javax.ws.rs.core.Response;

import com.devonfw.application.quarkus.sample.animalmanagement.logic.api.Animalmanagement;
import com.devonfw.application.quarkus.sample.animalmanagement.logic.api.to.NewAnimalDTO;
import com.devonfw.application.quarkus.sample.animalmanagement.logic.impl.AnimalsApi;

public class AnimalmanagementRestServiceImpl implements AnimalsApi {

	 @Inject
	 private Animalmanagement animalmanagement;

	@Override
	public Response animalsErrorGet() {

		return animalmanagement.animalsErrorGet();
	}

	@Override
	public Response animalsIdFactsGet(String id) {

		return animalmanagement.animalsIdFactsGet(id);
	}

	@Override
	public Response createNewAnimal(@Valid NewAnimalDTO newAnimalDTO) {

		return animalmanagement.createNewAnimal(newAnimalDTO);
	}

	@Override
	public Response deleteAnimalById(String id) {

		return animalmanagement.deleteAnimalById(id);
	}

	@Override
	public Response getAllAnimals(Integer legs, String name, Integer page, Integer size) {

		return animalmanagement.getAllAnimals(legs,name,page,size);
	}

	@Override
	public Response getAnimalById(String id) {

		return animalmanagement.getAnimalById(id);
	}

}
