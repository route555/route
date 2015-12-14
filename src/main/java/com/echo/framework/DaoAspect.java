package com.echo.framework;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class DaoAspect {
	private static Logger log = LoggerFactory.getLogger(DaoAspect.class);
	private static long DB_CRITICAL_ELAPSED_MSEC = 2000L;

	// @Around("execution(* com.echo..*.dao..*.*(..))")
	public Object Profiling(ProceedingJoinPoint pjp) throws Throwable {
		Date start = null, end = null;
		start = new Date();

		Object ret = pjp.proceed();

		end = new Date();
		long elapsedMsec = end.getTime() - start.getTime();

		if (elapsedMsec > DB_CRITICAL_ELAPSED_MSEC) {
			StringBuffer buff = new StringBuffer();
			buff.append(pjp.getSignature()).append(", elapsedTime(sec)=")
					.append((elapsedMsec / 1000.0F));

			log.error("{}", buff);
		}

		return ret;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Around("execution(java.util.List com.echo..*.dao..*.*(..))")
	public Object Profiling4List(ProceedingJoinPoint pjp) throws Throwable {
		Object ret = pjp.proceed();

		if (ret != null) {
			List<Map> list = (List) ret;
			Map param = null;
			if ((pjp.getArgs() != null) && (pjp.getArgs().length > 0)
					&& (pjp.getArgs()[0] instanceof Map)) {
				param = (Map) pjp.getArgs()[0];
			}

			if ((list != null) && (param != null)) {
				int start = param.get("start") != null ? (Integer) param
						.get("start") : 0;

				int i = 0;
				for (Map row : list) {
					row.put("rnum", (start + ++i));
				}
			}
		}

		return ret;
	}
}
