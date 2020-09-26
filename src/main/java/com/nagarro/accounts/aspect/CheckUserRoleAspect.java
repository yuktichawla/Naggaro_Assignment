package com.nagarro.accounts.aspect;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.nagarro.accounts.exception.ApplicationException;
import com.nagarro.accounts.filter.JwtTokenUtil;

@Component
@Aspect
public class CheckUserRoleAspect {

	@Autowired
	JwtTokenUtil jwttokenUtil;
	@Before("execution(* *.*(..)) && @annotation(checkRole)")
	private boolean isUserAllowed(JoinPoint joinPoint, CheckRole checkRole) throws ApplicationException {
		Object[] args = joinPoint.getArgs();
		HttpServletRequest re = (HttpServletRequest) args[5];
		String role = jwttokenUtil.getRoleFromToken(re.getHeader("Authorization").substring(7)).toString();
		if(role.equalsIgnoreCase("user") && (!StringUtils.isEmpty(args[1])
				|| !StringUtils.isEmpty(args[2])
				|| args[3] != null
				|| args[4] != null)){
			throw new ApplicationException("UNAUTHORIZED", "You are not authorized to perform this operation", HttpStatus.UNAUTHORIZED.value());
		}
		return true;
	}
}
