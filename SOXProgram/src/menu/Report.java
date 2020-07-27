package menu;

import java.util.ArrayList;

public class Report 
{
	
	private int cols;
	private int ID;
	private ArrayList<Record> records = new ArrayList<Record>();
	private String[] headers;
	
	/**
	 * Constructor
	 * An object representing a CSV report in a more manageable format
	 * 
	 * int ID- (0-14 representing what kind of report this is)
	 * int columnNums - (the number of columns)
	 */
	public Report(int ID, String[] headers)
	{
		this.ID = ID;
		this.headers = headers;
		cols = headers.length;
	}
	
	/**
	 * addRecord adds a new record to the records ArrayList
	 * 
	 * @param line- a string array containing the text for each column of a new record
	 */
	public void addRecord(String[] line)
	{
		records.add(new Record(line, cols));
	}
	
	/**
	 * addBlankRecord is similar to the addRecord method, except the record contains all blank strings
	 */
	public void addBlankRecord()
	{
		String[] blanks = new String[cols];
		records.add(new Record(blanks, cols));
	}
	
	
	/**
	 * getHeader()
	 * @return headers- the headers of this report
	 */
	public String[] getHeader()
	{
		return headers;
	}
	
	/**
	 * getRecord returns the an entire row of this report.
	 * @param rowNum - the number row you need to retrieve
	 * @return - the record you requested
	 */
	public Record getRecord(int rowNum)
	{
		return records.get(rowNum);
	}
	
	
	/**
	 * getNumberOfRecords() gets the size of this report
	 * @return the size of this report (how many records)
	 */
	public int getNumberOfRecords()
	{
		return records.size();
	}
	
	/**
	 * getID returns the ID of this report, which is different depending on the type. Each number is detailed in the View.java file secondMenu method
	 * @return ID- the id of this report
	 */
	public int getID()
	{
		return ID;
	}
	
	/**
	 * getCols gets the number of columns in this report, which is equal to the size of the headers
	 * @return cols- the number of columns
	 */
	public int getCols()
	{
		return cols;
	}
	
	/**
	 * Represents a particular record in a report
	 *
	 */
	public class Record
	{
		private String[] myValues;
		
		public Record(String[] values, int cols)
		{
			myValues = new String[cols];
			for(int grab=0;grab<myValues.length;grab++)
			{
				myValues[grab] = "N/A";
				if(grab<values.length)
					myValues[grab] = values[grab];
			}
		}
		
		/**
		 * getValue returns the string located at position colNum of this record
		 * @param colNum - the column of the requested string
		 * @return the string value located at the column
		 */
		public String getValue(int colNum)
		{
			return myValues[colNum];
		}
		
		/**
		 * setValue sets the value of the string located at position colNum of this record
		 * @param colNum - the column of the string to be set
		 * @param value - the value of the new string
		 */
		public void setValue(int colNum, String value)
		{
			myValues[colNum] = value;
		}
		
	}
}
