package com.project.pts.security;
  
import com.project.pts.entity.User; 
import com.project.pts.repository.UserRepository; 

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet; 

@Service
public class UserloginDetailsService implements UserDetailsService {
 
    private UserRepository  userRepository;
    
    public UserloginDetailsService(UserRepository ur ) { 
    	this.userRepository = ur; 
    }
    
   

    @Override
    public UserDetails loadUserByUsername(String un) throws UsernameNotFoundException {
    	 
        User user = userRepository.findByEmail(un); 
       
        if (user != null) { 
        	  return new org.springframework.security.core.userdetails.User(user.getEmail(),
                      user.getPassword(),
                      mapRolesToAuthorities(user.getType()));
        }
        else{
            throw new UsernameNotFoundException("Invalid username or password.");
        }
    	 
    }

    private Collection < ? extends GrantedAuthority> mapRolesToAuthorities(String role ) {
    	HashSet<GrantedAuthority> obj= new HashSet<GrantedAuthority>();
    	obj.add(new SimpleGrantedAuthority(role));
    	return obj;
    }
     
}

