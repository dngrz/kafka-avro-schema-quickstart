package pe.com.cloudexperts.tech.kafka.ws.rest;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import pe.com.cloudexperts.tech.kafka.quarkus.Movie;

@Path("/movies")
public interface MovieResource {

  @POST
  Response enqueueMovie(Movie movie);

}
