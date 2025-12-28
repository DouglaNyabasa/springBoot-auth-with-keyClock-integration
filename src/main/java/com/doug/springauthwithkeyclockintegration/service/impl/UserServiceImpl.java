package com.doug.springauthwithkeyclockintegration.service.impl;

import com.doug.springauthwithkeyclockintegration.model.User;
import com.doug.springauthwithkeyclockintegration.payload.dto.UserDTO;
import com.doug.springauthwithkeyclockintegration.service.UserService;

import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.RolesRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Value("${app.keycloak.realm}")
    private String realm;

    public final Keycloak keycloak;

    public UserServiceImpl(Keycloak keycloak) {
        this.keycloak = keycloak;
    }


    @Override
    public void createUser(UserDTO userDTO) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEnabled(true);
        userRepresentation.setUsername(userDTO.getUsername());
        userRepresentation.setFirstName(userDTO.getFirstName());
        userRepresentation.setLastName(userDTO.getLastName());
        userRepresentation.setEmail(userDTO.getUsername());
        userRepresentation.setEmailVerified(false);

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(userDTO.getPassword());

        userRepresentation.setCredentials(List.of(credentialRepresentation));

        UsersResource usersResource =  getUserResource();

        Response response = usersResource.create(userRepresentation);

        if (!Objects.equals(201, response.getStatus())) {
            log.info("Error creating user: {}", response.readEntity(String.class));
        }
        log.info("Created user: {}", response.readEntity(String.class));

        List<UserRepresentation> userRepresentations = usersResource.searchByUsername(userDTO.getUsername(), true);
        UserRepresentation userRepresentation1 = userRepresentations.get(0);
        sendVerificationEmail(userRepresentation1.getId());


    }

    @Override
    public void sendVerificationEmail(String userId) {

        UsersResource usersResource = getUserResource();
        usersResource.get(userId).sendVerifyEmail();

    }

    @Override
    public void deleteUser(String userId) {
        UsersResource usersResource = getUserResource();
         usersResource.delete(userId);

    }

    @Override
    public void forgotPassword(String email) {
        UsersResource usersResource =  getUserResource();

        List<UserRepresentation> userRepresentations = usersResource.searchByUsername(email, true);
        UserRepresentation userRepresentation1 = userRepresentations.get(0);

        UserResource userResource = usersResource.get(userRepresentation1.getId());
        userResource.executeActionsEmail(List.of("UPDATE_PASSWORD"));

    }

    @Override
    public UserResource getUser(String userId) {
        UsersResource usersResource =  getUserResource();
        return usersResource.get(userId);
    }

    @Override
    public List<RoleRepresentation> getRoles(String userId) {

        return  getUser(userId).roles().realmLevel().listAll();
    }

    private UsersResource getUserResource() {
       return keycloak.realm(realm).users();
    }
}
