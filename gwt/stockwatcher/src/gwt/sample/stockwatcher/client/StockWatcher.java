package gwt.sample.stockwatcher.client;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;

import com.google.gwt.http.client.URL;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.i18n.client.NumberFormat;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 *
 * @author Adrian Yau
 */
public class StockWatcher implements EntryPoint {

    private static final String JSON_URL = GWT.getModuleBaseURL() + "stockPrices?q=";

    private static final int REFRESH_INTERVAL = 5000; // msecs

    private VerticalPanel mainPanel = new VerticalPanel();
    private FlexTable stocksFlexTable = new FlexTable();
    private HorizontalPanel addPanel = new HorizontalPanel();
    private TextBox newSymbolTextBox = new TextBox();
    private Button addStockButton = new Button("Add");
    private Label lastUpdatedLabel = new Label();
    private Label errorMsgLabel = new Label();

    // Data structure to hold the list of stocks in the stock table.
    private ArrayList<String> stocks = new ArrayList<String>();

    // Instantiate a service proxy for the StockPriceService.
    private StockPriceServiceAsync stockPriceSvc = GWT.create(StockPriceService.class);

    // Interfaces are instantiated indirectly in order to use their accessor methods.
    private StockWatcherConstants constants = GWT.create(StockWatcherConstants.class);
    private StockWatcherMessages messages = GWT.create(StockWatcherMessages.class);


    /**
     * Entry point method.
     */
    public void onModuleLoad() {
        // Set the window title, the header text, and the Add button text.
        Window.setTitle(constants.stockWatcher());
        RootPanel.get("appTitle").add(new Label(constants.stockWatcher()));
        addStockButton = new Button(constants.add());

        // Create table for stock data.
        stocksFlexTable.setText(0, 0, constants.symbol());
        stocksFlexTable.setText(0, 1, constants.price());
        stocksFlexTable.setText(0, 2, constants.change());
        stocksFlexTable.setText(0, 3, constants.remove());

        // Add styles to elements in the stock list table.
        stocksFlexTable.setCellPadding(6);
        stocksFlexTable.getRowFormatter().addStyleName(0, "watchListHeader");
        stocksFlexTable.addStyleName("watchList");
        stocksFlexTable.getCellFormatter().addStyleName(0, 1, "watchListNumericColumn");
        stocksFlexTable.getCellFormatter().addStyleName(0, 2, "watchListNumericColumn");
        stocksFlexTable.getCellFormatter().addStyleName(0, 3, "watchListRemoveColumn");

        // Assemble Add Stock panel.
        addPanel.add(newSymbolTextBox);
        addPanel.add(addStockButton);
        addPanel.addStyleName("addPanel");

        // Assemble Main panel.
        errorMsgLabel.setStyleName("errorMessage");
        errorMsgLabel.setVisible(false);

        mainPanel.add(errorMsgLabel);
        mainPanel.add(stocksFlexTable);
        mainPanel.add(addPanel);
        mainPanel.add(lastUpdatedLabel);

        // Associate the Main panel with the HTML host page.
        RootPanel.get("stockList").add(mainPanel);

        // Move cursor focus to the input box.
        newSymbolTextBox.setFocus(true);

        // Setup timer to refresh list automatically.
        Timer refreshTimer = new Timer() {
            @Override
            public void run() {
                refreshWatchList();
            }
        };
        refreshTimer.scheduleRepeating(REFRESH_INTERVAL);

        // Listen for mouse click event on the Add button.
        addStockButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                addStock();
            }
        });

        // Listen for keyboard events in the input box.
        newSymbolTextBox.addKeyPressHandler(new KeyPressHandler() {
            @Override
            public void onKeyPress(KeyPressEvent event) {
                if (event.getCharCode() == KeyCodes.KEY_ENTER) {
                    addStock();
                }
            }
        });
    }


    /**
     * Add stock to FlexTable. Executed when the user clicks the addStockButton
     * or presses enter in the newSymbolTextBox.
     */
    private void addStock() {
        final String symbol = newSymbolTextBox.getText().toUpperCase().trim();
        newSymbolTextBox.setFocus(true);

        // Stock code must be between 1 and 10 chars that are numbers, letters,
        // or dots.
        if (!symbol.matches("^[0-9A-Z\\.]{1,10}$")) {
            Window.alert(messages.invalidSymbol(symbol));
            newSymbolTextBox.selectAll();
            return;
        }

        newSymbolTextBox.setText("");

        // Don't add the stock if it's already in the table.
        if (stocks.contains(symbol)) {
            return;
        }

        // Add the stock to the table.
        int row = stocksFlexTable.getRowCount();
        stocks.add(symbol);
        stocksFlexTable.setText(row, 0, symbol);
        stocksFlexTable.setWidget(row, 2, new Label());
        stocksFlexTable.getCellFormatter().addStyleName(row, 1, "watchListNumericColumn");
        stocksFlexTable.getCellFormatter().addStyleName(row, 2, "watchListNumericColumn");
        stocksFlexTable.getCellFormatter().addStyleName(row, 3, "watchListRemoveColumn");

        // Add a button to remove this stock from the table.
        Button removeStockButton = new Button("x");
        removeStockButton.addStyleDependentName("remove");  // adds a secondary style dependent on gwt-Button.

        removeStockButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                int removedIndex = stocks.indexOf(symbol);
                stocks.remove(removedIndex);
                stocksFlexTable.removeRow(removedIndex + 1);
            }
        });
        stocksFlexTable.setWidget(row, 3, removeStockButton);

        // Get the stock price.
        refreshWatchList();
    }


    /**
     * Refresh the stock prices. Prices are randomly generated.
     */
    private void refreshWatchList() {
        this.refreshWatchListJSON();
    }


    /**
     * Approach 1: Use GWT-RPC
     * Refresh the stock prices. Prices are randomly generated.
     */
    private void refreshWatchListRPC() {
        // Initialize the service proxy.
        if (stockPriceSvc == null) {
            stockPriceSvc = GWT.create(StockPriceService.class);
        }

        // Set up the callback object.
        AsyncCallback<StockPrice[]> callback = new AsyncCallback<StockPrice[]>() {
            public void onFailure(Throwable caught) {
                // TODO: Do something with errors.
            }

            public void onSuccess(StockPrice[] result) {
                updateTable(result);
            }
        };

        // Make the call to the stock price service.
        stockPriceSvc.getPrices(stocks.toArray(new String[0]), callback);
    }


    /**
     * Approach 2: Use JSON
     * Refresh the stock prices. Prices are randomly generated.
     */
    private void refreshWatchListJSON() {
        if (stocks.size() == 0) {
            return;
        }

        String url = JSON_URL;

        // Append watch list stock symbols to query URL.
        for (String stock : stocks) {
            url += stock;
            url += "+";
        }

        url = URL.encode(url);


        // Send request to server and catch any errors.
        RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);

        try {
            builder.sendRequest(null, new RequestCallback() {
                public void onError(Request request, Throwable exception) {
                    displayError("Couldn't retrieve JSON");
                }

                public void onResponseReceived(Request request,
                                               Response response) {
                    if (200 == response.getStatusCode()) {
                        JsArray<StockData> stockData = asArrayOfStockData(response.getText());
                        if (stockData.length() > 0) {
                            StockPrice[] stockPrices = new StockPrice[stockData.length()];
                            for (int i = 0; i < stockData.length(); i++) {
                                StockData sd = stockData.get(i);
                                stockPrices[i] = new StockPrice(sd.getSymbol(), sd.getPrice(), sd.getChange());
                            }
                            updateTable(stockPrices);
                        }
                    } else {
                        displayError("Couldn't retrieve JSON ("
                                + response.getStatusText() + ")");
                    }
                }
            });
        } catch (RequestException e) {
            displayError("Couldn't retrieve JSON");
        }
    }


    /**
     * Update the Price and Change fields all the rows in the stock table.
     *
     * @param prices Stock data for all rows.
     */
    private void updateTable(StockPrice[] prices) {
        for (StockPrice price : prices) {
            updateTable(price);
        }

        // Display timestamp showing last refresh.
        // lastUpdatedLabel.setText("Last update : " + DateTimeFormat.getFormat(PredefinedFormat.DATE_TIME_MEDIUM).format(new Date()));
        lastUpdatedLabel.setText(messages.lastUpdate(new Date()));

        // Clear any errors.
        errorMsgLabel.setVisible(false);
    }


    private void updateTable(StockPrice price) {
        // Make sure the stock is still in the stock table.
        if (!stocks.contains(price.getSymbol())) {
            return;
        }

        int row = stocks.indexOf(price.getSymbol()) + 1;

        // Format the data in the Price and Change fields.
        String priceText = NumberFormat.getFormat("#,##0.00").format(price.getPrice());
        NumberFormat changeFormat = NumberFormat.getFormat("+#,##0.00;-#,##0.00");
        String changeText = changeFormat.format(price.getChange());
        String changePercentText = changeFormat.format(price.getChangePercent());


        // Populate the Price and Change fields with new data.
        stocksFlexTable.setText(row, 1, priceText);
        Label changeWidget = (Label) stocksFlexTable.getWidget(row, 2);
        changeWidget.setText(changeText + " (" + changePercentText + "%)");


        // Change the color of text in the Change field based on its value.
        String changeStyleName = "noChange";

        if (price.getChangePercent() < -0.1f) {
            changeStyleName = "negativeChange";
        } else if (price.getChangePercent() > 0.1f) {
            changeStyleName = "positiveChange";
        }

        changeWidget.setStyleName(changeStyleName);
    }


    /**
     * Convert the string of JSON into JavaScript object.
     */
    final native JsArray<StockData> asArrayOfStockData(String json) /*-{
        return eval(json);
    }-*/;


    /**
     * If can't get JSON, display error message.
     *
     * @param error the error message
     */
    private void displayError(String error) {
        errorMsgLabel.setText("Error: " + error);
        errorMsgLabel.setVisible(true);
    }

}
