package com.Bento.Bento.models;

public class ResponseFile {
  private String id;
  private String name;
  private String url;
  private String type;
  private String title;
  private long size;

  public ResponseFile(String id, String title, String name, String url, String type, long size) {
    this.setId(id);
	this.name = name;
    this.url = url;
    this.type = type;
    this.size = size;
    this.title = title;
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

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public long getSize() {
    return size;
  }

  public void setSize(long size) {
    this.size = size;
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