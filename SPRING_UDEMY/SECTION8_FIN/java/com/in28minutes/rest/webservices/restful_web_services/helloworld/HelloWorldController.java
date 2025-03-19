package com.in28minutes.rest.webservices.restful_web_services.helloworld;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

//REST API
@RestController
public class HelloWorldController {

    private MessageSource messageSource;

    public HelloWorldController(MessageSource messageSource){
        this.messageSource = messageSource;
    }

//    @RequestMapping(method = RequestMethod.GET,path = "/hello-world")
    @GetMapping(path = "/hello-world")
    public String helloworld(){
        return "Hello World";
    }

    @GetMapping(path = "/hello-world-bean")
    public HelloWorldBean helloWorldBean(){
        return new HelloWorldBean("Hello World");
    }

    @GetMapping(path = "/hello-world/path-variable/{name}")
    public HelloWorldBean helloWorldPathVariable(@PathVariable String name){
        return new HelloWorldBean( String.format("Hello World %s",name));
    }
    // URL에 pass params를 주고싶을땐 {}로 또 그걸 받아야하니 메소드에 매개변수를

    @GetMapping(path = "/hello-world-internationalized")
    public String helloWorldInternationalize(){

        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage("good.morning.message",null,
                "Default Message",locale);
    }

}
