package stock.ecommerce;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record UpdateStock(String sku, int cantidad) {
}
