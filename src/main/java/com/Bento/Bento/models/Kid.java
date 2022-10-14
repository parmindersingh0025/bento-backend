package com.Bento.Bento.models;

import java.util.HashMap;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class Kid {
	
	@org.springframework.data.annotation.Id
	private String id;

//	private String name;
	
	private String parentId;
	
//	private String age;

	private List<HashMap<String, Object>> kids;

}
