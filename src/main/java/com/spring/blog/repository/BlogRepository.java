package com.spring.blog.repository;

import com.spring.blog.entity.Blog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BlogRepository {

    // 사전 정의 메서드
    void createBlogTable();

    // 더미데이터 입력d
    void insertTestData();

    // 테이블 삭제
    void dropBlogTable();

    // 전체 데이터 조회 기능 findAll()
    List<Blog> findAll();

    // 단일 행 조회 기능 findById()
    Blog findById(long blogId);

    // 데이터 저장기능 save()
    void save(Blog blog);

    // 데이터 삭제 기능 deleteById()
    void deleteById(long blogId);

    // 데이터 수정 기능
    void update(Blog blog);
}
