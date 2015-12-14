package com.echo.framework;

import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class ServiceAspect {
	private static Logger log = LoggerFactory.getLogger(ServiceAspect.class);
	private static long SERVICE_CRITICAL_ELAPSED_MSEC = 1000L;

	// @Around("execution(* com.echo..*.service..*.*(..))")
	public Object Profiling(ProceedingJoinPoint pjp) throws Throwable {
		Date start = null, end = null;
		start = new Date();

		Object ret = pjp.proceed();

		end = new Date();

		long elapsedMsec = end.getTime() - start.getTime();

		if (elapsedMsec > SERVICE_CRITICAL_ELAPSED_MSEC) {
			StringBuffer buff = new StringBuffer();
			buff.append(pjp.getSignature()).append(", elapsedTime(sec)=")
					.append((elapsedMsec / 1000.0F));

			log.error("{}", buff);
		}

		return ret;
	}
}
