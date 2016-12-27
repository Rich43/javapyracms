package com.pynguins.models;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;


@Transactional
public interface UserDao extends CrudRepository<User, Long> {
  User findById(Integer id);

  User findByEmail(String email);
}
