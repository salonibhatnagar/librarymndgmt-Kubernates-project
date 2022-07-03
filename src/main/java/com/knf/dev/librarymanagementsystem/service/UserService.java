package com.knf.dev.librarymanagementsystem.service;

import com.knf.dev.librarymanagementsystem.entity.Book;
import com.knf.dev.librarymanagementsystem.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface UserService extends UserDetailsService {

//    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
//    User findUserById(Long id);

    public void updateUser(User user);
}