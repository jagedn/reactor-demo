package stock.ecommerce;

import io.micronaut.serde.annotation.Serdeable;
import stock.Articulo;

import java.util.Collection;

@Serdeable
public record ArticuloPage(int total, Collection<Articulo> articulos) {

}
