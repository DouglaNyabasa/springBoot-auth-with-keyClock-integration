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

        Response response = getUserResource().create(userRepresentation);

        if (!Objects.equals(201, response.getStatus())) {
            log.info("Error creating user: {}", response.readEntity(String.class));
        }
        log.info("Created user: {}", response.readEntity(String.class));

    }

    private UsersResource getUserResource() {
       return keycloak.realm(realm).users();
    }
}
