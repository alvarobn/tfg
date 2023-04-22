package com.alvaro.demo.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;


@Component
@SessionScope
public class UserComponent {

    private User user;

    public User getLoggedUser() {
        return user;
    }

    public void setLoggedUser(User user) {
        this.user = user;
    }

    public boolean isLoggedUser() {
        return this.user != null;
    }

    public boolean isAdmin(){
        return this.user.getRoles().contains("ROLE_ADMIN");
    }

    public boolean isRegistered(){
        if (isLoggedUser()){
            return this.user.getRoles().contains("ROLE_USER") || this.user.getRoles().contains("ROLE_ADMIN");
        }else return false;
    }

}
