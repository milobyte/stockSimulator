public class Ticker {
    private String name;
    private double stockWorth;

    public Ticker(String name, double stockWorth) {
        this.name = name;
        this.stockWorth = stockWorth;
    }

    public String getName() {
        return name;
    }

    public double getStockWorth() {
        return stockWorth;
    }

    public void incWorthByPercent(double percentage){
        double incAmt = stockWorth * percentage;
        stockWorth += incAmt;
    }

    public void incWorthByDollar(double dollarAmt){
        this.stockWorth += dollarAmt;
    }

    public void decWorthByPercent(double percentage){
        double incAmt = stockWorth * percentage;
        stockWorth -= incAmt;
    }

    public void decWorthByDollar(double dollarAmt){
        this.stockWorth -= dollarAmt;
    }
}
