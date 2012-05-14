package gwt.sample.stockwatcher.client;

import java.io.Serializable;


public class StockPrice implements Serializable {
    private static final long serialVersionUID = 6920472853042767550L;
    
    private String symbol;
    private double price;
    private double change;

    public StockPrice() {
        super();
    }

    public StockPrice(String symbol, double price, double change) {
        this.symbol = symbol;
        this.price = price;
        this.change = change;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public double getPrice() {
        return this.price;
    }

    public double getChange() {
        return this.change;
    }

    public double getChangePercent() {
        return 100.0 * this.change / this.price;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setChange(double change) {
        this.change = change;
    }
}
