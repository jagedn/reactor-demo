package stock;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record Articulo(String sku, int cantidad, double precio) {

}
