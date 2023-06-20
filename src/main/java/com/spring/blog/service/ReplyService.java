package com.spring.blog.service;


import com.spring.blog.dto.reply.ReplyFindByIdDTO;
import com.spring.blog.dto.reply.ReplyInsertDTO;
import com.spring.blog.dto.reply.ReplyUpdateDTO;

import java.util.List;

public interface ReplyService {
    // 글번호 입력시 전체 조회해서 리턴해주는 findByBlogId() 메서드
    List<ReplyFindByIdDTO> findAllByBlogId(long blogId);

    ReplyFindByIdDTO findByReplyId(long replyId);

    void deleteByReplyId(long replyId);

    void save(ReplyInsertDTO replyInsertDTO);

    void update(ReplyUpdateDTO replyUpdateDTO);

}
