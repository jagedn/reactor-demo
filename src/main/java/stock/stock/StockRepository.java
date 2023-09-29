package stock.stock;

import io.micronaut.context.annotation.Context;
import jakarta.inject.Singleton;
import stock.Stock;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
@Context
public class StockRepository {

    Map<String, Stock> stocks = new HashMap<>(Map.of(
            "1", new Stock("1", 10),
            "2", new Stock("2", 20)));

    public Collection<Stock> getStocks() {
        return stocks.values();
    }

    public void updateStocks(List<Stock> list) {
        for (Stock s : list) {
            if (stocks.containsKey(s.sku())) {
                stocks.replace(s.sku(), new Stock(s.sku(), s.cantidad()));
            }
        }
    }
}
