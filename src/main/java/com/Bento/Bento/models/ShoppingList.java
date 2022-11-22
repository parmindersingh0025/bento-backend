package com.Bento.Bento.models;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "shoppingList")
public class ShoppingList {
	
	private String name;

}
