package com.example.textBoard.domain.controller;

import com.example.textBoard.base.CommonUtil;
import com.example.textBoard.domain.model.*;
import com.example.textBoard.domain.view.ArticleView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// Model - Controller - View
@Controller
public class ArticleController { // Model + Controller

    CommonUtil commonUtil = new CommonUtil();
    Repository articleRepository = new ArticleMemRepository(); // 메모리 DB
    @RequestMapping("/search")
    @ResponseBody
    public ArrayList<Article> search(@RequestParam(value = "keyword", defaultValue = "") String keyword) {

        ArrayList<Article> searchedList = articleRepository.findArticleByKeyword(keyword);
        return searchedList;
    }

    @RequestMapping("/detail")
    @ResponseBody
    public String detail(@RequestParam("num") int num) {

        Article article = articleRepository.findArticleById(num);

        if (article == null) {
            return "없는 게시물 입니다.";
        }

        article.increaseHit();
        articleRepository.hitSave(article);

        // 객체를 -> json 문자열로 반환 -> jackson 라이브러리 사용
        // ObjectMapper 생성
        ObjectMapper objectMapper = new ObjectMapper();

        // Java 객체를 JSON 문자열로 변환 (직렬화)
        try {
            String json = objectMapper.writeValueAsString(article);
            return json;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return "";


    }

    @RequestMapping("/delete")
    @ResponseBody
    public String delete(@RequestParam("num") int num) {

        Article article = articleRepository.findArticleById(num);

        if (article == null) {
            return "없는 게시물입니다.";
        }

        articleRepository.deleteArticle(article);
//        System.out.printf("%d 게시물이 삭제되었습니다.\n", inputId);
        return num + "번 게시물이 삭제되었습니다.";
    }

    @RequestMapping("/update")
    @ResponseBody
    public String update(@RequestParam("num") int num,
                         @RequestParam("title") String title,
                         @RequestParam("body") String body
                        ) {

        Article article = articleRepository.findArticleById(num);

        if (article == null) {

            return "없는 게시물입니다.";
        }
        articleRepository.updateArticle(article, title, body);
        return "%d번 게시물이 수정되었습니다.".formatted(num);
    }

    @RequestMapping("/list")
    @ResponseBody
    public ArrayList<Article> list() {
        ArrayList<Article> articleList = articleRepository.findAll();

        return articleList;
    }

    @RequestMapping("/add")
    @ResponseBody
    public String add(@RequestParam("title") String title, @RequestParam("body") String body) {

        articleRepository.saveArticle(title, body);
        return "게시물이 등록되었습니다.";
    }

}