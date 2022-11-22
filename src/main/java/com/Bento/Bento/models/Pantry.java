package com.Bento.Bento.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class Pantry {
	
	@Id
	private ObjectId id;
	
	private String name;

}
