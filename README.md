# Stock Simulator
This program is meant to simulate stock simulations. There are three types of commands that the program can take.
## Command Types
- Initial prices 
  - These set the initial price of stocks. They are of the form INITIAL PRICE &lt;ticker&gt; &lt;price&gt;
- Buy and sell commands 
  - Investors can buy and sell stocks at their current price, with commands of the form &lt;investor name&gt; &lt;buy or sell&gt; &lt;number of shares&gt; &lt;ticker&gt;
- Price change commands
  - These receive both relative and absolute price change commands, of the form &lt;ticker&gt; &lt;up or down&gt; &lt;change&gt;, and affect the value of a Ticker's stock
## Running the Program
The program can be run by the following within the src directory:
- java Driver.java &lt;optional text file&gt;

Providing a path to a custom text file will run the program on the commands within the file. Excluding this argument will run the unit tests located within the driver. 
