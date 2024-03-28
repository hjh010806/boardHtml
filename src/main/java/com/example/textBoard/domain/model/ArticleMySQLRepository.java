package com.example.textBoard.domain.model;


import com.example.textBoard.base.CommonUtil;

import java.sql.*;
import java.util.ArrayList;

public class ArticleMySQLRepository implements Repository {
    ArrayList<Article> articleList = new ArrayList<>();
    CommonUtil commonUtil = new CommonUtil();
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/t2";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    @Override
    public void makeTestData() {

    }

    @Override
    public ArrayList<Article> findArticleByKeyword(String keyword) {
        try {
            // 데이터베이스에 연결
            Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

            // SQL 문 생성
            String sql = "SELECT * FROM article WHERE title LIKE ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%"+ keyword+ "%");

            // SQL 문 실행 및 결과 가져오기
            ResultSet resultSet = preparedStatement.executeQuery();

            // 결과 처리
                if(resultSet.next()) {
                    do {
                        // 결과에서 각 컬럼의 값을 가져와 Article 객체 생성
                        int id = resultSet.getInt("id");
                        String title = resultSet.getString("title");
                        String content = resultSet.getString("body");
                        String regDate = resultSet.getString("regDate");
                        // 가져온 데이터로 Article 객체 생성 후 리스트에 추가
                        Article article = new Article(id, title, content, regDate);
                        articleList.add(article);
                    }   while (resultSet.next());
               }else{
                    return null;
                }
            // 리소스 해제 (ResultSet 먼저 닫기)
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return articleList;
    }


@Override
public Article findArticleById(int id) {
    try {
        // 데이터베이스에 연결
        Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

        // SQL 문 생성
        String sql = "SELECT * FROM article WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);

        // SQL 문 실행 및 결과 가져오기
        ResultSet resultSet = preparedStatement.executeQuery();

        // 결과 반복문을 통해 게시물 객체 생성하여 리스트에 추가
        while (resultSet.next()) {
            int articleid = resultSet.getInt("id");
            String title = resultSet.getString("title");
            String body = resultSet.getString("body");
            int hit = resultSet.getInt("hit");
            String regDate = resultSet.getString("regDate");

            // 새로운 게시물 객체 생성하여 리스트에 추가
            Article article = new Article(articleid, title, body, hit, regDate);
            return article;
        }

        // 리소스 해제
        resultSet.close();
        preparedStatement.close();
        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}

@Override
public void deleteArticle(Article article) {
    Article a1 = new Article();
    try {
        // 데이터베이스에 연결
        Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

        // SQL 문 생성
        String sql = "DELETE FROM article WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        // 기존 게시물의 ID를 설정
        preparedStatement.setInt(1, article.getId());

        // SQL 문 실행
        preparedStatement.executeUpdate();

        // 리소스 해제
        preparedStatement.close();
        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }

}

@Override
public void updateArticle(Article article, String newTitle, String newBody) {
    Article a1 = article;
    a1.setTitle(newTitle);
    a1.setBody(newBody);
    a1.setRegDate(commonUtil.getCurrentDateTime());
    try {
        // 데이터베이스에 연결
        Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

        // SQL 문 생성
        String sql = "UPDATE article SET title = ?, body = ?, regDate = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        // 변경된 제목과 내용, 그리고 수정된 시간을 설정
        preparedStatement.setString(1, a1.getTitle());
        preparedStatement.setString(2, a1.getBody());
        preparedStatement.setString(3, a1.getRegDate());

        // 기존 게시물의 ID를 설정
        preparedStatement.setInt(4, article.getId());

        // SQL 문 실행
        preparedStatement.executeUpdate();

        // 리소스 해제
        preparedStatement.close();
        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

@Override
public ArrayList<Article> findAll() {
        ArrayList<Article> findArticle = new ArrayList<>();
    try {
        // 데이터베이스에 연결
        Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

        // SQL 문 생성
        String sql = "SELECT * FROM article";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        // SQL 문 실행 및 결과 가져오기
        ResultSet resultSet = preparedStatement.executeQuery();

        // 결과 처리
        if(resultSet.next()) {
            do{
                // 결과에서 각 컬럼의 값을 가져와 Article 객체 생성
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String content = resultSet.getString("body");
                String regDate = resultSet.getString("regDate");
                // 가져온 데이터로 Article 객체 생성 후 리스트에 추가
                Article article = new Article(id, title, content, regDate);
                findArticle.add(article);
            }while (resultSet.next());
        }else{
            return null;
        }
        // 리소스 해제 (ResultSet 먼저 닫기)
        resultSet.close();
        preparedStatement.close();
        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return findArticle;
}


@Override
public Article saveArticle(String title, String body) {

    // article 테이블에 게시물 저장
    try {
        // 데이터베이스에 연결
        Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

        // 게시물 객체 생성
        Article article = new Article(title, body, 0, commonUtil.getCurrentDateTime());

        // SQL 문 생성
        String sql = "INSERT INTO article (title, body, hit, regDate) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        // 게시물 객체의 필드 값을 SQL 문에 설정
        preparedStatement.setString(1, article.getTitle());
        preparedStatement.setString(2, article.getBody());
        preparedStatement.setInt(3, article.getHit());
        preparedStatement.setString(4, article.getRegDate());

        // SQL 문 실행
        preparedStatement.executeUpdate();

        // 리소스 해제
        preparedStatement.close();
        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }


    return null;
}

public void hitSave(Article article) {
    try {
        // 데이터베이스에 연결
        Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

        // SQL 문 생성
        String sql = "UPDATE article SET hit = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        // 변경된 조회수를 설정
        preparedStatement.setInt(1, article.getHit());

        // 기존 게시물의 ID를 설정
        preparedStatement.setInt(2, article.getId());

        // SQL 문 실행
        preparedStatement.executeUpdate();

        // 리소스 해제
        preparedStatement.close();
        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
}
