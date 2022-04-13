import java.util.ArrayList;

/**
 * An object used to represent an Investor in the Stock market.
 * @author Miles Stanley
 * @version 1.0
 */
public class Investor {
    /** The name associated with the Investor*/
    private String name;
    /** The amount of money this Investor has spent buying in total*/
    private double amtBought;
    /** The amount of money this Investor has spent selling in total*/
    private double amtSold;
    /** The list that contains all the Stocks associated with this Investor*/
    private ArrayList<Stock> currStock;

    /**
     * The default constructor used to initialize an Investor.
     *
     * @param name - The name associated with an Investor
     **/
    public Investor(String name) {
        this.name = name;
        this.amtBought = 0;
        this.amtSold = 0;
        this.currStock = new ArrayList<Stock>();
    }

    /**
     * Returns the name of the Investor
     *
     * @return the name of the Investor
     **/
    public String getName() {
        return name;
    }

    /**
     * Returns the amount of money used to buy Stock by the Investor.
     *
     * @return the amount of money used to buy Stock by the Investor.
     **/
    public double getAmtBought() {
        return Math.round(amtBought * 100.0) / 100.0;
    }

    /**
     * Returns the amount of money gained by selling Stock.
     *
     * @return the amount of money gained by selling Stock.
     **/
    public double getAmtSold() {
        return Math.round(amtSold * 100.0) / 100.0;
    }

    /**
     * Returns the amount of money the Investor made in the end by adding the overall worth of an Investor's stock to
     * the amount of money they gained by selling Stock.
     *
     * @return the amount of money the Investor made in the end.
     **/
    public double getEndingTotal() {
        int currAmt = 0;
        for( Stock stock : this.currStock){
            currAmt += stock.getStockWorth();
        }
        currAmt += getAmtSold();
        return currAmt;
    }

    /**
     * Returns the percent difference between the amount of money and Investor used to buy Stock and the amount of
     * money they ended with.
     *
     * @return the percent difference in the amount bought and the total revenue.
     **/
    private double getDifference(){
        return Math.round((((getEndingTotal() - amtBought)/amtBought)*100.0) * 100.0) / 100.0;
    }

    /**
     * Increases the total amount of money an Investor has used to buy Stock.
     *
     * @param amt - the amount of money to add to the total.
     **/
    public void incBought(double amt){
        this.amtBought += amt;
    }

    /**
     * Increases the total amount of money an Investor has gained by selling Stock.
     *
     * @param amt - the amount of money to add to the total.
     **/
    public void incSold(double amt){
        this.amtSold += amt;
    }

    /**
     * Adds a new Stock object to the Investor's list of owned Stocks.
     *
     * @param newStock - the Stock object to add to the list of currently owned Stocks.
     **/
    public void addStock(Stock newStock){
        currStock.add(newStock);
    }

    /**
     * Increases the total amount of Stock an Investor owns for a particular Stock, or creates a new Stock object
     * if the Investor does not currently own Stock with a specific Ticker.
     *
     * @param ticker - the Ticker associated with the Stock being added
     * @param stockAmt - how much of the Stock an Investor has bought
     **/
    public void incStock(Ticker ticker, int stockAmt){
        for(Stock stock: this.currStock){
            if(stock.getTicker().equals(ticker)){ //If Stock already owned
                stock.incStock(stockAmt);
                return;
            }
        }
        //Add new Stock if the Stock has not been initialized
        addStock(new Stock(ticker, stockAmt));
    }

    /**
     * Decreases the total amount of Stock an Investor owns for a particular Stock, or throws an error if the Investor
     * is attempting to sell Stock that they do not own.
     *
     * @param ticker - the Ticker associated with the Stock being sold
     * @param stockAmt - how much of the Stock an Investor is trying to sell
     **/
    public void remStock(Ticker ticker, int stockAmt){
        for(Stock stock: this.currStock){
            if(stock.getTicker().equals(ticker)){
                if(stock.getStockAmt() >= stockAmt) {
                    stock.remStock(stockAmt);
                    return;
                }
                else{
                    throw new IllegalArgumentException("Error: Invalid stock amount provided. Investor: " + this.name +
                            " is attempting to sell more stock than they have.");
                }
            }
        }
        throw new IllegalArgumentException("Error: Invalid Ticker provided. This Investor has no Stock with " +
                "the Ticker provided.");
    }

    public String getResults(){

        return (this.name + " SPENT $" + getAmtBought() + " AND ENDED WITH $" + getEndingTotal() + ", A " +
                getDifference() + "% RETURN.");
    }

}
