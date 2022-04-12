import java.util.ArrayList;

public class Investor {
    private String name;
    private double amtBought;
    private double amtSold;
    private ArrayList<Stock> currStock;

    public Investor(String name) {
        this.name = name;
        this.amtBought = 0;
        this.amtSold = 0;
        this.currStock = new ArrayList<Stock>();
    }

    public String getName() {
        return name;
    }

    public double getAmtBought() {
        return Math.round(amtBought * 100.0) / 100.0;
    }

    public double getAmtSold() {
        return Math.round(amtSold * 100.0) / 100.0;
    }

    public double getCurrStockAmt() {
        int currAmt = 0;
        for( Stock stock : this.currStock){
            currAmt += stock.getStockWorth();
        }
        currAmt += getAmtSold();
        return currAmt;
    }

    private double getDifference(){
        return Math.round((((getCurrStockAmt() - amtBought)/amtBought)*100.0) * 100.0) / 100.0;
    }

    public void incBought(double amt){
        this.amtBought += amt;
    }

    public void incSold(double amt){
        this.amtSold += amt;
    }

    public void addStock(Stock newStock){
        currStock.add(newStock);
    }

    public void remStock(Ticker ticker, int stockAmt){
        for(Stock stock: this.currStock){
            if(stock.getTicker().equals(ticker)){
                if(stock.getStockAmt() >= stockAmt) {
                    stock.remStock(stockAmt);
                }
                else{
                    throw new IllegalArgumentException("Error: Invalid stock amount provided. Investor: " + this.name +
                            " is attempting to sell more stock than they have.");
                }
            }
        }

    }

    public String getResults(){

        return (this.name + " SPENT $" + getAmtBought() + " AND ENDED WITH $" + getCurrStockAmt() + ", A " +
                getDifference() + "% RETURN.");
    }

}
