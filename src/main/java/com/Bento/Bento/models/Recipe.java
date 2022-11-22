package com.Bento.Bento.models;

import java.util.List;

import javax.persistence.Lob;

import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document(collection = "recipe")
@NoArgsConstructor
public class Recipe {
	
	@Id
	private String id;

//	@Indexed(unique = true)
	private String title;

	@Lob
	private byte[] data;
	
	private String name;
	
	private List<String> ingredients;
	
	private List<String> steps;
	
	private String prepTime;
	
	public Recipe(String title, byte[] data, String name, List<String> ingredients, List<String> steps,String prepTime) {
		this.title = title;
		this.data = data;
		this.name = name;
		this.ingredients = ingredients;
		this.steps = steps;
		this.prepTime = prepTime;
	}

}
