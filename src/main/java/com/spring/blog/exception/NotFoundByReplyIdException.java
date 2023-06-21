package com.spring.blog.exception;

public class NotFoundByReplyIdException extends RuntimeException{
    // 생성자에 에러 사유를 전달할 수 있게 메세지를 적기

    public NotFoundByReplyIdException(String message){
        super(message);
    }
}
