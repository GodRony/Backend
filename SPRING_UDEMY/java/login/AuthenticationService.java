package login;

import org.springframework.stereotype.Service;

@Service // Bean대신에 Service로 할 수 있음
public class AuthenticationService {
    public boolean authenticate(String username,String password){
        boolean isValidUserName = username.equalsIgnoreCase("qwer");
        boolean isValidPassword = password.equalsIgnoreCase("asdf");
        return isValidUserName && isValidPassword;
    }
}
