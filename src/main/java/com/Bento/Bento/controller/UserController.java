package com.Bento.Bento.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Bento.Bento.models.Response;
import com.Bento.Bento.models.UserModel;
import com.Bento.Bento.repository.UserRepository;
import com.Bento.Bento.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserService userService;
	
	@GetMapping("/login/{type}")
	private ResponseEntity<?> userLogin(@PathVariable String type, @RequestParam String userName,@RequestParam(required = false) String password){
		try {
			if(StringUtils.isEmpty(userName) && StringUtils.isEmpty(password))
//				throw new BadCredentialsException("UserName and Name cannot be Empty");
		        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("UserName and Password cannot be Empty");
			if (!type.equalsIgnoreCase("social") && StringUtils.isEmpty(password)) 
//				throw new BadCredentialsException("Password cannot be Empty");
		        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new String("Password cannot be Empty"));
			
			return ResponseEntity.ok(new Response(userService.login(userName,password)));
		} catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error during Login"+ e.getMessage());
		}
	}
	
	@PostMapping("/reset/password")
	private ResponseEntity<?> resetPassword(@RequestParam String userName,@RequestParam String password ){
		try {
			if(StringUtils.isEmpty(userName) && StringUtils.isEmpty(password))
//				throw new BadCredentialsException("UserName and Name cannot be Empty");
		        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("UserName and Password cannot be Empty");

			
			return ResponseEntity.ok(new Response(userService.resetPassword(userName,password)));
		} catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error during Login - "+ e.getMessage());
		}
	}
	
	@PostMapping("/kid/creation")
	private ResponseEntity<?> kidCreation(@RequestParam String parentId, @RequestBody List<HashMap<String, Object>> kids){
		try {
			if(ObjectUtils.isEmpty(kids) && ObjectUtils.isEmpty(kids) && ObjectUtils.isEmpty(kids))
		        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Age, ParentId and Name cannot be Empty");

			userService.saveKid(parentId,kids);
		} catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error during Save Kids");
		}
		
		return ResponseEntity.ok(new Response("Kids Added"));
	}
	
	@PostMapping("/signup/{role}/{type}")
	private ResponseEntity<?> userCreation(@PathVariable String role,@PathVariable String type,@RequestBody UserModel userModel){
		try {
			if(StringUtils.isEmpty(userModel.getUserName()) && StringUtils.isEmpty(userModel.getName()))
//				throw new BadCredentialsException("UserName and Name cannot be Empty");
		        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("UserName and Name cannot be Empty");
			if (!type.equalsIgnoreCase("social") && StringUtils.isEmpty(userModel.getPassword()))
//				throw new BadCredentialsException("Password cannot be Empty");
		        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new String("Password cannot be Empty"));
			if(role.equalsIgnoreCase("Kid"))
				userModel.setRoles("ROLE_KID");
			else
				userModel.setRoles("ROLE_KID,ROLE_PARENT");
			userModel.setType(type);
			userModel.setActive(true);
			
			userModel = userService.signup(userModel);
		} catch (Exception e) {
			if(e.getLocalizedMessage().contains("userName dup key"))
		        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("UserName Already Exists "+userModel.getUserName());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error during Signup");
		}
		
		return ResponseEntity.ok(new Response(userModel.getId().toString()));
	}
	
	@GetMapping("/kid")
	private ResponseEntity<?> getKids(@RequestParam String parentId){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(userService.getKids(parentId));
		} catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error during Save Kids");
		}
			}
	
}
