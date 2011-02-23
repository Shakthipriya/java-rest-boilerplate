package examples;

import org.apache.shiro.authz.annotation.RequiresAuthentication;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

@Path("/secure")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class SecureResource {

	@GET
	@RequiresAuthentication
	public Message authenticated(@Context SecurityContext securityContext) {
		return new Message("Your are authenticated, " + securityContext.getUserPrincipal().getName());
	}
}
