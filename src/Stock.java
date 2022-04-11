public class Stock {
    private Ticker ticker;
    private int stockAmt;

    public Stock(Ticker ticker, int stockAmt) {
        this.ticker = ticker;
        this.stockAmt = stockAmt;
    }

    public Ticker getTicker() {
        return ticker;
    }

    public int getStockAmt() {
        return stockAmt;
    }

    public double getStockWorth(){
        return (this.ticker.getStockWorth() * this.stockAmt);
    }

    public void incStock(int stockAmt){
        this.stockAmt += stockAmt;
    }

    public void remStock(int stockAmt){
        this.stockAmt -= stockAmt;
        if(stockAmt < 0){
            this.stockAmt = 0;
        }
    }
}
