import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class StockAnalyzer {
    private ArrayList<Investor> investorList = new ArrayList<Investor>();
    private ArrayList<Ticker> tickerList = new ArrayList<Ticker>();
    public StockAnalyzer(String filename){
        try {
            FileReader fileScanner = new FileReader(filename);
            evaluateFile(fileScanner);
        }
        catch (FileNotFoundException e){
            System.out.println(e);
        }
    }
    private void evaluateFile(FileReader newFile){
        String investorName = "";
        String actionVar = "";
        String tickerName = "";
        int stockAmt = 0;
        String stockChange = "";
        try (BufferedReader br = new BufferedReader(newFile)) {
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
                }
                else{
                    System.out.println("NONE");
                }
            }

        }
        catch (IOException e){
            System.out.println(e);
        }
        for(Investor person : investorList){
            //ALPHABETICALLY
            System.out.println(person.getResults());
        }
    }

    private void initTicker(String tickerName, double tickerWorth){
        Ticker newTicker = new Ticker(tickerName, tickerWorth);
        tickerList.add(newTicker);
    }

    private void investorTransaction(String investorName, String action, int stockAmt, String tickerName){

        Investor currInvestor = getInvestor(investorName);
        Ticker currTicker = getTicker(tickerName);

        if(currInvestor == null){
            System.out.println("Setting up new Investor");
            Investor newInvestor = new Investor(investorName);
            investorList.add(newInvestor);
            currInvestor = newInvestor;
        }

        if(action.equals("BUY")){
            currInvestor.incBought(currTicker.getStockWorth() * stockAmt);
            currInvestor.addStock(new Stock(currTicker, stockAmt));
        }
        else if(action.equals("SELL")){
            //CHECK IF INVESTOR HAS ENOUGH STOCK
            currInvestor.incSold(currTicker.getStockWorth() * stockAmt);
            currInvestor.remStock(currTicker, stockAmt);
        }
    }

    private void dollarChange(String tickerName, String action, String stockChange){
        Ticker currTicker = getTicker(tickerName);

        if(stockChange.contains("$")){
            double dollarAmt = Math.round(Double.parseDouble(stockChange.replace("$", ""))
                    * 100.0) / 100.0;
            if(action.equals("UP")) {
                currTicker.incWorthByDollar(dollarAmt);
            }
            else if(action.equals("DOWN")){
                currTicker.decWorthByDollar(dollarAmt);
            }
        }
    }

    private void percentChange(String tickerName, String action, String stockChange){
        Ticker currTicker = getTicker(tickerName);

        if(stockChange.contains("%")){
            double percentChange = (Double.parseDouble(stockChange.replace("%", ""))/100);;
            if(action.equals("UP")) {
                currTicker.incWorthByPercent(percentChange);
            }
            else if(action.equals("DOWN")){
                currTicker.decWorthByPercent(percentChange);
            }
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
