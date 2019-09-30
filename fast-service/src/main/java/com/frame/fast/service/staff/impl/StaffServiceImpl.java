package com.frame.fast.service.staff.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.frame.fast.model.Staff;
import com.frame.fast.repository.StaffMapper;
import com.frame.fast.service.staff.IStaffService;
import lombok.NonNull;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 职工信息 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2019-09-24
 */
@Service
public class StaffServiceImpl extends ServiceImpl<StaffMapper, Staff> implements IStaffService, UserDetailsService {

    @Resource
    private StaffMapper staffMapper;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(@NonNull String mobile) throws UsernameNotFoundException {
        QueryWrapper<Staff> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Staff staff = staffMapper.selectOne(wrapper);
        if(staff == null){
            throw new UsernameNotFoundException("用户不存在或用户名密码不匹配");
        }
        User user = new User(mobile,passwordEncoder.encode(staff.getPassword()),
                AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN"));
        return user;
    }

    @Override
    public Staff getByUserNameAndPassword(@NonNull String mobile,@NonNull String password) throws UsernameNotFoundException {
        QueryWrapper<Staff> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        wrapper.eq("password",password);
        return staffMapper.selectOne(wrapper);
    }
}
