package com.frame.fast.service.staff;

import com.baomidou.mybatisplus.extension.service.IService;
import com.frame.fast.model.Staff;
import lombok.NonNull;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * <p>
 * 职工信息 服务类
 * </p>
 *
 * @author jobob
 * @since 2019-09-24
 */
public interface IStaffService extends IService<Staff> {

    Staff getByUserNameAndPassword(@NonNull String mobile, @NonNull String password) throws UsernameNotFoundException;
}
