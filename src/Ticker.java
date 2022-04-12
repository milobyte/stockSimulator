public class Ticker {
    private String name;
    private double stockWorth;

    public Ticker(String name, double stockWorth) {
        this.name = name;
        this.stockWorth =  Math.round(stockWorth * 100.0) / 100.0;
    }

    public String getName() {
        return name;
    }

    public double getStockWorth() {
        return stockWorth;
    }

    public void incWorthByPercent(double percentage){
        double incAmt =  Math.round((stockWorth * percentage) * 100.0) / 100.0;
        stockWorth += incAmt;
    }

    public void incWorthByDollar(double dollarAmt){
        this.stockWorth +=  Math.round(dollarAmt * 100.0) / 100.0;;
    }

    public void decWorthByPercent(double percentage){
        double decAmt =  Math.round((stockWorth * percentage) * 100.0) / 100.0;
        stockWorth -= decAmt;
    }

    public void decWorthByDollar(double dollarAmt){
        this.stockWorth -= Math.round(dollarAmt * 100.0) / 100.0;;
    }
}
