package com.sda.store.service;


import com.sda.store.model.Role;

import java.util.List;

public interface RoleService {
    Role create(Role role);
    Role findByName(String name);
    List<Role> findAll();

}
