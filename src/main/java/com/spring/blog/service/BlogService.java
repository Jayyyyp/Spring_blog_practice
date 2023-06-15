package com.spring.blog.service;

import com.spring.blog.entity.Blog;

import java.util.List;

public interface BlogService {

    // 비즈니스 로직을 담당할 메서드를 정의
    List<Blog> findAll();

    Blog findById(long blogId);

    void save(Blog blog);

    void deleteById(long blogId);

    void update(Blog blog);
}
