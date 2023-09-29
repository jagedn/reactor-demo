package stock.stock;

import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import reactor.core.publisher.Mono;
import stock.Stock;

import java.util.List;

@Controller("/stock")
public class StockController {

    StockRepository stockRepository;

    public StockController(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Get("/")
    Mono<StockPage> get() {
        StockPage page = new StockPage(stockRepository.getStocks().size(), stockRepository.getStocks());
        return Mono.just(page);
    }

    @Post("/update")
    void update(@Body List<Stock> stocks) {
        stockRepository.updateStocks(stocks);
    }

}
