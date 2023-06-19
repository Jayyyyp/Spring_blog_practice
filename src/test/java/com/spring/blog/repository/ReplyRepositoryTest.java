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
    @DisplayName("댓글번호 3번 자료의 댓글은 3번이고, 글쓴이는 바둑이")
    public void findByReplyIdTest(){
        long replyId = 3;
        String replyWriter = "바둑이";
        String replyContent = "3빠댓글";

        ReplyFindByIdDTO replyFindByIdDTO = replyRepository.findByReplyId(replyId);

        assertEquals(replyWriter, replyFindByIdDTO.getReplyWriter());
        assertEquals(replyContent, replyFindByIdDTO.getReplyContent());

    }

    @Test
    @Transactional
    @DisplayName("댓글번호 2번을 삭제한 다음, 전체 데이터 개수가 4개로, 그리고 2번으로 재조회시 null일 것이다. ")
    public void deletebyReplyIdTest(){
        long replyId = 2;
        long blogId = 2;

        replyRepository.deleteByReplyId(replyId);

        assertEquals(3, replyRepository.findAllByBlogId(blogId).size());
        assertNull(replyRepository.findByReplyId(replyId));
    }

    @Test
    @Transactional
    @DisplayName("픽스처를 이용해 INSERT후, 전체 데이터를 가져와서 마지막 인덱스 번호 요소를 얻어와 입력했던 fixture와 비교하면 같다")
    public void saveTest(){
        long blogId = 1;
        String replyWriter = "도비의스프링";
        String replyContent = "도비는자유입니다!!";
        ReplyInsertDTO replyInsertDTO = ReplyInsertDTO.builder()
                .blogId(blogId)
                .replyWriter(replyWriter)
                .replyContent(replyContent)
                .build();
        replyRepository.save(replyInsertDTO);

        List<ReplyFindByIdDTO> resultList = replyRepository.findAllByBlogId(blogId);
        ReplyFindByIdDTO result = resultList.get(resultList.size()-1);

        assertEquals(replyWriter, result.getReplyWriter());
        assertEquals(replyContent, result.getReplyContent());
    }

    @Test
    @Transactional
    @DisplayName("fixture로 수정할 글쓴이, 댓글내용, 3번 replyId를 지정한다. 수정 후 3번 자료를 DB에서 꺼내 fixture 비교 및 published_at과 updated_at이 다른지 확인 ")
    public void updateTest(){
        long replyId = 3;
        String replyWriter = "수정된글쓴이";
        String replyContent = "수정한내용물이야";
        ReplyUpdateDTO replyUpdateDTO = ReplyUpdateDTO.builder()
                .replyId(replyId)
                .replyWriter(replyWriter)
                .replyContent(replyContent)
                .build();

        replyRepository.update(replyUpdateDTO);

        ReplyFindByIdDTO result = replyRepository.findByReplyId(replyId);
        assertEquals(replyWriter, result.getReplyWriter());
        assertEquals(replyContent, result.getReplyContent());
        // System.out.println(result); // 눈으로 확인
        assertTrue(result.getUpdatedAt().isAfter(result.getPublishedAt()));
    }
}
