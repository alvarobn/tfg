package com.alvaro.demo.OAthUser;

import java.io.IOException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.alvaro.demo.User.User;
import com.alvaro.demo.User.UserComponent;
import com.alvaro.demo.User.UserRepository;
import com.alvaro.demo.User.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("oauth2authSuccessHandler")
public class Oauth2AuthenticationSuccessHandler  implements AuthenticationSuccessHandler{
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();;
	@Autowired
	private UserService userService;
	@Autowired
	private UserComponent userComponent;


	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
										Authentication authentication) throws IOException, ServletException {

		OAuth2AuthenticationToken authenticationToken = (OAuth2AuthenticationToken) authentication;

		String url = request.getRequestURL().toString();


		String name = null;
		//GOOGLE
		if(url.contains("google")){
			User user = userService.getByName(authenticationToken.getName());
			if (user == null) {
				name = authenticationToken.getPrincipal().getAttributes().get("given_name").toString();
				String email = authenticationToken.getPrincipal().getAttributes().get("email").toString();
				String[] roles = {"ROLE_USER_OAUTH"};
				user = new User(name,"",email,roles);
			}
			userComponent.setLoggedUser(user);
		}
		//GITHUB
		if(url.contains("github")){
			String loginName = authenticationToken.getPrincipal().getAttributes().get("login").toString();
			User user = userService.getByName(loginName);
			if(user == null){
				Object email = authenticationToken.getPrincipal().getAttributes().get("email");
				if(email!=null){ email = email.toString(); }
				else { email = "";}
				String[] roles = {"ROLE_USER_OAUTH"};
				user = new User(loginName,"","",roles);
			}
			userComponent.setLoggedUser(user);
		}

		this.redirectStrategy.sendRedirect(request, response,name!=null ? "/" :"/");
	}
}
