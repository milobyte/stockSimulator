public class Driver {
    private static final int ONE = 1;

    public static void main(String[] args) {
        if(args.length == ONE){
            String file = args[0];
            StockAnalyzer newAnalyzer = new StockAnalyzer(file);
        }
    }
}
