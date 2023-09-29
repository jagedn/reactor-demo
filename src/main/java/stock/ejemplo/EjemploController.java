package stock.ejemplo;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import stock.Articulo;
import stock.Stock;
import stock.ecommerce.ArticuloPage;
import stock.ecommerce.UpdateStock;
import stock.stock.StockPage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller("/ejemplo")
public class EjemploController {

    private final EcommerceClient ecommerceClient;
    private final StockClient stockClient;

    public EjemploController(EcommerceClient ecommerceClient, StockClient stockClient) {
        this.ecommerceClient = ecommerceClient;
        this.stockClient = stockClient;
    }

    @Get("/block")
    List<UpdateStock> block() {
        List<UpdateStock> ret = new ArrayList<>();
        int batchSize = 2;
        int maxArticles = 100;
        int offset = 0;
        while( offset < maxArticles ){
            var articuloPage = ecommerceClient.get(offset).block();

            var articulos = new ArrayList<String>();
            for(var articulo : articuloPage.articulos()){
                articulos.add(articulo.sku());
            }

            StockPage stockPage = stockClient.get( String.join(",",articulos) ).block();

            List<UpdateStock> updateStocks = new ArrayList<>();
            for (Stock s : stockPage.stocks()) {
                updateStocks.add(new UpdateStock(s.sku(), s.cantidad()));
            }

            ecommerceClient.update(updateStocks).block();
            offset+=batchSize;

            ret.addAll(updateStocks);
        }
        return ret;
    }

    @Get("/reactive")
    Mono<ArrayList<UpdateStock>> reactive() {
        int batchSize = 2;
        int maxArticles = 100;
        return Flux
                .generate(() -> 0, (offset, emitter) -> {
                    if (offset < maxArticles) {
                        emitter.next(offset);
                    } else {
                        emitter.complete();
                    }
                    return offset + batchSize;
                })
                .concatMap(page -> ecommerceClient.get((Integer) page))
                .map(ArticuloPage::articulos)
                .filter(items -> !items.isEmpty())
                .flatMap(items -> stockClient.get(items.stream().map(Articulo::sku).collect(Collectors.joining()))
                        .map(stocks -> stocks.stocks()
                                .stream()
                                .map(s -> new UpdateStock(s.sku(), s.cantidad()))
                                .toList()))
                .concatMap(ecommerceClient::update)
                .reduce(new ArrayList<>(), (prev, items) -> {
                    prev.addAll(items);
                    return prev;
                });
    }

}
