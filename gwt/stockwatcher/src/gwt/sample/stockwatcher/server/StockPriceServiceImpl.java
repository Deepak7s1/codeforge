package gwt.sample.stockwatcher.server;

import java.util.Random;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import gwt.sample.stockwatcher.client.StockPrice;
import gwt.sample.stockwatcher.client.StockPriceService;


/**
 * This class implements the StockPriceService interface.
 * The service implementation runs on the server as Java bytecode; 
 * it's not translated into JavaScript. Therefore, the service implementation 
 * does not have the same language constraints as the client-side code.
 * 
 * RemoteServiceServlet does the work of deserializing incoming requests 
 * from the client and serializing outgoing responses. 
 * 
 * @author Adrian Yau
 *
 */
@SuppressWarnings("serial")
public class StockPriceServiceImpl extends RemoteServiceServlet
                                   implements StockPriceService {
    
    private static final double MAX_PRICE = 100.0; // $100.00
    private static final double MAX_PRICE_CHANGE = 0.02; // +/- 2%

    @Override
    public StockPrice[] getPrices(String[] symbols) {
        Random rnd = new Random();

        StockPrice[] prices = new StockPrice[symbols.length];
        for (int i = 0; i < symbols.length; i++) {
            double price = rnd.nextDouble() * MAX_PRICE;
            double change = price * MAX_PRICE_CHANGE * (rnd.nextDouble() * 2.0 - 1.0);

            prices[i] = new StockPrice(symbols[i], price, change);
        }

        return prices;
    }

}
