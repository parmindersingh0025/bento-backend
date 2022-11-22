package com.Bento.Bento.models;


import javax.persistence.Lob;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "users")
public class UserModel {
	
	@Id
	private ObjectId id;
	
	private String type;
	
	@Indexed(unique = true)
	private String userName;
	
	private String name;
	
	private String password;
	
	@Lob
	private byte[] data;

	public UserModel() {
	}
	
	private boolean active;
	
	private String roles;

}
