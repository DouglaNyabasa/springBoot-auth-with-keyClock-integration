package com.doug.springauthwithkeyclockintegration.service;

public interface RoleService {

    void assignRole(String userId, String roleName);
    void removeRole(String userId, String roleName);
}
