package com.microc.bankmanagement.userservice.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.microc.bankmanagement.userservice.services.CustomerAuthenticationService;
import com.microc.bankmanagement.userservice.services.TokenGeneratorUtil;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class RequestFilter extends OncePerRequestFilter {

	@Autowired
	private TokenGeneratorUtil tokenGeneratorUtil;

	@Autowired
	private CustomerAuthenticationService cplayersUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
 		String token = null;
		String userName = null;
		final String authorizationHeader = request.getHeader("Authorization");
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			token = authorizationHeader.substring(7);
			try {
				userName = tokenGeneratorUtil.getUsernameFromToken(token);
			} catch (IllegalArgumentException iae) {
				logger.warn("Token provided is invalid");
			} catch (ExpiredJwtException eje) {
				logger.warn("Token has expired");

			}

		} else {
			logger.warn("Authorization header dosen't start with Beare");
		}

		if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = this.cplayersUserDetailsService.loadUserByUsername(userName);
			if (tokenGeneratorUtil.validateToken(token, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		filterChain.doFilter(request, response);

	}
}
