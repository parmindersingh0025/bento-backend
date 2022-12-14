package com.Bento.Bento.models;
//import javax.persistence.Id;
import javax.persistence.Lob;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

//@Entity
//@Table(name = "files")
@Document
public class Ingredients {
	
//  @Id
//  @GeneratedValue(generator = "uuid")
//  @GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.springframework.data.annotation.Id
  private String id;

  private String name;

  private String type;

  @Lob
  private byte[] data;
  
  @Indexed(unique = true)
  private String title;


  public Ingredients() {
  }

  public Ingredients(String title, String name, String type, byte[] data) {
    this.name = name;
    this.type = type;
    this.data = data;
    this.title = title;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public byte[] getData() {
    return data;
  }

  public void setData(byte[] data) {
    this.data = data;
  }

public String getTitle() {
	return title;
}

public void setTitle(String title) {
	this.title = title;
}

}