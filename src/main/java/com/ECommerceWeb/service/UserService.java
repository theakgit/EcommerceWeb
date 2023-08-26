package com.ECommerceWeb.service;

import com.ECommerceWeb.entity.User;
import com.ECommerceWeb.exception.UserException;


public interface UserService {

    public User findByUserId(Long userId) throws UserException;

    public User findUserProfileByJwt(String jwt) throws UserException;
}
