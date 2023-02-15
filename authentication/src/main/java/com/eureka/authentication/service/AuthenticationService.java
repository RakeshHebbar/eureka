package com.eureka.authentication.service;

import com.eureka.authentication.config.JwtTokenUtil;
import com.eureka.authentication.dao.UserDao;
import com.eureka.authentication.dto.AuthResponse;
import com.eureka.authentication.dto.UserDto;
import com.eureka.authentication.exception.UserAlreadyExistsException;
import com.eureka.authentication.model.UserModel;
import com.eureka.common.dto.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;


    /**
     *
     * @param userName -> userId is used as username for forwarding it to other services
     * @return User Details for setting it in auth context
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        if(userName == null || userName.isEmpty()) {
            throw new IllegalArgumentException("username cannot be null or empty");
        }
        UserModel userModel = userDao.findByUserId(Integer.parseInt(userName));
        if (userModel == null) {
            throw new UsernameNotFoundException("User not found with username: " + userName);
        }

        return new org.springframework.security.core.userdetails.User(userModel.getId().toString(),
                userModel.getPassword(), new ArrayList<>());
    }


    public Message signUp(UserDto userDto) throws Exception {

        validate(userDto);

        UserModel existingModel = userDao.findByUserName(userDto.getUserName());
        if(existingModel != null) {
            throw new UserAlreadyExistsException("User already exists with the same username");
        }

        try {
            UserModel userModel = new UserModel();
            userModel.setUsername(userDto.getUserName());
            userModel.setPassword(bcryptEncoder.encode(userDto.getPassword()));
            userDao.save(userModel);
            return new Message("Sign Up Successful", "USER.SIGNUP.SUCCESS");
        }catch (Exception e) {
            throw new Exception("Error in Signup");
        }
    }

    private void validate(UserDto userDto) {
        // validate
        if(userDto.getUserName() == null || userDto.getUserName().isEmpty()) {
            throw new IllegalArgumentException("User Name cannot be null or empty");
        }

        if(userDto.getPassword() == null || userDto.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
    }

    public AuthResponse authenticate(UserDto user) throws Exception {

        validate(user);

        UserModel userModel = userDao.findByUserName(user.getUserName());
        if(userModel == null) {
            throw new UsernameNotFoundException("User not found with username: " + user.getUserName());
        }

        final UserDetails userDetails = loadUserByUsername(userModel.getId().toString());

        try {
            authenticationManager.authenticate(new
                    UsernamePasswordAuthenticationToken(userModel.getId(), user.getPassword()));
        }catch (DisabledException de) {
            throw new DisabledException("USER_DISABLED", de);
        }catch (BadCredentialsException be) {
            throw new BadCredentialsException("incorrect password", be);
        }

        final String token = jwtTokenUtil.generateToken(userDetails);

        return new AuthResponse(token);
    }

}
