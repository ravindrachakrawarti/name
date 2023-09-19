package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.entity.User;
import com.example.repository.UserRespority;
@Component
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRespority userrespority;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		   User user=userrespority.findByEmail(username);
		
		   if(user==null)
		   {
			   throw new UsernameNotFoundException("user not found");
			   
			   
		   }
		   
		   else {
			   
			   
			   return new CustomUser(user);
		   }
		
	}

}
