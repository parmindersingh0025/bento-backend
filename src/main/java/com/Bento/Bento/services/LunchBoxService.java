package com.Bento.Bento.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Bento.Bento.models.Kid;
import com.Bento.Bento.models.LunchBox;
import com.Bento.Bento.models.Recipe;
import com.Bento.Bento.models.ResponseMessage;
import com.Bento.Bento.models.UserModel;
import com.Bento.Bento.repository.KidRepository;
import com.Bento.Bento.repository.LunchBoxRepository;
import com.Bento.Bento.repository.RecipeRepository;
import com.Bento.Bento.repository.UserRepository;

@Service
public class LunchBoxService {

	@Autowired
	private LunchBoxRepository lunchBoxRepository;

	@Autowired
	private RecipeRepository recipeRepository;

	@Autowired
	private UserRepository userRepository;

	@Value("${recipe.image.path.url}")
	private String storageDirectoryPathUrl;

	@Autowired
	private KidRepository kidRepository;

	public LunchBox addLunchBox(String recipeId, String parentId, List<String> day) {
		// TODO Auto-generated method stub
		HashMap<String, Object> daywise = new HashMap<>();

		for (String string : day) {
			daywise.put(string, true);
		}
		LunchBox lunchBox = new LunchBox();
		Recipe recipe = recipeRepository.findById(recipeId).get();
		daywise.put("recipeName", recipe.getTitle());
		daywise.put("recipeUrl", storageDirectoryPathUrl + recipe.getName());
//		lunchBox.setId(new ObjectId());
		lunchBox.setKids(new ArrayList<>());
		lunchBox.setActive(true);
		lunchBox.setDay(daywise);
		lunchBox.setDaysList(day);
		lunchBox.setRecipeId(recipeId);
		lunchBox.setParentId(parentId);
		lunchBox.setCreatedAt(new Date());
//		Optional<Kid> kidOptional = kidRepository.findByParentId(parentId);
//		if(kidOptional.isPresent()) {
//			lunchBox.setKids(kidOptional.get().getKids());
//		}
		return lunchBoxRepository.save(lunchBox);
	}

	public LunchBox getLunchBox(String parentId) {
		// TODO Auto-generated method stub
		return lunchBoxRepository.findTopByParentIdOrderByCreatedAtDesc(parentId);
	}
	
	public List<LunchBox> getAllLunchBoxes(String parentId) {
		// TODO Auto-generated method stub
		return lunchBoxRepository.findByParentId(parentId);
	}

	public void deleteLunchBox(String lunchBoxId, Boolean active, String day, String kidName) {
		// TODO Auto-generated method stub
//		Optional<LunchBox> lunchBoxOptional = lunchBoxRepository.findByParentIdAndRecipeId(parentId, recipeId);
		Optional<LunchBox> lunchBoxOptional = lunchBoxRepository.findById(lunchBoxId);
		
		if (lunchBoxOptional.isPresent()) {
			LunchBox lunchBox = lunchBoxOptional.get();
			lunchBox.getDay().put(day,false);
//			lunchBox.setActive(false);
			List<HashMap<String, Object>> days = lunchBox.getDays();
			days.forEach(d -> {
				if(d.get("day").toString().equalsIgnoreCase(day) && d.get("kidName").toString().equalsIgnoreCase(kidName)) {
					d.put("flag", false);
				}
//		        for (Map.Entry<String, Object> entry : d.entrySet()) {
//		        		if(entry.getKey().equalsIgnoreCase("day")&&entry.getValue().toString().equalsIgnoreCase(day)) {
//		        			if (entry.getKey()=="flag") {
//								entry.setValue(false);
//							}
//		        		}
//		        }
			});
			lunchBoxRepository.save(lunchBox);
		}
	}

	@SuppressWarnings("null")
	public void assignKidLunchBox(String lunchboxId, String parentId, List<String> kidNames, String day) {
		// TODO Auto-generated method stub
		Optional<LunchBox> lunchBoxOptional = lunchBoxRepository.findById(lunchboxId);
		HashMap<String, Object> recipeAssigned = new HashMap<String, Object>();

		if (lunchBoxOptional.isPresent()) {
			LunchBox lunchBox = lunchBoxOptional.get();
			List<HashMap<String, Object>> days = new ArrayList<HashMap<String,Object>>();

//			if(!lunchBox.getDays().isEmpty()) 
//				days.addAll(lunchBox.getDays());
//			else
//				days = new ArrayList<HashMap<String,Object>>();
			Recipe recipe = recipeRepository.findById(lunchBox.getRecipeId()).get();
			kidNames.forEach( k ->  {
				
				HashMap<String, Object> kidsLunch =new HashMap<>();

				recipeAssigned.put(k, day);
				kidsLunch.put("day", day);
				kidsLunch.put("kidName", k);
				kidsLunch.put("recipeName", recipe.getTitle());
				kidsLunch.put("recipeUrl", storageDirectoryPathUrl + recipe.getName());
				kidsLunch.put("flag", true);
				days.add(kidsLunch);
				});
			lunchBox.setRecipeAssigned(recipeAssigned);
			if(lunchBox.getDays() == null || lunchBox.getDays().isEmpty())
			lunchBox.setDays(days);
			else {
				lunchBox.getDays().addAll(days);
			}
			lunchBoxRepository.save(lunchBox);
		}
	}

	public LunchBox getLunchBoxById(String lunchboxId) {
		// TODO Auto-generated method stub
			return lunchBoxRepository.findById(lunchboxId).get();

	}
}
