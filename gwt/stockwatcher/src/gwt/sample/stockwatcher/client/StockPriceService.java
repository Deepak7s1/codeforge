package gwt.sample.stockwatcher.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


/**
 * Client-side stub for the GWT-RPC service.
 * 
 * The RemoteServiceRelativePath annotation associates the service 
 * with a default path relative to the module base URL.
 * 
 * @author Adrian Yau
 *
 */
@RemoteServiceRelativePath("stockPrices")
public interface StockPriceService extends RemoteService {

    StockPrice[] getPrices(String[] symbols);

}
