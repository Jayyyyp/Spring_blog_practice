package com.spring.blog.controller;

import com.spring.blog.dto.BmiDTO;
import com.spring.blog.dto.PersonDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // 문자열을 그대로 리턴시켜줄수있음
@RequestMapping("/resttest")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class RESTApiController {
    @GetMapping("/hello")
    public String hello(){
        return "안녕하세요";
    }

    @GetMapping("/foods")
    public List<String> foodList() {
        List<String> foodList = List.of("탕수육", "똠양꿍", "돈카츠");
        return foodList;
    }

    @GetMapping("/person")
    public PersonDTO person(){
        PersonDTO p = PersonDTO.builder()
                .id(1L)
                .name("좋코더")
                .age(20)
                .build();
        return p;
    }

    @GetMapping("/person-list")
    public ResponseEntity<?> personList(){
        PersonDTO p1 = PersonDTO.builder().id(1L).name("룰룰라").age(20).build();
        PersonDTO p2 = PersonDTO.builder().id(2L).name("팔랄라").age(30).build();
        PersonDTO p3 = PersonDTO.builder().id(3L).name("챨랄라").age(40).build();
        List<PersonDTO> personList = List.of(p1, p2, p3);

        return ResponseEntity.ok().body(personList);
    }

    @GetMapping("/bmi")
    public ResponseEntity<?> bmi(BmiDTO bmi){ // 커맨드 객체 형식

        if(bmi.getHeight() == 0){
            return ResponseEntity.badRequest().body("키에 0을 넣지말아주시고 제대로 넣어주세요");
        }
        double result = bmi.getWeight() / ((bmi.getHeight()/100) * (bmi.getHeight()/100));
        HttpHeaders headers = new HttpHeaders();
        headers.add("fruits", "melon");
        headers.add("lunch", "pizza");

        return ResponseEntity.ok().headers(headers).body(result);
    }

    //Postman 활용
    @GetMapping("/bmi2")
    public ResponseEntity<?> bmi2(@RequestBody BmiDTO bmi){ // 커맨드 객체 형식

        if(bmi.getHeight() == 0){
            return ResponseEntity.badRequest().body("키에 0을 넣지말아주시고 제대로 넣어주세요");
        }
        double result = bmi.getWeight() / ((bmi.getHeight()/100) * (bmi.getHeight()/100));
        HttpHeaders headers = new HttpHeaders();
        headers.add("fruits", "melon");
        headers.add("lunch", "pizza");

        return ResponseEntity.ok().headers(headers).body(result);
    }
}
