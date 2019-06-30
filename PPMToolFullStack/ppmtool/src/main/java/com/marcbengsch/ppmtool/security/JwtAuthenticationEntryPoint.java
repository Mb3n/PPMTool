package com.marcbengsch.ppmtool.security;


import com.google.gson.Gson;
import com.marcbengsch.ppmtool.exceptions.InvalidLoginResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
		InvalidLoginResponse loginResponse = new InvalidLoginResponse();
		String jsonLoginRespons = new Gson().toJson(loginResponse);


		httpServletResponse.setContentType("application/json");
		httpServletResponse.setStatus(401);
		httpServletResponse.getWriter().print(jsonLoginRespons);
	}
}
