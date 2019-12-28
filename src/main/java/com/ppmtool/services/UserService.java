package com.ppmtool.services;

import com.ppmtool.domain.User;
import com.ppmtool.exceptions.UserExistsException;
import com.ppmtool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User saveUser (User newUser){

        //username has to be unique
        try {
            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
            //confirm pw must match...
            return userRepository.save(newUser);
        } catch (Exception e){
            throw new UserExistsException("Username '" + newUser.getUsername() + "' exists.");
        }

            //password and confirmPassword must match
    }
}
