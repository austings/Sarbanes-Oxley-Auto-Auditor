package menu;

///need to resolve enterprise issues stma enterprise
//flex select is a new package
//path b, cancelled 1053
import menu.View;

/**
 * author- Austin Sierra
 * last edit- Oct 10, 2017
 * 
 * SOXMain- is the main class of the Sox Audit Processor. It is what is launched with the javac command in terminal.
 * 
 * The SOX Audit Processor's goal is to automate the Sarbane's Oxley audits originally completed through Excel using
 * VLookups and filters on various reports. Sarbane's Oxley Act of 2002 mandates Corporate accountability and responsibility
 * by applying certain requirements to privately held companies. The audit itself looks at client accounts and ensures the
 * client is running payroll under the correct product and meeting product requirements.
 * 
 * (C) 2017 for Paychex, Inc. by Austin Gilbert Sierra, Online Services Analyst
 * 
 */
public class SOXMain
{
	
	/**
	 * Create a new JFrame 'view' object
	 */
	public void start()
	{
		View v = new View();
	}
	

	public static void main(String[] args)
	{
		
		SOXMain c = new SOXMain();
		c.start();
	}
}