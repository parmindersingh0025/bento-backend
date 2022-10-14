package com.Bento.Bento.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.Bento.Bento.models.Ingredients;
import com.Bento.Bento.repository.IngredientsRepository;

@Service
public class IngredientService {

  @Autowired
  private IngredientsRepository ingredientsRepository;
  
  @Autowired
	private MongoTemplate mongoTemplate;

    public Ingredients getIngredientById(String id) {
	    return ingredientsRepository.findById(id).get();
	  }
  
  public Stream<Ingredients> getAllIngredients() {
    return ingredientsRepository.findAll().stream();
  }

public List<Ingredients> searchIngredient(List<String> title) {
	// TODO Auto-generated method stub
	Query query = new Query();
	List<Criteria> criteriaList = new ArrayList<>();
	for (String item : title) {
	  criteriaList.add(Criteria.where("title").is(item));
	}
//	Query.query(new Criteria.orOperator(title.toArray(new Criteria[title.size()])));

	if (!StringUtils.isEmpty(title) && title != null)
		query.addCriteria(Criteria.where("title").in(title));
	List<Ingredients> result = mongoTemplate.find(query, Ingredients.class);

	return result;
}

public List<Ingredients> searchIngredientWithTitle(String item) {
	// TODO Auto-generated method stub
	Query query = new Query();
	if (!StringUtils.isEmpty(item))
		query.addCriteria(Criteria.where("title").regex(item, "i"));
	List<Ingredients> result = mongoTemplate.find(query, Ingredients.class);

	return result;
}

public Ingredients addIngredients(String title, MultipartFile file) throws IOException {
    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
    Ingredients ingredients = new Ingredients(title,fileName, file.getContentType(), file.getBytes());
    return ingredientsRepository.save(ingredients);
  }

public Ingredients getIngredient(String title) throws Exception {
	  Optional<Ingredients> ingredients = ingredientsRepository.findByTitle(title);
	  if(!ingredients.isPresent()){
		  throw new Exception("No Ingredients Found");
	  }
  return ingredientsRepository.findByTitle(title).get();
}

}