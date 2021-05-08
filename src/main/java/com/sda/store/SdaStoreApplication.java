package com.sda.store;

import com.sda.store.model.Role;
import com.sda.store.model.RoleEnum;
import com.sda.store.service.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class SdaStoreApplication implements CommandLineRunner {

    private RoleService roleService;

    public SdaStoreApplication(RoleService roleService) {
        this.roleService = roleService;
    }

    public static void main(String[] args) {
        SpringApplication.run(SdaStoreApplication.class, args);
    }

    @Override
    public void run(String... args)  {
        List<RoleEnum> rolEnumList = Arrays.asList(RoleEnum.values());
        for(RoleEnum roleEnum: rolEnumList){
            Role role = new Role(roleEnum.name());
            roleService.create(role);
        }
    }
}
