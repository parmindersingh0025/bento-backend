package com.Bento.Bento.controller;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import com.Bento.Bento.models.Response;
import com.Bento.Bento.models.UserModel;
import com.Bento.Bento.repository.RatingRepository;
import com.Bento.Bento.repository.UserRepository;
import com.Bento.Bento.services.RatingService;
import com.Bento.Bento.services.UserService;

@RestController
@RequestMapping("/rating")
public class RatingController {

	@Autowired
	RatingRepository ratingRepository;

	@Autowired
	RatingService ratingService;

	@PostMapping("/recipe/{recipeId}")
	private ResponseEntity<?> addRating(@PathVariable String recipeId, @RequestParam String parentId, @RequestParam String rating,@RequestParam String kidName) {
		try {
			if (StringUtils.isEmpty(rating))
//				throw new BadCredentialsException("UserName and Name cannot be Empty");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("rating cannot be Empty");
			if (StringUtils.isEmpty(parentId))
//				throw new BadCredentialsException("UserName and Name cannot be Empty");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("parentId cannot be Empty");
			if (StringUtils.isEmpty(kidName))
//				throw new BadCredentialsException("UserName and Name cannot be Empty");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("kidName cannot be Empty");
			ratingService.addRating(recipeId,rating,kidName,parentId);
			return ResponseEntity.ok(new Response("Rating Added Successfully"));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error in Save Rating" + e.getMessage());
		}
	}

	@GetMapping("/get-rating/")
	private ResponseEntity<?> getKids(@RequestParam String parentId,@RequestParam String kidName) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(ratingService.getRating(parentId,kidName));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error during Save Kids");
		}
	}
}
