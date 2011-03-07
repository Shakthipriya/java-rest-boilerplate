package shiro;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.AbstractShiroFilter;

@Singleton
public class SimpleShiroFilter extends AbstractShiroFilter {

	@Inject
	public SimpleShiroFilter(WebSecurityManager securityManager) {
		setSecurityManager(securityManager);
	}
}