package com.tcodes.assignment3;
import com.sun.jdi.connect.Connector;
import edu.duke.*;
import org.apache.commons.csv.*;

import java.io.IOException;

public class WhichCountriesExport {
	private void listExportersTwoProducts(CSVParser parser, String exportItem1, String exportItem2)
	{
		for (CSVRecord record : parser) {
			String export = record.get("Exports");
			if (export.contains(exportItem1) && export.contains(exportItem2)) {
				String country = record.get("Country");
				System.out.println(country);
			}
		}

	}

	private int numberOfExporters(CSVParser parser, String exportItem)
	{
		return listExporters(parser, exportItem).size();
	}

	private void bigExporters(CSVParser parser, String amount)
	{
		int amountLength = amount.length();
		for(CSVRecord record : parser){
			String value = record.get("Value (dollars)");
			if(value.length() > amountLength)
				System.out.println(record.get("Country") + ": " + value);
		}
	}

	public StorageResource listExporters(CSVParser parser, String exportOfInterest) {
		StorageResource storageResource = new StorageResource();

		for (CSVRecord record : parser) {
			String export = record.get("Exports");
			if (export.contains(exportOfInterest)) {
				String country = record.get("Country");
				storageResource.add(country);
			}
		}

		return storageResource;
	}

	public void whoExportsCoffee() {
		FileResource fr = new FileResource();
		CSVParser parser = fr.getCSVParser();
		listExporters(parser, "coffee");
	}

	private String countryInfo(CSVParser parser, String country) {
		try {
			for(CSVRecord record : parser.getRecords())
			{
				String currCountry = record.get("Country");
				if(currCountry.equals(country)){
					return (currCountry + ": " + record.get("Exports") + ": "+ record.get("Value (dollars)"));
				}
			}
		}
		catch (Exception e)
		{
			System.out.println("Exception caught: " + e.getMessage());
		}
		return "NOT FOUND";
	}

	public void tester(){
		FileResource fr = new FileResource();
		CSVParser parser = fr.getCSVParser();
		bigExporters(parser, "$999,999,999,999");
	}
}
