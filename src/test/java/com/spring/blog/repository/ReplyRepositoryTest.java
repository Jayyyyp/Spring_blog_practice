package com.spring.blog.repository;

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
    @DisplayName("2번 댓글의 작성자는 짹짹이, 댓글내용은 2빠댓글인지 확인")
    public void findByReplyIdTest(){
        long replyId = 2;

        ReplyFindByIdDTO result = replyRepository.findByReplyId(replyId);

        assertEquals("짹짹이", result.getReplyWriter());
        assertEquals("2빠댓글", result.getReplyContent());

    }
    @Test
    @Transactional
    @DisplayName("2번 글에 연동된 댓글번호 2번 삭제 후, 2번 글의 댓글은 3개일 것이고, 2번 댓글번호 조회시, null인지 확인")
    public void deleteByReplyIdTest(){
        long replyId = 2; // 2번글에 연동된 댓글
        long blogId = 2; // 글번호 2번

        replyRepository.deleteByReplyId(replyId);

        assertNull(replyRepository.findByReplyId(replyId));
        assertEquals(3, replyRepository.findAllByBlogId(blogId).size());
    }
    @Test
    @Transactional
    @DisplayName("3번 글에 새로운 댓글 삽입, 댓글 작성자와 댓글 본문을 확인")
    public void saveTest(){
        long blogId = 3;
        String replyWriter = "신데렐라";
        String replyContent = "새로운본문";

        ReplyInsertDTO result = ReplyInsertDTO.builder()
                .blogId(blogId)
                .replyWriter(replyWriter)
                .replyContent(replyContent)
                .build();

        replyRepository.save(result);

        // blogId번 글의 전체 댓글을 가져와서 getter를 이용하여 위에서 넣은 fixture와 일치하는지 확인
        List<ReplyFindByIdDTO> resultList = replyRepository.findAllByBlogId(blogId);
        // resultList의 개수 - 1이 마지막 인덱스의 번호이므로, resultList에서 마지막 인덱스 요소만 가져오기
        ReplyFindByIdDTO resultLength = resultList.get(resultList.size() - 1);

        assertEquals(2, replyRepository.findAllByBlogId(blogId).size());
        assertEquals(replyWriter, resultLength.getReplyWriter());
        assertEquals(replyContent, resultLength.getReplyContent());
    }
    @Test
    @Transactional
    @DisplayName("")
    public void updateTest(){
        long replyId = 2;
        String replyWriter = "수정자";
        String replyContent = "댓글수정";

        ReplyUpdateDTO replyUpdateDTO = ReplyUpdateDTO.builder()
                .replyId(replyId)
                .replyWriter(replyWriter)
                .replyContent(replyContent)
                .build();

        replyRepository.update(replyUpdateDTO);

        ReplyFindByIdDTO result = replyRepository.findByReplyId(replyId);
        assertEquals(replyWriter, result.getReplyWriter());
        assertEquals(replyContent, result.getReplyContent());
        // 수정시, publishedAt보다 updateAt이 무조건 늦을 것이기 때문에, 참인지 확인
        assertTrue(result.getUpdatedAt().isAfter(result.getPublishedAt()));
    }
}
