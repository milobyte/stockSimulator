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
        System.out.println("STOCK WORTH BEFORE IS " + stockWorth);
        double incAmt = stockWorth * percentage;
        stockWorth += incAmt;
        System.out.println("STOCK WORTH IS NOW " + stockWorth);
    }

    public void incWorthByDollar(double dollarAmt){
        System.out.println("STOCK WORTH BEFORE IS " + stockWorth);
        this.stockWorth += dollarAmt;
        System.out.println("STOCK WORTH IS NOW " + stockWorth);
    }

    public void decWorthByPercent(double percentage){
        double incAmt = stockWorth * percentage;
        stockWorth -= incAmt;
    }

    public void decWorthByDollar(double dollarAmt){
        this.stockWorth -= dollarAmt;
    }
}
