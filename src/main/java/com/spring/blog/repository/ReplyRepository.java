package com.spring.blog.repository;


import com.spring.blog.dto.reply.ReplyFindByIdDTO;
import com.spring.blog.dto.reply.ReplyInsertDTO;
import com.spring.blog.dto.reply.ReplyUpdateDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReplyRepository {
    List<ReplyFindByIdDTO> findAllByBlogId(long blogId);

    ReplyFindByIdDTO findByReplyId(long replyId);

    void deleteByReplyId(long replyId);

    void save(ReplyInsertDTO replyInsertDTO);

    void update(ReplyUpdateDTO replyUpdateDTO);

    // blogId를 받아 특정 글의 연계된 댓글 전체를 삭제하는 메서드를 정의하기
    void deleteByBlogId(long blogId);

}
