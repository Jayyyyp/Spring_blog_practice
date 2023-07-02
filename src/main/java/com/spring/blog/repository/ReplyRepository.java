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
}
