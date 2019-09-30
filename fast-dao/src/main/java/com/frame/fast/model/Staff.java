package com.frame.fast.model;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 职工信息
 * </p>
 *
 * @author jobob
 * @since 2019-09-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Staff extends BaseEntity implements UserDetails {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    private String name;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 密码
     */
    private String password;


    /**
     * 邮箱
     */
    private String email;

    /**
     * 岗位
     */
    private StaffPost post;

    private Boolean valid;

    @TableField(exist = false)
    private StaffAuth auth;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<StaffAuth> auths = new ArrayList<>();
        StaffAuth auth = new StaffAuth(this.post);
        auths.add(auth);
        return auths;
    }

    @Override
    public String getUsername() {
        return this.getMobile();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
