package com.exam.services.implement;

import com.exam.entity.User;
import com.exam.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

@Repository
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
         User user=this.userRepository.findByuserName(s);
         if(user==null)
         {
             System.out.println("User not found exception");
             throw new UsernameNotFoundException("No user found");
         }
         return user;
    }
}
