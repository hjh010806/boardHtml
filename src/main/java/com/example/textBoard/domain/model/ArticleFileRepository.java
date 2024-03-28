package com.example.textBoard.domain.model;

import com.example.textBoard.base.CommonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ArticleFileRepository implements Repository {

    CommonUtil commonUtil = new CommonUtil();
    int latestArticleId = 1;
    private ArrayList<Article> articleList = new ArrayList<>();

    public ArticleFileRepository() {
        this.articleList = loadArticle();
        if (articleList.size() == 0) {
            latestArticleId = 0;
            return;
        }
        this.latestArticleId = this.articleList.get((articleList.size() - 1)).getId();
    }

    public void makeTestData(){
        System.out.println("테스트데이터를 생성하지 않습니다.");
    }

    public Article saveArticle(String title, String body) {
        // 제목이 title, 내용이 body, 조회수 0, 등록날짜 현재시간인 게시물을
        // json 파일로 저장
        latestArticleId++;
        Article article = new Article(latestArticleId, title, body, 0, commonUtil.getCurrentDateTime());
        articleList.add(article);

        ObjectMapper mapper = new ObjectMapper();

        try {
            // 객체를 JSON 형태로 변환하여 파일에 저장
            mapper.writeValue(new File("article.json"), articleList);
            System.out.println("객체가 JSON 형태로 파일에 저장되었습니다.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return article;
    }

    public ArrayList<Article> findAll() {
        //JSOn 파일
        String filePath = "Article.json";
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // JSON 파일 읽기
            File file = new File(filePath);

            // JSON 파일을 자바 객체로 변환
            ArrayList<Article> article = objectMapper.readValue(file, new TypeReference<ArrayList<Article>>() {
            });
            return article;

        } catch (IOException e) {
            System.out.println("An error occurred while reading object from JSON file.");
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("asd");
        }
        return articleList;
    }

    public ArrayList<Article> loadArticle() {
        String filePath = "Article.json";
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // JSON 파일 읽기
            File file = new File(filePath);

            // JSON 파일을 자바 객체로 변환
            ArrayList<Article> article = objectMapper.readValue(file, new TypeReference<ArrayList<Article>>() {
            });
            return article;

        } catch (IOException e) {
            System.out.println("An error occurred while reading object from JSON file.");
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("da");
        }
        return articleList;
    }

    public Article findArticleById(int id) {
        // id에 해당하는 게시물(article 반환)
        for (int i = 0; i < articleList.size(); i++) {
            Article a1 = articleList.get(i);
            if (a1.getId() == id) {
                return a1;
            }
        }
        return null;
    }

    public void deleteArticle(Article article) {
        articleList.remove(article);

        String filePath = "Article.json";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // JSON 파일 읽기
            File file = new File(filePath);

            objectMapper.writeValue(file, articleList);
        } catch (IOException e) {
            System.out.println("An error occurred while reading object from JSON file.");
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("da");
        }
    }

    public void updateArticle(Article article, String newTitle, String newBody) {
        article.setTitle(newTitle);
        article.setBody(newBody);

        String filePath = "Article.json";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // JSON 파일 읽기
            File file = new File(filePath);

            //
            objectMapper.writeValue(file, articleList);
        } catch (IOException e) {
            System.out.println("An error occurred while reading object from JSON file.");
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("da");
        }
    }


    public ArrayList<Article> findArticleByKeyword(String keyword) {
        ArrayList<Article> searchedList = new ArrayList<>();

        for (int i = 0; i < articleList.size(); i++) {
            Article article = articleList.get(i);
            if (article.getTitle().contains(keyword)) {
                searchedList.add(article);
            }
        }

        return searchedList;
    }

    public void hitSave(Article article) {
        String filePath = "Article.json";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // JSON 파일 읽기
            File file = new File(filePath);

            //
            objectMapper.writeValue(file, articleList);
        } catch (IOException e) {
            System.out.println("An error occurred while reading object from JSON file.");
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("da");
        }
    }
}
