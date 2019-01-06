package com.nouri.api.ui.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nouri.api.ui.model.reponse.UserRest;
import com.nouri.api.ui.model.request.UserDetailRequestModel;


@RestController
@RequestMapping("users")
public class UserController {

	Map<String, UserRest> users;
	
	@GetMapping
	public String getUsers(@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "limit", defaultValue = "50") int limit,
			@RequestParam(value = "sort", required = false) String sort) {
		return "get user func was called with page: " + page + " limit: " + limit + " and sort = " + sort;
	}

	@GetMapping(path = "/{userId}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UserRest> getUser(@PathVariable String userId) {
		
		if (users.containsKey(userId)) {
			return new ResponseEntity<UserRest>(users.get(userId), HttpStatus.OK);
		}

		return new ResponseEntity<UserRest>(HttpStatus.NO_CONTENT);
	}

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<UserRest> createUser(@Valid @RequestBody UserDetailRequestModel userDetail) {
		
		UserRest response = new UserRest();
		response.setFirstName(userDetail.getFirstName());
		response.setLastName(userDetail.getLastName());
		response.setEmail(userDetail.getEmail());
		String userId = UUID.randomUUID().toString();
		response.setUserId(userId);
		
		if (users == null) users = new HashMap<>();
		users.put(userId, response);

		return new ResponseEntity<UserRest>(response, HttpStatus.CREATED);
	}

	@PutMapping()
	public String updateUser() {
		return "update user func was called";
	}

	@DeleteMapping()
	public String deleteUser() {
		return "delete user func was called";
	}
}
