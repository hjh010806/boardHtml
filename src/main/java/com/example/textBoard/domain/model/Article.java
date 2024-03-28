package com.example.textBoard.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Article {
    private int id; // 번호
    private String title; // 제목

    private String body; // 내용

    private int hit; // 조회수

    private String regDate; // 등록날짜


    public void increaseHit() {
        this.hit++;
    }
    Article(String title, String body, int hit, String regdate){
        this.title = title;
        this.body = body;
        this.hit = hit;
        this.regDate = regdate;
    }
    Article(int id,String title, String content, String regDate){
        this.id = id;
        this.title = title;
        this.body = content;
        this.regDate = regDate;
    }
}