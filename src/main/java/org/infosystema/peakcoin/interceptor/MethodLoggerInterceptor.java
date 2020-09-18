package org.infosystema.peakcoin.interceptor;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class MethodLoggerInterceptor {
	
	@AroundInvoke
	public Object logger(InvocationContext inv) throws Exception {
		System.out.println("called: " + inv.getTarget().getClass().getInterfaces()[0] + ", method = " + inv.getMethod().getName());
		return inv.proceed();
	}

}
