package com.Bento.Bento.controller;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Bento.Bento.models.Recipe;
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
	private ResponseEntity<?> userLogin(@PathVariable String type, @RequestParam String userName,
			@RequestParam(required = false) String password) {
		try {
			if (StringUtils.isEmpty(userName) && StringUtils.isEmpty(password))
//				throw new BadCredentialsException("UserName and Name cannot be Empty");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("UserName and Password cannot be Empty");
			if (!type.equalsIgnoreCase("social") && StringUtils.isEmpty(password))
//				throw new BadCredentialsException("Password cannot be Empty");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new String("Password cannot be Empty"));
			UserModel userModel = userService.login(userName, password);
			return ResponseEntity.ok(new Response(userModel.getId().toString(), userModel.getName()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error during Login" + e.getMessage());
		}
	}

	@PostMapping("/reset/password")
	private ResponseEntity<?> resetPassword(@RequestParam String userName, @RequestParam String password) {
		try {
			if (StringUtils.isEmpty(userName) && StringUtils.isEmpty(password))
//				throw new BadCredentialsException("UserName and Name cannot be Empty");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("UserName and Password cannot be Empty");

			return ResponseEntity.ok(new Response(userService.resetPassword(userName, password)));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error during Login - " + e.getMessage());
		}
	}

	@PostMapping("/kid/creation")
	private ResponseEntity<?> kidCreation(@RequestParam String parentId,
			@RequestBody List<HashMap<String, Object>> kids) {
		try {
			if (ObjectUtils.isEmpty(kids) && ObjectUtils.isEmpty(kids) && ObjectUtils.isEmpty(kids))
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Age, ParentId and Name cannot be Empty");

			userService.saveKid(parentId, kids);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error during Save Kids");
		}

		return ResponseEntity.ok(new Response("Kids Added"));
	}

	@PostMapping("/signup/{role}/{type}")
	private ResponseEntity<?> userCreation(@PathVariable String role, @PathVariable String type,
			@RequestParam String name, @RequestParam String userName, @RequestParam String password) {
		UserModel userModel = new UserModel();

		try {
			userModel.setUserName(userName);
			userModel.setName(name);
			int strength = 10; // work factor of bcrypt
			BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(strength, new SecureRandom());
			String encodedPassword = bCryptPasswordEncoder.encode(password);
//			 String pepper = "pepper"; // secret key used by password encoding
//			 int iterations = 200000;  // number of hash iteration
//			 int hashWidth = 256;      // hash width in bits

			/*
			 * Pbkdf2PasswordEncoder pbkdf2PasswordEncoder = new
			 * Pbkdf2PasswordEncoder(pepper, iterations, hashWidth);
			 * pbkdf2PasswordEncoder.setEncodeHashAsBase64(true); encodedPassword =
			 * pbkdf2PasswordEncoder.encode(password); int cpuCost = (int) Math.pow(2, 14);
			 * // factor to increase CPU costs int memoryCost = 8; // increases memory usage
			 * int parallelization = 1; // currently not supported by Spring Security int
			 * keyLength = 32; // key length in bytes int saltLength = 64; // salt length in
			 * bytes
			 * 
			 */
//			 SCryptPasswordEncoder sCryptPasswordEncoder = new SCryptPasswordEncoder(
//			    cpuCost, 
//			    memoryCost,
//			    parallelization,
//			    keyLength,
//			    saltLength);
//			  encodedPassword = sCryptPasswordEncoder.encode(password);
//			  saltLength = 16; // salt length in bytes
//			  int hashLength = 32; // hash length in bytes
//			  int parallelism = 1; // currently not supported by Spring Security
//			  int memory = 4096;   // memory costs
//			  iterations = 3;
//
//			  Argon2PasswordEncoder argon2PasswordEncoder = new Argon2PasswordEncoder(
//			    saltLength,
//			    hashLength,
//			    parallelism,
//			    memory,
//			    iterations);
//			  String encodePassword = argon2PasswordEncoder.encode(password);
			userModel.setPassword(encodedPassword);
			if (StringUtils.isEmpty(userModel.getUserName()) && StringUtils.isEmpty(userModel.getName()))
//				throw new BadCredentialsException("UserName and Name cannot be Empty");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("UserName and Name cannot be Empty");
			if (!type.equalsIgnoreCase("social") && StringUtils.isEmpty(userModel.getPassword()))
//				throw new BadCredentialsException("Password cannot be Empty");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new String("Password cannot be Empty"));
			if (role.equalsIgnoreCase("Kid"))
				userModel.setRoles("ROLE_KID");
			else
				userModel.setRoles("ROLE_KID,ROLE_PARENT");
			userModel.setType(type);
			userModel.setActive(true);

			userModel = userService.signup(userModel);
		} catch (Exception e) {
			if (e.getLocalizedMessage().contains("userName dup key"))
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("UserName Already Exists " + userModel.getUserName());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error during Signup");
		}

		return ResponseEntity.ok(new Response(userModel.getId().toString(), userModel.getName()));
	}

	@PatchMapping("/update-profile/{parentId}/{role}/{type}")
	private ResponseEntity<?> userCreation(@PathVariable String parentId, @PathVariable String role,
			@PathVariable String type, @RequestParam String name, @RequestParam(required = false) String userName,
			@RequestParam String password) {
		UserModel userModel = new UserModel();
		UserModel userModelOld = userRepository.findById(parentId).get();

		try {
			if (!StringUtils.isEmpty(name))
				userModelOld.setName(name);
			if (!StringUtils.isEmpty(userName))
				userModelOld.setUserName(userName);
			if (!StringUtils.isEmpty(password)) {
				int strength = 10; // work factor of bcrypt
				BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(strength, new SecureRandom());
				String encodedPassword = bCryptPasswordEncoder.encode(password);
				userModelOld.setPassword(encodedPassword);
			}
			userModel = userService.updateProfile(parentId, userModelOld);
		} catch (Exception e) {
			if (e.getLocalizedMessage().contains("userName dup key"))
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("UserName Already Exists " + userModel.getUserName());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error during Update Profile" + e.getMessage());
		}

		return ResponseEntity.ok(new Response(userModel.getId().toString(), userModel.getName()));
	}

	@GetMapping("/kid")
	private ResponseEntity<?> getKids(@RequestParam String parentId) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(userService.getKids(parentId));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error during Save Kids");
		}
	}
	
	@PostMapping("/upload-avatar")
	  public ResponseEntity<?> uploadAvatar(@RequestParam String parentId, @RequestPart("image") MultipartFile file) {
	    String message = "";
		try {
			String name = StringUtils.cleanPath(file.getOriginalFilename());

		 Optional<UserModel> userModelOp = userRepository.findById(parentId);
		 UserModel userModel = userModelOp.get();
		 userModel.setData(file.getBytes());
		 userModel = userService.uploadAvatar(userModel);
	      return ResponseEntity.status(HttpStatus.OK).
			        header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getOriginalFilename() + "\"")
			        .body(file.getBytes());
	    } catch (Exception e) {
	    	message = "Could not upload the file: " + file.getOriginalFilename() + "!";

	    	if (e instanceof org.springframework.dao.DuplicateKeyException) {
				message = "Could not upload the file: " + "Avatar Already Present in the profile";
			}
	      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Error in Save " + message);
	    }
	  }

	@GetMapping("/delete-avatar")
	  public ResponseEntity<?> deleteAvatar(@RequestParam String parentId) {
	    String message = "";
		try {
		 Optional<UserModel> userModelOp = userRepository.findById(parentId);
		 UserModel userModel = userModelOp.get();
		 userModel.setData(new byte[0]);
		 userRepository.save(userModel);
	      return ResponseEntity.status(HttpStatus.OK).body(new Response("Avatar Deleted"));
	    } catch (Exception e) {
	    	message = "Could not delete the file: " + "!";
	      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Error in Save " + message);
	    }
	  }

	@PostMapping("/delete-parent-profile")
	  public ResponseEntity<?> deleteParent(@RequestParam String parentId) {
	    String message = "";
		try {

		 Optional<UserModel> userModelOp = userRepository.findById(parentId);
		 UserModel userModel = userModelOp.get();
		 userModel.setActive(false);
		 userRepository.save(userModel);
	      return ResponseEntity.status(HttpStatus.OK).body("Deactivated Parent Profile");
	    } catch (Exception e) {
	    	message = "Could not delete parent profile: " + "!";
	      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Error in Save " + message);
	    }
	  }
	
	@PostMapping("/delete-kid-profile")
	  public ResponseEntity<?> deleteKid(@RequestParam String parentId,@RequestParam String kidName) {
	    String message = "";
		try {
			userService.deleteKid(parentId,kidName);
	      return ResponseEntity.status(HttpStatus.OK).body("Deactivated Kid Profile");
	    } catch (Exception e) {
	    	message = "Could not delete Kid profile: " + "!";
	      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Error in Save " + message);
	    }
	  }
	
	@PatchMapping("/update-kid-profile")
	  public ResponseEntity<?> updateKid(@RequestParam String parentId,@RequestParam(required = false) String newKidName,@RequestParam(required = false) String oldKidName,@RequestParam(required = false) String newKidAge,@RequestParam(required = false) String oldKidAge) {
	    String message = "";
		try {
			userService.updateKid(parentId,newKidName,oldKidName,newKidAge,oldKidAge);
	      return ResponseEntity.status(HttpStatus.OK).body("Updated Kid Profile");
	    } catch (Exception e) {
	    	message = "Could not delete Kid profile: " + "!";
	      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Error in Save " + message);
	    }
	  }
	
}
