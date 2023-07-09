package com.spring.blog.service;

import com.spring.blog.dto.reply.ReplyFindByIdDTO;
import com.spring.blog.dto.reply.ReplyInsertDTO;
import com.spring.blog.dto.reply.ReplyUpdateDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ReplyServiceTest {

    // 서비스 객체 세팅
    @Autowired
    ReplyService replyService;

    @Test
    @Transactional
    @DisplayName("2번글과 연동된 댓글 전체를 가져와 개수가 4개일 것이라 단언")
    public void findAllByBlogIdTest(){
        long blogId = 2;

        List<ReplyFindByIdDTO> result = replyService.findAllByBlogId(blogId);

        assertEquals(4, result.size());
    }

    @Test
    @Transactional
    @DisplayName("댓글번호 5번의 이름은  개발고수이고, replyId는 5일 것이다.")
    public void findByReplyIdTest(){
        long replyId = 5;
        String replyWriter = "개발고수";

        ReplyFindByIdDTO replyFindByIdDTO = replyService.findByReplyId(replyId);

        assertEquals(replyId, replyFindByIdDTO.getReplyId());
        assertEquals(replyWriter, replyFindByIdDTO.getReplyWriter());
    }

    @Test
    @Transactional
    @DisplayName("댓글번호 1번 삭제 시, 2번 포스팅의 댓글은 3개이고, 1번 댓글로 재요청시, null")
    public void deleteByReplyIdTest(){
        long replyId = 1;
        long blogId = 2;

        replyService.deleteByReplyId(replyId);

        assertEquals(3, replyService.findAllByBlogId(blogId).size());
        assertNull(replyService.findByReplyId(replyId));
    }

    @Test
    @Transactional
    @DisplayName("replyWriter는 토비, replyContent는 토비보자, blogId는 3번으로 입력한 후, 연동댓글은 2개")
    public void saveTest(){
        String replyWriter = "토비";
        String replyContent = "토비보자";
        long blogId = 3;
        long replyId = 2;
        ReplyInsertDTO replyInsertDTO = ReplyInsertDTO.builder()
                .replyWriter(replyWriter)
                .replyContent(replyContent)
                .blogId(blogId)
                .build();

        replyService.save(replyInsertDTO);

        List<ReplyFindByIdDTO> resultList = replyService.findAllByBlogId(blogId);
        assertEquals(2, resultList.size());

        ReplyFindByIdDTO result = resultList.get(resultList.size() - 1);
        assertEquals(replyWriter, result.getReplyWriter());
        assertEquals(replyContent, result.getReplyContent());

    }

    @Test
    @Transactional
    @DisplayName("replyId 2번의 replyWriter를 바뀐짹짹이, replyContent를 부엉부엉, updatedAt이 publishedAt보다 뒤인 시간으로")
    public void updateTest(){
        long replyId = 2;
        String replyWriter = "바뀐짹짹이";
        String replyContent = "부엉부엉";

        ReplyUpdateDTO replyUpdateDTO = new ReplyUpdateDTO();
        replyUpdateDTO.setReplyId(replyId);
        replyUpdateDTO.setReplyWriter(replyWriter);
        replyUpdateDTO.setReplyContent(replyContent);

        replyService.update(replyUpdateDTO);

        ReplyFindByIdDTO result = replyService.findByReplyId(replyId);
        assertEquals(replyWriter, result.getReplyWriter());
        assertEquals(replyContent, result.getReplyContent());
        assertTrue(result.getUpdatedAt().isAfter(result.getPublishedAt()));
    }

}
