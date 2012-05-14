package gwt.sample.stockwatcher.client;

import com.google.gwt.core.client.JavaScriptObject;


/**
 * StockData is an overlay type.  It looks like a normal Java data type, but it can
 * interact with arbitrary JavaScript objects using JSNI (JavaScript Native Interface).
 * 
 * @author Adrian Yau
 *
 */
class StockData extends JavaScriptObject {                                   // (1)
    // Overlay types always have protected, zero argument constructors.
    protected StockData() {}                                                 // (2)

    // JSNI methods to get stock data.
    public final native String getSymbol() /*-{ return this.symbol; }-*/;    // (3)
    public final native double getPrice() /*-{ return this.price; }-*/;
    public final native double getChange() /*-{ return this.change; }-*/;

    // Non-JSNI method to return change percentage.                          // (4)
    public final double getChangePercent() {
        return 100.0 * getChange() / getPrice();
    }

}


/*
 * Implementation Notes
   --------------------
   
    (1) StockData is a subclass of JavaScriptObject, a marker type that GWT uses to denote JavaScript objects.
        JavaScriptObject gets special treatment from the GWT compiler and development mode code server. Its 
        purpose is to provide an opaque representation of native JavaScript objects to Java code.
    
    (2) Overlay types always have protected, zero-argument constructors.
    
    (3) Typically methods on overlay types are JSNI.
        These getters directly access the JSON fields you know exist.
        By design, all methods on overlay types are final and public; thus every method is statically resolvable 
        by the compiler, so there is no need for dynamic dispatch at runtime.
    
    (4) However, methods on overlay types are not required to be JSNI.
        Just as you did in the StockPrice class, you calculate the change percentage based on the price and change values.
 *
 */
