/**
 * An object used to represent the amount of a specific Stock belonging to an Investor.
 * @author Miles Stanley
 * @version 1.0
 */
public class Stock {
    /**The Ticker associated with this Stock. Determines the worth of the Stock*/
    private Ticker ticker;
    /**The amount of Stock currently owned by an Investor*/
    private int stockAmt;

    /**
     * The default constructor used to initialize this Stock based on associated Ticker and amount bought.
     *
     * @param ticker - The Ticker that's associated with this Stock.
     * @param stockAmt - the amount of Stock that has been bought.
     **/
    public Stock(Ticker ticker, int stockAmt) {
        this.ticker = ticker;
        this.stockAmt = stockAmt;
    }

    /**
     * Returns the Ticker associated with this Stock.
     *
     * @return the Ticker associated with the Stock.
     **/
    public Ticker getTicker() {
        return ticker;
    }

    /**
     * Returns the amount of Stock owned by an Investor.
     *
     * @return the amount of Stock owned by an Investor.
     **/
    public int getStockAmt() {
        return stockAmt;
    }

    /**
     * A helper method to calculate and return the overall worth of an Investor's stock based on the worth associated
     * with the Ticker and the amount of this Stock that the Investor owns.
     *
     * @return a double representing the overall worth of an Investor's stock with a particular Ticker.
     **/
    public double getStockWorth(){
        return (this.ticker.getStockWorth() * this.stockAmt);
    }

    /**
     * A helper method to increase the amount of Stock that an Investor owns.
     *
     * @param stockAmt - the amount of Stock to increase by
     **/
    public void incStock(int stockAmt){
        this.stockAmt += stockAmt;
    }

    /**
     * A helper method to decrease the amount of Stock that an Investor owns.
     *
     * @param stockAmt - the amount of Stock to decrease by
     **/
    public void remStock(int stockAmt){
        this.stockAmt -= stockAmt;
        if(stockAmt < 0){ //If more stock was sold than available, set to 0
            this.stockAmt = 0;
        }
    }
}
