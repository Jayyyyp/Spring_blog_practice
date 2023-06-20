package com.spring.blog.repository;

import com.spring.blog.dto.reply.ReplyFindByIdDTO;
import com.spring.blog.dto.reply.ReplyInsertDTO;
import com.spring.blog.service.ReplyService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ReplyServiceTest {

    @Autowired
    ReplyService replyService;

    @Test
    @Transactional
    @DisplayName("2번글에 연동된 댓글 개수가 4개인지 확인")
    public void findByBlogIdTest(){
        long blogId = 2;

        List<ReplyFindByIdDTO> result = replyService.findAllByBlogId(blogId);

        assertEquals(4, result.size());
    }

    @Test
    @Transactional
    @DisplayName("댓글번호 5번 테스트")
    public void findByReplyIdTest(){
        long replyId = 5;
        String replyWriter = "우르르";
        String replyContent = "용병단";

        ReplyFindByIdDTO result = replyService.findByReplyId(replyId);

        assertEquals(replyId, result.getReplyId());
        assertEquals(replyWriter, result.getReplyWriter());
        assertEquals(replyContent, result.getReplyContent());
    }

    @Test
    @Transactional
    @DisplayName("댓글번호 2번 삭제테스트(연동된것까지삭제) 및 전체 개수 조회")
    public void deleteByReplyIdTest(){
        long replyId = 2;
        long blogId = 3;

        replyService.deleteByReplyId(replyId);

        assertEquals(1, replyService.findAllByBlogId(blogId).size());
        assertNull(replyService.findByReplyId(replyId));
    }

    @Test
    @Transactional
    @DisplayName("save(insert) Test")
    public void saveTest(){
        long blogId = 3;
        String replyWriter = "git-flow마스터";
        String replyContent = "git-flow하는법";

        ReplyInsertDTO replyInsertDTO = ReplyInsertDTO.builder()
                .blogId(blogId)
                .replyWriter(replyWriter)
                .replyContent(replyContent)
                .build();

        replyService.save(replyInsertDTO);

        assertEquals(blogId, replyInsertDTO.getBlogId());
        assertEquals(replyWriter, replyInsertDTO.getReplyWriter());
        assertEquals(replyContent, replyInsertDTO.getReplyContent());
    }

}
