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
        runFromString("INITIAL PRICE GOOG $100\n" +
                "INITIAL PRICE FB $100\n" +
                "Fred BUY 10 GOOG\n" +
                "Fred BUY 5 FB\n" +
                "FB UP 50%\n" +
                "GOOG DOWN 25%\n" +
                "Fred SELL 5 GOOG\n" +
                "GOOG UP $10");
        runFromString("HELLO");
        runFromString("INITIAL PRICE GOOG $100\n" +
                "INITIAL PRICE FB $100\n" +
                "Fred BUY 10 GOOG\n" +
                "Fred BUY 5 FGGGG\n" + //Cause of Error
                "FB UP 50%\n" +
                "GOOG DOWN 25%\n" +
                "Fred SELL 5 GOOG\n" +
                "GOOG UP $10");
        runFromString("INITIAL PRICE GOOG $100\n" +
                "INITIAL PRICE FB $100\n" +
                "Fred BUY 10 GOOG\n" +
                "Fred BUY GHB FB\n" + //Cause of Error
                "FB UP 50%\n" +
                "GOOG DOWN 25%\n" +
                "Fred SELL 5 GOOG\n" +
                "GOOG UP $10");
        runFromString("INITIAL PRICE GOOG $100\n" +
                "INITIAL PRICE FB $100\n" +
                "Fred BUY 10 GOOG\n" +
                "Fred BUY 5 FB\n" +
                "FB UP 50\n" + //Cause of Error
                "GOOG DOWN 25%\n" +
                "Fred SELL 5 GOOG\n" +
                "GOOG UP $10");
    }

}
