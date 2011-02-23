package examples;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class HelloWorldResource {

	@GET
	public Message helloWorld() {
		return hello("world!");
	}

	@GET
	@Path("/{name}")
	public Message hello(@PathParam("name") String name) {
		return new Message(String.format("Hello, %s!", name));
	}
}
