package com.Bento.Bento.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.Bento.Bento.models.Ingredients;
import com.Bento.Bento.models.Recipe;
import com.Bento.Bento.models.ResponseRecipe;
import com.Bento.Bento.repository.RecipeRepository;

@Service
public class RecipeService {

	@Autowired
	private RecipeRepository recipeRepository;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	public Recipe addRecipe(Recipe recipe,MultipartFile file) throws IOException {
		// TODO Auto-generated method stub
	    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
	    
	    recipe.setData(file.getBytes());
		return recipeRepository.save(recipe);
	}

	public Stream<Recipe> getAllRecipes() {
		// TODO Auto-generated method stub
	    return recipeRepository.findAll().stream();
	}

	public Recipe getRecipeById(String id) {
		// TODO Auto-generated method stub
	    return recipeRepository.findById(id).get();
	}



	public Recipe getRecipe(String title) throws Exception {
		// TODO Auto-generated method stub
		  Optional<Recipe> recipe = recipeRepository.findByTitle(title);
		  if(!recipe.isPresent()){
			  throw new Exception("No Recipe Found");
		  }
	  return recipeRepository.findByTitle(title).get();
	}

	public Recipe getRecipeDetailsByTitle(String title) {
		// TODO Auto-generated method stub
	    return recipeRepository.findByTitle(title).get();
	}
	
	public HashMap<String, List<ResponseRecipe>> searchIngredient(List<String> ingredients) {
		// TODO Auto-generated method stub
		Query query = new Query();
		HashMap<String, List<ResponseRecipe>> recipeData = new HashMap<>();
		List<ResponseRecipe> matchedResponseRecipe = new ArrayList<>();
		List<Recipe> matchedRecipe = new ArrayList<>();
		if (!StringUtils.isEmpty(ingredients)) {
			query.addCriteria(Criteria.where("ingredients").all(ingredients).size(ingredients.size()));
		matchedRecipe = mongoTemplate.find(query,Recipe.class);
		matchedResponseRecipe = matchedRecipe.stream().map(dbFile -> {
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
		}
		recipeData.put("matchedRecipe", matchedResponseRecipe);
		query = new Query();
//			query.addCriteria(Criteria.where("ingredients").ne(ingredients));
		List<Recipe> unMatchedRecipe = mongoTemplate.find(query,Recipe.class);
		unMatchedRecipe.removeAll(matchedRecipe);
		List<ResponseRecipe> unMatchedResponseRecipe = unMatchedRecipe.stream().map(dbFile -> {
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
		
		recipeData.put("unMatchedRecipe", unMatchedResponseRecipe);
		return recipeData;
//	    return recipeRepository.findById(id).get();
//		return null;
	}

}
