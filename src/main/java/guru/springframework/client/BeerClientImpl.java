package guru.springframework.client;

import com.fasterxml.jackson.databind.JsonNode;
import guru.springframework.model.BeerDto;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BeerClientImpl implements BeerClient {

  public static final String BEER_PATH = "/api/v3/beer";
  public static final String BEER_PATH_ID = BEER_PATH + "/{beerId}";
  private final WebClient webClient;

  public BeerClientImpl(WebClient.Builder builder) {
    this.webClient = builder.build();
  }

  @Override
  public Flux<String> listBeers() {
    return webClient.get().uri(BEER_PATH).retrieve().bodyToFlux(String.class);
  }

  @Override
  public Flux<Map> listBeerMap() {
    return webClient.get().uri(BEER_PATH).retrieve().bodyToFlux(Map.class);
  }

  @Override
  public Flux<JsonNode> listBeersJsonNode() {
    return webClient.get().uri(BEER_PATH).retrieve().bodyToFlux(JsonNode.class);
  }

  @Override
  public Flux<BeerDto> listBeerDtos() {
    return webClient.get().uri(BEER_PATH).retrieve().bodyToFlux(BeerDto.class);
  }

  @Override
  public Mono<BeerDto> getBeerById(String id) {
    return webClient
        .get()
        .uri(uriBuilder -> uriBuilder.path(BEER_PATH_ID).build(id))
        .retrieve()
        .bodyToMono(BeerDto.class);
  }

  @Override
  public Flux<BeerDto> getBeerByStyle(String style) {
    return webClient
        .get()
        .uri(uriBuilder -> uriBuilder.path(BEER_PATH).queryParam("beerStyle", style).build())
        .retrieve()
        .bodyToFlux(BeerDto.class);
  }

  @Override
  public Mono<BeerDto> createBeer(BeerDto newDto) {
    return webClient.post().uri(BEER_PATH)
        .body(Mono.just(newDto), BeerDto.class)
        .retrieve()
        .toBodilessEntity()
        .flatMap(voidResponseEntity -> Mono.just(voidResponseEntity.getHeaders().get("Location").get(0)))
        .map(path -> path.split("/")[path.split("/").length -1])
        .flatMap(this::getBeerById);
  }
}
