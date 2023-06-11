package com.spring.blog.repository;

import com.spring.blog.entity.Blog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BlogRepository {

    // 사전 정의 메서드
    void createBlogTable();

    // 더미데이터 입력
    void insertTestData();

    // 테이블 삭제
    void dropBlogTable();

    // 전체 데이터 조회 기능 findAll()
    List<Blog> findAll();
}
