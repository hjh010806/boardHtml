package com.example.textBoard.domain.controller;

import com.example.textBoard.base.CommonUtil;
import com.example.textBoard.domain.model.Article;
import com.example.textBoard.domain.model.ArticleFileRepository;
import com.example.textBoard.domain.model.ArticleMySQLRepository;
import com.example.textBoard.domain.model.Repository;
import com.example.textBoard.domain.view.ArticleView;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Scanner;

// Model - Controller - View
@Controller
public class ArticleController { // Model + Controller

    CommonUtil commonUtil = new CommonUtil();
    ArticleView articleView = new ArticleView();
    Repository articleRepository = new ArticleFileRepository(); // 메모리 DB

    Scanner scan = commonUtil.getScanner();
    int WRONG_VALUE = -1;

    @RequestMapping("/search")
    @ResponseBody
    public ArrayList<Article> search(@RequestParam("keyword") String keyword) {
        // 검색어를 입력
//        System.out.print("검색 키워드를 입력해주세요 :");
//        String keyword = scan.nextLine();
        ArrayList<Article> searchedList = articleRepository.findArticleByKeyword(keyword);

//        if (searchedList == null) {
//            System.out.println("없는 게시물입니다.");
//            return null;
//        }

//        articleView.printArticleList(searchedList);
        return searchedList;
    }

    @RequestMapping("/detail")
    @ResponseBody
    public Article detail(@RequestParam("num") int num) {
//        System.out.print("상세보기 할 게시물 번호를 입력해주세요 : ");

//        int inputId = getParamAsInt(num, WRONG_VALUE);
//        if (inputId == WRONG_VALUE) {
//            return null;
//        }

        Article article = articleRepository.findArticleById(num);

//        if (article == null) {
//            System.out.println("없는 게시물입니다.");
//            return ;
//        }

        article.increaseHit();
        articleView.printArticleDetail(article);
        articleRepository.hitSave(article);

        return article;


    }

    @RequestMapping("/delete")
    @ResponseBody
    public String delete(@RequestParam("num") int num) {

//        System.out.print("삭제할 게시물 번호를 입력해주세요 : ");

//        int inputId = getParamAsInt(num, WRONG_VALUE);
//        if (inputId == WRONG_VALUE) {
//            return null;
//        }

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
//        System.out.print("수정할 게시물 번호를 입력해주세요 : ");

//        int inputId = getParamAsInt(num, WRONG_VALUE);
//        if(inputId == WRONG_VALUE) {
//            return null;
//        }

        Article article = articleRepository.findArticleById(num);

        if (article == null) {

            return "없는 게시물입니다.";
        }

//        System.out.print("새로운 제목을 입력해주세요 : ");
//        String newTitle = scan.nextLine();

//        System.out.print("새로운 내용을 입력해주세요 : ");
//        String newBody = scan.nextLine();

        articleRepository.updateArticle(article, title, body);
//        System.out.printf("%d번 게시물이 수정되었습니다.\n", inputId);
        return "%d번 게시물이 수정되었습니다.".formatted(num);
    }

    @RequestMapping("/list")
    @ResponseBody
    public ArrayList<Article> list() {
        ArrayList<Article> articleList = articleRepository.findAll();

        return articleList;
//        articleView.printArticleList(articleList); // 전체 출력 -> 전체 저장소 넘기기
    }

    @RequestMapping("/add")
    @ResponseBody
    public String add(@RequestParam("title") String title, @RequestParam("body") String body) {
//        System.out.print("게시물 제목을 입력해주세요 : ");
//        String title = scan.nextLine();
//
//        System.out.print("게시물 내용을 입력해주세요 : ");
//        String body = scan.nextLine();

        articleRepository.saveArticle(title, body);
//        System.out.println("게시물이 등록되었습니다.");
        return "게시물이 등록되었습니다.";
    }

//    private int getParamAsInt(String param, int defaultValue) {
//        try {
//            return Integer.parseInt(param);
//        } catch (NumberFormatException e) {
//            System.out.println("숫자를 입력해주세요.");
//            return defaultValue;
//        }
//    }
}