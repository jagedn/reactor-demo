package stock.ecommerce;

import io.micronaut.context.annotation.Context;
import jakarta.inject.Singleton;
import reactor.core.publisher.Mono;
import stock.Articulo;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
@Context
public class EcommerceRepository {

    Map<String, Articulo> articulos = new HashMap<>(Map.of(
            "1", new Articulo("1", 10, 1.1),
            "2", new Articulo("2", 20, 2.2)));


    public Collection<Articulo> getArticulos() {
        return articulos.values();
    }

    public Mono<List<UpdateStock>> updateStocks(List<UpdateStock> list) {
        for (UpdateStock a : list) {
            if (articulos.containsKey(a.sku())) {
                Articulo articulo = articulos.get(a.sku());
                articulos.replace(a.sku(), new Articulo(a.sku(), a.cantidad(), articulo.precio()));
            }
        }
        return Mono.just(list);
    }
}
