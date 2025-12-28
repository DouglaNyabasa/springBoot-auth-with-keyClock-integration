package com.doug.springauthwithkeyclockintegration.service;

import com.doug.springauthwithkeyclockintegration.model.User;
import com.doug.springauthwithkeyclockintegration.payload.dto.UserDTO;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.RolesRepresentation;

import java.util.List;

public interface UserService {

    void createUser(UserDTO userDTO);
    void sendVerificationEmail(String userId);
    void deleteUser(String userId);
    void forgotPassword(String email);
    UserResource getUser(String userId);
    List<RoleRepresentation> getRoles(String userId);
}
