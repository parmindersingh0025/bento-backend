package com.Bento.Bento.controller;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.Bento.Bento.models.Ingredients;
import com.Bento.Bento.models.Recipe;
import com.Bento.Bento.models.ResponseFile;
import com.Bento.Bento.models.ResponseMessage;
import com.Bento.Bento.models.ResponseRecipe;
import com.Bento.Bento.services.RecipeService;

@Controller
@RequestMapping("/recipe")
public class RecipeController {
	
	@Autowired
	private RecipeService recipeService;
	
	@PostMapping("/add")
	  public ResponseEntity<?> addIngredients(@RequestParam String title,@RequestParam String prepTime,@RequestParam List<String> ingredients,@RequestParam List<String> steps, @RequestParam String name, @RequestPart("image") MultipartFile file) {
	    String message = "";
		try {
	    	Recipe recipe = new Recipe(title, file.getBytes(), name, ingredients, steps, prepTime);
	      return ResponseEntity.status(HttpStatus.OK).body(recipeService.addRecipe(recipe,file));
	    } catch (Exception e) {
	    	message = "Could not upload the file: " + file.getOriginalFilename() + "!";

	    	if (e instanceof org.springframework.dao.DuplicateKeyException) {
				message = "Could not upload the file: " + "Ingredient Already Present in the list";
			}
	      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Error in Save " + message);
	    }
	  }
	
	@GetMapping("/getbyid/{id}")
	  public ResponseEntity<byte[]> getRecipeById(@PathVariable String id) {
	    Recipe fileDB = recipeService.getRecipeById(id);

	    return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
	        .body(fileDB.getData());
	  }
	  
	  @GetMapping("/get/{title}")
	  public ResponseEntity<byte[]> getRecipeByName(@PathVariable String title) {
	    Recipe fileDB;
		try {
			fileDB = recipeService.getRecipe(title);
			return ResponseEntity.ok()
			        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
			        .body(fileDB.getData());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.notFound().build();
	  }
	
	@GetMapping("/get-all")
	  public ResponseEntity<List<ResponseRecipe>> getAllRecipes() {
	    List<ResponseRecipe> files = recipeService.getAllRecipes().map(dbFile -> {
	      String fileDownloadUri = ServletUriComponentsBuilder
	          .fromCurrentContextPath()
	          .path("/recipe/getbyid/")
	          .path(dbFile.getId())
	          .toUriString();

	      return new ResponseRecipe(
	    	  dbFile.getId(),
	    	  dbFile.getTitle(),
	    	  dbFile.getPrepTime(),
	          dbFile.getName(),
	          fileDownloadUri,
	          dbFile.getIngredients(),
	          dbFile.getSteps());
	    }).collect(Collectors.toList());

	    return ResponseEntity.status(HttpStatus.OK).body(files);
	  }
	
	@GetMapping("/getRecipeDetailsByTitle/{title}")
	  public ResponseEntity<ResponseRecipe> searchByIngredient(@PathVariable String title) {
		Recipe fileDB = recipeService.getRecipeDetailsByTitle(title);
//				.map(dbFile -> {
		      String fileDownloadUri = ServletUriComponentsBuilder
			          .fromCurrentContextPath()
			          .path("/recipe/getbyid/")
			          .path(fileDB.getId())
			          .toUriString();
//
			       
//			    }).collect(Collectors.toList());

	    return ResponseEntity.status(HttpStatus.OK).body(new ResponseRecipe(
	    		   fileDB.getId(),
	    		   fileDB.getTitle(),
	    		   fileDB.getPrepTime(),
	    		   fileDB.getName(),
	          fileDownloadUri,
	          fileDB.getIngredients(),
	          fileDB.getSteps()));
	  }
	
	@GetMapping("/search-by-ingredient")
	  public ResponseEntity<HashMap<String, List<ResponseRecipe>>> searchByIngredient(@RequestParam(required = false) List<String> ingredients) {
		HashMap<String, List<ResponseRecipe>> fileDB = recipeService.searchIngredient(ingredients);
	    return ResponseEntity.status(HttpStatus.OK).body(fileDB);
	  }

}
