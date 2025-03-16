package login;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@SessionAttributes("name")
public class WelcomeController {

//    private Logger logger = LoggerFactory.getLogger(getClass());

    //localhost:8080/login?name=Ranga
//    @RequestMapping("login")
//    public String login(@RequestParam String name, ModelMap model){
//        // name과 일치
//        model.put("name",name);
//        logger.debug("Request param is {}",name);
//        return "login";
//    }

//    // 이거는 login이여도 get post 등 모두 지원하는거임..
//    @RequestMapping("login")
//    public String gotoLoginPage(){
//        // name과 일치
//        return "login";
//    }

//    private AuthenticationService authenticationService;
//
//    public LoginController(AuthenticationService authenticationService) {
//        this.authenticationService = authenticationService;
//    }

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String gotoWelcomePage(ModelMap model){
        model.put("name",getLoggedinUsername());
        return "welcome";
    }

    private String getLoggedinUsername(){
        Authentication authentication =
            SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

//    @RequestMapping(value = "login",method = RequestMethod.POST)
//    public String gotoWelcomePage(@RequestParam String name,
//                                  @RequestParam String password,ModelMap model){
//
//        if(authenticationService.authenticate(name,password)){
//            model.put("name",name); // welcome에 넘겨줄 정보를 model에 담아
//            model.put("password",password); // 그럼 welocme이 이 변수를  이용하는겨
//            return "welcome";
//        }
//        model.put("errorMessage","Invalid Credentials");
//        return "login";
//    }
    //method = RequestMethod를 다르게 해주면 같은 /login이여도 다른 view를 주는게 가능
    // 처음에 했던 @RequestParam String name(get디폴트)와 똑같지만 Post여서
    // URL에 jsp에서 입력한 정보만 드러나지 않을뿐 넘겨받을 수 있음
    // 참고로 이전 방식은 URL에 ?name=name, 이런식으로 입력하는 구조였음
}
