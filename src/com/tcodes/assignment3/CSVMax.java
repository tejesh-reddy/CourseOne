package com.tcodes.assignment3;
import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

public class CSVMax {
	public CSVRecord hottestHourInFile(CSVParser parser) {
		CSVRecord largestSoFar = null;
		//For each row (currentRow) in the CSV File
		for (CSVRecord currentRow : parser) {
			//If largestSoFar is nothing
			if (largestSoFar == null) {
				largestSoFar = currentRow;
			}
			//Otherwise
			else {
				double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
				double largestTemp = Double.parseDouble(largestSoFar.get("TemperatureF"));
				//Check if currentRow’s temperature > largestSoFar’s
				if (currentTemp > largestTemp) {
					//If so update largestSoFar to currentRow
					largestSoFar = currentRow;
				}
			}
		}
		//The largestSoFar is the answer
		return largestSoFar;
	}


	public void testHottestInDay(){
		FileResource fr = new FileResource();
		CSVRecord largest = hottestHourInFile(fr.getCSVParser());
		System.out.println("hottest temperature was " + largest.get("TemperatureF") +
				   " at " + largest.get("TimeEST"));
	}

	private CSVRecord coldestHourInFile(CSVParser parser)
	{
		float minTemp = Float.POSITIVE_INFINITY;
		CSVRecord minRecord = null;
		for(CSVRecord record : parser){
			if(minRecord == null)
				minRecord = record;
			float temp = Float.parseFloat(record.get("TemperatureF"));
			if(minTemp > temp && temp != -9999){
				minTemp = temp;
				minRecord = record;
			}
		}

		return minRecord;
	}

	public void testColdestHour()
	{
		FileResource fr = new FileResource();
		CSVParser parser = fr.getCSVParser();
		CSVRecord minRecord = coldestHourInFile(parser);
		System.out.println(minRecord.get("TimeEST") + " "+minRecord.get("TemperatureF")+"deg F.");
	}

	public void fileWithColdestTemperature()
	{
		float minTemp = Float.POSITIVE_INFINITY;
		FileResource result = null;
		File file = null;
		DirectoryResource directoryResource = new DirectoryResource();
		for(File f : directoryResource.selectedFiles())
		{
			FileResource fr = new FileResource(f);
			CSVParser parser = fr.getCSVParser();
			if(result == null)
				result = fr;
			CSVRecord currRecord = coldestHourInFile(parser);
			Float currTemp = Float.parseFloat(currRecord.get("TemperatureF"));
			if(minTemp > currTemp){
				minTemp = currTemp;
				result = fr;
				file = f;
			}
		}

		System.out.println("Coldest Day was in the file: " + file);
		System.out.println("Coldest temperature on that day was: " + minTemp);
		System.out.println("All the temperatures on the coldest day were: ");
		CSVParser parser = result.getCSVParser();
		for(CSVRecord record : parser){
			System.out.println(record.get("TimeEST") + " " + record.get("TemperatureF"));
		}
	}

	private CSVRecord lowestHumidityInFile(CSVParser parser)
	{
		int minhum = Integer.MAX_VALUE;
		CSVRecord minRecord = null;
		for(CSVRecord record : parser){
			if(minRecord == null)
				minRecord = record;
			int humidity = Integer.parseInt(record.get("Humidity"));
			if(minhum > humidity){
				minhum = humidity;
				minRecord = record;
			}
		}

		return minRecord;
	}

	private CSVRecord lowestHumidityInManyFiles()
	{
		int minHum = Integer.MAX_VALUE;
		CSVRecord result = null;
		DirectoryResource directoryResource = new DirectoryResource();
		for(File f : directoryResource.selectedFiles())
		{
			FileResource fr = new FileResource(f);
			CSVParser parser = fr.getCSVParser();
			CSVRecord currRecord = lowestHumidityInFile(parser);
			int currHum = Integer.parseInt(currRecord.get("Humidity"));
			if(minHum > currHum){
				minHum = currHum;
				result = currRecord;
			}
		}

		return result;
	}

	public void testLowestHumidityInManyFiles()
	{
		CSVRecord lowestHumidityRecord = lowestHumidityInManyFiles();
		System.out.println("Lowest Humidity was " + lowestHumidityRecord.get("Humidity") + " at "
				+ lowestHumidityRecord.get("DateUTC") + " " + lowestHumidityRecord.get("TimeEST"));
	}

	public float averageTemperatureInFile(CSVParser parser)
	{
		float tempSum = 0;
		int cnt = 0;
		for(CSVRecord record : parser){
			float temp = Float.parseFloat(record.get("TemperatureF"));
			tempSum += temp;
			cnt++;
		}

		return tempSum/cnt;
	}

	public void testaverageTemperatureInFile()
	{
		FileResource fr = new FileResource();
		CSVParser parser = fr.getCSVParser();
		System.out.println(averageTemperatureInFile(parser));
	}

	public double averageTemperatureWithHighHumidity(CSVParser parser, int value)
	{
		float tempSum = 0;
		int cnt = 0;
		for(CSVRecord record : parser){
			float temp = Float.parseFloat(record.get("TemperatureF"));
			if(value <= Integer.parseInt(record.get("Humidity"))) {
				tempSum += temp;
				cnt++;
			}
		}
		if(cnt == 0)
			return 0;
		return tempSum/cnt;
	}

	public void testAvergeTemperatureWithHighHumidity()
	{
		FileResource fr = new FileResource();
		CSVParser parser = fr.getCSVParser();
		double res = averageTemperatureWithHighHumidity(parser, 80);
		if(res == 0)
			System.out.println("No temperatures with that humidity");
		else
			System.out.println("Average Temp when high Humidity is "+res);
	}

}
