package com.Bento.Bento.models;

import java.util.List;

import org.bson.types.Binary;

import lombok.Data;

@Data
public class ResponseRecipe {
	private String id;
	private String name;
	private String url;
	private String title;
	private Binary image;
	private List<String> ingredients;
	private List<String> steps;
	private String prepTime;

	public ResponseRecipe(String id, String title, String prepTime, String name, String url,
			List<String> ingredients, List<String> steps) {
		this.setId(id);
		this.name = name;
		this.url = url;
		this.ingredients = ingredients;
		this.title = title;
		this.steps = steps;
		this.prepTime = prepTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}