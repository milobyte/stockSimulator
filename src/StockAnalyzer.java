import java.io.*;
import java.util.ArrayList;

/**
 * An object used to perform transactions and cost changes related to a simulated Stock market.
 * @author Miles Stanley
 * @version 1.0
 */
public class StockAnalyzer {
    /** The list that stores each Investor found in the input*/
    private ArrayList<Investor> investorList = new ArrayList<Investor>();
    /** The list that stores each Ticker found in the input*/
    private ArrayList<Ticker> tickerList = new ArrayList<Ticker>();
    /** The reader used to read each command passed in by line*/
    private BufferedReader br;
    /** A double representing 100 for mathematical purposes */
    private static final double hundredVar = 100.0;

    /**
     * The default constructor used to initialize the BufferedReader
     *
     * @param br - The reader used to read each command passed in by line
     **/
    public StockAnalyzer(BufferedReader br){
        this.br = br;
    }

    /**
     * The main method used to evaluate a series of commands regarding the simulated Stock market. This method calls
     * a series of other methods that initialize and edit Stock, Ticker, and Investor objects. At the end, an
     * alphabetically sorted list of results will be printed for each Investor.
     **/
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

            //Sorts investors by name
            investorList.sort((investor1, investor2) -> investor1.getName().compareToIgnoreCase(investor2.getName()));

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

    /**
     * A helper method used to initialize a new Ticker based on name and Stock worth provided
     *
     * @param tickerName - the name of the Ticker
     * @param tickerWorth - the worth of any stocks pertaining to said Ticker
     **/
    private void initTicker(String tickerName, double tickerWorth){
        if(getTicker(tickerName) == null) {
            Ticker newTicker = new Ticker(tickerName, tickerWorth);
            tickerList.add(newTicker);
        }
        else {
            System.out.println("Error: Ticker: " + tickerName + " already initialized");
        }
    }

    /**
     * A helper method used to execute BUY or SELL commands associated with an Investor
     *
     * @param investorName - the name of an Investor
     * @param action - the action associated with the Investor (BUY or SELL)
     * @param stockAmt - the amount of Stock that an Investor is buying or selling
     * @param tickerName - the name of the Ticker associated with the Investor's Stock
     **/
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
                currInvestor.incStock(currTicker, stockAmt);
            } else if (action.equals("SELL")) {
                currInvestor.incSold(currTicker.getStockWorth() * stockAmt);
                currInvestor.remStock(currTicker, stockAmt);
            }
        }
        catch (NullPointerException e){
            throw new IllegalArgumentException("Error: Ticker provided was not initialized");
        }
    }

    /**
     * A helper method to change the value of a Ticker's stock value by dollar amount.
     *
     * @param tickerName - the name of the Ticker associated with the changing Stock.
     * @param action - The action associated with the change of a Ticker's stock (UP or DOWN).
     * @param stockChange - the amount (in terms of $) that the Stock's value will change.
     **/
    private void dollarChange(String tickerName, String action, String stockChange) throws IllegalArgumentException{
        Ticker currTicker = getTicker(tickerName);
        if(currTicker != null) {
            if (stockChange.contains("$")) {
                double dollarAmt = Math.round(Double.parseDouble(stockChange.replace("$", ""))
                        * hundredVar) / hundredVar;
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

    /**
     * A helper method to change the value of a Ticker's stock value by percentage.
     *
     * @param tickerName - the name of the Ticker associated with the changing Stock.
     * @param action - The action associated with the change of a Ticker's stock (UP or DOWN).
     * @param stockChange - the amount (in terms of %) that the Stock's value will change.
     **/
    private void percentChange(String tickerName, String action, String stockChange) throws NullPointerException{
        Ticker currTicker = getTicker(tickerName);

        try {
            if (stockChange.contains("%")) {
                double percentChange = (Double.parseDouble(stockChange.replace("%", "")) / hundredVar);
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

    /**
     * A helper method to get an Investor whose information has been saved.
     *
     * @param name - the name associated with the Investor
     *
     * @return the Investor associated with the name parameter, or NULL if the Investor has not been saved.
     **/
    private Investor getInvestor(String name){
        for(Investor person: this.investorList){
            if(person.getName().equals(name)){
                return person;
            }
        }
        return null;
    }

    /**
     * A helper method to get a Ticker whose information has been saved.
     *
     * @param name - the name associated with the Ticker
     *
     * @return the Ticker associated with the name parameter, or NULL if the Ticker has not been saved.
     **/
    private Ticker getTicker(String name){
        for(Ticker ticker: this.tickerList){
            if(ticker.getName().equals(name)){
                return ticker;
            }
        }
        return null;
    }
}
