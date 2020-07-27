/*
*title: CSVReader.java
*author: Austin Sierra
*last edit: 9/19/2017
*/

package menu;
import java.io.BufferedReader;
import menu.Report;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

/*
*tutorial:
*https://www.mkyong.com/java/how-to-read-and-parse-csv-file-in-java/
*/
public class CSVReader {

    private String csvFile = "C:/Users/asierra/Desktop/Paychex/test.csv";
    private BufferedReader br = null;
    private String line = "";
    private final String splitter = ",";
		
	
	public CSVReader()
	{
	}

    public Report read(String filepath,int reportID)
    {
    	csvFile = filepath;
	
    	Report r = null;
        try {

            br = new BufferedReader(new FileReader(csvFile));
			int linenum = 1;
			//replaces commas within quotations with @@@@ so not to place them in a seperate column. Replaces at end
			//https://stackoverflow.com/questions/1658538/regular-expression-replace-all-commas-between-double-quotes
            while ((line = br.readLine()) != null) {
            	String reg = "(?<=\")([^\"]+?),([^\"]+?)(?=\")";
            	String old = line;
        		line = line.replaceAll(reg, "$1@@@$2");
        		while (!line.equalsIgnoreCase(old)){  
        		    old = line;  
        		    line = line.replaceAll(reg, "$1@@@$2");  
        		}  
            	
            	String[] lineArray = line.split(splitter);
            	for(int i = 0; i<lineArray.length;i++){
            			lineArray[i]= lineArray[i].replaceAll(Pattern.quote("@@@"), ",");
            	}
            	
            	//skip headerline
				if(linenum!=1){
					if(!(lineArray.length>0))
						break;
					// use comma as separator
					r.addRecord(lineArray);
				}
				else{   r = new Report(reportID, lineArray);}
				
				linenum++;
               

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "The file path, \""+csvFile+"\" was not found.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
           
        }
        return r;
    }
}
