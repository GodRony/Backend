package com.naver.shopping.webScope;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
//  @Service: 비즈니스 로직을 처리하는 서비스 클래스에 사용.
// @RequiredArgsConstructor: final로 선언된 필드나 @NonNull 필드를 파라미터로 받는 생성자를 자동으로 생성.
// 따라서 MyLogger에는 생성자가 없음.
public class LogService {
    private final ObjectProvider<MyLogger> myLoggerProvider;
    public void logic(String id) {
        MyLogger myLogger = myLoggerProvider.getObject();
        myLogger.log("service id = " + id);
    }
}