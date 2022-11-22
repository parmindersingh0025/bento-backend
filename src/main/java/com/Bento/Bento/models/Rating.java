package com.Bento.Bento.models;

import java.time.LocalDate;
import java.util.Date;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "rating")
public class Rating {

	@Id
	private String id;
	
	private String rating;
		
	private String parentId;
	
	private String kidName;
	
	private String recipeId;
	
	private String recipeName;
	
	private LocalDate createdAt;
}
