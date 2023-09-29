package stock.stock;

import io.micronaut.serde.annotation.Serdeable;
import stock.Stock;

import java.util.Collection;

@Serdeable
public record StockPage(int total, Collection<Stock> stocks) {

}
