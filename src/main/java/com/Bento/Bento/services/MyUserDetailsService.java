package com.Bento.Bento.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Bento.Bento.models.MyUserDetails;
import com.Bento.Bento.models.User;
import com.Bento.Bento.models.UserModel;
import com.Bento.Bento.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<UserModel> user = userRepository.findByUserName(username);
		user.orElseThrow(()-> new UsernameNotFoundException("Not Found:" + username));
		return user.map(MyUserDetails::new).get();
	}

}
