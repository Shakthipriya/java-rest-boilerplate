package guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.servlet.GuiceServletContextListener;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import examples.HelloWorldResource;
import examples.LoginResource;
import examples.Message;
import examples.SecureResource;
import shiro.SampleShiroRealm;
import shiro.SimpleShiroFilter;
import org.aopalliance.intercept.MethodInterceptor;
import org.apache.shiro.authz.annotation.*;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AopAllianceAnnotationsAuthorizingMethodInterceptor;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.WebSecurityManager;
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
				bind(LoginResource.class);

				bind(MessageBodyReader.class).to(JacksonJsonProvider.class);
				bind(MessageBodyWriter.class).to(JacksonJsonProvider.class);

				MethodInterceptor interceptor = new AopAllianceAnnotationsAuthorizingMethodInterceptor();
				bindInterceptor(any(), annotatedWith(RequiresRoles.class), interceptor);
				bindInterceptor(any(), annotatedWith(RequiresPermissions.class), interceptor);
				bindInterceptor(any(), annotatedWith(RequiresAuthentication.class), interceptor);
				bindInterceptor(any(), annotatedWith(RequiresUser.class), interceptor);
				bindInterceptor(any(), annotatedWith(RequiresGuest.class), interceptor);

				bind(Realm.class).to(SampleShiroRealm.class);

				filter("/*").through(SimpleShiroFilter.class);

				// TODO: Authentication does not work, yet
				bind(BasicHttpAuthenticationFilter.class).in(Singleton.class);
				filter("/secure/*").through(BasicHttpAuthenticationFilter.class);

				serve("/*").with(GuiceContainer.class);
			}

			@Provides
			public WebSecurityManager provideWebSecurityManager(Realm realm) {
				return new DefaultWebSecurityManager(realm);
			}

		});
	}
}