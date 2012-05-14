package gwt.sample.stockwatcher.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * An AsyncCallback method must be added to all GWT-RPC service methods.
 * 
 * (1) It must have the same name as the service interface, appended with Async (for example, StockPriceServiceAsync).
 * (2) It must be located in the same package as the service interface.
 * (3) Each method must have the same name and signature as in the service interface with an important difference: 
 *     the method has no return type but instead the last parameter is an AsyncCallback object.
 *
 * @author Adrian Yau
 *
 */
public interface StockPriceServiceAsync {

    void getPrices(String[] symbols, AsyncCallback<StockPrice[]> callback);

}
