package applicationTask;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.Scanner;

/*** Application Task
* The SalesTaxes-Class incorporates all of the functions. It saves inputs, calculates the 
* corresponding taxes and returns the receipt, when the user has entered all of his inputs.
* @author Valentin
* @version 1.0 (Last Update 23.03.22)
*/

public class SalesTaxes 
{
	// List with all the previously made inputs
	protected static LinkedList<String> shoppingCart = new LinkedList<String>();
	
	// Variable, which saves the whole tax amount for all processed inputs so far
	protected static double taxAmount;
	
	// Variable, which saves the total price for all processed inputs so far
	protected static double totalAmount;
	
	// The scanner reads the user inputs, in order to be able to process them.
	protected static Scanner scanner = new Scanner(System.in);
	
    /**
     * Main-Method
     * Displays initial message for the user and starts the application.
     */
	public static void main(String[] args) 
	{
		System.out.println(" Hello! \n This tool is supposed to help you calculate "
				+ "the taxes of your shopping cart. To use it please enter the "
				+ "corresponding items you wish to calculate. Be aware, that only "
				+ "inputs in the following form will be accepted and processed: \n"
				+ " Amount Name at Price \n An acceptable example could therefore "
				+ "look like this: \n 1 Banana at 0.50");
		
		processInput();
	}
	
    /**
     * Once the user has entered an item, this method proccesses the input. This includes
     * the following aspects:
     * Extraction of the data-values out of the input line
     * Input validation
     * Tax and cost-calculations
     * Saving the results
     */
	protected static void processInput()
	{
		// Collecting the input
		String data;
		data = scanner.nextLine();
		String[] dataFragments = data.split(" ");
		
		try
		{
			// Extraction of the respective values (In case of an invalid input the catch
			// block will be executed and the user has to try again)
			int amount = Integer.parseInt(dataFragments[0]);
			double price = Double.parseDouble(dataFragments[dataFragments.length - 1]);
			if (!dataFragments[dataFragments.length -2].equalsIgnoreCase("at"))
			{
				throw new Exception("Input not valid!");
			}
			dataFragments[0] = "";
			dataFragments[dataFragments.length - 1] = "";
			dataFragments[dataFragments.length - 2] = "";
			String name = "";
			for (int i = 1; i < (dataFragments.length -2); i++)
			{
				name = name.concat(" " + dataFragments[i]);
			}

			// Tax and price calculation
			double tax = calculateTax(name, price);	
			price = roundMoneyDigits(price + tax);	
			
			// Saving
			taxAmount = roundMoneyDigits(taxAmount + tax);
			totalAmount = roundMoneyDigits(totalAmount + price);
			shoppingCart.add(amount + name + ": " + formatMoneyAmount(price));
			
			System.out.println(" Your input was successful! \n Do you wish to enter another "
					+ "item? If yes please enter \"y\", if no enter \"n\"!");
			
			checkFinished();
		}
		catch(Exception e)
		{
			System.out.println("Please enter a valid item!");
			processInput();
		}
	}
	
    /**
     * After the user input was sucessfully processed, this function asks the user, wether
     * he is finished or not. In the first case the receipt will be shown, in the second he'll
     * be able to enter another item.
     */
	protected static void checkFinished()
	{		
		String data = scanner.nextLine();
		
		if (data.equalsIgnoreCase("y"))
		{
			processInput();
		}
		else if (data.equalsIgnoreCase("n"))
		{
			returnResult();
		}
		else
		{
			System.out.println("Please enter a valid answer!");
			checkFinished();
		}
	}
	
    /**
     * This method rounds the exact taxAmount up to the nearest 0.05Ct.An example would be:
     * 11.81 --> 11.85
     * @param Exact tax amount.
     * @return Rounded tax value. 
     */
	protected static double roundTax(double d)
	{
		double rounded = Math.ceil(d * 20);
		return rounded / 20;
	}
	
    /**
     * This method rounds the digits from the 3rd decimal place (incl.) on.An example would be:
     * 11.81000000002 --> 11.81
     * @param Exact money amount.
     * @return Rounded money value. 
     */
	protected static double roundMoneyDigits(double d)
	{
		double rounded = Math.round(d * 100);
		return rounded / 100;
	}
	
    /**
     * This method checks, if the item is imported.
     * @param Name of the item.
     * @return True, if imported. False, if not imported. 
     */
	protected static boolean isImported(String s)
	{
		return (s.contains("imported") || s.contains("Imported"));
	}
	
    /**
     * This method checks, if the item is exempt of the sales tax. Items to which this rule
     * applies are books, food and medical products. For Simplicity only a limited selection
     * is checked (If desired, the list can be extended).
     * @param Name of the item.
     * @return True, if exempt of tax. False, if tax applies. 
     */
	protected static boolean taxExempt(String s)
	{
		boolean exempt = false;
		String[] specialGoods = {"book", "chocolate", "pills", "banana", 
				"bread", "water", "medicine"}; // The list could be continued...
		
		for (String entry : specialGoods)
		{
			if (s.toLowerCase(). contains(entry))
			{
				exempt = true;
			}
		}
		
		return exempt;
	}
	
    /**
     * This method calculates the appropriate tax depending on the applicable taxrate 
     * and the rounding rule.
     * @param Name of the item.
     * @param Price (User-Input).
     * @return Correct/final tax value of an item. 
     */
	protected static double calculateTax(String name, double price)
	{
		if (isImported(name) && taxExempt(name))
		{
			double importTax = roundTax(price * 0.05);
			return importTax;
		}
		else if (isImported(name) && !taxExempt(name))
		{
			double importTax = roundTax(price * 0.05);
			double salesTax = roundTax(price * 0.10);
			return roundMoneyDigits(importTax + salesTax);
		}
		else if (!isImported(name) && !taxExempt(name))
		{
			double salesTax = roundTax(price * 0.10);
			return salesTax;
		}
		else // (!isImported(name) && taxExempt(name))
		{
			return 0.0; 
		}
	}
	
    /**
     * This method prints the receipt details of the previously entered item to the screen.
     */
	protected static void returnResult()
	{
		scanner.close();
		
		for (String s : shoppingCart)
		{
			System.out.println(s);
		}
		
		System.out.println("Sales Taxes: " + formatMoneyAmount(taxAmount));
		System.out.println("Total: " + formatMoneyAmount(totalAmount));
	}
	
    /**
     * This method formates the money amounts correctly. An example could look like the following:
     * 10.5 --> 10.50
     * @param Money value, that shall be formatted.
     * @return Formatted money value. 
     */
	protected static String formatMoneyAmount(double d)
	{
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(d).replace(",", ".");
	}
}
