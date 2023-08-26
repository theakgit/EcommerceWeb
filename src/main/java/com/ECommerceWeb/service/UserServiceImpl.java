package com.ECommerceWeb.service;

import com.ECommerceWeb.config.JwtProvider;
import com.ECommerceWeb.entity.User;
import com.ECommerceWeb.exception.UserException;
import com.ECommerceWeb.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;
    private JwtProvider jwtProvider;

    public UserServiceImpl(UserRepository userRepository,JwtProvider jwtProvider){
        this.userRepository =userRepository;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public User findByUserId(Long userId) throws UserException {
        Optional<User>user = userRepository.findById(userId);
          if(user.isPresent()){
              return user.get();
          }
              throw new UserException("user not found with id :"+userId);

    }

    @Override
    public User findUserProfileByJwt(String jwt) throws UserException {
        String email = jwtProvider.getEmailFromToken(jwt);

        User user = userRepository.findByEmail(email);

        if(user == null){
            throw  new UserException("user not found with email :"+ email);
        }

        return user;
    }
}
