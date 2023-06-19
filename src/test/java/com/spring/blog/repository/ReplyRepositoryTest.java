package com.spring.blog.repository;

import com.spring.blog.dto.reply.ReplyFindByIdDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ReplyRepositoryTest {

    @Autowired
    ReplyRepository replyRepository;

    @Test
    @Transactional
    @DisplayName("2번글에 연동된 댓글 개수가 4개인지 확인")
    public void findAllByBlogIdTest(){
        long blogId = 2;

        List<ReplyFindByIdDTO> result = replyRepository.findAllByBlogId(blogId);

        assertEquals(4, result.size());
    }

    @Test
    @Transactional
    @DisplayName("댓글번호 3번 자료의 댓글은 3번이고, 글쓴이는 바둑이")
    public void findByReplyIdTest(){
        long replyId = 3;
        String replyWriter = "바둑이";
        String replyContent = "3빠댓글";

        ReplyFindByIdDTO replyFindByIdDTO = replyRepository.findByReplyId(replyId);

        assertEquals(replyWriter, replyFindByIdDTO.getReplyWriter());
        assertEquals(replyContent, replyFindByIdDTO.getReplyContent());

    }
}
