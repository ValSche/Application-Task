package applicationTask;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/*** Application Task
* This class functions as a Test Class for the SalesTaxes-Class. Therefore inputs, methods,
* etc. will be tested here correspondingly.
* @author Valentin
* @version 1.0 (Last Update 23.03.22)
*/

public class SalesTaxesTest 
{
	SalesTaxes salesTaxes = new SalesTaxes();
    
    /**
     * This Test checks the "roundTax" method.
     */
    @Test
    public void testRoundTax()
    {
    	Double x = 1.234;
    	Double y = SalesTaxes.roundTax(x);
    	assertEquals(y, 1.25);
    }
    
    /**
     * This Test checks the "roundMoneyDigits" method.
     */
    @Test
    public void testRoundMoneyDigits()
    {
    	Double x = 1.234;
    	Double y = SalesTaxes.roundMoneyDigits(x);
    	assertEquals(y, 1.23);
    }
    
    /**
     * This Test checks the "isImported" method.
     */
    @Test
    public void testIsImported()
    {
    	String x = "imported PC";
    	assertEquals(SalesTaxes.isImported(x), true);
    	
    	String y = "PC";
    	assertEquals(SalesTaxes.isImported(y), false);
    }
 
    /**
     * This Test checks the "taxExempt" method.
     */
    @Test
    public void testTaxExempt()
    {
    	String x = "book";
    	assertEquals(SalesTaxes.taxExempt(x), true);
    	
    	String y = "Computer";
    	assertEquals(SalesTaxes.taxExempt(y), false);
    }
    
    /**
     * This Test checks the "CalculateTax" method.
     */
    @Test
    public void testCalculateTax()
    {
    	String w = "PC";
    	String x = "Imported PC";
    	String y = "banana";
    	String z = "Imported banana";
    	double price = 1.0;
    	
    	assertEquals(SalesTaxes.calculateTax(w, price), 0.1);
    	assertEquals(SalesTaxes.calculateTax(x, price), 0.15);
    	assertEquals(SalesTaxes.calculateTax(y, price), 0.0);
    	assertEquals(SalesTaxes.calculateTax(z, price), 0.05);
    }
    
    /**
     * This Test checks the "formatMoneyAmount" method.
     */
    @Test
    public void testFormatMoneyAmount()
    {
    	Double d = .0;
    	String s = SalesTaxes.formatMoneyAmount(d);
    	assertEquals(s, "0.00");
    }
    
}
