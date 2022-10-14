package com.Bento.Bento.services;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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

	public String login(String userName, String password) throws Exception {
		Optional<UserModel> userModelOp =  userRepository.findByUserName(userName);
		if(!userModelOp.isPresent())
			throw new Exception("User is not present: " + userName);
		UserModel userModel = userModelOp.get();
		if(!userModel.getType().equalsIgnoreCase("social") && !userModel.getPassword().equals(password))
			throw new Exception("Invalid Password: " + userName);
		return "Successful Login";
		// TODO Auto-generated method stub
		
	}
	
	public String resetPassword(String userName, String password) throws Exception {
		Optional<UserModel> userModelOp =  userRepository.findByUserName(userName);
		if(!userModelOp.isPresent())
			throw new Exception("User is not present: " + userName);
		UserModel userModel = userModelOp.get();
		userModel.setPassword(password);
		userRepository.save(userModel);
		return "Successful Password Reset";
		// TODO Auto-generated method stub
		
	}

	public void saveKid(String parentId, List<HashMap<String, Object>> kids) {
		// TODO Auto-generated method stub
		Kid kid = new Kid();
		kid.setKids(kids);
		kid.setParentId(parentId);
		kidRepository.save(kid);
	}

	public Kid getKids(String parentId) {
		// TODO Auto-generated method stub
		return kidRepository.findByParentId(parentId);

		
	}
	
	
}
