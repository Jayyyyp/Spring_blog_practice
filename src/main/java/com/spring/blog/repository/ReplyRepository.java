package com.spring.blog.repository;


import com.spring.blog.dto.reply.ReplyResponseDTO;
import com.spring.blog.dto.reply.ReplyCreateResponseDTO;
import com.spring.blog.dto.reply.ReplyUpdateRequestDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReplyRepository {
    List<ReplyResponseDTO> findAllByBlogId(long blogId);

    ReplyResponseDTO findByReplyId(long replyId);

    void deleteByReplyId(long replyId);

    void save(ReplyCreateResponseDTO replyInsertDTO);

    void update(ReplyUpdateRequestDTO replyUpdateRequestDTO);

    // blogId를 받아 특정 글의 연계된 댓글 전체를 삭제하는 메서드를 정의하기
    void deleteByBlogId(long blogId);

}
