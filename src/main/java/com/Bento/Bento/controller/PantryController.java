package com.Bento.Bento.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Bento.Bento.services.PantryService;

import io.jsonwebtoken.lang.Collections;

@Controller
@RequestMapping("/pantry")
//@CrossOrigin("http://localhost:8081")
public class PantryController {

	@Autowired
	private PantryService ingredientService;
	
	List<String> items = new ArrayList<>(Arrays.asList("Spagetti","Garlic Bread","Pickles"));


	@PostMapping("/add-item/{item}")
	public ResponseEntity<List<String>> addItem(@PathVariable List<String> item) {
		if(!items.contains(item))
		items.addAll(item);
		return ResponseEntity.status(HttpStatus.OK).body(items);
	}
	
	@GetMapping("/get-items")
	public ResponseEntity<List<String>> getItems() {
		return ResponseEntity.status(HttpStatus.OK).body(items);
	}

	@GetMapping("/delete-item/{item}")
	public ResponseEntity<List<String>> deleteItems(@PathVariable List<String> item) {
		items.removeAll(item);
		return ResponseEntity.status(HttpStatus.OK).body(items);
	}
}