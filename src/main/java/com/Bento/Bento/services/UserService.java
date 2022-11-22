package com.Bento.Bento.services;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.Bento.Bento.models.Kid;
import com.Bento.Bento.models.MyUserDetails;
import com.Bento.Bento.models.UserModel;
import com.Bento.Bento.repository.KidRepository;
import com.Bento.Bento.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	KidRepository kidRepository;

	public UserModel signup(UserModel userModel) {

		return userRepository.save(userModel);
		// TODO Auto-generated method stub

	}

	public UserModel updateProfile(String parentId, UserModel userModel) {

		return userRepository.save(userModel);
		// TODO Auto-generated method stub

	}

	public UserModel login(String userName, String password) throws Exception {
		Optional<UserModel> userModelOp = userRepository.findByUserName(userName);
		if (!userModelOp.isPresent())
			throw new Exception("User is not present: " + userName);
		int strength = 10;
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(strength, new SecureRandom());
		boolean encodedPassword = bCryptPasswordEncoder.matches(password, userModelOp.get().getPassword());
		UserModel userModel = userModelOp.get();
		if (!userModel.getType().equalsIgnoreCase("social") && !encodedPassword)
			throw new Exception("Invalid Password: " + userName);
		return userModel;
		// TODO Auto-generated method stub

	}

	public String resetPassword(String userName, String password) throws Exception {
		Optional<UserModel> userModelOp = userRepository.findByUserName(userName);
		if (!userModelOp.isPresent())
			throw new Exception("User is not present: " + userName);
		UserModel userModel = userModelOp.get();
		int strength = 10; // work factor of bcrypt
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(strength, new SecureRandom());
		String encodedPassword = bCryptPasswordEncoder.encode(password);
		userModel.setPassword(encodedPassword);
		userRepository.save(userModel);
		return "Successful Password Reset";
		// TODO Auto-generated method stub

	}

	public void saveKid(String parentId, List<HashMap<String, Object>> kids) {
		// TODO Auto-generated method stub
		Kid kid = new Kid();
		kid.setKids(kids);
		if (kidRepository.findByParentId(parentId).isPresent()) {
			kid = kidRepository.findByParentId(parentId).get();
			if (kid.getKids() != null && !kid.getKids().isEmpty())
				kid.getKids().addAll(kids);
			else
				kid.setKids(kids);
		}
		kid.setParentId(parentId);
		kidRepository.save(kid);
	}

	public Kid getKids(String parentId) {
		// TODO Auto-generated method stub
		return kidRepository.findByParentId(parentId).get();
	}

	public UserModel uploadAvatar(UserModel userModel) {
		// TODO Auto-generated method stub
		return userRepository.save(userModel);

	}

	public void updateKid(String parentId, String newkidName, String oldkidName, String newKidAge, String oldkidAge) {
		// TODO Auto-generated method stub
		Optional<Kid> kidOptional = kidRepository.findByParentId(parentId);
		Kid kidObject = kidOptional.get();
		List<HashMap<String, Object>> kids = kidObject.getKids();
//	  kids.forEach(k -> k.entrySet());
		for (HashMap<String, Object> kid : kids) {
			if (kid.containsKey("Name") && (kid.get("Name").toString()).equalsIgnoreCase(oldkidName)) {
				kid.replace("Name", oldkidName, newkidName);
			}
			if (kid.containsKey("name") && (kid.get("name").toString()).equalsIgnoreCase(oldkidName)) {
				kid.replace("name", oldkidName, newkidName);
			}

			if (kid.containsKey("age") && (kid.get("age").toString()).equalsIgnoreCase(oldkidAge)
					&& kid.containsKey("name") && (kid.get("name").toString()).equalsIgnoreCase(newkidName)) {
				kid.replace("age", newKidAge);
			}

			if (kid.containsKey("Age") && (kid.get("Age").toString()).equalsIgnoreCase(oldkidAge)
					&& kid.containsKey("Name") && (kid.get("Name").toString()).equalsIgnoreCase(newkidName)) {
				kid.replace("Age", newKidAge);
			}
		}

		kidObject.setKids(kids);
		kidRepository.save(kidObject);
	}

	public void deleteKid(String parentId, String kidName) {
		// TODO Auto-generated method stub
		Optional<Kid> kidOptional = kidRepository.findByParentId(parentId);
		Kid kidObject = kidOptional.get();
		List<HashMap<String, Object>> kids = kidObject.getKids();
//	  kids.forEach(k -> k.entrySet());
		for (HashMap<String, Object> kid : kids) {
			if (kid.containsKey("Name") && (kid.get("Name").toString()).equalsIgnoreCase(kidName)) {
				kid.remove("Name", kidName);
				kid.remove("Age");
				kid.clear();
				kid.entrySet().removeIf(e -> StringUtils.isEmpty(e.getKey()));
			}
			if (kid.containsKey("name") && (kid.get("name").toString()).equalsIgnoreCase(kidName)) {
				kid.remove("name", kidName);
				kid.remove("age");
				kid.clear();
				kid.entrySet().removeIf(e -> StringUtils.isEmpty(e.getKey()));

			}
		}
//		kids.clear();
		kids = kids.stream().filter(Objects::nonNull).collect(Collectors.toList());

//		kid
		kidObject.setKids(kids);
		kidRepository.save(kidObject);
	}

}
