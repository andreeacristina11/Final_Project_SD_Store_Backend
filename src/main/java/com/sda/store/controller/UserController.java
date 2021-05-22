package com.sda.store.controller;

import com.sda.store.controller.dto.user.Address;
import com.sda.store.controller.dto.user.UserDto;
import com.sda.store.model.Role;
import com.sda.store.model.User;
import com.sda.store.service.RoleService;
import com.sda.store.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class UserController {

    private UserService userService;
    private RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostMapping(value = "/register")
    public UserDto create(@RequestBody UserDto userDto) {
        User user = userService.create(mapUserDtoToUser(userDto));
        return mapUserToUserDto(user);
    }

    @GetMapping(value = "/users/roles")
    public List<Role> getRoles() {
        return roleService.findAll();
    }

    @PostMapping(value = "/users/login")
    public org.springframework.security.core.userdetails.User login() {
        return (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private UserDto mapUserToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setImageUrl(user.getImageUrl());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setMessagingChannel(user.getMessagingChannel());

        Set<Role> userRoles = user.getRole();
        for (Role role: userRoles) {
            userDto.setRole(role.getName());
        }

        userDto.setAddress(mapAddressToAddressDto(user.getAddress()));
        return userDto;
    }

    private Address mapAddressToAddressDto(com.sda.store.model.Address address){
        Address addressDto = new Address();
        addressDto.setCity(address.getCity());
        addressDto.setCountry(address.getCountry());
        addressDto.setZipcode(address.getZipcode());
        addressDto.setStreet(address.getStreet());
        return addressDto;
    }

    private User mapUserDtoToUser(UserDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setImageUrl(userDto.getImageUrl());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setMessagingChannel(userDto.getMessagingChannel());
        Role role = roleService.findByName(userDto.getRole());
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(role);
        user.setRole(userRoles);
        user.setAddress(mapAddressDtoToAddress(userDto.getAddress()));
        return user;
    }

    private com.sda.store.model.Address mapAddressDtoToAddress(Address addressDto) {
        com.sda.store.model.Address address = new com.sda.store.model.Address();
        address.setCity(addressDto.getCity());
        address.setCountry(addressDto.getCountry());
        address.setZipcode(addressDto.getZipcode());
        address.setStreet(addressDto.getStreet());
        return address;
    }
}
