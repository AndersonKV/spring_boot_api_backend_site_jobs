package com.backend.demo.utils;

import com.backend.demo.DTO.UserUpdateDTO;
import com.backend.demo.enums.UserRole;
import com.backend.demo.model.User;

public class UserFactory {
    public User createUserRoleCompany(String email) {
        User user = new User();

        user.setName("exemplo");
        user.setEmail(email);
        user.setPassword("senhasenha");
        user.setConfirm_password("senhasenha");
        user.setRole(UserRole.company);

        return user;
    }

    public User createWithRoleUser(String email) {
        User user = new User();

        user.setName("exemplo");
        user.setEmail(email);
        user.setPassword("senhasenha");
        user.setConfirm_password("senhasenha");
        user.setRole(UserRole.user);

        return user;
    }


    public User createWithWrongPassword(String email) {
        User user = new User();

        user.setName("exemplo");
        user.setEmail(email);
        user.setPassword("senhasenha");
        user.setConfirm_password("sewnhasenha");
        user.setRole(UserRole.company);

        return user;
    }

    public User createWithWrongValues() {
        User user = new User();

        user.setName("sdds");
        user.setEmail("sda5sa1d6s5");
        user.setPassword("senhasenha");
        user.setConfirm_password("sewnhasenha");
        user.setRole(UserRole.company);

        return user;
    }

    public User createWithWrongCompany() {
        User user = new User();

        user.setName("exemplo");
        user.setEmail("email.com@gmail.com");
        user.setPassword("senhasenha");
        user.setConfirm_password("senhasenha");
        user.setRole(UserRole.company);
        return user;
    }

    public UserUpdateDTO updateUser(UserRole role, Long id, String token) {
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO();

        userUpdateDTO.setName("dsadsasadsdadsadsa");
        userUpdateDTO.setEmail("sdasdasda" + id + "sda@gmail.com");
        userUpdateDTO.setPassword("123456789");
        userUpdateDTO.setConfirm_password("123456789");
        userUpdateDTO.setRole(role);
        userUpdateDTO.setId(id);
        userUpdateDTO.setToken(token);

        return userUpdateDTO;
    }

    public UserUpdateDTO updateUserWithWrongPassword(UserRole role, Long id, String token) {
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO();

        userUpdateDTO.setName("dsadsasadsdadsadsa");
        userUpdateDTO.setEmail("sdasdasdasda@gmail.com");
        userUpdateDTO.setPassword("123456789");
        userUpdateDTO.setConfirm_password("123456789aaa");
        userUpdateDTO.setRole(role);
        userUpdateDTO.setId(id);
        userUpdateDTO.setToken(token);

        return userUpdateDTO;
    }

    public UserUpdateDTO updateUserWithWrongEmail(Long id, String token) {
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO();

        userUpdateDTO.setName("dsadsasadsdadsadsa");
        userUpdateDTO.setEmail("sdasdasdasda@gmail.com");
        userUpdateDTO.setPassword("123456789");
        userUpdateDTO.setConfirm_password("123456789");
        userUpdateDTO.setRole(UserRole.company);
        userUpdateDTO.setId(id);
        userUpdateDTO.setToken(token);

        return userUpdateDTO;
    }

    public UserUpdateDTO updateUserWithEmailExistent(Long id, String email, String token) {
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO();

        userUpdateDTO.setName("dsadsasadsdadsadsa");
        userUpdateDTO.setEmail(email);
        userUpdateDTO.setPassword("123456789");
        userUpdateDTO.setConfirm_password("123456789");
        userUpdateDTO.setRole(UserRole.company);
        userUpdateDTO.setId(id);
        userUpdateDTO.setToken(token);

        return userUpdateDTO;
    }
}
