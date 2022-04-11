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
        return amtBought;
    }

    public double getAmtSold() {
        return amtSold;
    }

    public double getCurrStockAmt() {
        int currAmt = 0;
        for( Stock stock : this.currStock){
            currAmt += stock.getStockWorth();
        }
        currAmt += this.amtSold;
        return currAmt;
    }

    private double getDifference(){
        return ((getCurrStockAmt() - amtBought)/amtBought)*100;
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
                stock.remStock(stockAmt);
            }
        }

    }

    public String getResults(){

        return (this.name + " SPENT $" + this.amtBought + " AND ENDED WITH $" + getCurrStockAmt() + ", A " +
                getDifference() + "% RETURN.");
    }

}
