package pe.com.cloudexperts.tech.kafka.ws.controller;

import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import pe.com.cloudexperts.tech.kafka.quarkus.Movie;
import pe.com.cloudexperts.tech.kafka.ws.rest.MovieResource;

@Slf4j
public class MovieResourceImpl implements MovieResource {

  @Channel("movies")
  Emitter<Movie> emitter;

  @Override
  public Response enqueueMovie(Movie movie) {
    log.info("Sending movie {} to Kafka", movie.getTitle());
    emitter.send(movie);
    return Response.accepted().build();
  }
}
