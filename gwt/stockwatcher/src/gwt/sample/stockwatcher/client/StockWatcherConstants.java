package gwt.sample.stockwatcher.client;

import com.google.gwt.i18n.client.Constants;

/**
 * Default values can be specified as annotations.
 */
public interface StockWatcherConstants extends Constants {
    @DefaultStringValue("StockWatcher")
    String stockWatcher();

    @DefaultStringValue("Symbol")
    String symbol();

    @DefaultStringValue("Price")
    String price();

    @DefaultStringValue("Change")
    String change();

    @DefaultStringValue("Remove")
    String remove();

    @Constants.DefaultStringValue("Add")
    String add();
}