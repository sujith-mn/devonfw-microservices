package com.devonfw.application.quarkus.sample.animalmanagement.logic.api;

import java.util.List;
import java.util.Random;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.eclipse.microprofile.opentracing.Traced;

import com.devonfw.application.quarkus.sample.animalmanagement.common.api.AnimalMapper;
import com.devonfw.application.quarkus.sample.animalmanagement.common.api.AnimalSearchCriteria;
import com.devonfw.application.quarkus.sample.animalmanagement.dataaccess.api.Animal;
import com.devonfw.application.quarkus.sample.animalmanagement.logic.api.to.NewAnimalDTO;
import com.devonfw.application.quarkus.sample.animalmanagement.logic.impl.AnimalDAO;

import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
@Traced
public class Animalmanagement {

	@Inject
	AnimalDAO animalDAO;

	@Inject
	AnimalMapper mapper;

	  @Context
	  UriInfo uriInfo;


	public Response animalsErrorGet() {

		throw new NotFoundException("Test not found exception");
	}

	  private List<String> getFactsFromUnreliableSource(String id) {

		    // our source will randomly fail
		    boolean willFail = new Random().nextBoolean();
		    if (willFail) {
		      log.info("Ooops, fact fetching failed");
		      throw new IllegalStateException("Unreliable source failed");
		    } else {
		      log.info("Cool, fact fetching succeed");
		      return List.of("imagine real data here", "and also this shocking fact");
		    }
		  }

	public Response animalsIdFactsGet(String id) {
		List<String>  list =getFactsFromUnreliableSource(id);
		return Response.ok(list).build();
	}

	public Response createNewAnimal(@Valid NewAnimalDTO dto) {

		Animal created = this.animalDAO.create(this.mapper.create(dto));
		UriBuilder uriBuilder = this.uriInfo.getAbsolutePathBuilder();
		uriBuilder.path(created.getId());
		return Response.created(uriBuilder.build()).build();
	}

	public Response deleteAnimalById(String id) {

		   Animal animal = this.animalDAO.findById(id);
		    if (animal != null) {
		      this.animalDAO.delete(animal);
		      return Response.ok(animal).build();
		    } else {
		      return Response.status(Response.Status.NOT_FOUND).build();
		    }
	}

	//@Beanparam
	public Response getAllAnimals(Integer legs, String name, Integer page, Integer size) {

		AnimalSearchCriteria dto = new AnimalSearchCriteria();
		dto.setNumberOfLegs(legs);
		dto.setName(name);
		dto.setPageNumber(page);
		dto.setPageSize(size);

		//PageResult<Animal> animals = this.animalDAO.searchByCriteria(this.mapper.map(dto));
		//return Response.ok(this.mapper.map(animals)).build();
		return Response.ok().build();
	}

	public Response getAnimalById(String id) {
	    Animal animal = this.animalDAO.findById(id);
	    if (animal != null) {
	      return Response.ok(animal).build();
	    } else {
	      return Response.status(Response.Status.NOT_FOUND).build();
	    }
	}

}
