import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import examples.HelloWorldResource;
import examples.Message;
import examples.SecureResource;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.mgt.*;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AopAllianceAnnotationsAuthorizingMethodInterceptor;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;

import static com.google.inject.matcher.Matchers.annotatedWith;
import static com.google.inject.matcher.Matchers.any;

public class GuiceServletConfig extends GuiceServletContextListener {
	@Override
	protected Injector getInjector() {
		return Guice.createInjector(new JerseyServletModule() {

			@Override
			protected void configureServlets() {

				bind(Message.class);
				bind(HelloWorldResource.class);
				bind(SecureResource.class);

				bind(MessageBodyReader.class).to(JacksonJsonProvider.class);
				bind(MessageBodyWriter.class).to(JacksonJsonProvider.class);

				// TODO SecurityManager is not correctly bound
				bind(SecurityManager.class).to(DefaultSecurityManager.class);
				bindInterceptor(any(), annotatedWith(RequiresAuthentication.class), new AopAllianceAnnotationsAuthorizingMethodInterceptor());

				serve("/*").with(GuiceContainer.class);
			}
		});
	}
}