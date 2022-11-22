package com.Bento.Bento.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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

	@Value("${ingredient.image.path}")
	private String storageDirectoryPath;
	
	@Value("${ingredient.image.path.url}")
	private String storageDirectoryPathUrl;
	
	@GetMapping("/get")
	public ResponseEntity<List<ResponseFile>> getAllIngredients() {
		List<ResponseFile> files = new ArrayList<>();
		files = ingredientService.getAllIngredients().map(dbFile -> {
//			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/ingredient/getbyid/")
//					.path(dbFile.getId()).toUriString();

			String destination = storageDirectoryPathUrl + dbFile.getName();// retrieve the image by its name
			System.out.println("destination:-   " + destination);
			 ;
			
				return new ResponseFile(dbFile.getId(), dbFile.getTitle(), dbFile.getName(), destination,
						dbFile.getType(), dbFile.getData().length);			} 
		).collect(Collectors.toList());
		return ResponseEntity.status(HttpStatus.OK).body(files);
	}

	@GetMapping("/getbyid/{id}")
	public ResponseEntity<byte[]> getIngredientById(@PathVariable String id) {
		Ingredients fileDB = ingredientService.getIngredientById(id);

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
				.body(fileDB.getData());
	}

	@GetMapping("/search")
	public ResponseEntity<List<ResponseFile>> searchIngredient(@RequestParam(required = false) String item) {
		List<Ingredients> fileDB = ingredientService.searchIngredientWithTitle(item);
		List<ResponseFile> files = fileDB.stream().map(dbFile -> {
//			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/ingredient/getbyid/")
//					.path(dbFile.getId()).toUriString();
			String destination = storageDirectoryPathUrl + dbFile.getName();// retrieve the image by its name

			return new ResponseFile(dbFile.getId(), dbFile.getTitle(), dbFile.getName(), destination,
					dbFile.getType(), dbFile.getData().length);
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
	public ResponseEntity<ResponseMessage> addIngredients(@RequestParam("title") String title,
			@RequestParam("image") MultipartFile file) {
		String message = "";
		try {
			ingredientService.addIngredients(title, file);
			message = "Uploaded the file successfully: " + file.getOriginalFilename();
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
		} catch (Exception e) {
			message = "Could not upload the file: " + file.getOriginalFilename() + "!";

			if (e instanceof org.springframework.dao.DuplicateKeyException) {
				message = "Could not upload the file: " + "Ingredient Already Present in the list";
			}
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
		}
	}

//  @GetMapping(value = "/{name}", produces = MediaType.ALL_VALUE)
//  public ResponseEntity<InputStreamResource> getImage() throws IOException {
//
//	  ClassPathResource imgFile = new ClassPathResource("image/sid.jpg");
//
//      return ResponseEntity
//              .ok()
//              .contentType(MediaType.IMAGE_JPEG)
//              .body(new InputStreamResource(imgFile.getInputStream()));
//  }

//  @GetMapping("/get-image-dynamic-type")
//  @ResponseBody
//  public ResponseEntity<InputStreamResource> getImageDynamicType(@RequestParam("jpg") boolean jpg) {
//      MediaType contentType = jpg ? MediaType.IMAGE_JPEG : MediaType.IMAGE_PNG;
//      InputStream in = jpg ?
//        getClass().getResourceAsStream("/com/baeldung/produceimage/image.jpg") :
//        getClass().getResourceAsStream("/com/baeldung/produceimage/image.png");
//      return ResponseEntity.ok()
//        .contentType(contentType)
//        .body(new InputStreamResource(in));
//  }
//  
	@GetMapping(value = "getImage/{imageName}", produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE,
			MediaType.IMAGE_PNG_VALUE, MediaType.ALL_VALUE })
	public @ResponseBody byte[] getImageWithMediaType(@PathVariable(name = "imageName") String fileName)
			throws IOException {
		return ingredientService.getImageWithMediaType(fileName);
	}
}