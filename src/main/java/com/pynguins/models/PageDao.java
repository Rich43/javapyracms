package com.pynguins.models;

import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;


@Transactional
public interface PageDao extends CrudRepository<Page, Long> {
    Page findById(Integer id);
    Page findByName(String name);
}
