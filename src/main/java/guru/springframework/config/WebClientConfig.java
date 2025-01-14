package guru.springframework.config;

import javax.swing.JRootPane;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient.Builder;

@Configuration
public class WebClientConfig implements WebClientCustomizer {

  private final String rootUrl;

  public WebClientConfig(@Value("${webclient.rooturl}") String rootUrl){
    this.rootUrl = rootUrl;
  }

  @Override
  public void customize(Builder webClientBuilder) {
    webClientBuilder.baseUrl(rootUrl);
  }
}
