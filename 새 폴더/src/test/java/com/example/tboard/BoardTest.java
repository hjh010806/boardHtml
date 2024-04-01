package com.example.tboard;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BoardTest {
    private final InputStream systemInBackup = System.in; // 원래의 System.in 백업
    private ByteArrayInputStream testIn;

    @BeforeEach
    public void setUpInput() {
        String testString = """
                hihi
                byebye
                kkkk
                """; // 테스트를 위한 입력 값
        testIn = new ByteArrayInputStream(testString.getBytes());
        System.setIn(testIn); // System.in을 대체
    }

    @AfterEach
    public void restoreSystemInput() {
        System.setIn(systemInBackup); // 테스트 후 System.in을 원래대로 복원
    }

    @Test
    public void testScanner() {
        Scanner scanner = new Scanner(System.in);
        String input1 = scanner.nextLine();
        String input2 = scanner.nextLine();
        String input3 = scanner.nextLine();
        assertEquals("hihi", input1); // 예상되는 입력 값 검증
        assertEquals("byebye", input2); // 예상되는 입력 값 검증
        assertEquals("kkkk", input3); // 예상되는 입력 값 검증
    }
}
