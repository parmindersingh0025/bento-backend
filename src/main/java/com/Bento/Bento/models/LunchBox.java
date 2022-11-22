package com.Bento.Bento.models;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Document(collection = "lunchBox")
public class LunchBox {
	
	@Id
	private String id;

	private Boolean active;
	
	private String recipeId;
	
	private String parentId;
	
	private List<String> daysList; 
	
	private List<HashMap<String, Object>> days;

	private HashMap<String, Object> day;
	
	private List<HashMap<String, Object>> kids;
	
	private HashMap<String, Object> recipeAssigned;
	
	private Date createdAt;

}
