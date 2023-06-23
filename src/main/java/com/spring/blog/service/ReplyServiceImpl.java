package com.spring.blog.service;

import com.spring.blog.dto.reply.ReplyResponseDTO;
import com.spring.blog.dto.reply.ReplyCreateResponseDTO;
import com.spring.blog.dto.reply.ReplyUpdateRequestDTO;
import com.spring.blog.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplyServiceImpl implements ReplyService{

    ReplyRepository replyRepository;

    @Autowired
    public ReplyServiceImpl(ReplyRepository replyRepository){
        this.replyRepository = replyRepository;
    }

    @Override
    public List<ReplyResponseDTO> findAllByBlogId(long blogId) {
        return replyRepository.findAllByBlogId(blogId);
    }

    @Override
    public ReplyResponseDTO findByReplyId(long replyId) {
        return replyRepository.findByReplyId(replyId);
    }

    @Override
    public void deleteByReplyId(long replyId) {
        replyRepository.deleteByReplyId(replyId);
    }

    @Override
    public void save(ReplyCreateResponseDTO replyInsertDTO) {
        replyRepository.save(replyInsertDTO);
    }

    @Override
    public void update(ReplyUpdateRequestDTO replyUpdateRequestDTO) {
        replyRepository.update(replyUpdateRequestDTO);
    }
}
