package stock.ejemplo;

import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;
import reactor.core.publisher.Mono;
import stock.Stock;
import stock.stock.StockPage;

import java.util.List;

@Client("/stock")
public interface StockClient {

    @Get("/")
    Mono<StockPage> get(@QueryValue String skus);

}
