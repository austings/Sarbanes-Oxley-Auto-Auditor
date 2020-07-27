/*
*title: CSVWriter.java
*author: Austin Sierra
*last edit: 9/19/2017
*/


package menu;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


import java.io.Writer;
import java.util.List;

import menu.Report.Record;

//export 
//https://www.mkyong.com/java/how-to-export-data-to-csv-file-java/
public class CSVWriter {

    private static final char DEFAULT_SEPARATOR = ',';
	private BufferedWriter w;

			//output file opened by BufferedWriter // Single Record 
    public void writeReport(Report r, String outputDest) throws IOException {
    	try {
    		System.out.println("Writing");
			w = new BufferedWriter(new FileWriter(outputDest));
			StringBuilder sb = new StringBuilder();
			for(int i = 0;i<r.getNumberOfRecords();i++){
				if(i==0){
					//first time write the headers
					for(String s: r.getHeader()){
						sb.append(s+DEFAULT_SEPARATOR);
					}
					sb.append("\n");
					w.write(sb.toString());
					sb = new StringBuilder();
				}
				Record rec = r.getRecord(i);
				for(int j = 0; j<r.getCols();j++){
					String s = rec.getValue(j);
					if(s != null)
						sb.append(rec.getValue(j)+DEFAULT_SEPARATOR);
				}
				sb.append("\n");
				w.write(sb.toString());
				sb = new StringBuilder();}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
	        if (w != null) {
	            try {
	                w.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	       
	    }


    }

}

