package com.benit.team.services;


import com.benit.team.entities.mongo.user.User;
import com.benit.team.entities.mongo.user.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleGroupService roleGroupService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUserName(username);
        List<GrantedAuthority> authorities = getUserAuthority(user.roleGroupIds);
        return buildUserDetails(user, authorities);
    }

    List<GrantedAuthority> getUserAuthority(List<String> roleIds) {
        return roleGroupService.getRoleByIds(roleIds)
                .stream()
                .map(SimpleGrantedAuthority::new)
                .distinct()
                .collect(Collectors.toList());
    }

    UserDetails buildUserDetails(User user, List<GrantedAuthority> authorities) {
        return new UserAccount(user, authorities);
    }
}
