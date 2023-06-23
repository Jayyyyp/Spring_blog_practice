package com.spring.blog.service;

import com.spring.blog.dto.reply.ReplyResponseDTO;
import com.spring.blog.dto.reply.ReplyCreateResponseDTO;
import com.spring.blog.dto.reply.ReplyUpdateRequestDTO;
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

        List<ReplyResponseDTO> result = replyService.findAllByBlogId(blogId);

        assertEquals(4, result.size());
    }

    @Test
    @Transactional
    @DisplayName("댓글번호 5번 테스트")
    public void findByReplyIdTest(){
        long replyId = 5;
        String replyWriter = "우르르";
        String replyContent = "용병단";

        ReplyResponseDTO result = replyService.findByReplyId(replyId);

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

        ReplyCreateResponseDTO replyInsertDTO = ReplyCreateResponseDTO.builder()
                .blogId(blogId)
                .replyWriter(replyWriter)
                .replyContent(replyContent)
                .build();

        replyService.save(replyInsertDTO);


        List<ReplyResponseDTO> resultList = replyService.findAllByBlogId(blogId);
        ReplyResponseDTO result = resultList.get(resultList.size() - 1);
        // 마지막 인덱스에 추가되었을 것이라 resultList.size()에 -1을 함

        assertEquals(replyWriter, result.getReplyWriter());
        assertEquals(replyContent, result.getReplyContent());
    }

//    @Test
//    @Transactional
//    @DisplayName("insertErrorCheckException")
//    public void errorCheckTest(){
//        assertThrows(DataIntegrityViolationException.class, () ->{
//        long blogId = 3;
//        String replyWriter = null;
//        String replyContent = "git-flow하는법";
//
//        ReplyInsertDTO replyInsertDTO = ReplyInsertDTO.builder()
//                .replyWriter(replyWriter)
//                .replyContent(replyContent)
//                .blogId(blogId)
//                .build();
//        replyService.save(replyInsertDTO);
//
//        List<ReplyFindByIdDTO> resultList = replyService.findAllByBlogId(blogId);
//        ReplyFindByIdDTO result = resultList.get(resultList.size() - 1);
//        assertEquals("git-flow 마스터", result.getReplyWriter());
//        assertEquals(replyContent, result.getReplyContent());
//        });
//    }

    @Test
    @Transactional
    @DisplayName("update Test")
    public void updateTest(){
        long replyId = 2;
        String replyWriter = "너굴맨";
        String replyContent = "너굴너굴";

        ReplyUpdateRequestDTO replyUpdateRequestDTO = ReplyUpdateRequestDTO.builder()
                .replyId(replyId)
                .replyWriter(replyWriter)
                .replyContent(replyContent)
                .build();

        replyService.update(replyUpdateRequestDTO);

        ReplyResponseDTO result = replyService.findByReplyId(replyId);
        assertEquals(replyWriter, result.getReplyWriter());
        assertEquals(replyContent, result.getReplyContent());
        assertTrue(result.getUpdatedAt().isAfter(result.getPublishedAt()));
        // 업데이트한 시간이 출판한 시간보다 이후일 것이다.
    }

    // blog와 함께 reply가 삭제되는 케이스는 따로 다시 테스트코드를 하나 더 작성하는 것이 좋다.
}
