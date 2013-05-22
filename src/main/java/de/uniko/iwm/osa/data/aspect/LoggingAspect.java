package de.uniko.iwm.osa.data.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class LoggingAspect {
	
	 static Logger log = Logger.getLogger(LoggingAspect.class.getName());
	
	@Before("execution(* de.uniko.iwm.osa.data.controller.*.*(..))")
	public void logBefore(JoinPoint joinPoint) {
		log.info("Aspect: " + joinPoint.getSignature().getName());
	}

}
