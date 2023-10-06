package guru.springframework.client;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BeerClientImplTest {

  @Autowired
  BeerClient client;

  @Test
  void listBeer() {
    client.listBeers().subscribe(resp -> System.out.println(resp));

  }

}