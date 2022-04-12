import java.io.*;
import java.util.ArrayList;

public class StockAnalyzer {
    private ArrayList<Investor> investorList = new ArrayList<Investor>();
    private ArrayList<Ticker> tickerList = new ArrayList<Ticker>();
    private BufferedReader br;

    public StockAnalyzer(BufferedReader br){
        this.br = br;
    }

    public void evaluateFile(){
        String investorName = "";
        String actionVar = "";
        String tickerName = "";
        int stockAmt = 0;
        String stockChange = "";
        try {
            String line;
            while ((line = br.readLine()) != null) {
                String[] lineParts = line.split(" ");

                if((lineParts[0] + " " + lineParts[1]).equals("INITIAL PRICE")){
                    initTicker(lineParts[2], Double.parseDouble(lineParts[3].replace("$", "")));
                }
                else if(lineParts[1].equals("BUY") || lineParts[1].equals("SELL")){
                    investorName = lineParts[0];
                    actionVar = lineParts[1];
                    stockAmt = Integer.parseInt(lineParts[2]);
                    tickerName = lineParts[3];
                    investorTransaction(investorName, actionVar, stockAmt, tickerName);
                }
                else if(lineParts[1].equals("UP") || lineParts[1].equals("DOWN")){
                    tickerName = lineParts[0];
                    actionVar = lineParts[1];
                    stockChange = lineParts[2];

                    if(stockChange.contains("%")){
                        percentChange(tickerName, actionVar, stockChange);
                    }
                    else if(stockChange.contains("$")) {
                        dollarChange(tickerName, actionVar, stockChange);
                    }
                    else{
                        System.out.println("Syntax Error: % or $ amount not specified.");
                        return;
                    }
                }
                else{
                    System.out.println("NONE");
                }
            }

            for(Investor person : investorList){
                //ALPHABETICALLY
                System.out.println(person.getResults());
            }

        }
        catch (NumberFormatException e){
            System.out.println("Syntax Error: Please ensure the correct syntax of number amounts.");
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Syntax Error: Invalid line provided.");
        }
        catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        catch (IOException e){
            System.out.println("Error: File could not be read.");
        }
    }

    private void initTicker(String tickerName, double tickerWorth){
        if(getTicker(tickerName) == null) {
            Ticker newTicker = new Ticker(tickerName, tickerWorth);
            tickerList.add(newTicker);
        }
        else {
            System.out.println("Error: Ticker: " + tickerName + " already initialized");
        }
    }

    private void investorTransaction(String investorName, String action, int stockAmt, String tickerName) throws IllegalArgumentException{

        Investor currInvestor = getInvestor(investorName);
        Ticker currTicker = getTicker(tickerName);

        if(currInvestor == null){
            Investor newInvestor = new Investor(investorName);
            investorList.add(newInvestor);
            currInvestor = newInvestor;
        }

        try {
            if (action.equals("BUY")) {
                currInvestor.incBought(currTicker.getStockWorth() * stockAmt);
                currInvestor.addStock(new Stock(currTicker, stockAmt));
            } else if (action.equals("SELL")) {
                //CHECK IF INVESTOR HAS ENOUGH STOCK
                currInvestor.incSold(currTicker.getStockWorth() * stockAmt);
                currInvestor.remStock(currTicker, stockAmt);
            }
        }
        catch (NullPointerException e){
            throw new IllegalArgumentException("Error: Ticker provided was not initialized");
        }
    }

    private void dollarChange(String tickerName, String action, String stockChange) throws IllegalArgumentException{
        Ticker currTicker = getTicker(tickerName);
        if(currTicker != null) {
            if (stockChange.contains("$")) {
                double dollarAmt = Math.round(Double.parseDouble(stockChange.replace("$", ""))
                        * 100.0) / 100.0;
                if (action.equals("UP")) {
                    currTicker.incWorthByDollar(dollarAmt);
                } else if (action.equals("DOWN")) {
                    currTicker.decWorthByDollar(dollarAmt);
                }
            }
        }
        else {
            throw new IllegalArgumentException("Ticker provided was not initialized");
        }
    }

    private void percentChange(String tickerName, String action, String stockChange) throws NullPointerException{
        Ticker currTicker = getTicker(tickerName);

        try {
            if (stockChange.contains("%")) {
                double percentChange = (Double.parseDouble(stockChange.replace("%", "")) / 100);
                ;
                if (action.equals("UP")) {
                    currTicker.incWorthByPercent(percentChange);
                } else if (action.equals("DOWN")) {
                    currTicker.decWorthByPercent(percentChange);
                }
            }
        }
        catch(NullPointerException e){
            System.out.println("Ticker provided was not initialized.");
            throw e;
        }
    }

    private Investor getInvestor(String name){
        for(Investor person: this.investorList){
            if(person.getName().equals(name)){
                return person;
            }
        }
        return null;
    }

    private Ticker getTicker(String name){
        for(Ticker ticker: this.tickerList){
            if(ticker.getName().equals(name)){
                return ticker;
            }
        }
        return null;
    }
}
