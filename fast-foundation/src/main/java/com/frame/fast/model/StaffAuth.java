package com.frame.fast.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
public class StaffAuth implements GrantedAuthority {

    private String auth;
    @Override
    public String getAuthority() {
        return auth;
    }

    public StaffAuth (StaffPost post){
        this.auth = "ROLE_" + post.name();
    }
}
