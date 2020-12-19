package com.benit.team.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
class RoleGroupService {

    public List<String> getRoleById(String roleGroupId) {
//        RoleGroup roleGroup = roleGroupRepository.findById(roleGroupId);
//        return roleGroup.roles;
        return Collections.emptyList();
    }

    public List<String> getRoleByIds(List<String> roleGroupIds) {
//        List<RoleGroup> roleGroups = roleGroupRepository.findByIds(roleGroupIds);
//        return roleGroups.collect { it.roles }.flatten() as List<String>;
        return Collections.emptyList();
    }

    public Long remove(String roleGroupId) {
        return 1L;
//        return roleGroupRepository.remove(roleGroupId);
    }
}
