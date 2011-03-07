package examples;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.security.Security;

@Path("/login")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class LoginResource {

	private final Logger logger = LoggerFactory.getLogger(LoginResource.class);

	@GET
	public Message login() {
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			return new Message("Is Authenticated");
		}
		return new Message("Is not Authenticated");
	}
}