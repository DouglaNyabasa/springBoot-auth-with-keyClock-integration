package com.doug.springauthwithkeyclockintegration.service;

import com.doug.springauthwithkeyclockintegration.model.User;
import com.doug.springauthwithkeyclockintegration.payload.dto.UserDTO;

public interface UserService {

    void createUser(UserDTO userDTO);
}
