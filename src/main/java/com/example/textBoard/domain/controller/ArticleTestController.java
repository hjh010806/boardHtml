//package com.example.textBoard.domain.controller;
//
//import org.example.base.CommonUtil;
//import org.example.domain.model.Article;
//import org.example.domain.view.ArticleTestView;
//import org.example.domain.view.ArticleView;
//import org.example.domain.model.ArticleFileRepository;
//
//import java.util.ArrayList;
//import java.util.Scanner;
//import java.util.SplittableRandom;
//
//public class ArticleTestController {
//    CommonUtil commonUtil = new CommonUtil();
//    ArticleTestView articleView = new ArticleTestView();
//    ArticleFileRepository articleFileRepository = new ArticleFileRepository();
//
//    Scanner scan = commonUtil.getScanner();
//    int WRONG_VALUE = -1;
//
//    public Article findArticleById(int index) {
//        return articleFileRepository.findById(index);
//
//    }
//    public void add() {
//
//        System.out.print("게시물 제목을 입력해주세요 : ");
//        String title = scan.nextLine();
//
//        System.out.print("게시물 내용을 입력해주세요 : ");
//        String body = scan.nextLine();
//
//        articleFileRepository.saveArticle(title, body);
//        System.out.println("게시물이 등록되었습니다.");
//
//    }
//
//    public void list() {
//        ArrayList<Article> articleList = articleFileRepository.findAll();
//        articleView.printArticleList(articleList); // 전체 출력 -> 전체 저장소 넘기기
//    }
//    public void delete() {
//        System.out.print("삭제할 게시물 번호를 입력해주세요 : ");
//
//        int inputId = getParamAsInt(scan.nextLine(), WRONG_VALUE);
//        if(inputId == WRONG_VALUE) {
//            return;
//        }
//
//        Article a1 = findArticleById(inputId);
//
//        if (a1 == null) {
//            System.out.println("없는 게시물입니다.");
//            return;
//        }
//
//        articleFileRepository.deleteArticle(a1);
//        System.out.printf("%d번 게시물이 삭제되었습니다.\n", a1.getId());
//    }
//
//    public void update() {
//        System.out.print("수정할 게시물 번호를 입력해주세요 : ");
//
//        int inputId = getParamAsInt(scan.nextLine(), WRONG_VALUE);
//        if(inputId == WRONG_VALUE) {
//            return;
//        }
//
//        Article article = findArticleById(inputId);
//
//        if (article == null) {
//            System.out.println("없는 게시물입니다.");
//            return;
//        }
//
//        System.out.print("새로운 제목을 입력해주세요 : ");
//        String newTitle = scan.nextLine();
//
//        System.out.print("새로운 내용을 입력해주세요 : ");
//        String newBody = scan.nextLine();
//
//        articleFileRepository.updateArticle(article, newTitle, newBody);
//        System.out.printf("%d번 게시물이 수정되었습니다.\n", inputId);
//    }
//
//    public void detail() {
//        System.out.print("상세보기 할 게시물 번호를 입력해주세요 : ");
//
//        int inputId = getParamAsInt(scan.nextLine(), WRONG_VALUE);
//        if(inputId == WRONG_VALUE) {
//            return;
//        }
//
//        Article article = findArticleById(inputId);
//
//        if (article == null) {
//            System.out.println("없는 게시물입니다.");
//            return;
//        }
//
//        article.increaseHit();
//        articleView.printArticleDetail(article);
//        articleFileRepository.hitSave();
//
//
//    }
//
//    public void search() {
//        // 검색어를 입력
//        System.out.print("검색 키워드를 입력해주세요 :");
//        String keyword = scan.nextLine();
//        ArrayList<Article> searchedList = articleFileRepository.findArticleByKeyword(keyword);
//
//        articleView.printArticleList(searchedList);
//    }
//
//    private int getParamAsInt(String param, int defaultValue) {
//        try {
//            return Integer.parseInt(param);
//        } catch (NumberFormatException e) {
//            System.out.println("숫자를 입력해주세요.");
//            return defaultValue;
//        }
//    }
//}
