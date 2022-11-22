package com.Bento.Bento.controller;
//package com.Bento.Bento.controller;
//
//import java.util.Base64;
//import java.util.Map;
//
//import org.bson.BsonBinarySubType;
//import org.bson.types.Binary;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.ui.Model;
//import org.springframework.util.ObjectUtils;
//import org.springframework.util.StringUtils;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.Bento.Bento.models.AuthenticationRequest;
//import com.Bento.Bento.models.AuthenticationResponse;
//import com.Bento.Bento.models.Ingredients;
//import com.Bento.Bento.models.Response;
//import com.Bento.Bento.models.UserModel;
//import com.Bento.Bento.repository.UserRepository;
//import com.Bento.Bento.services.IngredientsService;
//import com.Bento.Bento.services.MyUserDetailsService;
//import com.Bento.Bento.services.UserService;
//import com.Bento.Bento.utils.JwtUtil;
//
//@RestController
//@RequestMapping("/ingredient")
//public class IngredientController {
//	
//	@Autowired
//	UserRepository userRepository;
//	
//	@Autowired
//	IngredientsService ingredientsService;
//	
//	@PostMapping("/add")
//	private ResponseEntity<?> addIngredients(@RequestParam("title") String title, 
//			  @RequestParam("image") MultipartFile image){
//		try {
//			if(StringUtils.isEmpty(title) && ObjectUtils.isEmpty(title))
////				throw new BadCredentialsException("UserName and Name cannot be Empty");
//		        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Title and Image cannot be Empty");
//			Ingredients ingredients = new Ingredients();
//			ingredients.setTitle(title);
////			ingredients.setImage(Base64.getEncoder().encodeToString(image.getImage().getData()))
//			ingredients.setImage(new Binary(BsonBinarySubType.BINARY, image.getBytes())); 
//			ingredientsService.addIngredients(ingredients);
//		} catch (Exception e) {
//	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error during save Ingredients");
//		}
//		
//		return ResponseEntity.ok(new Response("Saved Ingredients" ));
//	}
//	
//	@GetMapping("/get")
//	private ResponseEntity<?> getIngredients(){
//		try {
//			return  ResponseEntity.status(HttpStatus.FOUND).body(ingredientsService.getIngredients());
//		} catch (Exception e) {
//	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error during save Ingredients");
//		}
//	}
//	
//	 @GetMapping("/photos/{id}")
//	    public Map<String, Object> getPhoto(@PathVariable String id, Model model) {
//		 try {
//	        Ingredients photo = ingredientsService.getIngredient(id);
//	        model.addAttribute("title", photo.getTitle());
//	        model.addAttribute("image", Base64.getEncoder().encodeToString(photo.getImage().getData()));
//	        return model.asMap();
//
//		 }
//		 catch (Exception e) {
//			// TODO: handle exception
//			 e.printStackTrace();
//		}
//		return null;
//	    }
//		
//}
