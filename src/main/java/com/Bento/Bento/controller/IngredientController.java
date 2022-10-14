package com.Bento.Bento.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.Bento.Bento.models.Ingredients;
import com.Bento.Bento.models.ResponseFile;
import com.Bento.Bento.models.ResponseMessage;
import com.Bento.Bento.services.IngredientService;

@Controller
@RequestMapping("/ingredient")
//@CrossOrigin("http://localhost:8081")
public class IngredientController {

  @Autowired
  private IngredientService ingredientService;


  @GetMapping("/get")
  public ResponseEntity<List<ResponseFile>> getAllIngredients() {
    List<ResponseFile> files = ingredientService.getAllIngredients().map(dbFile -> {
      String fileDownloadUri = ServletUriComponentsBuilder
          .fromCurrentContextPath()
          .path("/ingredient/getbyid/")
          .path(dbFile.getId())
          .toUriString();

      return new ResponseFile(
    	  dbFile.getId(),
    	  dbFile.getTitle(),
          dbFile.getName(),
          fileDownloadUri,
          dbFile.getType(),
          dbFile.getData().length);
    }).collect(Collectors.toList());

    return ResponseEntity.status(HttpStatus.OK).body(files);
  }

  @GetMapping("/getbyid/{id}")
  public ResponseEntity<byte[]> getIngredientById(@PathVariable String id) {
    Ingredients fileDB = ingredientService.getIngredientById(id);

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
        .body(fileDB.getData());
  }
  
  @GetMapping("/select-search")
  public ResponseEntity<List<ResponseFile>> selectedSearchIngredient(@RequestParam(required = false) List<String> ingredient) {
    List<Ingredients> fileDB = ingredientService.searchIngredient(ingredient);
    List<ResponseFile> files = fileDB.stream().map(dbFile -> {
        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/files/")
                .path(dbFile.getId())
                .toUriString();

            return new ResponseFile(
          	  dbFile.getId(),
          	  dbFile.getTitle(),
                dbFile.getName(),
                fileDownloadUri,
                dbFile.getType(),
                dbFile.getData().length);
          }).collect(Collectors.toList());
    return ResponseEntity.status(HttpStatus.OK).body(files);
  }
  
  @GetMapping("/search")
  public ResponseEntity<List<ResponseFile>> searchIngredient(@RequestParam(required = false) String item) {
    List<Ingredients> fileDB = ingredientService.searchIngredientWithTitle(item);
    List<ResponseFile> files = fileDB.stream().map(dbFile -> {
        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/files/")
                .path(dbFile.getId())
                .toUriString();

            return new ResponseFile(
          	  dbFile.getId(),
          	  dbFile.getTitle(),
                dbFile.getName(),
                fileDownloadUri,
                dbFile.getType(),
                dbFile.getData().length);
          }).collect(Collectors.toList());
    return ResponseEntity.status(HttpStatus.OK).body(files);
  }
  
  @GetMapping("/get/{title}")
  public ResponseEntity<byte[]> getIngredientByName(@PathVariable String title) {
    Ingredients fileDB;
	try {
		fileDB = ingredientService.getIngredient(title);
		return ResponseEntity.ok()
		        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
		        .body(fileDB.getData());
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return ResponseEntity.notFound().build();
  }
  
  @PostMapping("/add")
  public ResponseEntity<ResponseMessage> addIngredients(@RequestParam("title") String title, @RequestParam("image") MultipartFile file) {
    String message = "";
    try {
    	ingredientService.addIngredients(title,file);
      message = "Uploaded the file successfully: " + file.getOriginalFilename();
      return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
    } catch (Exception e) {
      message = "Could not upload the file: " + file.getOriginalFilename() + "!";
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
    }
  }
}