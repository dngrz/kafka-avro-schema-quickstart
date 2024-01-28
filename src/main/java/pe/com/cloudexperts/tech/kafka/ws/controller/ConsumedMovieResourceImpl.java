package pe.com.cloudexperts.tech.kafka.ws.controller;

import io.smallrye.mutiny.Multi;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Channel;
import pe.com.cloudexperts.tech.kafka.quarkus.Movie;
import pe.com.cloudexperts.tech.kafka.ws.rest.ConsumedMovieResource;

@Slf4j
@ApplicationScoped
public class ConsumedMovieResourceImpl implements ConsumedMovieResource {

  @Channel("movies-from-kafka")
  Multi<Movie> movies;

  @Override
  public Multi<String> stream() {
    return movies.map(movie -> String.format("'%s' from %s", movie.getTitle(), movie.getYear()));
  }
}
