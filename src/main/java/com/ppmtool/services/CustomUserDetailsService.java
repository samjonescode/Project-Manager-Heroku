package com.ppmtool.services;

import com.ppmtool.domain.User;
import com.ppmtool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if(user==null){
            throw new UsernameNotFoundException("User not found"); // comes with spring
        }

        return user;
    }

    @Transactional
    public User loadUserById(Long userId){
        User user = userRepository.getById(userId);
        if(user==null) new UsernameNotFoundException(("User not found"));
        return user;
    }
}
