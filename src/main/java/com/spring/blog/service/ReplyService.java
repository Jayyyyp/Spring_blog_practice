package com.spring.blog.service;


import java.util.List;

public interface ReplyService {
    // 글번호 입력시 전체 조회해서 리턴해주는 findByBlogId() 메서드
    List<ReplyResponseDTO> findAllByBlogId(long blogId);

    ReplyResponseDTO findByReplyId(long replyId);

    void deleteByReplyId(long replyId);

    void save(ReplyCreateResponseDTO replyInsertDTO);

    void update(ReplyUpdateRequestDTO replyUpdateRequestDTO);

}
