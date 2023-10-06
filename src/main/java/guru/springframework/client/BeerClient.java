package guru.springframework.client;

import com.fasterxml.jackson.databind.JsonNode;
import guru.springframework.model.BeerDto;
import java.util.Map;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BeerClient {

  Flux<String> listBeers();

  Flux<Map> listBeerMap();

  Flux<JsonNode> listBeersJsonNode();

  Flux<BeerDto> listBeerDtos();

  Mono<BeerDto> getBeerById(String id);

  Flux<BeerDto> getBeerByStyle(String style);

  Mono<BeerDto> createBeer(BeerDto newDto);
}
