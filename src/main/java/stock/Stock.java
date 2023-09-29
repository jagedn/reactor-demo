package stock;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record Stock(String sku, int cantidad) {


}
