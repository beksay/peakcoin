package org.infosystema.peakcoin.listener;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.el.MethodExpression;
import javax.faces.component.UICommand;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.infosystema.peakcoin.annotation.Logged;
import org.infosystema.peakcoin.annotation.RolesAllowed;
import org.infosystema.peakcoin.domain.User;
import org.infosystema.peakcoin.util.web.ElResolver;
import org.infosystema.peakcoin.util.web.LoginUtil;
import org.infosystema.peakcoin.util.web.Messages;

import com.sun.faces.application.ActionListenerImpl;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class JSFActionListener extends ActionListenerImpl {
	
	@Inject
	private LoginUtil loginUtil;

	@SuppressWarnings("el-syntax")
	@Override
	public void processAction(ActionEvent event) throws AbortProcessingException {
		System.out.println("ActionListener called!");
		
        UICommand command = (UICommand)event.getComponent();;
        MethodExpression expression = command.getActionExpression();
        if(expression != null) {
            String expString = expression.getExpressionString();
            Pattern pattern = Pattern.compile("#\\{(\\w*)\\.(\\w*)(\\((.*)\\))?\\}");
            Matcher matcher = pattern.matcher(expString);
            matcher.matches();
            StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("#{");
			stringBuilder.append(matcher.group(1));
			stringBuilder.append("}");
			String objectStr = stringBuilder.toString();
            String methodStr = matcher.group(2);
            String paramsStr = matcher.group(4);
            Class<?>[] params = getClasses(paramsStr);
            Object object = new ElResolver<Object>().evaluateValueExpressionAndGet(objectStr, Object.class);
            try {
                Method method = object.getClass().getDeclaredMethod(methodStr, params);
                Annotation[] annotations = method.getAnnotations();
				Set<Annotation> set = getRecursiveAnnotations(object.getClass());
				set.addAll(Arrays.asList(annotations));
				System.out.println(set);
				
                boolean allowed = true;
                for (Annotation annotation : set) {
                    if(annotation instanceof Logged){
                    	if(!loginUtil.isLogged()) allowed = false;
                    }
                    else if(annotation instanceof RolesAllowed){
                    	String[] roles = ((RolesAllowed) annotation).roles();
                    	User user = loginUtil.getCurrentUser();
                    	if(!loginUtil.userHasRole(user, roles)) allowed = false;
                    }
                }
                System.out.println(allowed);
                if(!allowed) {
                    deinedAccess();
                    return;
                }
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        
        System.out.println(" called super method superProcess.");
        
        super.processAction(event);
	}

	private void deinedAccess() {
		HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
		try {
		    response.sendError(HttpServletResponse.SC_FORBIDDEN, Messages.getMessage("accessDeined"));
		    FacesContext.getCurrentInstance().responseComplete();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}

	@SuppressWarnings("el-syntax")
	private Class<?>[] getClasses(String paramExp) {
		if(paramExp == null) return new Class<?>[]{};
		String[] paramsStr = paramExp.split(",");
		List<Class<?>> params = new ArrayList<Class<?>>();
		
		for (String param : paramsStr) {
			if(param == null || param.isEmpty()) continue;
			else if(isString(param)) params.add(String.class);
			else {
				System.out.println("param=(" + param + ")");
				Object object = new ElResolver<Object>().evaluateValueExpressionAndGet("#{" + param + "}", Object.class);
				System.out.println(object);
				params.add(object == null ? Object.class : object.getClass());
			}
		}
		
		return params.toArray(new Class<?>[]{}); 
	}
	
	private Set<Annotation> getRecursiveAnnotations(Class<?> clazz) {
		Set<Annotation> annotations = new HashSet<Annotation>();
		annotations.addAll(Arrays.asList(clazz.getAnnotations()));
		if(clazz.getSuperclass() != null) annotations.addAll(getRecursiveAnnotations(clazz.getSuperclass()));
		return annotations;
	}

	private boolean isString(String param) {
		return param.startsWith("'") && param.endsWith("'") || param.startsWith("\"") && param.endsWith("\"");
	}

}

