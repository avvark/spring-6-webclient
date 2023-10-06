package guru.springframework.client;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BeerClientImplTest {

  @Autowired BeerClient client;

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
