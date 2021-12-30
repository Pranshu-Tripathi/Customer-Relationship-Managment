package com.pranshu.crm.springdemo.aspects;

import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
	
	
	// set up logger
	private Logger logger = Logger.getLogger(getClass().getName());
	
	// setup point cut declarations
	
	@Pointcut("execution(* com.pranshu.crm.springdemo.controller.*.*(..))")
	private void forControllerPackage() {}
	
	@Pointcut("execution(* com.pranshu.crm.springdemo.service.*.*(..))")
	private void forServicePackage() {}
	
	@Pointcut("execution(* com.pranshu.crm.springdemo.dao.*.*(..))")
	private void forDAOPackage() {}
	
	@Pointcut("execution(* com.pranshu.crm.springdemo.config.*.*(..))")
	private void forConfigPackage() {}
	
	@Pointcut("execution(* com.pranshu.crm.springdemo.user.*.*(..))")
	private void forUserPackage() {}
	
	@Pointcut("forControllerPackage() || forServicePackage() || forDAOPackage() || forConfigPackage() || forUserPackage()")
	private void forAppFlow() {}
	
	// add @Before Advice
	@Before("forAppFlow()")
	public void before(JoinPoint theJoinPoint) {
		// display the method name
		String theMethod = theJoinPoint.getSignature().toShortString();
		logger.info("====>>  @Before: calling method - " + theMethod);
		
		// display the arguments to the method
		Object[] args = theJoinPoint.getArgs();
		for(Object temp : args) {
			logger.info("====>> argument - " + temp);
		}
	}
	
	// add @AfterReturning advice
	@AfterReturning(
			pointcut = "forAppFlow()",
			returning = "theResult"
			)
	public void afterReturning(JoinPoint theJoinPoint, Object theResult) {
		// display the method name
		String theMethod = theJoinPoint.getSignature().toShortString();
		logger.info("====>> @AfterReturning: returning from method - " + theMethod);
		// display the result object to the method
		logger.info("====>> result : " + theResult);
	}
	
}
