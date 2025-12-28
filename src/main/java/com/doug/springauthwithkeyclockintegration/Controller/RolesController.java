package com.doug.springauthwithkeyclockintegration.Controller;

import com.doug.springauthwithkeyclockintegration.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roles")
public class RolesController {

    private final RoleService roleService;

    public RolesController(RoleService roleService) {
        this.roleService = roleService;
    }


    @PutMapping("/assign/{userId}")
    public ResponseEntity<?> assignUserToRole(@PathVariable String userId, @RequestParam String roleName) {
     roleService.assignRole(userId, roleName);
     return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/remove/{userId}")
    public ResponseEntity<?> removeRole(@PathVariable String userId, @RequestParam String roleName) {
        roleService.removeRole(userId, roleName);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
