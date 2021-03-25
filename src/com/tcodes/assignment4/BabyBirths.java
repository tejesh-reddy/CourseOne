package com.tcodes.assignment4; /**
 * Print out total number of babies born, as well as for each gender, in a given CSV file of baby name data.
 * 
 * @author Duke Software Team 
 */
import edu.duke.*;
import org.apache.commons.csv.*;

import java.io.File;

public class BabyBirths {
	public void printNames () {
		FileResource fr = new FileResource();
		for (CSVRecord rec : fr.getCSVParser(false)) {
			int numBorn = Integer.parseInt(rec.get(2));
			if (numBorn <= 100) {
				System.out.println("Name " + rec.get(0) +
						   " Gender " + rec.get(1) +
						   " Num Born " + rec.get(2));
			}
		}
	}

	public void totalBirths (FileResource fr) {
		int totalBirths = 0;
		int totalBoys = 0;
		int totalGirls = 0;

		int totalNames = 0;
		int totalBoyNames = 0;
		int totalGirlNames = 0;
		StorageResource boyNames = new StorageResource();
		StorageResource girlNames = new StorageResource();

		for (CSVRecord rec : fr.getCSVParser(false)) {
			int numBorn = Integer.parseInt(rec.get(2));
			totalBirths += numBorn;
			if (rec.get(1).equals("M")) {
				totalBoys += numBorn;
				if(!boyNames.contains(rec.get(0)))
					boyNames.add(rec.get(0));
			}
			else {
				totalGirls += numBorn;
				if(!girlNames.contains(rec.get(0)))
					girlNames.add(rec.get(0));
			}
		}
		System.out.println("total births = " + totalBirths);
		System.out.println("female girls = " + totalGirls);
		System.out.println("male boys = " + totalBoys);

		totalBoyNames = boyNames.size();
		totalGirlNames = girlNames.size();
		totalNames = totalBoyNames + totalGirlNames;
		System.out.println("total names = " + totalNames);
		System.out.println("total boy names = " + totalBoyNames);
		System.out.println("total girl names = " + totalGirlNames);
	}

	public int getRank(int year, String name, String gender)
	{
		int rank = 1;
		FileResource fr = new FileResource("data/yob"+year+".csv");
		CSVParser parser = fr.getCSVParser(false);
		for(CSVRecord record : parser){
			if(record.get(1).equals(gender) && record.get(0).equals(name))
				return rank;
			rank++;
		}

		return -1;
	}

	public String getName(int year, int rank, String gender)
	{
		int curr = 0;
		FileResource fileResource = new FileResource("data/yob"+year+".csv");
		CSVParser parser = fileResource.getCSVParser(false);
		for(CSVRecord record : parser){
			if(gender.equals(record.get(1)))
				curr++;
			if(curr == rank)
				return record.get(0);
		}

		return "NO NAME";
	}

	public void whatIsNameInYear(String name, int year, int newYear, String gender)
	{
		int rank = getRank(year, name, gender);
		String newName = getName(newYear, rank, gender);
		System.out.print(name + " born in " + year + " would be " + newName + " if ");
		if(gender.equals("M"))
			System.out.print(" he ");
		else
			System.out.print(" she ");
		System.out.println("was born in " + newYear);
	}

	public int yearOfHighestRank(String name, String gender)
	{
		DirectoryResource directoryResource = new DirectoryResource();
		int maxRank = Integer.MAX_VALUE;
		int highestYear = -1;
		for(File f : directoryResource.selectedFiles())
		{
			String fileYear = f.getName().substring(2, 7);
			int year = Integer.parseInt(fileYear);
			int rank = getRank(year, name, gender);
			if(rank < maxRank){
				maxRank = rank;
				highestYear = year;
			}
		}

		return highestYear;
	}

	public double getAverageRank(String name, String gender)
	{
		DirectoryResource directoryResource = new DirectoryResource();
		int cnt = 0;
		double totalRank = 0;
		for(File f : directoryResource.selectedFiles())
		{
			String fileYear = f.getName().substring(2, 7);
			int year = Integer.parseInt(fileYear);
			int rank = getRank(year, name, gender);
			totalRank += rank;
			cnt++;
		}

		return totalRank/cnt;
	}

	public int getTotalBirthsRankedHigher(int year, String name, String gender)
	{
		int result = 0;
		int rank = getRank(year, name, gender);
		int cnt = 0;
		FileResource fr = new FileResource("data/yob"+year+".csv");
		CSVParser parser = fr.getCSVParser(false);
		for(CSVRecord record : parser){
			if(gender.equals(record.get(1))){
				if(rank == cnt)
					break;
				result += Integer.parseInt(record.get(2));
			}
		}

		return result;
	}

	public void testTotalBirths () {
		//FileResource fr = new FileResource();
		FileResource fr = new FileResource();
		totalBirths(fr);
	}


	public void testGetRank()
	{
		System.out.println("Rank of Mason in 2014 is " + getRank(2014, "Mason", "M"));
	}
}
