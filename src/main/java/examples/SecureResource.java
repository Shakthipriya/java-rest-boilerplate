package examples;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

@Path("/secure")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class SecureResource {

	private Logger logger = LoggerFactory.getLogger(SecureResource.class);

	@GET
	@RequiresAuthentication
	public Message authenticated(@Context SecurityContext securityContext) {
		return new Message("foo" + securityContext.toString());
//		return new Message("Your are authenticated, " + securityContext.getUserPrincipal().getName());
	}
}
