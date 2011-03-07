package shiro;

import com.google.inject.internal.ImmutableSet;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Set;

// Example Realm which accepts every user that has 'password' as her password
public class SampleShiroRealm extends AuthorizingRealm {

	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;

		String username = upToken.getUsername();

		if (username == null) {
			throw new AccountException("Null usernames are not allowed by this realm.");
		}
		String password = "password";
		return new SimpleAuthenticationInfo(username, password, this.getName());
	}

	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		if (principals == null) {
			throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
		}
		String username = (String) principals.fromRealm(getName()).iterator().next();
		Set<String> roleNames = ImmutableSet.of();
		if (username != null) {
			roleNames = ImmutableSet.of("foo", "goo");
		}
		return new SimpleAuthorizationInfo(roleNames);
	}
}