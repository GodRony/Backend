package com.naver.shopping.webScope;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
// @Controller: 웹 애플리케이션에서 HTTP 요청을 처리하는 컨트롤러 클래스에 사용.
// @RequiredArgsConstructor: final로 선언된 필드나 @NonNull 필드를 파라미터로 받는 생성자를 자동으로 생성.
// 따라서 LogService, MyLogger에는 생성자가 없음
public class LogController {
    private final LogService logService;
    private final ObjectProvider<MyLogger> myLoggerProvider;
    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) {
        MyLogger myLogger = myLoggerProvider.getObject();
        String requestURL = request.getRequestURL().toString();
        myLogger.setRequestURL(requestURL);
        myLogger.log("controller test");
        logService.logic("testId");
        return "OK";
    }
}
