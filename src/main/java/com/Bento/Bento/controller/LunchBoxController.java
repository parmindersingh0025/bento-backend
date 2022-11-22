package com.Bento.Bento.controller;

import java.util.HashMap;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Bento.Bento.models.LunchBox;
import com.Bento.Bento.models.ResponseMessage;
import com.Bento.Bento.services.LunchBoxService;

@RestController
@RequestMapping("/lunchbox")
public class LunchBoxController {

	@Autowired
	private LunchBoxService lunchBoxService;

	@PostMapping("/add/{recipeId}")
	public ResponseEntity<?> addLunchBox(@PathVariable String recipeId,
			@RequestParam("parentId") String parentId, @RequestParam("day") List<String> day) {
		String message = "";
		try {
			LunchBox lunchBox = lunchBoxService.addLunchBox(recipeId, parentId, day);
			message = "LunchBox added successfully: ";
			return ResponseEntity.status(HttpStatus.OK).body(lunchBox);
		} catch (Exception e) {
			message = "Could not add LunchBox: " + "!";
			e.printStackTrace();

			if (e instanceof org.springframework.dao.DuplicateKeyException) {
				message = "Could not add LunchBox: " + "LunchBox Already Present in the list";
				e.printStackTrace();

			}
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
		}
	}
//

	@PostMapping("/assign-kid-lunchbox/{lunchboxId}")
	public ResponseEntity<ResponseMessage> assignKidLunchBox(@PathVariable String lunchboxId,@RequestParam String parentId,
			@RequestParam List<String> kidsName,@RequestParam String day) {
		String message = "";
		try {
			lunchBoxService.assignKidLunchBox(lunchboxId, parentId,kidsName,day);
			message = "LunchBox Assigned successfully: ";
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
		} catch (Exception e) {
			message = "Could not Assign LunchBox: " + "!";
			e.printStackTrace();

			if (e instanceof org.springframework.dao.DuplicateKeyException) {
				message = "Could not Assgin LunchBox: " + "LunchBox Already Assgin in the list";
				e.printStackTrace();

			}
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
		}
	}

	@GetMapping("/get/{parentId}")
	public ResponseEntity<LunchBox> getLunchBox(@PathVariable String parentId) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(lunchBoxService.getLunchBox(parentId));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.notFound().build();
	}

	
	@GetMapping("/get-assign-kid/{parentId}")
	public ResponseEntity<LunchBox> getAssignKidLunchBox(@PathVariable String parentId) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(lunchBoxService.getLunchBox(parentId));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/get-history-list/{parentId}")
	public ResponseEntity<List<LunchBox>> getHistoryListLunchBox(@PathVariable String parentId) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(lunchBoxService.getAllLunchBoxes(parentId));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.notFound().build();
	}

	
	@GetMapping("/get-by-lunchboxId/{lunchboxId}")
	public ResponseEntity<LunchBox> getLunchBoxById(@PathVariable String lunchboxId) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(lunchBoxService.getLunchBoxById(lunchboxId));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.notFound().build();
	}

	
	@GetMapping("/delete/{lunchBoxId}")
	public ResponseEntity<ResponseMessage> deleteLunchBox(@PathVariable String lunchBoxId, @RequestParam Boolean active,@RequestParam String kidName,@RequestParam String day) {
		String message = "";
		try {
			lunchBoxService.deleteLunchBox(lunchBoxId,active,day,kidName);
			message = "LunchBox deleted successfully: ";
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			message = "Could not delete LunchBox: " + "!";
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));

		}
	}
}
