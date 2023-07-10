package com.spring.blog.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc // MVC 테스트는 브라우저를 켜야 원래 테스트가 가능하므로 브라우저를 대체할 객체를 만들어 수행
class ReplyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    // 컨트롤러를 테스트해야 하는데 컨트롤러는 서버에 url만 입력하면 동작하므로 컨트롤러를 따로 생성하지 않는다.
    // 각 테스트 전에 설정하기
    @BeforeEach
    public void setMockMvc(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    @Test
    @DisplayName("2번 글에 대한 전체 댓글을 조회했을 때 0번째 요소의 replyWriter는 댓글쓴사람, replyId는 1")
    void findAllReplies() throws Exception{ // mockMvc 예외를 던져줄 예외 설정
        // given : fixture 설정, 접속 주소 설정
        String replyWriter = "댓글쓴사람";
        long replyId = 1;
        String url = "/reply/2/all";

        // when : 위에 설정한 주소로 접속 후 json 데이터 리턴받아 저장하기, ResultActions 형 자료로 json 저장하기
        // get() 메서드의 경우 작성 후 alt+enter 눌러 mockmvc 관련 요소로 import
                                    // fetch(url, {method:'get'}).then(res => res.json()); 에 대응하는 코드
        final ResultActions result = mockMvc.perform(get(url) // url 주소로 get 요청 넣기
                                            .accept(MediaType.APPLICATION_JSON)); // 리턴 자료가 json임을 명시

        // then : 리턴받은 json 목록의 0번째 요소의 replyWriter와 replyId가 예상과 일치하는지 확인
        result
                .andExpect(status().isOk()) // 200 코드가 출력되는지 확인
                .andExpect(jsonPath("$[0].replyWriter").value(replyWriter)) // 첫 json의 replyWriter
                .andExpect(jsonPath("$[0].replyId").value(replyId)); // 첫 json의 replyId 검사
    }

    @Test
    @Transactional
    @DisplayName("replyId 2번 조회시 얻어진 json 객체의 replyWriter는 짹짹이, blogId는 2번")
    public void findByReplyIdTest() throws Exception {
        // given
        long replyId = 2;
        String replyWriter = "짹짹이";
        String url = "/reply/2";

        // when : 위에 설정한 url로 접속 후 json 데이터 리턴받아 저장하기
        final ResultActions result = mockMvc.perform(get(url)
                                            .accept(MediaType.APPLICATION_JSON));

        // then :
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.replyWriter").value(replyWriter))
                .andExpect(jsonPath("$.replyId").value(replyId));
    }
}