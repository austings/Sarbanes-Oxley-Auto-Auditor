package menu;

import static java.time.temporal.TemporalAdjusters.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import menu.*;

/**
 * author(s)
 * - Austin Sierra
 * -
 * last edit- March 8, 2018
 * 
 * Compile- Performs the "Vlookups" and filters on the reports. It creates and compiles
 * the output by using the other reports and then writes the correct
 * action under the Key Indicators, Comments, and Action Needed columns.
 * 
 */
public class Compile 
{
	
	//						//
	//		VARIABLES		//
	//						//
	
	//Path for the output file
	String outPath = "";
	
	//Report Object Declaration. We need a report java object for each report we input into the program
	Report EOMClientBaseSTM;//0
	Report EOMClientBaseTLO;//1
	Report currentClientSTM;//2
	Report currentClientTLO;//3
	Report qryActive1152;//4
	Report qryActive1257;//5
	Report tblTLOwAS;//6
	Report EDW;//7
	Report PRPackage;//8
	Report PRStatus;//9
	Report qryCancelTLO;//10
	Report qryCancelSTM;//11
	Report activeSTMCodes;//12
	Report cancelPayroll;//13
	
	Report output;//14
	
	//						//
	//		METHODS			//
	//						//
	
	/**
	 * Compile- Constructor for the compile class. The constructor creates a new CSVReader and,
	 * after considering the ID of the audit we are running, reads & saves them under the correct report object.
	 * 
	 * @param paths: paths is the collection of needed file paths for each report, received from the View and inputed by the user.
	 * @param ID: is the associated ID of the audit we are running. Ex: TLO PNG A, TLO ASO B, etc. I assign a different integer number to each report type.
	 */
	public Compile(ArrayList<String> paths, int ID) throws NumberFormatException
	{
		
		CSVReader csvr = new CSVReader();
		
		//A switch takes a different action depending on the integer it receives. Each case represents a different integer representing the report type.
		switch(ID)
		{
			case 1://1 is the number I use to represent TLO A PNG. We are dealing with TLO A PNGin this case.
				EOMClientBaseTLO = csvr.read(paths.get(0), 1);
				//tblTLOwAS = csvr.read(paths.get(1), 6); The program doesn't check for the assigned rep report. So I commented this.
				EDW = csvr.read(paths.get(2), 7);
				PRPackage = csvr.read(paths.get(3), 8);
				PRStatus = csvr.read(paths.get(4), 9);
				cancelPayroll = csvr.read(paths.get(5), 13);
				TLOAPNG();
				break;
			case 2://dealing with TLO B PNG
				EOMClientBaseTLO = csvr.read(paths.get(0), 1);
				currentClientTLO = csvr.read(paths.get(1), 3);
				qryActive1152 = csvr.read(paths.get(2), 4);
				EDW = csvr.read(paths.get(3), 7);
				PRPackage = csvr.read(paths.get(4), 8);
				qryCancelTLO = csvr.read(paths.get(5), 10);
				TLOBPNG();
				break;
			case 3://dealing with TLO A ASO
				EOMClientBaseTLO = csvr.read(paths.get(0), 1);
				//tblTLOwAS = csvr.read(paths.get(1), 6);
				EDW = csvr.read(paths.get(2), 7);
				PRPackage = csvr.read(paths.get(3), 8);
				PRStatus = csvr.read(paths.get(4), 9);
				cancelPayroll = csvr.read(paths.get(5), 13);
				TLOAASO();
				break;
			case 4://dealing with TLO B PNG
				EOMClientBaseTLO = csvr.read(paths.get(0), 1);
				currentClientTLO = csvr.read(paths.get(1), 3);
				qryActive1152 = csvr.read(paths.get(2), 4);
				EDW = csvr.read(paths.get(3), 7);
				PRPackage = csvr.read(paths.get(4), 8);
				qryCancelTLO = csvr.read(paths.get(5), 10);
				TLOBASO();
				break;
			case 5://dealing with TLO A PATH
				EOMClientBaseTLO = csvr.read(paths.get(0), 1);
				//tblTLOwAS = csvr.read(paths.get(1), 6);
				EDW = csvr.read(paths.get(2), 7);
				PRPackage = csvr.read(paths.get(3), 8);
				PRStatus = csvr.read(paths.get(4), 9);
				cancelPayroll = csvr.read(paths.get(5), 13);
				TLOAPATH();
				break;
			case 6://dealing with TLO B PATH
				EOMClientBaseTLO = csvr.read(paths.get(0), 1);
				currentClientTLO = csvr.read(paths.get(1), 3);
				EDW = csvr.read(paths.get(2), 7);
				PRPackage = csvr.read(paths.get(3), 8);
				cancelPayroll = csvr.read(paths.get(4), 13);
				TLOBPATH();
				break;
			case 7://dealing with STM A
				EOMClientBaseSTM = csvr.read(paths.get(0), 0);
				//tblTLOwAS =csvr.read(paths.get(1), 6);
				EDW = csvr.read(paths.get(2), 7);
				PRPackage = csvr.read(paths.get(3), 8);
				PRStatus = csvr.read(paths.get(4), 9);
				cancelPayroll = csvr.read(paths.get(5), 13);
				STMA();
				break;
			case 8://dealing with STM B
				EOMClientBaseSTM = csvr.read(paths.get(0), 0);
				qryActive1257 = csvr.read(paths.get(1), 5);
				EDW = csvr.read(paths.get(2), 7);
				PRPackage = csvr.read(paths.get(3), 8);
				qryCancelSTM = csvr.read(paths.get(4), 11);
				activeSTMCodes = csvr.read(paths.get(5), 12);
				cancelPayroll = csvr.read(paths.get(6), 13);
				STMB();
				break;
			case 9://dealing with ESS A
				EOMClientBaseSTM = csvr.read(paths.get(0), 0);
				//tblTLOwAS =csvr.read(paths.get(1), 6);
				EDW = csvr.read(paths.get(2), 7);
				PRPackage = csvr.read(paths.get(3), 8);
				PRStatus = csvr.read(paths.get(4), 9);
				cancelPayroll = csvr.read(paths.get(5), 13);
				ESSA();
			default:
				break;
		}
		
		//the output file destination is always the last string in paths array.
		outPath = paths.get(paths.size()-1);
		output();
	}
	
	
	/**
	 * TLOAPNG- returns no values and takes no parameters
	 * 
	 * This is the method which is called after the user selects this as the audit they want to run, and all the reports have already been read by the constructor.
	 */
	public void TLOAPNG()
	{
		//First, define the output headers. These headers are different for each report depending on the type of information we want to display in the output file.
		String[] headers = {"Bis ID","Branch","Client#","Plan Code","Company Name","Key Indicator","Comment","Action Needed",
				"EDW?", "TLO Cancel","PR Status","PR Package","Last PR Run Date", "Notes"};
		//instantiate the output report object.
		output = new Report(14,headers);
		
		//copy Bis, Branch, clientnum, plancode, compName, and cancel date from HRIS report, and write to the output java object. 

		int track = 0;
		for(int i = 0;i<EOMClientBaseTLO.getNumberOfRecords();i++)
		{
			if(EOMClientBaseTLO.getRecord(i).getValue(3).toString().trim().equalsIgnoreCase("1205")) {
				//copy
				output.addBlankRecord();
				String bis = EOMClientBaseTLO.getRecord(i).getValue(0);
				String branch = EOMClientBaseTLO.getRecord(i).getValue(1);
				String clientnum = EOMClientBaseTLO.getRecord(i).getValue(2);
				String plancode = EOMClientBaseTLO.getRecord(i).getValue(3);
				String compName = EOMClientBaseTLO.getRecord(i).getValue(4);
				String cancelDate = EOMClientBaseTLO.getRecord(i).getValue(5);//grab cancel date.
				
				//paste
				output.getRecord(track).setValue(0, bis);
				output.getRecord(track).setValue(1, branch);
				output.getRecord(track).setValue(2, clientnum);
				output.getRecord(track).setValue(3, plancode);
				output.getRecord(track).setValue(4, compName);
				output.getRecord(track).setValue(9, cancelDate);
				track++;
				}
		}
		
		//just print to the console an arbitrary percentage so we know the program is running.
		System.out.println("10%");
		
		
		//See if the BIS id is on the EDW report
		for(int i = 0; i<output.getNumberOfRecords();i++)
		{
			boolean found = false;
			String bis = output.getRecord(i).getValue(0);
			for(int j = 0; j<EDW.getNumberOfRecords();j++)
			{
				//filter on productivity package and TLO online transaction fee
				if((EDW.getRecord(j).getValue(7).equals("Paychex Productivity")))
				{
					if(EDW.getRecord(j).getValue(10).equals("Time and Labor Online Transaction Fee"))
					{
						if(bis.equals(EDW.getRecord(j).getValue(1)))
						{
						//if a bis id is found, its on the EDW
						output.getRecord(i).setValue(8, "Yes");
						found = true;
						break;
						}
					}
				}
			}
			//if the bis id is not found, its not on the EDW
			if(!found)
				output.getRecord(i).setValue(8, "No");
			System.out.println(output.getNumberOfRecords()-i);
		}
		System.out.println("25%");
		
		
		//see if the BIS id concatenated with the CLIENTNUM matches with an entry on the PRStatus Report
		for(int i = 0; i<output.getNumberOfRecords();i++)
		{
			boolean found = false;
			
			String concat = output.getRecord(i).getValue(0)+
					("00000000"+output.getRecord(i).getValue(2)).substring(output.getRecord(i).getValue(2).length());
			
			for(int j = 0; j<PRStatus.getNumberOfRecords();j++)//update to concat
			{
				String concattarget = PRStatus.getRecord(j).getValue(0)+
						("00000000"+PRStatus.getRecord(j).getValue(3)).substring(PRStatus.getRecord(j).getValue(3).length());
				
				//if its on the PR status report, write the PR status on the output report
				if(concat.equals(concattarget))
				{
							
					output.getRecord(i).setValue(10, PRStatus.getRecord(j).getValue(5));
					found = true;
					break;
				}

			}
			//if its not on the PR Status report, write 'N/A'
			if(!found)
				output.getRecord(i).setValue(10, "N/A");
			System.out.println(output.getNumberOfRecords()-i);
		}		
		System.out.println("50%");
		
		
		//see if its on PR Package report
		for(int i = 0; i<output.getNumberOfRecords();i++)
		{
			boolean found = false;
			
			String concat = output.getRecord(i).getValue(0)+
					("00000000"+output.getRecord(i).getValue(2)).substring(output.getRecord(i).getValue(2).length());
			
			//see if the BIS id concatenated with the CLIENTNUM matches with an entry on the PRPackage Report
			for(int j = 0; j<PRPackage.getNumberOfRecords();j++)
			{
				String concattarget = PRPackage.getRecord(j).getValue(0)+
						("00000000"+PRPackage.getRecord(j).getValue(3)).substring(PRPackage.getRecord(j).getValue(3).length());
				
				//if its on the PR status report, write the Package on the output report
				if(concat.equals(concattarget))
				{
					//First, check if the value is null. This sometimes happens if the last two entries are blank.
					//In this case, update the date to 1/1/1900 which is the null value for a date.
					if(PRPackage.getRecord(j).getValue(5) == null)	
					{
						output.getRecord(i).setValue(11, PRPackage.getRecord(j).getValue(4));
						output.getRecord(i).setValue(12, "1/1/1900");
						found = true;
						break;
					}
					else
					{
						output.getRecord(i).setValue(11, PRPackage.getRecord(j).getValue(4));
						output.getRecord(i).setValue(12, PRPackage.getRecord(j).getValue(5));
						found = true;
						break;
					}
				}

			}
			if(!found) 
			{
				output.getRecord(i).setValue(11, "N/A");
				output.getRecord(i).setValue(12, "1/1/1900");
			}
			System.out.println(output.getNumberOfRecords()-i);
			
		}

		System.out.println("75%");
		//cross reference columns, and write the appropriate action
		for(int i = 0; i<output.getNumberOfRecords();i++)
		{
			//if this is a demo client ELSE
			if(Integer.parseInt(output.getRecord(i).getValue(1).toString())>1900)
			{
				output.getRecord(i).setValue(5, "Demo Client");
				output.getRecord(i).setValue(6, "Demo Client");
				output.getRecord(i).setValue(7, "None Needed");
			}
			else
			{
				//if this client was already cancelled ELSE
				if(output.getRecord(i).getValue(9).length()>1)
				{
					output.getRecord(i).setValue(5, "Timing Issue");
					output.getRecord(i).setValue(6, "TLO Cancelled");
					output.getRecord(i).setValue(7, "None Needed");
				}
				else
				{
					//if they've cancelled payroll ELSE
					if(output.getRecord(i).getValue(10).equals("Terminated/Lost")|
							output.getRecord(i).getValue(10).equals("Inactive"))
					{
						output.getRecord(i).setValue(5, "Cancelled Payroll");
						output.getRecord(i).setValue(6, "Cancelled Payroll");
						output.getRecord(i).setValue(7, "Follow Proposed to cancel process");
					}
					else
					{
						//They're running the standalone payroll product ELSE
						if(!(output.getRecord(i).getValue(11).trim().equalsIgnoreCase("Paychex Productivity"))&
								!(output.getRecord(i).getValue(11).trim().equalsIgnoreCase("Paychex HR Solutions - Administrative Service"))&
								!(output.getRecord(i).getValue(11).trim().equalsIgnoreCase("N/A")))
						{
							output.getRecord(i).setValue(5, "Branch Operations");
							output.getRecord(i).setValue(6, "Running "+output.getRecord(i).getValue(11));
							output.getRecord(i).setValue(7, "Create case for AS to get updated order form.");
						}
						else
						{
							//They're running the wrong bundle ELSE
							if(output.getRecord(i).getValue(11).trim().equalsIgnoreCase("Paychex HR Solutions - Administrative Service"))
							{
								output.getRecord(i).setValue(5, "Branch Operations");
								output.getRecord(i).setValue(6, "Running ASO");
								output.getRecord(i).setValue(7, "Update HRIS.net");
							}
							else
							{
								//if the last date they've run payroll is over 90 days of the month being auditted
								LocalDate thisDate;
								DateTimeFormatter dtf = DateTimeFormatter.ofPattern("M/d/yyyy");
								LocalDate currentDate = LocalDate.now();
								currentDate = currentDate.with(firstDayOfMonth());
								currentDate = currentDate.minusDays(1);
								LocalDate ninetyDays = currentDate.minusDays(90);
								if(output.getRecord(i).getValue(12) != null&!output.getRecord(i).getValue(12).equalsIgnoreCase("N/A")) {
									thisDate = LocalDate.parse(output.getRecord(i).getValue(12), dtf);}
								else
								{
									if(output.getRecord(i).getValue(12).equalsIgnoreCase("N/A"))
										output.getRecord(i).setValue(12,"1/1/1900");
									thisDate = LocalDate.parse("1/1/1900",dtf);
								}
								
								if(output.getRecord(i).getValue(11).trim().equalsIgnoreCase("Paychex Productivity")&
										ninetyDays.isAfter(thisDate)&!output.getRecord(i).getValue(12).equalsIgnoreCase("1/1/1900"))
								{
									output.getRecord(i).setValue(5, "Payroll Not Run >90 Days");
									output.getRecord(i).setValue(6, "Payroll Not Run >90 Days");
									output.getRecord(i).setValue(7, "Contact hub to see if running.");
								}
								else
								{
									//else if they're on the EDW already
									if((ninetyDays.isBefore(thisDate)|output.getRecord(i).getValue(12).equalsIgnoreCase("1/1/1900"))&output.getRecord(i).getValue(8).equals("Yes"))
									{
										output.getRecord(i).setValue(5, "N/A");
										output.getRecord(i).setValue(6, "Client is on EDW-PNG");
										output.getRecord(i).setValue(7, "None Needed");
									}
									else
									{
										//else if they havent run payroll, but its been less than 90 days
										if(ninetyDays.isBefore(thisDate))
										{
											output.getRecord(i).setValue(5, "Payroll Not Run <90 Days");
											output.getRecord(i).setValue(6, "Payroll Not Run <90 Days");
											output.getRecord(i).setValue(7, "None Needed");
										}
										else
										{
											//all other cases need to be researched
											output.getRecord(i).setValue(5, "NEEDS RESEARCH");
											output.getRecord(i).setValue(6, "NEEDS RESEARCH");
											output.getRecord(i).setValue(7, "NEEDS RESEARCH");
										}
									}
								}
							}

						}
					}
				}
			}
			
		}	
		
		//see if on cancel payroll report
		for(int i = 0; i<output.getNumberOfRecords();i++)
		{
			String bis = output.getRecord(i).getValue(0)+
					("00000000"+output.getRecord(i).getValue(2)).substring(output.getRecord(i).getValue(2).length());
			for(int j = 0; j<cancelPayroll.getNumberOfRecords();j++)//update to concat
			{
				String concattarget = cancelPayroll.getRecord(j).getValue(0)+
						("00000000"+cancelPayroll.getRecord(j).getValue(3)).substring(cancelPayroll.getRecord(j).getValue(3).length());
		
				if(bis.equals(concattarget))
				{		
					output.getRecord(i).setValue(5, "On Cancel Payroll Report");
					output.getRecord(i).setValue(6, "On Cancel Payroll Report");
					output.getRecord(i).setValue(7, "None Needed");
					break;
				}

			}

		}
	}
	
	/**
	 * TLOBPNG- returns no values and takes no parameters
	 *
	 * This is the method which is called after the user selects this as the audit they want to run, and all the reports have already been read by the constructor.
	 */
	public void TLOBPNG()
	{
		//create the output headers
		String[] headers = {"Bis ID","Branch","Client#","Company Name","Key Indicator","Comment","Action Needed",
				"HRIS EOM", "1152","PR Package","HRIS-Current","1152 Cancel","Notes"};
		output = new Report(14,headers);
				
		//copy Bis, Branch, clientnum, compName from EDW report

		int EDWtrack = 0;
		int outputtrack = 0;
		System.out.println(EDW.getNumberOfRecords());
		while(EDW.getNumberOfRecords()>EDWtrack)
		{
			if((EDW.getRecord(EDWtrack).getValue(7).trim().equalsIgnoreCase("Paychex Productivity")))
			{
				if(EDW.getRecord(EDWtrack).getValue(10).trim().equalsIgnoreCase("Time and Labor Online Transaction Fee"))
				{
					output.addBlankRecord();
					String bis = EDW.getRecord(EDWtrack).getValue(1);
					String branch = EDW.getRecord(EDWtrack).getValue(2);
					String clientnum = EDW.getRecord(EDWtrack).getValue(4);
					String compName = EDW.getRecord(EDWtrack).getValue(3);
					output.getRecord(outputtrack).setValue(0, bis);
					output.getRecord(outputtrack).setValue(1, branch);
					output.getRecord(outputtrack).setValue(2, clientnum);
					output.getRecord(outputtrack).setValue(3, compName);

					outputtrack++;
				}
			}
			EDWtrack++;
		}
		
		System.out.println("15%");
				
				
		//See if the BIS id is on the HRISEOM report
		for(int i = 0; i<output.getNumberOfRecords();i++)
		{
			boolean found = false;
			String bis = output.getRecord(i).getValue(0);
			for(int j = 0; j<EOMClientBaseTLO.getNumberOfRecords();j++)
			{
				//filter on productivity package and TLO online transaction fee
				if(!EOMClientBaseTLO.getRecord(j).getValue(3).trim().equalsIgnoreCase("1273"))
				{
					if(bis.equals(EOMClientBaseTLO.getRecord(j).getValue(0)))
					{
								//if a bis id is found, copy plan code
								output.getRecord(i).setValue(7, EOMClientBaseTLO.getRecord(j).getValue(3));
								found = true;
								break;
					}
				}
			}
					//if the bis id is not found, no plan code was found
					if(!found)
						output.getRecord(i).setValue(7, "N/A");
		}
		System.out.println("30%");
		
		//get 1152
		for(int i = 0; i<output.getNumberOfRecords();i++)
		{
			boolean found = false;
			String bis = output.getRecord(i).getValue(0);
			for(int j = 0; j<qryActive1152.getNumberOfRecords();j++)
			{
				if(bis.equals(qryActive1152.getRecord(j).getValue(0)))
				{
						//if a bis id is found, copy plan code
						output.getRecord(i).setValue(8, qryActive1152.getRecord(j).getValue(4));
						found = true;
						break;
				}
			}
					//if the bis id is not found, no plan code was found
					if(!found)
						output.getRecord(i).setValue(8, "N/A");
		}
		System.out.println("45%-Starting the PRPackage comparison... Please wait a few moments.");
		
		//PR Package
		for(int i = 0; i<output.getNumberOfRecords();i++)
		{
			boolean found = false;
			
			String concat = output.getRecord(i).getValue(0)+
					("00000000"+output.getRecord(i).getValue(2)).substring(output.getRecord(i).getValue(2).length());
			
			//see if the BIS id concatenated with the CLIENTNUM matches with an entry on the PRPackage Report
			for(int j = 0; j<PRPackage.getNumberOfRecords();j++)
			{
				String concattarget = PRPackage.getRecord(j).getValue(0)+
						("00000000"+PRPackage.getRecord(j).getValue(3)).substring(PRPackage.getRecord(j).getValue(3).length());
				
				//if its on the PR status report, write the Package on the output report
				if(concat.equals(concattarget))
				{

					output.getRecord(i).setValue(9, PRPackage.getRecord(j).getValue(4));
					found = true;
					break;
					
				}

			}
			if(!found) 
			{
				output.getRecord(i).setValue(9, "N/A");
			}
		}		
		
		System.out.println("60%");
		
		//See if the BIS id is on the HRISCurrent report
		for(int i = 0; i<output.getNumberOfRecords();i++)
		{
			boolean found = false;
			String bis = output.getRecord(i).getValue(0);
			for(int j = 0; j<currentClientTLO.getNumberOfRecords();j++)
			{
				//filter on productivity package and TLO online transaction fee
				if(!currentClientTLO.getRecord(j).getValue(3).trim().equalsIgnoreCase("1273"))
				{
					if(bis.equals(currentClientTLO.getRecord(j).getValue(0)))
					{
							//if a bis id is found, copy plan code
							output.getRecord(i).setValue(10, currentClientTLO.getRecord(j).getValue(3));
							found = true;
							break;
					}
				}
			}
					//if the bis id is not found, no plan code was found
					if(!found)
						output.getRecord(i).setValue(10, "N/A");
		}

		System.out.println("75%");
		
		//get 1152 cancel
		for(int i = 0; i<output.getNumberOfRecords();i++)
		{
			boolean found = false;
			String bis = output.getRecord(i).getValue(0);
			for(int j = 0; j<qryCancelTLO.getNumberOfRecords();j++)
			{
				if(bis.equals(qryCancelTLO.getRecord(j).getValue(0)))
				{
					if(qryCancelTLO.getRecord(j).getValue(3).toString().trim().equals("1152")) 
					{
						//if a bis id is found, copy plan code
						output.getRecord(i).setValue(11, qryCancelTLO.getRecord(j).getValue(5));
						found = true;
						break;
					}
				}
			}
					//if the bis id is not found, no plan code was found
					if(!found)
						output.getRecord(i).setValue(11, "N/A");
		}
		
		System.out.println("90%");
		
		//START FROM HERE
		
		for(int i = 0; i<output.getNumberOfRecords();i++)
		{
			//if this is a demo client ELSE
			if(Integer.parseInt(output.getRecord(i).getValue(1))>1900)
			{
				output.getRecord(i).setValue(4, "Demo Client");
				output.getRecord(i).setValue(5, "Demo Client");
				output.getRecord(i).setValue(6, "None Needed");
			}
			else
			{
				//Parse the product and 1152 codes from strings to ints
				int code;
				int code2;
				//If this should be running ASO else
				if(output.getRecord(i).getValue(10).trim().equalsIgnoreCase("N/A"))
				{
					code =9999;
				}
				else
				{
					code = Integer.parseInt(output.getRecord(i).getValue(10).trim());
				}
				
				if(output.getRecord(i).getValue(8).trim().equalsIgnoreCase("N/A"))
				{
					code2 =9999;
				}
				else
				{
					code2 = Integer.parseInt(output.getRecord(i).getValue(8).trim());
				}
				if(output.getRecord(i).getValue(9).trim().equalsIgnoreCase("Paychex HR Solutions - Administrative Service")&
						code!=1206&code!=9999)
				{
					output.getRecord(i).setValue(4, "Branch Operations");
					output.getRecord(i).setValue(5, "Running ASO");
					output.getRecord(i).setValue(6, "Update HRIS.net");
				}
				else
				{
					//If this should be running Prod else
					if(output.getRecord(i).getValue(9).trim().equalsIgnoreCase("Paychex Productivity")&
							code!=1205&code!=9999)
					{
						output.getRecord(i).setValue(4, "Branch Operations");
						output.getRecord(i).setValue(5, "Running Productivity");
						output.getRecord(i).setValue(6, "Update HRIS.net");
					}
					else
					{
						//else if it has the right coding already else//
						if(code2==1152&code==1205)
						{
							output.getRecord(i).setValue(4, "N/A");
							output.getRecord(i).setValue(5, "Client has 1205/1152");
							output.getRecord(i).setValue(6, "None Needed");
						}
						else
						{
							//if the last date they've been cancelled is over 90 days of the month being auditted
							LocalDate thisDate;
							DateTimeFormatter dtf = DateTimeFormatter.ofPattern("M/d/yyyy");
							LocalDate currentDate = LocalDate.now();
							currentDate = currentDate.with(firstDayOfMonth());
							currentDate = currentDate.minusDays(1);
							LocalDate ninetyDays = currentDate.minusDays(90);
							if(!output.getRecord(i).getValue(11).equalsIgnoreCase("N/A"))
							{
								thisDate = LocalDate.parse(output.getRecord(i).getValue(11), dtf);//
							}
							else
							{
								thisDate = LocalDate.parse("1/1/1900",dtf);
							}
							//if it is, its a timing issue. ELSE//
							if(!output.getRecord(i).getValue(11).equalsIgnoreCase("N/A")
									&ninetyDays.isBefore(thisDate))
							{
								output.getRecord(i).setValue(4, "Timing Issue");
								output.getRecord(i).setValue(5, "Cancelled in "+thisDate.getMonth());
								output.getRecord(i).setValue(6, "None Needed");
							}
							else
							{
								if(output.getRecord(i).getValue(9).trim().equalsIgnoreCase("Paychex HR Solutions - Administrative Service"))
								{
									output.getRecord(i).setValue(4, "Error");
									output.getRecord(i).setValue(5, "Should not be on EDW");
									output.getRecord(i).setValue(6, "None Needed");
								}
								else
								{
									output.getRecord(i).setValue(4, "RESEARCH");
									output.getRecord(i).setValue(5, "RESEARCH");
									output.getRecord(i).setValue(6, "RESEARCH");
								}
							}
						}
					}
				}
			}
		}
		
	}
	
	/**
	 * TLOAASO- returns no values and takes no parameters
	 * NEEDS TESTING
	 * This is the method which is called after the user selects this as the audit they want to run, and all the reports have already been read by the constructor.
	 */
	public void TLOAASO()
	{
		//create the output headers
		String[] headers = {"Bis ID","Branch","Client#","Plan Code","Company Name","Key Indicator","Comment","Action Needed",
				"EDW?", "TLO Cancel","PR Status","PR Package","Last PR Run Date", "Notes"};
		output = new Report(14,headers);
		
		//copy Bis, Branch, clientnum, plancode, compName, and cancel date from HRIS report

		int track = 0;
		for(int i = 0;i<EOMClientBaseTLO.getNumberOfRecords();i++)
		{
			if(EOMClientBaseTLO.getRecord(i).getValue(3).trim().equalsIgnoreCase("1206")) {
				output.addBlankRecord();
				String bis = EOMClientBaseTLO.getRecord(i).getValue(0);
				String branch = EOMClientBaseTLO.getRecord(i).getValue(1);
				String clientnum = EOMClientBaseTLO.getRecord(i).getValue(2);
				String plancode = EOMClientBaseTLO.getRecord(i).getValue(3);
				String compName = EOMClientBaseTLO.getRecord(i).getValue(4);
				String cancelDate = EOMClientBaseTLO.getRecord(i).getValue(5);//grab cancel date.
				
				output.getRecord(track).setValue(0, bis);
				output.getRecord(track).setValue(1, branch);
				output.getRecord(track).setValue(2, clientnum);
				output.getRecord(track).setValue(3, plancode);
				output.getRecord(track).setValue(4, compName);
				output.getRecord(track).setValue(9, cancelDate);
				track++;
				}
			System.out.println(EOMClientBaseTLO.getNumberOfRecords()-i);
		}
		System.out.println("10%");
		
		//See if the BIS id is on the EDW report
		for(int i = 0; i<output.getNumberOfRecords();i++)
		{
			boolean found = false;
			String bis = output.getRecord(i).getValue(0);
			for(int j = 0; j<EDW.getNumberOfRecords();j++)
			{
				//filter on productivity package and TLO online transaction fee
				if((EDW.getRecord(j).getValue(7).trim().equalsIgnoreCase("Paychex HR Solutions - Administrative Service")))
				{
					if(EDW.getRecord(j).getValue(10).trim().equalsIgnoreCase("Time and Labor Online Transaction Fee"))
					{
						if(bis.equals(EDW.getRecord(j).getValue(1)))
						{
						//if a bis id is found, its on the EDW
						output.getRecord(i).setValue(8, "Yes");
						found = true;
						break;
						}
					}
				}
			}
			//if the bis id is not found, its not on the EDW
			if(!found)
				output.getRecord(i).setValue(8, "No");
			System.out.println(output.getNumberOfRecords()-i);
		}
		System.out.println("25%");
		
		
		//see if the BIS id concatenated with the CLIENTNUM matches with an entry on the PRStatus Report
		for(int i = 0; i<output.getNumberOfRecords();i++)
		{
			boolean found = false;
			
			String concat = output.getRecord(i).getValue(0)+
					("00000000"+output.getRecord(i).getValue(2)).substring(output.getRecord(i).getValue(2).length());
			
			for(int j = 0; j<PRStatus.getNumberOfRecords();j++)//update to concat
			{
				String concattarget = PRStatus.getRecord(j).getValue(0)+
						("00000000"+PRStatus.getRecord(j).getValue(3)).substring(PRStatus.getRecord(j).getValue(3).length());
				
				//if its on the PR status report, write the PR status on the output report
				if(concat.equals(concattarget))
				{
							
					output.getRecord(i).setValue(10, PRStatus.getRecord(j).getValue(5));
					found = true;
					break;
				}

			}
			//if its not on the PR Status report, write 'N/A'
			if(!found)
				output.getRecord(i).setValue(10, "N/A");
			System.out.println(output.getNumberOfRecords()-i);
		}		
		System.out.println("50%");
		
		
		//see if its on PR Package report
		for(int i = 0; i<output.getNumberOfRecords();i++)
		{
			boolean found = false;
			
			String concat = output.getRecord(i).getValue(0)+
					("00000000"+output.getRecord(i).getValue(2)).substring(output.getRecord(i).getValue(2).length());
			
			//see if the BIS id concatenated with the CLIENTNUM matches with an entry on the PRPackage Report
			for(int j = 0; j<PRPackage.getNumberOfRecords();j++)
			{
				String concattarget = PRPackage.getRecord(j).getValue(0)+
						("00000000"+PRPackage.getRecord(j).getValue(3)).substring(PRPackage.getRecord(j).getValue(3).length());
				
				//if its on the PR status report, write the Package on the output report
				if(concat.equals(concattarget))
				{
					//First, check if the value is null. This sometimes happens if the last two entries are blank.
					//In this case, update the date to 1/1/1900 which is the null value for a date.
					if(PRPackage.getRecord(j).getValue(5) == null)	
					{
						output.getRecord(i).setValue(11, PRPackage.getRecord(j).getValue(4));
						output.getRecord(i).setValue(12, "1/1/1900");
						found = true;
						break;
					}
					else
					{
						output.getRecord(i).setValue(11, PRPackage.getRecord(j).getValue(4));
						output.getRecord(i).setValue(12, PRPackage.getRecord(j).getValue(5));
						found = true;
						break;
					}
				}

			}
			if(!found) 
			{
				output.getRecord(i).setValue(11, "N/A");
				output.getRecord(i).setValue(12, "1/1/1900");
			}
			System.out.println(output.getNumberOfRecords()-i);
		}

		System.out.println("Comparing");
		//cross reference columns, and write the appropriate action
		for(int i = 0; i<output.getNumberOfRecords();i++)
		{
			//if this is a demo client ELSE
			if(Integer.parseInt(output.getRecord(i).getValue(1).toString())>1900)
			{
				output.getRecord(i).setValue(5, "Demo Client");
				output.getRecord(i).setValue(6, "Demo Client");
				output.getRecord(i).setValue(7, "None Needed");
			}
			else
			{
				//if this client was already cancelled ELSE
				if(output.getRecord(i).getValue(9).length()>1)
				{
					output.getRecord(i).setValue(5, "Timing Issue");
					output.getRecord(i).setValue(6, "TLO Cancelled");
					output.getRecord(i).setValue(7, "None Needed");
				}
				else
				{
					//if they've cancelled payroll ELSE
					if(output.getRecord(i).getValue(10).equals("Terminated/Lost")|
							output.getRecord(i).getValue(10).equals("Inactive"))
					{
						output.getRecord(i).setValue(5, "Cancelled Payroll");
						output.getRecord(i).setValue(6, "Cancelled Payroll");
						output.getRecord(i).setValue(7, "Follow Proposed to cancel process");
					}
					else
					{
						//They're running the standalone payroll product ELSE
						if(!(output.getRecord(i).getValue(11).trim().equalsIgnoreCase("Paychex Productivity"))&
								!(output.getRecord(i).getValue(11).trim().equalsIgnoreCase("Paychex HR Solutions - Administrative Service"))&
								!(output.getRecord(i).getValue(11).trim().equalsIgnoreCase("N/A")))
						{
							output.getRecord(i).setValue(5, "Branch Operations");
							output.getRecord(i).setValue(6, "Running "+output.getRecord(i).getValue(11));
							output.getRecord(i).setValue(7, "Create case for AS to get updated order form.");
						}
						else
						{
							//They're running the wrong bundle ELSE
							if(output.getRecord(i).getValue(11).trim().equalsIgnoreCase("Paychex Productivity"))
							{
								output.getRecord(i).setValue(5, "Branch Operations");
								output.getRecord(i).setValue(6, "Running PNG");
								output.getRecord(i).setValue(7, "Update HRIS.net");
							}
							else
							{
								//if the last date they've run payroll is over 90 days of the month being auditted
								LocalDate thisDate;
								DateTimeFormatter dtf = DateTimeFormatter.ofPattern("M/d/yyyy");
								LocalDate currentDate = LocalDate.now();
								currentDate = currentDate.with(firstDayOfMonth());
								currentDate = currentDate.minusDays(1);
								LocalDate ninetyDays = currentDate.minusDays(90);
								if(output.getRecord(i).getValue(12) != null&!output.getRecord(i).getValue(12).equalsIgnoreCase("N/A")) {
									thisDate = LocalDate.parse(output.getRecord(i).getValue(12), dtf);}
								else
								{
									if(output.getRecord(i).getValue(12).equalsIgnoreCase("N/A"))
										output.getRecord(i).setValue(12,"1/1/1900");
									thisDate = LocalDate.parse("1/1/1900",dtf);
								}
								
								if(output.getRecord(i).getValue(11).trim().equalsIgnoreCase("Paychex HR Solutions - Administrative Service")&
										ninetyDays.isAfter(thisDate)&!output.getRecord(i).getValue(12).equalsIgnoreCase("1/1/1900"))
								{
									output.getRecord(i).setValue(5, "Payroll Not Run >90 Days");
									output.getRecord(i).setValue(6, "Payroll Not Run >90 Days");
									output.getRecord(i).setValue(7, "Contact hub to see if running.");
								}
								else
								{
									//else if they're on the EDW already
									if(ninetyDays.isBefore(thisDate)&output.getRecord(i).getValue(8).equals("Yes"))
									{
										output.getRecord(i).setValue(5, "N/A");
										output.getRecord(i).setValue(6, "Client is on EDW-ASO");
										output.getRecord(i).setValue(7, "None Needed");
									}
									else
									{
										//else if they havent run payroll, but its been less than 90 days
										if(ninetyDays.isBefore(thisDate))
										{
											output.getRecord(i).setValue(5, "Payroll Not Run <90 Days");
											output.getRecord(i).setValue(6, "Payroll Not Run <90 Days");
											output.getRecord(i).setValue(7, "None Needed");
										}
										else
										{
											//all other cases need to be researched
											output.getRecord(i).setValue(5, "NEEDS RESEARCH");
											output.getRecord(i).setValue(6, "NEEDS RESEARCH");
											output.getRecord(i).setValue(7, "NEEDS RESEARCH");
										}
									}
								}
							}
						}
					}
				}
			}		
		}	
		
		//see if on cancel payroll report
		for(int i = 0; i<output.getNumberOfRecords();i++)
		{
			String bis = output.getRecord(i).getValue(0)+
					("00000000"+output.getRecord(i).getValue(2)).substring(output.getRecord(i).getValue(2).length());
			
			for(int j = 0; j<cancelPayroll.getNumberOfRecords();j++)//update to concat
			{
				String concattarget = cancelPayroll.getRecord(j).getValue(0)+
						("00000000"+cancelPayroll.getRecord(j).getValue(3)).substring(cancelPayroll.getRecord(j).getValue(3).length());
				if(bis.equals(concattarget))
				{		
					output.getRecord(i).setValue(5, "On Cancel Payroll Report");
					output.getRecord(i).setValue(6, "On Cancel Payroll Report");
					output.getRecord(i).setValue(7, "None Needed");
					break;
				}

			}

		}		
	}
		
	/**
	 * TLOBASO- returns no values and takes no parameters
	 * NEEDS TESTING
	 * This is the method which is called after the user selects this as the audit they want to run, and all the reports have already been read by the constructor.
	 */
	public void TLOBASO()
	{
		//create the output headers
		String[] headers = {"Bis ID","Branch","Client#","Company Name","Key Indicator","Comment","Action Needed",
				"HRIS EOM", "1152","PR Package","HRIS-Current","1152 Cancel","Notes"};
		output = new Report(14,headers);
				
		//copy Bis, Branch, clientnum, compName from HRIS report

		int EDWtrack = 0;
		int outputtrack = 0;
		while(EDW.getNumberOfRecords()>EDWtrack)
		{
			if((EDW.getRecord(EDWtrack).getValue(7).trim().equalsIgnoreCase("Paychex HR Solutions - Administrative Service")))
			{
				if(EDW.getRecord(EDWtrack).getValue(10).trim().equalsIgnoreCase("Time and Labor Online Transaction Fee"))
				{
					output.addBlankRecord();
					String bis = EDW.getRecord(EDWtrack).getValue(1);
					String branch = EDW.getRecord(EDWtrack).getValue(2);
					String clientnum = EDW.getRecord(EDWtrack).getValue(4);
					String compName = EDW.getRecord(EDWtrack).getValue(3);
					output.getRecord(outputtrack).setValue(0, bis);
					output.getRecord(outputtrack).setValue(1, branch);
					output.getRecord(outputtrack).setValue(2, clientnum);
					output.getRecord(outputtrack).setValue(3, compName);
					outputtrack++;
				}
			}
			System.out.println(EDW.getNumberOfRecords()-EDWtrack);
			EDWtrack++;
		}
		
		System.out.println("15%");
				
				
		//See if the BIS id is on the HRISEOM report
		for(int i = 0; i<output.getNumberOfRecords();i++)
		{
			System.out.println(output.getNumberOfRecords()-i);
			boolean found = false;
			String bis = output.getRecord(i).getValue(0);
			for(int j = 0; j<EOMClientBaseTLO.getNumberOfRecords();j++)
			{
				//filter on productivity package and TLO online transaction fee
				if(!EOMClientBaseTLO.getRecord(j).getValue(3).trim().equalsIgnoreCase("1273"))
				{
					if(bis.equals(EOMClientBaseTLO.getRecord(j).getValue(0)))
					{
								//if a bis id is found, copy plan code
								output.getRecord(i).setValue(7, EOMClientBaseTLO.getRecord(j).getValue(3));
								found = true;
								break;
					}
				}
			}
					//if the bis id is not found, no plan code was found
					if(!found)
						output.getRecord(i).setValue(7, "N/A");
		}
		System.out.println("30%");
		
		//get 1152
		for(int i = 0; i<output.getNumberOfRecords();i++)
		{
			boolean found = false;
			String bis = output.getRecord(i).getValue(0);
			for(int j = 0; j<qryActive1152.getNumberOfRecords();j++)
			{
				if(bis.equals(qryActive1152.getRecord(j).getValue(0)))
				{
						//if a bis id is found, copy plan code
						output.getRecord(i).setValue(8, qryActive1152.getRecord(j).getValue(4));
						found = true;
						break;
				}
			}
					//if the bis id is not found, no plan code was found
					if(!found)
						output.getRecord(i).setValue(8, "N/A");
		}
		System.out.println("45%-Starting the PRPackage comparison... Please wait a few moments.");
		
		//PR Package
		for(int i = 0; i<output.getNumberOfRecords();i++)
		{
			boolean found = false;
			
			String concat = output.getRecord(i).getValue(0)+
					("00000000"+output.getRecord(i).getValue(2)).substring(output.getRecord(i).getValue(2).length());
			
			//see if the BIS id concatenated with the CLIENTNUM matches with an entry on the PRPackage Report
			for(int j = 0; j<PRPackage.getNumberOfRecords();j++)
			{
				String concattarget = PRPackage.getRecord(j).getValue(0)+
						("00000000"+PRPackage.getRecord(j).getValue(3)).substring(PRPackage.getRecord(j).getValue(3).length());
				
				//if its on the PR status report, write the Package on the output report
				if(concat.equals(concattarget))
				{

					output.getRecord(i).setValue(9, PRPackage.getRecord(j).getValue(4));
					found = true;
					break;
					
				}

			}
			if(!found) 
			{
				output.getRecord(i).setValue(9, "N/A");
			}
		}		
		
		System.out.println("60%");
		
		//See if the BIS id is on the HRISCurrent report
		for(int i = 0; i<output.getNumberOfRecords();i++)
		{
			boolean found = false;
			String bis = output.getRecord(i).getValue(0);
			for(int j = 0; j<currentClientTLO.getNumberOfRecords();j++)
			{
				//filter on productivity package and TLO online transaction fee
				if(!currentClientTLO.getRecord(j).getValue(3).trim().equalsIgnoreCase("1273"))
				{
					if(bis.equals(currentClientTLO.getRecord(j).getValue(0)))
					{
							//if a bis id is found, copy plan code
							output.getRecord(i).setValue(10, currentClientTLO.getRecord(j).getValue(3));
							found = true;
							break;
					}
				}
			}
					//if the bis id is not found, no plan code was found
					if(!found)
						output.getRecord(i).setValue(10, "N/A");
		}

		System.out.println("75%");
		
		//get 1152 cancel
		for(int i = 0; i<output.getNumberOfRecords();i++)
		{
			boolean found = false;
			String bis = output.getRecord(i).getValue(0);
			for(int j = 0; j<qryCancelTLO.getNumberOfRecords();j++)
			{
				if(bis.equals(qryCancelTLO.getRecord(j).getValue(0)))
				{
					if(qryCancelTLO.getRecord(j).getValue(3).toString().trim().equals("1152")) 
					{
						//if a bis id is found, copy plan code
						output.getRecord(i).setValue(11, qryCancelTLO.getRecord(j).getValue(5));
						found = true;
						break;
					}
				}
			}
					//if the bis id is not found, no plan code was found
					if(!found)
						output.getRecord(i).setValue(11, "N/A");
		}
		
		System.out.println("90%");
		
		
		for(int i = 0; i<output.getNumberOfRecords();i++)
		{
			//if this is a demo client ELSE
			if(Integer.parseInt(output.getRecord(i).getValue(1))>1900)
			{
				output.getRecord(i).setValue(4, "Demo Client");
				output.getRecord(i).setValue(5, "Demo Client");
				output.getRecord(i).setValue(6, "None Needed");
			}
			else
			{
				//Parse the product and 1152 codes from strings to ints
				int code;
				int code2;
				//If this should be running ASO else
				if(output.getRecord(i).getValue(10).trim().equalsIgnoreCase("N/A"))
				{
					code =9999;
				}
				else
				{
					code = Integer.parseInt(output.getRecord(i).getValue(10).trim());
				}
				
				if(output.getRecord(i).getValue(8).trim().equalsIgnoreCase("N/A"))
				{
					code2 =9999;
				}
				else
				{
					code2 = Integer.parseInt(output.getRecord(i).getValue(8).trim());
				}
				
				if(output.getRecord(i).getValue(9).trim().equalsIgnoreCase("Paychex HR Solutions - Administrative Service")&
						code!=1206&code!=9999)
				{
					output.getRecord(i).setValue(4, "Branch Operations");
					output.getRecord(i).setValue(5, "Running ASO");
					output.getRecord(i).setValue(6, "Update HRIS.net");
				}
				else
				{
					//If this should be running Prod else
					if(output.getRecord(i).getValue(9).trim().equalsIgnoreCase("Paychex Productivity")&
							code==1206)
					{
						output.getRecord(i).setValue(4, "Branch Operations");
						output.getRecord(i).setValue(5, "Running Productivity");
						output.getRecord(i).setValue(6, "Update HRIS.net");
					}
					else
					{
						//else if it has the right coding already else//
						if(code2==1152&code==1206)
						{
							output.getRecord(i).setValue(4, "N/A");
							output.getRecord(i).setValue(5, "Client has 1206/1152");
							output.getRecord(i).setValue(6, "None Needed");
						}
						else
						{
							//if the last date they've been cancelled is over 90 days of the month being auditted
							LocalDate thisDate;
							DateTimeFormatter dtf = DateTimeFormatter.ofPattern("M/d/yyyy");
							LocalDate currentDate = LocalDate.now();
							currentDate = currentDate.with(firstDayOfMonth());
							currentDate = currentDate.minusDays(1);
							LocalDate ninetyDays = currentDate.minusDays(90);
							if(!output.getRecord(i).getValue(11).equalsIgnoreCase("N/A"))
							{
								thisDate = LocalDate.parse(output.getRecord(i).getValue(11), dtf);//
							}
							else
							{
								thisDate = LocalDate.parse("1/1/1900",dtf);
							}
							//if it is, its a timing issue. ELSE//
							if(!output.getRecord(i).getValue(11).equalsIgnoreCase("N/A")
									&ninetyDays.isBefore(thisDate))
							{
								output.getRecord(i).setValue(4, "Timing Issue");
								output.getRecord(i).setValue(5, "Cancelled in "+thisDate.getMonth());
								output.getRecord(i).setValue(6, "None Needed");
							}
							else
							{
								if(code2==1152&code==1205)
								{
									output.getRecord(i).setValue(4, "N/A");
									output.getRecord(i).setValue(5, "Running Productivity");
									output.getRecord(i).setValue(6, "None Needed");
								}
								else
								{
									if(output.getRecord(i).getValue(9).trim().equalsIgnoreCase("Paychex Productivity"))
									{
										output.getRecord(i).setValue(4, "Error");
										output.getRecord(i).setValue(5, "Should not be on EDW");
										output.getRecord(i).setValue(6, "None Needed");
									}else 
									{
										output.getRecord(i).setValue(4, "RESEARCH");
										output.getRecord(i).setValue(5, "RESEARCH");
										output.getRecord(i).setValue(6, "RESEARCH");
									}
								}
							}
						}
					}
				}
			}
		}
				
	}
	
	/**
	 * TLOAPATH- returns no values and takes no parameters
	 * NEEDS TESTING
	 * This is the method which is called after the user selects this as the audit they want to run, and all the reports have already been read by the constructor.
	 */
	public void TLOAPATH()
	{
		//create the output headers
		String[] headers = {"Bis ID","Branch","Client#","Company Name","Key Indicator","Comment","Action Needed",
				"EDW?", "TLO Cancel","PR Status","PR Package","Last PR Run Date", "Notes"};
		output = new Report(14,headers);
		
		//copy Bis, Branch, clientnum, plancode, compName, and cancel date from HRIS report
		int track = 0;
		for(int i = 0;i<EOMClientBaseTLO.getNumberOfRecords();i++)
		{
			if(EOMClientBaseTLO.getRecord(i).getValue(3).trim().equalsIgnoreCase("1053")) {
				output.addBlankRecord();
				String bis = EOMClientBaseTLO.getRecord(i).getValue(0);
				String branch = EOMClientBaseTLO.getRecord(i).getValue(1);
				String clientnum = EOMClientBaseTLO.getRecord(i).getValue(2);
				String compName = EOMClientBaseTLO.getRecord(i).getValue(4);
				String cancelDate = EOMClientBaseTLO.getRecord(i).getValue(5);//grab cancel date.
				
				output.getRecord(track).setValue(0, bis);
				output.getRecord(track).setValue(1, branch);
				output.getRecord(track).setValue(2, clientnum);
				output.getRecord(track).setValue(3, compName);
				output.getRecord(track).setValue(8, cancelDate);
				track++;
			}
			System.out.println(EOMClientBaseTLO.getNumberOfRecords()-i);
		}
		System.out.println("10%");
		
		//see if the BIS id concatenated with the CLIENTNUM matches with an entry on the PRStatus Report
		for(int i = 0; i<output.getNumberOfRecords();i++)
		{
			boolean found = false;
			
			String concat = output.getRecord(i).getValue(0)+
					("00000000"+output.getRecord(i).getValue(2)).substring(output.getRecord(i).getValue(2).length());
			
			for(int j = 0; j<PRStatus.getNumberOfRecords();j++)//update to concat
			{
				String concattarget = PRStatus.getRecord(j).getValue(0)+
						("00000000"+PRStatus.getRecord(j).getValue(3)).substring(PRStatus.getRecord(j).getValue(3).length());
				
				//if its on the PR status report, write the PR status on the output report
				if(concat.equals(concattarget))
				{
							
					output.getRecord(i).setValue(9, PRStatus.getRecord(j).getValue(5));
					found = true;
					break;
				}

			}
			//if its not on the PR Status report, write 'N/A'
			if(!found)
				output.getRecord(i).setValue(9, "N/A");
			System.out.println(output.getNumberOfRecords()-i);
		}		
		System.out.println("50%");
		
		//See if the BIS id is on the EDW report
		for(int i = 0; i<output.getNumberOfRecords();i++)
		{
			boolean found = false;
			String bis = output.getRecord(i).getValue(0);
			for(int j = 0; j<EDW.getNumberOfRecords();j++)
			{
				//filter MMS
				if((EDW.getRecord(j).getValue(13).trim().equalsIgnoreCase("M")))
				{
					if(bis.equals(EDW.getRecord(j).getValue(1)))
					{
						//if a bis id is found, its on the EDW
						output.getRecord(i).setValue(7, "Yes");
						output.getRecord(i).setValue(9, EDW.getRecord(j).getValue(5));//pr status indicator
						found = true;
						break;
					}
				}
			}
			//if the bis id is not found, its not on the EDW
			if(!found)
				output.getRecord(i).setValue(7, "No");
			System.out.println(output.getNumberOfRecords()-i);
		}
		System.out.println("25%");
		
		//see if its on PR Package report
		for(int i = 0; i<output.getNumberOfRecords();i++)
		{
			boolean found = false;
			
			String concat = output.getRecord(i).getValue(0)+
					("00000000"+output.getRecord(i).getValue(2)).substring(output.getRecord(i).getValue(2).length());
			
			//see if the BIS id concatenated with the CLIENTNUM matches with an entry on the PRPackage Report
			for(int j = 0; j<PRPackage.getNumberOfRecords();j++)
			{
				String concattarget = PRPackage.getRecord(j).getValue(0)+
						("00000000"+PRPackage.getRecord(j).getValue(3)).substring(PRPackage.getRecord(j).getValue(3).length());
				
				//if its on the PR status report, write the Package on the output report
				if(concat.equals(concattarget))
				{
					//First, check if the value is null. This sometimes happens if the last two entries are blank.
					//In this case, update the date to 1/1/1900 which is the null value for a date.
					if(PRPackage.getRecord(j).getValue(5) == null)	
					{
						output.getRecord(i).setValue(10, PRPackage.getRecord(j).getValue(4));
						output.getRecord(i).setValue(11, "1/1/1900");
						found = true;
						break;
					}
					else
					{
						output.getRecord(i).setValue(10, PRPackage.getRecord(j).getValue(4));
						output.getRecord(i).setValue(11, PRPackage.getRecord(j).getValue(5));
						found = true;
						break;
					}
				}
				else
				{
					if(output.getRecord(i).getValue(7).trim().equals("Yes"))
					{
						if(output.getRecord(i).getValue(0).equals(PRPackage.getRecord(j).getValue(0)))
						{
							if(PRPackage.getRecord(j).getValue(5) == null)	
							{
								output.getRecord(i).setValue(10, PRPackage.getRecord(j).getValue(4));
								output.getRecord(i).setValue(11, "1/1/1900");
								found = true;
								break;
							}
							else
							{
								output.getRecord(i).setValue(10, PRPackage.getRecord(j).getValue(4));
								output.getRecord(i).setValue(11, PRPackage.getRecord(j).getValue(5));
								found = true;
								break;
							}
						}
					}
				}


			}
			if(!found) 
			{
				output.getRecord(i).setValue(10, "N/A");
				output.getRecord(i).setValue(11, "1/1/1900");
			}
			System.out.println(output.getNumberOfRecords()-i);
		}

		System.out.println("75%");
		//cross reference columns, and write the appropriate action
		for(int i = 0; i<output.getNumberOfRecords();i++)
		{
			//if this is a demo client ELSE
			if(Integer.parseInt(output.getRecord(i).getValue(1).toString())>1900)
			{
				output.getRecord(i).setValue(4, "Demo Client");
				output.getRecord(i).setValue(5, "Demo Client");
				output.getRecord(i).setValue(6, "None Needed");
			}
			else
			{
				//if this client was already cancelled ELSE
				if(output.getRecord(i).getValue(8).length()>1)
				{
					output.getRecord(i).setValue(4, "Timing Issue");
					output.getRecord(i).setValue(5, "TLO Cancelled");
					output.getRecord(i).setValue(6, "None Needed");
				}
				else
				{
					//if they've cancelled payroll ELSE
					if(output.getRecord(i).getValue(9).equals("Terminated/Lost")|
							output.getRecord(i).getValue(9).equals("Inactive"))
					{
						output.getRecord(i).setValue(4, "Cancelled Payroll");
						output.getRecord(i).setValue(5, "Cancelled Payroll");
						output.getRecord(i).setValue(6, "Follow Proposed to cancel process");
					}
					else
					{
						//They're running the standalone payroll product ELSE
						if(!(output.getRecord(i).getValue(10).trim().equalsIgnoreCase("Paychex Productivity"))&
								!(output.getRecord(i).getValue(10).trim().equalsIgnoreCase("Paychex HR Solutions - Administrative Service"))&
								!(output.getRecord(i).getValue(10).trim().equalsIgnoreCase("N/A"))&
								(output.getRecord(i).getValue(9).trim().equalsIgnoreCase("Active")))
						{
							output.getRecord(i).setValue(4, "Branch Operations");
							output.getRecord(i).setValue(5, "Running "+output.getRecord(i).getValue(10));
							output.getRecord(i).setValue(6, "Create case for AS to get updated order form.");
						}
						else
						{
							//They're running the wrong bundle ELSE
							if(output.getRecord(i).getValue(10).trim().equalsIgnoreCase("Paychex Productivity")&
									(output.getRecord(i).getValue(9).trim().equalsIgnoreCase("Active")))
							{
								output.getRecord(i).setValue(4, "Branch Operations");
								output.getRecord(i).setValue(5, "Running Productivity");
								output.getRecord(i).setValue(6, "Update HRIS.net");
							}
							else
							{
								//if the last date they've run payroll is over 90 days of the month being auditted
								LocalDate thisDate;
								DateTimeFormatter dtf = DateTimeFormatter.ofPattern("M/d/yyyy");
								LocalDate currentDate = LocalDate.now();
								currentDate = currentDate.with(firstDayOfMonth());
								currentDate = currentDate.minusDays(1);
								LocalDate ninetyDays = currentDate.minusDays(90);
								if(output.getRecord(i).getValue(11) != null&!output.getRecord(i).getValue(11).trim().equalsIgnoreCase("N/A")) {
									thisDate = LocalDate.parse(output.getRecord(i).getValue(11), dtf);}
								else
								{
									if(output.getRecord(i).getValue(11).equalsIgnoreCase("N/A"))
										output.getRecord(i).setValue(11,"1/1/1900");
									thisDate = LocalDate.parse("1/1/1900",dtf);
								}
								
								if(output.getRecord(i).getValue(10).trim().equalsIgnoreCase("Paychex HR Solutions – Administrative Service")&
										ninetyDays.isAfter(thisDate)&!output.getRecord(i).getValue(11).trim().equalsIgnoreCase("1/1/1900"))
								{
									output.getRecord(i).setValue(4, "Payroll Not Run >90 Days");
									output.getRecord(i).setValue(5, "Payroll Not Run >90 Days");
									output.getRecord(i).setValue(6, "Contact hub to see if running.");
								}
								else
								{
									System.out.print(output.getRecord(i).getValue(11)+" ");
									System.out.print(ninetyDays+" ");
									System.out.print(thisDate+" ");
									System.out.print(output.getRecord(i).getValue(7)+" \n");
									//else if they're on the EDW already
									if((ninetyDays.isBefore(thisDate)|output.getRecord(i).getValue(11).trim().equalsIgnoreCase("1/1/1900"))&output.getRecord(i).getValue(7).equals("Yes"))
									{
										output.getRecord(i).setValue(4, "N/A");
										output.getRecord(i).setValue(5, "Client is on EDW-PATH");
										output.getRecord(i).setValue(6, "None Needed");
									}
									else
									{
										//else if they havent run payroll, but its been less than 90 days
										if(ninetyDays.isBefore(thisDate))
										{
											output.getRecord(i).setValue(4, "Payroll Not Run <90 Days");
											output.getRecord(i).setValue(5, "Payroll Not Run <90 Days");
											output.getRecord(i).setValue(6, "None Needed");
										}
										else
										{
											//all other cases need to be researched
											output.getRecord(i).setValue(4, "NEEDS RESEARCH");
											output.getRecord(i).setValue(5, "NEEDS RESEARCH");
											output.getRecord(i).setValue(6, "NEEDS RESEARCH");
										}
									}
								}
							}
						}
					}
				}
			}	
		}	
		
		//see if on cancel payroll report
		for(int i = 0; i<output.getNumberOfRecords();i++)
		{
			String bis = output.getRecord(i).getValue(0);
			for(int j = 0; j<cancelPayroll.getNumberOfRecords();j++)//update to concat
			{
				if(bis.equals(cancelPayroll.getRecord(j).getValue(0)))
				{		
					output.getRecord(i).setValue(4, "On Cancel Payroll Report");
					output.getRecord(i).setValue(5, "On Cancel Payroll Report");
					output.getRecord(i).setValue(6, "None Needed");
					break;
				}

			}

		}		
	}

	/**
	 * TLOBPATH- returns no values and takes no parameters
	 * NEEDS TESTING
	 * This is the method which is called after the user selects this as the audit they want to run, and all the reports have already been read by the constructor.
	 */
	public void TLOBPATH()
	{
		String[] headers = {"Bis ID","Branch","Company Name","Client#","Key Indicator","Comment","Action Needed",
				"HRIS-EOM", "PR Package","1273","HRIS-Current", "Notes"};
		output = new Report(14,headers);
		
		//copy Bis, Branch, clientnum, compName from EDW report

		int EDWtrack = 0;
		int outputtrack = 0;
		System.out.println(EDW.getNumberOfRecords());
		while(EDW.getNumberOfRecords()>EDWtrack)
		{
			if(EDW.getRecord(EDWtrack).getValue(13).trim().equalsIgnoreCase("M")&(EDW.getRecord(EDWtrack).getValue(0).trim().equalsIgnoreCase("TLO")))
			{
					output.addBlankRecord();
					String bis = EDW.getRecord(EDWtrack).getValue(1);
					String branch = EDW.getRecord(EDWtrack).getValue(2);
					String clientnum = EDW.getRecord(EDWtrack).getValue(4);
					String compName = EDW.getRecord(EDWtrack).getValue(3);
					output.getRecord(outputtrack).setValue(0, bis);
					output.getRecord(outputtrack).setValue(1, branch);
					output.getRecord(outputtrack).setValue(3, clientnum);
					output.getRecord(outputtrack).setValue(2, compName);

					outputtrack++;
			}
			System.out.println(EDW.getNumberOfRecords()-EDWtrack);
			EDWtrack++;
		}
				
		//See if the BIS id is on the HRISEOM report
		for(int i = 0; i<output.getNumberOfRecords();i++)
		{
			System.out.println(output.getNumberOfRecords()-i);
			boolean found = false;
			String bis = output.getRecord(i).getValue(0);
			for(int j = 0; j<EOMClientBaseTLO.getNumberOfRecords();j++)
			{
				//filter on productivity package and TLO online transaction fee
				if(!EOMClientBaseTLO.getRecord(j).getValue(3).trim().equalsIgnoreCase("1273"))
				{
					if(bis.equals(EOMClientBaseTLO.getRecord(j).getValue(0)))
					{
								//if a bis id is found, copy plan code
								output.getRecord(i).setValue(7, EOMClientBaseTLO.getRecord(j).getValue(3));
								found = true;
								break;
					}
				}
			}
					//if the bis id is not found, no plan code was found
					if(!found)
						output.getRecord(i).setValue(7, "N/A");
		}
		System.out.println("30%");
		
		//PR Package
		for(int i = 0; i<output.getNumberOfRecords();i++)
		{
			System.out.println(output.getNumberOfRecords()-i);
			boolean found = false;
			
			String bis = output.getRecord(i).getValue(0);
			
			//see if the BIS id concatenated with the CLIENTNUM matches with an entry on the PRPackage Report
			for(int j = 0; j<PRPackage.getNumberOfRecords();j++)
			{
				String target = PRPackage.getRecord(j).getValue(0);
				
				//if its on the PR status report, write the Package on the output report
				if(bis.equals(target))
				{

					output.getRecord(i).setValue(8, PRPackage.getRecord(j).getValue(4));
					found = true;
					break;
					
				}

			}
			if(!found) 
			{
				output.getRecord(i).setValue(8, "N/A");
			}
		}
		
		//See if the BIS id is on the HRISEOM report
		for(int i = 0; i<output.getNumberOfRecords();i++)
		{
			System.out.println(output.getNumberOfRecords()-i);
			boolean found = false;
			String bis = output.getRecord(i).getValue(0);
			for(int j = 0; j<EOMClientBaseTLO.getNumberOfRecords();j++)
			{
				//filter on productivity package and TLO online transaction fee
				if(EOMClientBaseTLO.getRecord(j).getValue(3).trim().equalsIgnoreCase("1273"))
				{
					if(bis.equals(EOMClientBaseTLO.getRecord(j).getValue(0)))
					{
								//if a bis id is found, copy plan code
								output.getRecord(i).setValue(9, EOMClientBaseTLO.getRecord(j).getValue(3));
								found = true;
								break;
					}
				}
			}
					//if the bis id is not found, no plan code was found
					if(!found)
						output.getRecord(i).setValue(9, "N/A");
		}
		
		
		//See if the BIS id is on the HRISCurrent report
		for(int i = 0; i<output.getNumberOfRecords();i++)
		{
			System.out.println(output.getNumberOfRecords()-i);
			boolean found = false;
			String bis = output.getRecord(i).getValue(0);
			for(int j = 0; j<currentClientTLO.getNumberOfRecords();j++)
			{
				//filter on productivity package and TLO online transaction fee
				if(!currentClientTLO.getRecord(j).getValue(3).trim().equalsIgnoreCase("1273"))
				{
					if(bis.equals(currentClientTLO.getRecord(j).getValue(0)))
					{
						//if a bis id is found, copy plan code
						output.getRecord(i).setValue(10, currentClientTLO.getRecord(j).getValue(3));
						found = true;
						break;
					}
				}
			}
				//if the bis id is not found, no plan code was found
			if(!found)
				output.getRecord(i).setValue(10, "N/A");
		}
		
		//cross reference columns, and write the appropriate action
		for(int i = 0; i<output.getNumberOfRecords();i++)
		{
			//if this is a demo client ELSE
			int a = (output.getRecord(i).getValue(1).isEmpty()|output.getRecord(i).getValue(1)== null) ? 0 : Integer.parseInt(output.getRecord(i).getValue(1));
			if(a>1900)
			{
				output.getRecord(i).setValue(4, "Demo Client");
				output.getRecord(i).setValue(5, "Demo Client");
				output.getRecord(i).setValue(6, "None Needed");
			}
			else
			{
				int b = (output.getRecord(i).getValue(9).isEmpty()|output.getRecord(i).getValue(9)== null|output.getRecord(i).getValue(9).equals("N/A")) ? 0 : Integer.parseInt(output.getRecord(i).getValue(9));
				if(b==1273&output.getRecord(i).getValue(10).trim().equalsIgnoreCase("N/A"))
				{
					output.getRecord(i).setValue(4, "Child Company");
					output.getRecord(i).setValue(5, "Child Company");
					output.getRecord(i).setValue(6, "None Needed");
				}
				else
				{
					if(output.getRecord(i).getValue(8).trim().equalsIgnoreCase("Paychex Productivity")&output.getRecord(i).getValue(10).trim().equals("1205"))
					{
						output.getRecord(i).setValue(4, "Timing Issue");
						output.getRecord(i).setValue(5, "On 1205");
						output.getRecord(i).setValue(6, "None Needed");
					}
					else
					{
						if(output.getRecord(i).getValue(8).trim().equalsIgnoreCase("Paychex HR Solutions – Administrative Service")&output.getRecord(i).getValue(10).trim().equals("1206"))
						{
							output.getRecord(i).setValue(4, "Timing Issue");
							output.getRecord(i).setValue(5, "On 1206");
							output.getRecord(i).setValue(6, "None Needed");
						}
						else
						{
							
							if(((a<400|a>499)&a!=0)&output.getRecord(i).getValue(10).trim().equals("1053"))
							{
								output.getRecord(i).setValue(4, "Branch Operations");
								output.getRecord(i).setValue(5, "1053 Non-MMS");
								output.getRecord(i).setValue(6, "Update HRIS.net");
							}
							else
							{
								if(output.getRecord(i).getValue(10).trim().equals("1053")&output.getRecord(i).getValue(9).trim().equalsIgnoreCase("N/A"))
								{
									output.getRecord(i).setValue(4, "N/A");
									output.getRecord(i).setValue(5, "Billing 1053");
									output.getRecord(i).setValue(6, "None Needed");
								}
								else
								{
									//all other cases need to be researched
									output.getRecord(i).setValue(4, "NEEDS RESEARCH");
									output.getRecord(i).setValue(5, "NEEDS RESEARCH");
									output.getRecord(i).setValue(6, "NEEDS RESEARCH");
								}
							}
						}
					}
				}
			}
		}		
	}
	
	/**
	 * STMA- returns no values and takes no parameters
	 * NEEDS TESTING
	 * This is the method which is called after the user selects this as the audit they want to run, and all the reports have already been read by the constructor.
	 */
	public void STMA()
	{
		String[] headers = {"Bis ID","Branch","Client#","Product Code","Company Name","Key Indicator","Comment","Action Needed",
				"EDW?", "STM Cancel","PR Status","PR Package","Last PR Run Date", "Notes"};
		output = new Report(14,headers);
		

		int track = 0;
		for(int i = 0;i<EOMClientBaseSTM.getNumberOfRecords();i++)
		{
			if(EOMClientBaseSTM.getRecord(i).getValue(3).trim().equalsIgnoreCase("1264")|EOMClientBaseSTM.getRecord(i).getValue(3).trim().equalsIgnoreCase("1263")) {
				output.addBlankRecord();
				String bis = EOMClientBaseSTM.getRecord(i).getValue(0);
				String branch = EOMClientBaseSTM.getRecord(i).getValue(1);
				String clientnum = EOMClientBaseSTM.getRecord(i).getValue(2);
				String plancode = EOMClientBaseSTM.getRecord(i).getValue(3);
				String compName = EOMClientBaseSTM.getRecord(i).getValue(5);
				String cancelDate = EOMClientBaseSTM.getRecord(i).getValue(4);//grab cancel date.
				
				output.getRecord(track).setValue(0, bis);
				output.getRecord(track).setValue(1, branch);
				output.getRecord(track).setValue(2, clientnum);
				output.getRecord(track).setValue(3, plancode);
				output.getRecord(track).setValue(4, compName);
				output.getRecord(track).setValue(9, cancelDate);
				track++;
				System.out.println(EOMClientBaseSTM.getNumberOfRecords()-i);
			}
		}
		System.out.println("Copied HRIS to output file%");
		
		//See if the BIS id is on the EDW report
		for(int i = 0; i<output.getNumberOfRecords();i++)
		{
			boolean found = false;
			String bis = output.getRecord(i).getValue(0);
			int code = Integer.parseInt(output.getRecord(i).getValue(3).toString());
			for(int j = 0; j<EDW.getNumberOfRecords();j++)
			{
				//filter on productivity package and TLO online transaction fee
				if( (EDW.getRecord(j).getValue(7).trim().equalsIgnoreCase("Paychex Productivity") & code==1263) | (EDW.getRecord(j).getValue(7).trim().equalsIgnoreCase("Paychex Flex Enterprise") & code==1263) |
						(EDW.getRecord(j).getValue(7).trim().equalsIgnoreCase("Paychex HR Solutions - Administrative Service") & code==1264) )
				{
					if(EDW.getRecord(j).getValue(9).trim().equalsIgnoreCase("Flex Time")|EDW.getRecord(j).getValue(9).trim().equalsIgnoreCase("Stratustime"))
					{
						if(bis.equals(EDW.getRecord(j).getValue(1)))
						{
						//if a bis id is found, its on the EDW
						output.getRecord(i).setValue(8, "Yes");
						found = true;
						break;
						}
					}
				}
			}
			//if the bis id is not found, its not on the EDW
			if(!found)
				output.getRecord(i).setValue(8, "No");
			System.out.println(output.getNumberOfRecords()-i);
		}
		System.out.println("Found all on EDW");
		
		//see if the BIS id concatenated with the CLIENTNUM matches with an entry on the PRStatus Report
		for(int i = 0; i<output.getNumberOfRecords();i++)
		{
			boolean found = false;
			
			String concat = output.getRecord(i).getValue(0)+
					("00000000"+output.getRecord(i).getValue(2)).substring(output.getRecord(i).getValue(2).length());
			
			for(int j = 0; j<PRStatus.getNumberOfRecords();j++)
			{
				String concattarget = PRStatus.getRecord(j).getValue(0)+
						("00000000"+PRStatus.getRecord(j).getValue(3)).substring(PRStatus.getRecord(j).getValue(3).length());
				
				//if its on the PR status report, write the PR status on the output report
				if(concat.equals(concattarget))
				{
							
					output.getRecord(i).setValue(10, PRStatus.getRecord(j).getValue(5));
					found = true;
					break;
				}

			}
			//if its not on the PR Status report, write 'N/A'
			if(!found)
				output.getRecord(i).setValue(10, "N/A");
			System.out.println(output.getNumberOfRecords()-i);
		}		
		System.out.println("PR Status Report");
		
		//see if its on PR Package report
		for(int i = 0; i<output.getNumberOfRecords();i++)
		{
			boolean found = false;
			
			String concat = output.getRecord(i).getValue(0)+
					("00000000"+output.getRecord(i).getValue(2)).substring(output.getRecord(i).getValue(2).length());
			
			//see if the BIS id concatenated with the CLIENTNUM matches with an entry on the PRPackage Report
			for(int j = 0; j<PRPackage.getNumberOfRecords();j++)
			{
				String concattarget = PRPackage.getRecord(j).getValue(0)+
						("00000000"+PRPackage.getRecord(j).getValue(3)).substring(PRPackage.getRecord(j).getValue(3).length());
				
				//if its on the PR status report, write the Package on the output report
				if(concat.equals(concattarget))
				{
					//First, check if the value is null. This sometimes happens if the last two entries are blank.
					//In this case, update the date to 1/1/1900 which is the null value for a date.
					if(PRPackage.getRecord(j).getValue(5) == null)	
					{
						output.getRecord(i).setValue(11, PRPackage.getRecord(j).getValue(4));
						output.getRecord(i).setValue(12, "1/1/1900");
						found = true;
						break;
					}
					else
					{
						output.getRecord(i).setValue(11, PRPackage.getRecord(j).getValue(4));
						output.getRecord(i).setValue(12, PRPackage.getRecord(j).getValue(5));
						found = true;
						break;
					}
				}

			}
			if(!found) 
			{
				output.getRecord(i).setValue(11, "N/A");
				output.getRecord(i).setValue(12, "1/1/1900");
			}
			System.out.println(output.getNumberOfRecords()-i);
		}
		
		System.out.println("75%");
		//cross reference columns, and write the appropriate action
		for(int i = 0; i<output.getNumberOfRecords();i++)
		{
			int code = Integer.parseInt(output.getRecord(i).getValue(3).toString());
			//if this is a demo client ELSE
			if(Integer.parseInt(output.getRecord(i).getValue(1).toString())>1900)
			{
				output.getRecord(i).setValue(5, "Demo Client");
				output.getRecord(i).setValue(6, "Demo Client");
				output.getRecord(i).setValue(7, "None Needed");
			}
			else
			{
				//IF its on edw AND does NOT have a cancel date AND plan code is equal to 1263 with prod package OR 1264 with hr solutions package
				if( output.getRecord(i).getValue(8).trim().equalsIgnoreCase("Yes") & !(output.getRecord(i).getValue(9).length()>1) &
					(code==1263&output.getRecord(i).getValue(11).trim().equalsIgnoreCase("Paychex Productivity") |code==1263&output.getRecord(i).getValue(11).trim().equalsIgnoreCase("Paychex Flex Enterprise")| code==1264&output.getRecord(i).getValue(11).trim().equalsIgnoreCase("Paychex HR Solutions - Administrative Service")))
				{
					output.getRecord(i).setValue(5, "N/A");
					output.getRecord(i).setValue(6, "Client is on EDW");
					output.getRecord(i).setValue(7, "None Needed");
				}
				else
				{
					if(Integer.parseInt(output.getRecord(i).getValue(1).toString())>400 &Integer.parseInt(output.getRecord(i).getValue(1).toString())<500)
					{
						output.getRecord(i).setValue(5, "Reload");
						output.getRecord(i).setValue(6, "Transfer to MMS");
						output.getRecord(i).setValue(7, "Create case for AS to get updated order form");
					}
					else
					{
						if(output.getRecord(i).getValue(9).length()>1)
						{
							output.getRecord(i).setValue(5, "Timing Issue");
							output.getRecord(i).setValue(6, "STM Cancelled");
							output.getRecord(i).setValue(7, "None Needed");
						}
						else
						{
							//if they've cancelled payroll ELSE
							if(output.getRecord(i).getValue(10).equals("Terminated/Lost")|
									output.getRecord(i).getValue(10).equals("Inactive"))
							{
								output.getRecord(i).setValue(5, "Cancelled Payroll");
								output.getRecord(i).setValue(6, "Cancelled Payroll");
								output.getRecord(i).setValue(7, "Follow Proposed to cancel process");
							}
							else
							{
								//They're running the standalone payroll product ELSE
								if(!(output.getRecord(i).getValue(11).trim().equalsIgnoreCase("Paychex Productivity"))&
										!(output.getRecord(i).getValue(11).trim().equalsIgnoreCase("Paychex HR Solutions - Administrative Service"))&
										!(output.getRecord(i).getValue(11).trim().equalsIgnoreCase("Paychex Flex Enterprise"))&
										!(output.getRecord(i).getValue(11).trim().equalsIgnoreCase("N/A")))
								{
									output.getRecord(i).setValue(5, "Branch Operations");
									output.getRecord(i).setValue(6, "Running "+output.getRecord(i).getValue(11));
									output.getRecord(i).setValue(7, "Create case for AS to get updated order form.");
								}
								else
								{
									if(code==1263&output.getRecord(i).getValue(11).trim().equalsIgnoreCase("Paychex HR Solutions - Administrative Service"))
									{
										output.getRecord(i).setValue(5, "Branch Operations");
										output.getRecord(i).setValue(6, "Running ASO");
										output.getRecord(i).setValue(7, "Update HRIS.net");
									}
									else
									{
										if(code==1264&output.getRecord(i).getValue(11).trim().equalsIgnoreCase("Paychex Productivity"))
										{
											output.getRecord(i).setValue(5, "Branch Operations");
											output.getRecord(i).setValue(6, "Running Productivity");
											output.getRecord(i).setValue(7, "Update HRIS.net");
										}
										else
										{
											//if the last date they've run payroll is over 90 days of the month being auditted
											LocalDate thisDate;
											DateTimeFormatter dtf = DateTimeFormatter.ofPattern("M/d/yyyy");
											LocalDate currentDate = LocalDate.now();
											currentDate = currentDate.with(firstDayOfMonth());
											currentDate = currentDate.minusDays(1);
											LocalDate ninetyDays = currentDate.minusDays(90);
											if(output.getRecord(i).getValue(12) != null&!output.getRecord(i).getValue(12).equalsIgnoreCase("N/A")) {
												thisDate = LocalDate.parse(output.getRecord(i).getValue(12), dtf);}
											else
											{
												if(output.getRecord(i).getValue(12).equalsIgnoreCase("N/A"))
													output.getRecord(i).setValue(12,"1/1/1900");
												thisDate = LocalDate.parse("1/1/1900",dtf);
											}
											
											if( (output.getRecord(i).getValue(11).trim().equalsIgnoreCase("Paychex Productivity") | output.getRecord(i).getValue(11).trim().equalsIgnoreCase("Paychex Flex Enterprise") | output.getRecord(i).getValue(11).trim().equalsIgnoreCase("Paychex HR Solutions - Administrative Service") )&
													ninetyDays.isAfter(thisDate)&!output.getRecord(i).getValue(12).equalsIgnoreCase("1/1/1900"))//wondering if 1/1/1900 could break the timing issue case above
											{
												output.getRecord(i).setValue(5, "Payroll Not Run >90 Days");
												output.getRecord(i).setValue(6, "Payroll Not Run >90 Days");
												output.getRecord(i).setValue(7, "Contact hub to see if running.");
											}
											else
											{
												//else if they're on the EDW already
												if(ninetyDays.isBefore(thisDate)&output.getRecord(i).getValue(8).equals("Yes"))
												{
													output.getRecord(i).setValue(5, "N/A");
													output.getRecord(i).setValue(6, "Client is on EDW-PNG");
													output.getRecord(i).setValue(7, "None Needed");
												}
												else
												{
													//else if they havent run payroll, but its been less than 90 days
													if(ninetyDays.isBefore(thisDate))
													{
														output.getRecord(i).setValue(5, "Payroll Not Run <90 Days");
														output.getRecord(i).setValue(6, "Payroll Not Run <90 Days");
														output.getRecord(i).setValue(7, "None Needed");
													}
													else
													{
														//all other cases need to be researched
														output.getRecord(i).setValue(5, "NEEDS RESEARCH");
														output.getRecord(i).setValue(6, "NEEDS RESEARCH");
														output.getRecord(i).setValue(7, "NEEDS RESEARCH");
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}	
		
		//see if on cancel payroll report
		for(int i = 0; i<output.getNumberOfRecords();i++)
		{
			String bis = output.getRecord(i).getValue(0)+
					("00000000"+output.getRecord(i).getValue(2)).substring(output.getRecord(i).getValue(2).length());
			for(int j = 0; j<cancelPayroll.getNumberOfRecords();j++)//update to concat
			{
				String concattarget = cancelPayroll.getRecord(j).getValue(0)+
						("00000000"+cancelPayroll.getRecord(j).getValue(3)).substring(cancelPayroll.getRecord(j).getValue(3).length());
				
				if(bis.equals(concattarget))
					
				{		
					output.getRecord(i).setValue(5, "On Cancel Payroll Report");
					output.getRecord(i).setValue(6, "On Cancel Payroll Report");
					output.getRecord(i).setValue(7, "None Needed");
					break;
				}

			}

		}
	}
	
	/**
	 * STMB- returns no values and takes no parameters
	 * This is the method which is called after the user selects this as the audit they want to run, and all the reports have already been read by the constructor.
	 */
	public void STMB()
	{
		String[] headers = {"Bis ID","Branch","Client#","Company Name","PR Package","Key Indicator","Comment","Action Needed",
				"HRIS EOM", "1228/1230/1289/1290","1282","1257","Child 1274","1257 Cancels", "Notes"};
		
		output = new Report(14,headers);
		
		//copy Bis, Branch, clientnum, compName from EDW report

		int EDWtrack = 0;
		int outputtrack = 0;
		System.out.println(EDW.getNumberOfRecords());
		String previous = "@@@@@";
		while(EDW.getNumberOfRecords()>EDWtrack)
		{
			String bis = EDW.getRecord(EDWtrack).getValue(1);
			if(EDW.getRecord(EDWtrack).getValue(7).trim().equalsIgnoreCase("Paychex Productivity") | EDW.getRecord(EDWtrack).getValue(7).trim().equalsIgnoreCase("Paychex Flex Pro")|
					EDW.getRecord(EDWtrack).getValue(7).trim().equalsIgnoreCase("Paychex HR Solutions - Administrative Service")&!bis.equals(previous))
			{
				if(EDW.getRecord(EDWtrack).getValue(10).trim().equalsIgnoreCase("MMC Time & Attendance")|
						EDW.getRecord(EDWtrack).getValue(10).trim().equalsIgnoreCase("ASO Time & Attendance"))
				{
					output.addBlankRecord();
					previous = bis;
					String branch = EDW.getRecord(EDWtrack).getValue(2);
					String clientnum = EDW.getRecord(EDWtrack).getValue(4);
					String compName = EDW.getRecord(EDWtrack).getValue(3);
					output.getRecord(outputtrack).setValue(0, bis);
					output.getRecord(outputtrack).setValue(1, branch);
					output.getRecord(outputtrack).setValue(2, clientnum);
					output.getRecord(outputtrack).setValue(3, compName);

					outputtrack++;
				}
			}
			EDWtrack++;
			System.out.println(EDW.getNumberOfRecords()-EDWtrack);
		}	
		
		System.out.println("15%");
		
		//pull payroll package
		for(int i = 0; i<output.getNumberOfRecords();i++)
		{
			boolean found = false;
			
			String bis = output.getRecord(i).getValue(0);
			
			for(int j = 0; j<PRPackage.getNumberOfRecords();j++)
			{
				String bistarget = PRPackage.getRecord(j).getValue(0);
				
				//if its on the PR status report, write the Package on the output report
				if(bis.equals(bistarget))
				{
					output.getRecord(i).setValue(4, PRPackage.getRecord(j).getValue(4));
					found = true;
					break;
					
				}

			}
			if(!found) 
			{
				output.getRecord(i).setValue(4, "N/A");
			}
			System.out.println(output.getNumberOfRecords()-i);
		}	
		System.out.println("30%");
		
		//hris 1263 or 1264, 1274
		for(int i = 0; i<output.getNumberOfRecords();i++)
		{
			boolean found = false;
			boolean found1274 = false;
			String bis = output.getRecord(i).getValue(0);
			for(int j = 0; j<EOMClientBaseSTM.getNumberOfRecords();j++)
			{

				if(EOMClientBaseSTM.getRecord(j).getValue(3).trim().equalsIgnoreCase("1264")|EOMClientBaseSTM.getRecord(j).getValue(3).trim().equalsIgnoreCase("1316")|EOMClientBaseSTM.getRecord(j).getValue(3).trim().equalsIgnoreCase("1263"))
				{
					if(bis.equals(EOMClientBaseSTM.getRecord(j).getValue(0)))
					{
						//if a bis id is found, copy plan code
						output.getRecord(i).setValue(8, EOMClientBaseSTM.getRecord(j).getValue(3));
						found = true;
					}

				}
				else
				{
					if(EOMClientBaseSTM.getRecord(j).getValue(3).trim().equalsIgnoreCase("1274"))
					{
						if(bis.equals(EOMClientBaseSTM.getRecord(j).getValue(0)))
						{
							//if a bis id is found, copy plan code
							output.getRecord(i).setValue(12, EOMClientBaseSTM.getRecord(j).getValue(3));
							found1274 = true;
						}
					}
				}
			}
			//if the bis id is not found, no plan code was found
			if(!found)
				output.getRecord(i).setValue(8, "N/A");
			if(!found1274)
				output.getRecord(i).setValue(12, "N/A");
			System.out.println(output.getNumberOfRecords()-i);
		}
		System.out.println("40%");

		//look in the activeSTMCodes
		for(int i = 0; i<output.getNumberOfRecords();i++)
		{
			boolean found = false;
			String bis = output.getRecord(i).getValue(0);
			for(int j = 0; j<activeSTMCodes.getNumberOfRecords();j++)
			{
				if(bis.equals(activeSTMCodes.getRecord(j).getValue(0)))
				{
					//if a bis id is found, copy plan code
					output.getRecord(i).setValue(9, activeSTMCodes.getRecord(j).getValue(3));
					found = true;
					break;
				}
			}
			//if the bis id is not found, no plan code was found
			if(!found)
				output.getRecord(i).setValue(9, "N/A");
			System.out.println(output.getNumberOfRecords()-i);
		}
		
		System.out.println("50%");
		
		//look in the active integration code
		for(int i = 0; i<output.getNumberOfRecords();i++)
		{
			boolean found1282 = false;
			boolean found1257 = false;
			String bis = output.getRecord(i).getValue(0);
			for(int j = 0; j<qryActive1257.getNumberOfRecords();j++)
			{
				if(bis.equals(qryActive1257.getRecord(j).getValue(0)))
				{
					if(qryActive1257.getRecord(j).getValue(4).trim().equalsIgnoreCase("1257"))
					{
						output.getRecord(i).setValue(11, "1257");
						found1257= true;
					}
					else
					{
						if(qryActive1257.getRecord(j).getValue(4).trim().equalsIgnoreCase("1282"))
						{
							output.getRecord(i).setValue(10, "1282");
							found1282 = true;
						}
					}
					
					
				}
			}
			//if the bis id is not found, no plan code was found
			if(!found1282)
				output.getRecord(i).setValue(10, "N/A");
			if(!found1257)
				output.getRecord(i).setValue(11, "N/A");
			System.out.println(output.getNumberOfRecords()-i);
		}

		System.out.println("60%");
		
		//grab 1257 cancel
		for(int i = 0; i<output.getNumberOfRecords();i++)
		{
			boolean found = false;
			String bis = output.getRecord(i).getValue(0);
			for(int j = 0; j<qryCancelSTM.getNumberOfRecords();j++)
			{
				//filter on productivity package and TLO online transaction fee
				if(qryCancelSTM.getRecord(j).getValue(5).trim().equalsIgnoreCase("1257"))
				{
					if(bis.equals(qryCancelSTM.getRecord(j).getValue(0)))
					{
						//if a bis id is found, copy plan code
						output.getRecord(i).setValue(13, qryCancelSTM.getRecord(j).getValue(4));
						found = true;
						break;
					}
				}
			}
			//if the bis id is not found, no plan code was found
			if(!found)
				output.getRecord(i).setValue(13, "N/A");
			System.out.println(output.getNumberOfRecords()-i);
		}
		System.out.println("70%");
		
		
		
		for(int i = 0; i<output.getNumberOfRecords();i++)
		{
			//
			if((!output.getRecord(i).getValue(9).trim().equalsIgnoreCase("N/A")|
					output.getRecord(i).getValue(8).trim().equalsIgnoreCase("1263")|
					output.getRecord(i).getValue(8).trim().equalsIgnoreCase("1264")|
					output.getRecord(i).getValue(8).trim().equalsIgnoreCase("1316"))&
					output.getRecord(i).getValue(12).trim().equalsIgnoreCase("1274"))
			{
				output.getRecord(i).setValue(5, "Branch Operations");
				output.getRecord(i).setValue(6, "Standalone and Child Coding active");
				output.getRecord(i).setValue(7, "Research");
			}
			else {
				if(!output.getRecord(i).getValue(9).trim().equalsIgnoreCase("N/A")&output.getRecord(i).getValue(10).trim().equalsIgnoreCase("1282"))
				{
					output.getRecord(i).setValue(5, "Active HRIS Bill");
					output.getRecord(i).setValue(6, "Standalone Billing Active");
					output.getRecord(i).setValue(7, "None Needed");
				}
				else
				{
					if(!output.getRecord(i).getValue(9).trim().equalsIgnoreCase("N/A")&output.getRecord(i).getValue(11).trim().equalsIgnoreCase("1257"))
					{
						output.getRecord(i).setValue(5, "TAA Operations");
						output.getRecord(i).setValue(6, "(1228/1230/1289/1290)  & 1257 Active");
						output.getRecord(i).setValue(7, "Update HRIS.net");
					}
					else
					{
						LocalDate thisDate;
						DateTimeFormatter dtf = DateTimeFormatter.ofPattern("M/d/yyyy");
						LocalDate currentDate = LocalDate.now();
						//currentDate = currentDate.with(firstDayOfMonth());
						//currentDate = currentDate.minusDays(1);
						//LocalDate ninetyDays = currentDate.minusDays(90);
						if(output.getRecord(i).getValue(13) != null&!output.getRecord(i).getValue(13).equalsIgnoreCase("N/A")) {
							thisDate = LocalDate.parse(output.getRecord(i).getValue(13), dtf);}
						else
						{
							if(output.getRecord(i).getValue(13).equalsIgnoreCase("N/A"))
								output.getRecord(i).setValue(13,"1/1/1900");
							thisDate = LocalDate.parse("1/1/1900",dtf);
						}
						if(currentDate.isAfter(thisDate)&!output.getRecord(i).getValue(13).trim().equalsIgnoreCase("1/1/1900"))
						{
							output.getRecord(i).setValue(5, "Timing Issue");
							output.getRecord(i).setValue(6, "Cancelled in "+thisDate.getMonth());
							output.getRecord(i).setValue(7, "None Needed");
						}
						else
						{
							if((output.getRecord(i).getValue(4).trim().equalsIgnoreCase("Paychex Productivity")&output.getRecord(i).getValue(8).trim().equalsIgnoreCase("1263"))|
									(output.getRecord(i).getValue(4).trim().equalsIgnoreCase("Paychex Flex Enterprise")&output.getRecord(i).getValue(8).trim().equalsIgnoreCase("1263"))|
									(output.getRecord(i).getValue(4).trim().equalsIgnoreCase("Paychex HR Solutions - Administrative Service")&output.getRecord(i).getValue(8).trim().equalsIgnoreCase("1264"))|
									(output.getRecord(i).getValue(4).trim().equalsIgnoreCase("Paychex Flex Pro")&output.getRecord(i).getValue(8).trim().equalsIgnoreCase("1316")))
							{
								output.getRecord(i).setValue(5, "N/A");
								output.getRecord(i).setValue(6, "Client is active on STM");
								output.getRecord(i).setValue(7, "None Needed");
							}
							else
							{
								if(output.getRecord(i).getValue(4).trim().equalsIgnoreCase("Paychex Flex Pro")&((output.getRecord(i).getValue(8).trim().equalsIgnoreCase("1264")|output.getRecord(i).getValue(8).trim().equalsIgnoreCase("1263"))))
								{
									output.getRecord(i).setValue(5, "Branch Operations");
									output.getRecord(i).setValue(6, "Running Essentials");
									output.getRecord(i).setValue(7, "Update HRIS.net");
								}else {
									if(output.getRecord(i).getValue(4).trim().equalsIgnoreCase("Paychex Productivity")&((output.getRecord(i).getValue(8).trim().equalsIgnoreCase("1264")|output.getRecord(i).getValue(8).trim().equalsIgnoreCase("1316"))))
									{
										output.getRecord(i).setValue(5, "Branch Operations");
										output.getRecord(i).setValue(6, "Running Productivity");
										output.getRecord(i).setValue(7, "Update HRIS.net");
									}
									else
									{
										if(output.getRecord(i).getValue(4).trim().equalsIgnoreCase("Paychex HR Solutions - Administrative Service")&((output.getRecord(i).getValue(8).trim().equalsIgnoreCase("1263")|output.getRecord(i).getValue(8).trim().equalsIgnoreCase("1316"))))
										{
											output.getRecord(i).setValue(5, "Branch Operations");
											output.getRecord(i).setValue(6, "Running ASO");
											output.getRecord(i).setValue(7, "Update HRIS.net");
										}
										else
										{
											if(output.getRecord(i).getValue(10).trim().equalsIgnoreCase("1282")&output.getRecord(i).getValue(11).trim().equalsIgnoreCase("1257"))
											{
												output.getRecord(i).setValue(5, "TAA Operations");
												output.getRecord(i).setValue(6, "1257 & 1282 Active");
												output.getRecord(i).setValue(7, "Update HRIS.net");
											}
											else
											{
												if(output.getRecord(i).getValue(12).trim().equalsIgnoreCase("1274")&output.getRecord(i).getValue(11).trim().equalsIgnoreCase("1257"))
												{
													output.getRecord(i).setValue(5, "C2C Child ID");
													output.getRecord(i).setValue(6, "C2C Child ID");
													output.getRecord(i).setValue(7, "None Needed");
												}
												else
												{
													if(output.getRecord(i).getValue(12).trim().equalsIgnoreCase("1274"))
													{
														output.getRecord(i).setValue(5, "TAA Operations");
														output.getRecord(i).setValue(6, "Child ID without 1257");
														output.getRecord(i).setValue(7, "Research");
													}
													else
													{
														if((output.getRecord(i).getValue(8).trim().equalsIgnoreCase("1263")||output.getRecord(i).getValue(8).trim().equalsIgnoreCase("1316")|output.getRecord(i).getValue(8).trim().equalsIgnoreCase("1264"))&output.getRecord(i).getValue(10).trim().equalsIgnoreCase("1282"))
														{
															output.getRecord(i).setValue(5, "TAA Operations");
															output.getRecord(i).setValue(6, "Wrong integration code active");
															output.getRecord(i).setValue(7, "Update HRIS.net");
														}
														else
														{
															if((output.getRecord(i).getValue(8).trim().equalsIgnoreCase("1316")|output.getRecord(i).getValue(8).trim().equalsIgnoreCase("1263")|output.getRecord(i).getValue(8).trim().equalsIgnoreCase("1264"))&output.getRecord(i).getValue(11).trim().equalsIgnoreCase("1257"))
															{
																output.getRecord(i).setValue(5, "N/A");
																output.getRecord(i).setValue(6, "Client is on active STM");
																output.getRecord(i).setValue(7, "None Needed");
															}
															else
															{
																output.getRecord(i).setValue(5, "NEEDS RESEARCH");
																output.getRecord(i).setValue(6, "NEEDS RESEARCH");
																output.getRecord(i).setValue(7, "NEEDS RESEARCH");
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * ESSA- returns no values and takes no parameters
	 * This is the method which is called after the user selects this as the audit they want to run, and all the reports have already been read by the constructor.
	 */
	public void ESSA()
	{
		String[] headers = {"Bis ID","Branch","Client#","Product Code","Company Name","Key Indicator","Comment","Action Needed",
				"EDW?", "STM Cancel","PR Status","PR Package","Last PR Run Date", "Notes"};
		output = new Report(14,headers);
		

		int track = 0;
		for(int i = 0;i<EOMClientBaseSTM.getNumberOfRecords();i++)
		{
			if(EOMClientBaseSTM.getRecord(i).getValue(3).trim().equalsIgnoreCase("1316")) {
				output.addBlankRecord();
				String bis = EOMClientBaseSTM.getRecord(i).getValue(0);
				String branch = EOMClientBaseSTM.getRecord(i).getValue(1);
				String clientnum = EOMClientBaseSTM.getRecord(i).getValue(2);
				String plancode = EOMClientBaseSTM.getRecord(i).getValue(3);
				String compName = EOMClientBaseSTM.getRecord(i).getValue(5);
				String cancelDate = EOMClientBaseSTM.getRecord(i).getValue(4);//grab cancel date.
				
				output.getRecord(track).setValue(0, bis);
				output.getRecord(track).setValue(1, branch);
				output.getRecord(track).setValue(2, clientnum);
				output.getRecord(track).setValue(3, plancode);
				output.getRecord(track).setValue(4, compName);
				output.getRecord(track).setValue(9, cancelDate);
				track++;
				System.out.println(EOMClientBaseSTM.getNumberOfRecords()-i);
			}
		}
		System.out.println("Copied HRIS to output file%");
		
		//See if the BIS id is on the EDW report
		for(int i = 0; i<output.getNumberOfRecords();i++)
		{
			boolean found = false;
			String bis = output.getRecord(i).getValue(0);
			int code = Integer.parseInt(output.getRecord(i).getValue(3).toString());
			for(int j = 0; j<EDW.getNumberOfRecords();j++)
			{
				//filter on productivity package and TLO online transaction fee
				if( EDW.getRecord(j).getValue(7).trim().equalsIgnoreCase("Paychex Flex Pro") & code==1316 )
				{
					if(EDW.getRecord(j).getValue(9).trim().equalsIgnoreCase("Flex Time")|EDW.getRecord(j).getValue(9).trim().equalsIgnoreCase("Stratustime"))
					{
						if(bis.equals(EDW.getRecord(j).getValue(1)))
						{
						//if a bis id is found, its on the EDW
						output.getRecord(i).setValue(8, "Yes");
						found = true;
						break;
						}
					}
				}
			}
			//if the bis id is not found, its not on the EDW
			if(!found)
				output.getRecord(i).setValue(8, "No");
			System.out.println(output.getNumberOfRecords()-i);
		}
		System.out.println("Found all on EDW");
		
		//see if the BIS id concatenated with the CLIENTNUM matches with an entry on the PRStatus Report
		for(int i = 0; i<output.getNumberOfRecords();i++)
		{
			boolean found = false;
			
			String concat = output.getRecord(i).getValue(0)+
					("00000000"+output.getRecord(i).getValue(2)).substring(output.getRecord(i).getValue(2).length());
			
			for(int j = 0; j<PRStatus.getNumberOfRecords();j++)
			{
				String concattarget = PRStatus.getRecord(j).getValue(0)+
						("00000000"+PRStatus.getRecord(j).getValue(3)).substring(PRStatus.getRecord(j).getValue(3).length());
				
				//if its on the PR status report, write the PR status on the output report
				if(concat.equals(concattarget))
				{
							
					output.getRecord(i).setValue(10, PRStatus.getRecord(j).getValue(5));
					found = true;
					break;
				}

			}
			//if its not on the PR Status report, write 'N/A'
			if(!found)
				output.getRecord(i).setValue(10, "N/A");
			System.out.println(output.getNumberOfRecords()-i);
		}		
		System.out.println("PR Status Report");
		
		//see if its on PR Package report
		for(int i = 0; i<output.getNumberOfRecords();i++)
		{
			boolean found = false;
			
			String concat = output.getRecord(i).getValue(0)+
					("00000000"+output.getRecord(i).getValue(2)).substring(output.getRecord(i).getValue(2).length());
			
			//see if the BIS id concatenated with the CLIENTNUM matches with an entry on the PRPackage Report
			for(int j = 0; j<PRPackage.getNumberOfRecords();j++)
			{
				String concattarget = PRPackage.getRecord(j).getValue(0)+
						("00000000"+PRPackage.getRecord(j).getValue(3)).substring(PRPackage.getRecord(j).getValue(3).length());
				
				//if its on the PR status report, write the Package on the output report
				if(concat.equals(concattarget))
				{
					//First, check if the value is null. This sometimes happens if the last two entries are blank.
					//In this case, update the date to 1/1/1900 which is the null value for a date.
					if(PRPackage.getRecord(j).getValue(5) == null)	
					{
						output.getRecord(i).setValue(11, PRPackage.getRecord(j).getValue(4));
						output.getRecord(i).setValue(12, "1/1/1900");
						found = true;
						break;
					}
					else
					{
						output.getRecord(i).setValue(11, PRPackage.getRecord(j).getValue(4));
						output.getRecord(i).setValue(12, PRPackage.getRecord(j).getValue(5));
						found = true;
						break;
					}
				}

			}
			if(!found) 
			{
				output.getRecord(i).setValue(11, "N/A");
				output.getRecord(i).setValue(12, "1/1/1900");
			}
			System.out.println(output.getNumberOfRecords()-i);
		}
		
		System.out.println("75%");
		//cross reference columns, and write the appropriate action
		for(int i = 0; i<output.getNumberOfRecords();i++)
		{
			int code = Integer.parseInt(output.getRecord(i).getValue(3).toString());
			//if this is a demo client ELSE
			if(Integer.parseInt(output.getRecord(i).getValue(1).toString())>1900)
			{
				output.getRecord(i).setValue(5, "Demo Client");
				output.getRecord(i).setValue(6, "Demo Client");
				output.getRecord(i).setValue(7, "None Needed");
			}
			else
			{
				//IF its on edw AND does NOT have a cancel date AND plan code is equal to 1263 with prod package OR 1264 with hr solutions package
				if( output.getRecord(i).getValue(8).trim().equalsIgnoreCase("Yes") & !(output.getRecord(i).getValue(9).length()>1) &
					(code==1316&output.getRecord(i).getValue(11).trim().equalsIgnoreCase("Paychex Flex Pro")))
				{
					output.getRecord(i).setValue(5, "N/A");
					output.getRecord(i).setValue(6, "Client is on EDW");
					output.getRecord(i).setValue(7, "None Needed");
				}
				else
				{
					if(Integer.parseInt(output.getRecord(i).getValue(1).toString())>400 &Integer.parseInt(output.getRecord(i).getValue(1).toString())<500)
					{
						output.getRecord(i).setValue(5, "Reload");
						output.getRecord(i).setValue(6, "Transfer to MMS");
						output.getRecord(i).setValue(7, "Create case for AS to get updated order form");
					}
					else
					{
						if(output.getRecord(i).getValue(9).length()>1)
						{
							output.getRecord(i).setValue(5, "Timing Issue");
							output.getRecord(i).setValue(6, "STM Cancelled");
							output.getRecord(i).setValue(7, "None Needed");
						}
						else
						{
							//if they've cancelled payroll ELSE
							if(output.getRecord(i).getValue(10).equals("Terminated/Lost")|
									output.getRecord(i).getValue(10).equals("Inactive"))
							{
								output.getRecord(i).setValue(5, "Cancelled Payroll");
								output.getRecord(i).setValue(6, "Cancelled Payroll");
								output.getRecord(i).setValue(7, "Follow Proposed to cancel process");
							}
							else
							{
								//They're running the standalone payroll product ELSE
								if(!(output.getRecord(i).getValue(11).trim().equalsIgnoreCase("Paychex Productivity"))&
										!(output.getRecord(i).getValue(11).trim().equalsIgnoreCase("Paychex HR Solutions - Administrative Service"))&
										!(output.getRecord(i).getValue(11).trim().equalsIgnoreCase("Paychex Flex Pro"))&
										!(output.getRecord(i).getValue(11).trim().equalsIgnoreCase("N/A")))
								{
									output.getRecord(i).setValue(5, "Branch Operations");
									output.getRecord(i).setValue(6, "Running "+output.getRecord(i).getValue(11));
									output.getRecord(i).setValue(7, "Create case for AS to get updated order form.");
								}
								else
								{
									if(output.getRecord(i).getValue(11).trim().equalsIgnoreCase("Paychex HR Solutions - Administrative Service"))
									{
										output.getRecord(i).setValue(5, "Branch Operations");
										output.getRecord(i).setValue(6, "Running ASO");
										output.getRecord(i).setValue(7, "Update HRIS.net");
									}
									else
									{
										if(output.getRecord(i).getValue(11).trim().equalsIgnoreCase("Paychex Productivity"))
										{
											output.getRecord(i).setValue(5, "Branch Operations");
											output.getRecord(i).setValue(6, "Running Productivity");
											output.getRecord(i).setValue(7, "Update HRIS.net");
										}
										else
										{
											//if the last date they've run payroll is over 90 days of the month being auditted
											LocalDate thisDate;
											DateTimeFormatter dtf = DateTimeFormatter.ofPattern("M/d/yyyy");
											LocalDate currentDate = LocalDate.now();
											currentDate = currentDate.with(firstDayOfMonth());
											currentDate = currentDate.minusDays(1);
											LocalDate ninetyDays = currentDate.minusDays(90);
											if(output.getRecord(i).getValue(12) != null&!output.getRecord(i).getValue(12).equalsIgnoreCase("N/A")) {
												thisDate = LocalDate.parse(output.getRecord(i).getValue(12), dtf);}
											else
											{
												if(output.getRecord(i).getValue(12).equalsIgnoreCase("N/A"))
													output.getRecord(i).setValue(12,"1/1/1900");
												thisDate = LocalDate.parse("1/1/1900",dtf);
											}
											
											if( (output.getRecord(i).getValue(11).trim().equalsIgnoreCase("Paychex Productivity") | output.getRecord(i).getValue(11).trim().equalsIgnoreCase("Paychex HR Solutions - Administrative Service") )&
													ninetyDays.isAfter(thisDate)&!output.getRecord(i).getValue(12).equalsIgnoreCase("1/1/1900"))//wondering if 1/1/1900 could break the timing issue case above
											{
												output.getRecord(i).setValue(5, "Payroll Not Run >90 Days");
												output.getRecord(i).setValue(6, "Payroll Not Run >90 Days");
												output.getRecord(i).setValue(7, "Contact hub to see if running.");
											}
											else
											{
												//else if they're on the EDW already
												if(ninetyDays.isBefore(thisDate)&output.getRecord(i).getValue(8).equals("Yes"))
												{
													output.getRecord(i).setValue(5, "N/A");
													output.getRecord(i).setValue(6, "Client is on EDW-PNG");
													output.getRecord(i).setValue(7, "None Needed");
												}
												else
												{
													//else if they havent run payroll, but its been less than 90 days
													if(ninetyDays.isBefore(thisDate))
													{
														output.getRecord(i).setValue(5, "Payroll Not Run <90 Days");
														output.getRecord(i).setValue(6, "Payroll Not Run <90 Days");
														output.getRecord(i).setValue(7, "None Needed");
													}
													else
													{
														//all other cases need to be researched
														output.getRecord(i).setValue(5, "NEEDS RESEARCH");
														output.getRecord(i).setValue(6, "NEEDS RESEARCH");
														output.getRecord(i).setValue(7, "NEEDS RESEARCH");
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}	
		
		//see if on cancel payroll report
		for(int i = 0; i<output.getNumberOfRecords();i++)
		{
			String bis = output.getRecord(i).getValue(0)+
					("00000000"+output.getRecord(i).getValue(2)).substring(output.getRecord(i).getValue(2).length());
			for(int j = 0; j<cancelPayroll.getNumberOfRecords();j++)//update to concat
			{
				String concattarget = cancelPayroll.getRecord(j).getValue(0)+
						("00000000"+cancelPayroll.getRecord(j).getValue(3)).substring(cancelPayroll.getRecord(j).getValue(3).length());
			
				if(bis.equals(concattarget))
				{		
					output.getRecord(i).setValue(5, "On Cancel Payroll Report");
					output.getRecord(i).setValue(6, "On Cancel Payroll Report");
					output.getRecord(i).setValue(7, "None Needed");
					break;
				}

			}

		}
	}
	
	/**
	 * output- returns no values and takes no parameters
	 * 
	 * This method is responsible for creating the writer for the compiled output Report object, and calling the method to write it a .csv file
	 */
	public void output()
	{
		CSVWriter w = new CSVWriter();
		try {
			w.writeReport(output, outPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
