package com.springsecurity;

import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

import javax.annotation.security.RolesAllowed;


@RequestMapping("/guest")
@RestController

public class RestApiController {

    @Secured("ROLE_USER")
    @GetMapping(value = "/user/getUser1")
    public String getUser1(@AuthenticationPrincipal User user) {
        return "Welcome User " + user.getUsername();
    }

    @Secured({"ROLE_USER", "ROLE_EXECUTIVE","ROLE_MANAGER"})
    @GetMapping(value = "/user/getUser2")
    public String getUser2(@AuthenticationPrincipal User user) {
        return "Welcome User " + user.getUsername();
    }


    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping(value = "/user/getUser3")
    public String getUser3(@AuthenticationPrincipal User user) {
        return "Welcome User " + user.getUsername();
    }


    @PreAuthorize("hasRole('USER') AND hasRole('MANAGER') AND hasRole('EXECUTIVE')")
    @GetMapping(value = "/user/getUser4")
    public String getUser4(@AuthenticationPrincipal User user) {
        return "Welcome User " + user.getUsername();
    }

    @PreAuthorize("hasRole('MANAGER') OR hasRole('EXECUTIVE')")
    @GetMapping(value = "/user/getUser5")
    public String getUser5(@AuthenticationPrincipal User user) {
        return "Welcome User " + user.getUsername();
    }
    
   
    @PreAuthorize("hasRole('USER')")
    @PostAuthorize("(returnObject.username == principal.username) AND hasRole('MANAGER') AND hasRole('EXECUTIVE')")
    @GetMapping(value = "/user/getUser6")
    public UserProfile getUser6(@AuthenticationPrincipal User user) {
    	
        return UserProfile.build(user.getUsername(), user.getPassword(), user.getAuthorities());
    }

    /**
     * <p>
     * This method can be accessed by any user with ROLE_USER.
     * But the content will be returned if the user has the ROLE_ADMIN and
     * authenticated principal name is same as the username of the return object.
     * </p>
     *
     * @param user
     * @return
     */
    @PreAuthorize("hasRole('USER')")
    @PostAuthorize("(returnObject.username == principal.username) AND hasRole('ADMIN')")
    @GetMapping(value = "/admin/getUser7")
    public ResponseEntity<UserProfile> getUser7(@AuthenticationPrincipal User user) {
    	
    	return  ResponseEntity.ok().cacheControl(CacheControl.maxAge(30, TimeUnit.SECONDS)).
    			body(UserProfile.build(user.getUsername(), user.getPassword(), user.getAuthorities()));
        
    }


    @RolesAllowed("ROLE_USER")
    @GetMapping(value = "/admin/getUser8")
    public String getUser8(@AuthenticationPrincipal User user) {
    	System.out.println();
        return "Welcome User " + user.getUsername();
    }


    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping(value = "/admin/getUser9")
    public String getUser9(@AuthenticationPrincipal User user) {
        return "Welcome User " + user.getUsername();
    }
    
    @PreAuthorize("hasRole('USER') OR hasRole('ADMIN')")
    @GetMapping(value = "/user/getUser10")
    public String getUser10(@AuthenticationPrincipal User user) {
        return "Welcome User " + user.getUsername();
    }
    
   

   
}
