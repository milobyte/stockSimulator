import java.io.*;

public class Driver {
    private static final int ZERO = 0;
    private static final int ONE = 1;

    public static void main(String[] args) {
        if(args.length == ONE){
            String file = args[ZERO];
            evaluateFromFile(file);
        }
        else {
            System.out.println("RUNNING UNIT TESTS\n--------------------------");
            runUnitTests();
        }
    }

    private static void evaluateFromFile(String fileName){
        try {
            File initFile = new File(fileName);
            System.setIn(new FileInputStream(initFile));
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(System.in));
            StockAnalyzer newAnalyzer = new StockAnalyzer(fileReader);
            newAnalyzer.evaluateFile();
        }
        catch(FileNotFoundException e){
            System.out.println("Error: File could not be found.");
        }
    }

    private static void runFromString(String testString){
        try {
            InputStream testStream = new ByteArrayInputStream(testString.getBytes("UTF-8"));
            System.setIn(testStream);
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(System.in));
            StockAnalyzer newAnalyzer = new StockAnalyzer(fileReader);
            newAnalyzer.evaluateFile();
        }
        catch(UnsupportedEncodingException e){
            System.out.println("Error: Conversion of test string to bytes was unsuccessful");
        }
    }

    private static void runUnitTests() {
        //Normal Test
        runFromString("INITIAL PRICE GOOG $100\n" +
                "INITIAL PRICE FB $100\n" +
                "Fred BUY 10 GOOG\n" +
                "Fred BUY 5 FB\n" +
                "FB UP 50%\n" +
                "GOOG DOWN 25%\n" +
                "Fred SELL 5 GOOG\n" +
                "GOOG UP $10");

        //Test for invalid string input
        runFromString("HELLO");

        //Test for invalid Ticker provided
        runFromString("INITIAL PRICE GOOG $100\n" +
                "INITIAL PRICE FB $100\n" +
                "Fred BUY 10 GOOG\n" +
                "Fred BUY 5 FGGGG\n" + //Cause of Error
                "FB UP 50%\n" +
                "GOOG DOWN 25%\n" +
                "Fred SELL 5 GOOG\n" +
                "GOOG UP $10");

        //Test for invalid stock amount provided.
        runFromString("INITIAL PRICE GOOG $100\n" +
                "INITIAL PRICE FB $100\n" +
                "Fred BUY 10 GOOG\n" +
                "Fred BUY GHB FB\n" + //Cause of Error
                "FB UP 50%\n" +
                "GOOG DOWN 25%\n" +
                "Fred SELL 5 GOOG\n" +
                "GOOG UP $10");

        //Test for invalid increase provided (no $ or %)
        runFromString("INITIAL PRICE GOOG $100\n" +
                "INITIAL PRICE FB $100\n" +
                "Fred BUY 10 GOOG\n" +
                "Fred BUY 5 FB\n" +
                "FB UP 50\n" + //Cause of Error
                "GOOG DOWN 25%\n" +
                "Fred SELL 5 GOOG\n" +
                "GOOG UP $10");

        //Test for invalid stock amount sold
        runFromString("INITIAL PRICE GOOG $100\n" +
                "INITIAL PRICE FB $100\n" +
                "Fred BUY 10 GOOG\n" +
                "Fred BUY 5 FB\n" +
                "FB UP 50%\n" +
                "GOOG DOWN 25%\n" +
                "Fred SELL 11 GOOG\n" + //Cause of Error
                "GOOG UP $10");

        //Test for alphabetical organization
        runFromString("INITIAL PRICE GOOG $100\n" +
                "INITIAL PRICE FB $100\n" +
                "Fred BUY 10 GOOG\n" +
                "Allan BUY 5 FB\n" +
                "FB UP 50%\n" +
                "GOOG DOWN 25%\n" +
                "Fred SELL 5 GOOG\n" +
                "GOOG UP $10");

        //Test for alphabetical organization
        runFromString("INITIAL PRICE GOOG $100\n" +
                "INITIAL PRICE FB $100\n" +
                "Fred BUY 10 GOOG\n" +
                "Allan BUY 5 FB\n" +
                "Sarah BUY 10 FB\n" +
                "Allan SELL 2 FB\n" +
                "FB UP 50%\n" +
                "GOOG DOWN 25%\n" +
                "Fred SELL 5 GOOG\n" +
                "GOOG UP $10\n" +
                "Sarah SELL 8 FB\n" +
                "FB UP $200");

        //Test for double rounding
        runFromString("INITIAL PRICE GOOG $100.18\n" +
                "INITIAL PRICE FB $100.43\n" +
                "Fred BUY 10 GOOG\n" +
                "Allan BUY 5 FB\n" +
                "Sarah BUY 10 FB\n" +
                "Allan SELL 2 FB\n" +
                "FB UP 50%\n" +
                "GOOG DOWN 25%\n" +
                "Fred SELL 5 GOOG\n" +
                "GOOG UP $10\n" +
                "Sarah SELL 8 FB\n" +
                "FB UP $200");
    }

}
