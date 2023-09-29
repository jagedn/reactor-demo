package stock.ejemplo;

import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;
import reactor.core.publisher.Mono;
import stock.ecommerce.ArticuloPage;
import stock.ecommerce.UpdateStock;

import java.util.List;
import java.util.Map;

@Client("/ecommerce")
public interface EcommerceClient {

    @Get("/articulos")
    Mono<ArticuloPage> get(@QueryValue int page);

    @Post("/update")
    Mono<List<UpdateStock>> update(@Body List<UpdateStock> update);

}
