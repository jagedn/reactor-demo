package stock.ecommerce;

import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import reactor.core.publisher.Mono;

import java.util.List;

@Controller("/ecommerce")
public class EcommerceController {

    private final EcommerceRepository ecommerceRepository;

    public EcommerceController(EcommerceRepository ecommerceRepository) {
        this.ecommerceRepository = ecommerceRepository;
    }

    @Get("/articulos")
    public Mono<ArticuloPage> get() {
        return Mono.just(new ArticuloPage(ecommerceRepository.getArticulos().size(), ecommerceRepository.getArticulos()));
    }

    @Post("/update")
    public Mono<List<UpdateStock>> update(@Body List<UpdateStock> update){
        return ecommerceRepository.updateStocks(update);
    }
}
