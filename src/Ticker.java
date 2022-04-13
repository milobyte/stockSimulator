/**
 * This class represents a Ticker within the Stock market. It's stock worth and name define it.
 * @author Miles Stanley
 * @version 1.0
 */
public class Ticker {
    /** The name of the Ticker */
    private String name;
    /** the amount of money this Ticker's stock is worth */
    private double stockWorth;
    /** A double representing 100 for mathematical purposes */
    private static final double hundredVar = 100.0;

    /**
     * The constructor for initializing a new Ticker
     * @param name The name of the Ticker
     * @param stockWorth the amount of money this Ticker's stock is worth
     */
    public Ticker(String name, double stockWorth) {
        this.name = name;
        this.stockWorth =  Math.round(stockWorth * hundredVar) / hundredVar;
    }

    /**
     * Returns the name of the Ticker
     * @return the name of the Ticker
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the stock worth of a Ticker.
     * @return the stock worth of a Ticker.
     */
    public double getStockWorth() {
        return stockWorth;
    }

    /**
     * Increases the stock worth of a Ticker based on a percentage passed in.
     * @param percentage the percentage of increase in the Stock worth
     */
    public void incWorthByPercent(double percentage){
        double incAmt =  Math.round((stockWorth * percentage) * hundredVar) / hundredVar;
        stockWorth += incAmt;
    }

    /**
     * Increases the stock worth of a Ticker based on a dollar amount passed in.
     * @param dollarAmt the dollar amount of increase in the Stock worth
     */
    public void incWorthByDollar(double dollarAmt){
        this.stockWorth +=  Math.round(dollarAmt * hundredVar) / hundredVar;;
    }

    /**
     * Decreases the stock worth of a Ticker based on a percentage passed in.
     * @param percentage the percentage of decrease in the Stock worth
     */
    public void decWorthByPercent(double percentage){
        double decAmt =  Math.round((stockWorth * percentage) * hundredVar) / hundredVar;
        stockWorth -= decAmt;
    }

    /**
     * Decreases the stock worth of a Ticker based on a dollar amount passed in.
     * @param dollarAmt the dollar amount of decrease in the Stock worth
     */
    public void decWorthByDollar(double dollarAmt){
        this.stockWorth -= Math.round(dollarAmt * hundredVar) / hundredVar;;
    }
}
