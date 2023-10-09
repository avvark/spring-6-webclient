package guru.springframework.client;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

import guru.springframework.model.BeerDto;
import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BeerClientImplTest {

  @Autowired BeerClient client;

  @Test
  void getCreateBeer() {
    AtomicBoolean atomicBoolean = new AtomicBoolean(false);

    BeerDto newDto =
        BeerDto.builder()
            .price(new BigDecimal("10.99"))
            .beerName("Mango Bobs")
            .beerStyle("IPA")
            .quantityOnHand(500)
            .upc("123456")
            .build();

    client
        .createBeer(newDto)
        .subscribe(
            dto -> {
              System.out.println(dto.toString());
              atomicBoolean.set(true);
            });

    await().untilTrue(atomicBoolean);
  }

  @Test
  void deleteBeer() {
    final String name = "New Name";

    AtomicBoolean atomicBoolean = new AtomicBoolean(false);

    client
        .listBeerDtos()
        .next()
        .flatMap(dto -> client.deleteBeer(dto))
        .doOnSuccess(mt -> atomicBoolean.set(true))
        .subscribe();

    await().untilTrue(atomicBoolean);
  }

  @Test
  void getPatchBeer() {
    final String name = "New Name";

    AtomicBoolean atomicBoolean = new AtomicBoolean(false);

    client
        .listBeerDtos()
        .next()
        .doOnNext(beerDto -> beerDto.setBeerName(name))
        .flatMap(dto -> client.patchBeer(dto))
        .subscribe(
            dto -> {
              System.out.println(dto.toString());
              atomicBoolean.set(true);
            });

    await().untilTrue(atomicBoolean);
  }

  @Test
  void getUpdateBeer() {
    final String name = "New Name";

    AtomicBoolean atomicBoolean = new AtomicBoolean(false);

    client
        .listBeerDtos()
        .next()
        .doOnNext(beerDto -> beerDto.setBeerName(name))
        .flatMap(dto -> client.updateBeer(dto))
        .subscribe(
            dto -> {
              System.out.println(dto.toString());
              atomicBoolean.set(true);
            });

    await().untilTrue(atomicBoolean);
  }

  @Test
  void getGetBeerByStyle() {
    AtomicBoolean atomicBoolean = new AtomicBoolean(false);
    client
        .getBeerByStyle("Pale Ale")
        .subscribe(
            dto -> {
              System.out.println(dto.toString());
              atomicBoolean.set(true);
            });

    await().untilTrue(atomicBoolean);
  }

  @Test
  void getGetBeerById() {
    AtomicBoolean atomicBoolean = new AtomicBoolean(false);
    client
        .listBeerDtos()
        .flatMap(dto -> client.getBeerById(dto.getId()))
        .subscribe(
            dto -> {
              System.out.println(dto.getBeerName());
              atomicBoolean.set(true);
            });

    await().untilTrue(atomicBoolean);
  }

  @Test
  void getGetBeerDto() {
    AtomicBoolean atomicBoolean = new AtomicBoolean(false);
    client
        .listBeerDtos()
        .subscribe(
            dto -> {
              System.out.println(dto.getBeerName());
              atomicBoolean.set(true);
            });

    await().untilTrue(atomicBoolean);
  }

  @Test
  void getBeerJson() {
    AtomicBoolean atomicBoolean = new AtomicBoolean(false);
    client
        .listBeersJsonNode()
        .subscribe(
            jsonNode -> {
              System.out.println(jsonNode.toPrettyString());
              atomicBoolean.set(true);
            });

    await().untilTrue(atomicBoolean);
  }

  @Test
  void listBeerMap() {
    AtomicBoolean atomicBoolean = new AtomicBoolean(false);
    client
        .listBeerMap()
        .subscribe(
            resp -> {
              System.out.println(resp);
              atomicBoolean.set(true);
            });

    await().untilTrue(atomicBoolean);
  }

  @Test
  void listBeer() {
    AtomicBoolean atomicBoolean = new AtomicBoolean(false);
    client
        .listBeers()
        .subscribe(
            resp -> {
              System.out.println(resp);
              atomicBoolean.set(true);
            });

    await().untilTrue(atomicBoolean);
  }
}
