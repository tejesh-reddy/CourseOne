package com.tcodes.assignment1;

import edu.duke.DirectoryResource;
import edu.duke.FileResource;
import edu.duke.Point;
import edu.duke.Shape;

import java.io.File;

public class PerimeterAssignmentRunner {
    public static double getPerimeter (Shape s) {
        // Start with totalPerim = 0
        double totalPerim = 0.0;
        // Start wth prevPt = the last point 
        Point prevPt = s.getLastPoint();
        // For each point currPt in the shape,
        for (Point currPt : s.getPoints()) {
            // Find distance from prevPt point to currPt 
            double currDist = prevPt.distance(currPt);
            // Update totalPerim by currDist
            totalPerim = totalPerim + currDist;
            // Update prevPt to be currPt
            prevPt = currPt;
        }
        // totalPerim is the answer
        return totalPerim;
    }

    public int getNumPoints (Shape s) {
        int numPts = 0;
        for(Point p : s.getPoints())
            numPts++;
        return numPts;
    }

    public double getAverageLength(Shape s) {
        int numPts = getNumPoints(s);
        double perim = getPerimeter(s);
        return perim/numPts;
    }

    public double getLargestSide(Shape s) {
        double largestSide = 0;
        double newdist;
        Point lastpt = s.getLastPoint();

        for(Point p : s.getPoints()){
            newdist = p.distance(lastpt);
            if(newdist > largestSide)
                largestSide = newdist;
            lastpt = p;
        }
        return largestSide;
    }

    public double getLargestX(Shape s) {
        double largestX = s.getLastPoint().getX();

        for(Point p : s.getPoints())
        {
            double newx = p.getX();
            if(largestX < newx)
                largestX = newx;
        }
        return largestX;
    }

    public double getLargestPerimeterMultipleFiles() {
        LargestFilePerimeter largestFilePerimeter = new LargestFilePerimeter();
        return largestFilePerimeter.getMaxPerim();
    }

    public String getFileWithLargestPerimeter() {
        LargestFilePerimeter largestFilePerimeter = new LargestFilePerimeter();
        File largestFile = largestFilePerimeter.getLargestFile();
        return largestFile.getName();
    }

    public void testPerimeter () {
        FileResource fr = new FileResource();
        Shape s = new Shape(fr);
        getPerimDetails(s);
    }

    private void getPerimDetails(Shape s) {
        double length = getPerimeter(s);

        System.out.println("Perimeter = " + length);

        //Optional
        int numPts = getNumPoints(s);
        double avgLen = getAverageLength(s);
        double largestSide = getLargestSide(s);
        double largestX = getLargestX(s);

        System.out.println("Number of points = " + numPts);
        System.out.println("Average Length = " + avgLen);
        System.out.println("Largest Side = " + largestSide);
        System.out.println("Largest X = " + largestX);
    }
    
    public void testPerimeterMultipleFiles() {
        System.out.println("The largest perimeter: "+ getLargestPerimeterMultipleFiles());
    }

    public void testFileWithLargestPerimeter() {
        System.out.println("File with largest perimeter: "+ getFileWithLargestPerimeter());
    }


    // This method creates a triangle that you can use to test your other methods
    public void triangle(){
        Shape triangle = new Shape();
        triangle.addPoint(new Point(0,0));
        triangle.addPoint(new Point(6,0));
        triangle.addPoint(new Point(3,6));
        for (Point p : triangle.getPoints()){
            System.out.println(p);
        }
        double peri = getPerimeter(triangle);
        System.out.println("perimeter = "+peri);
    }

    // This method prints names of all files in a chosen folder that you can use to test your other methods
    public void printFileNames() {
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            System.out.println(f.getName());
        }
    }

    public static void main (String[] args) {
        PerimeterAssignmentRunner pr = new PerimeterAssignmentRunner();
        pr.testPerimeter();
        pr.testPerimeterMultipleFiles();
        pr.testFileWithLargestPerimeter();
    }
}

class LargestFilePerimeter
{
    private double maxPerim;
    private File largestFile;


    public LargestFilePerimeter()
    {
        DirectoryResource dir = new DirectoryResource();

        for(File f : dir.selectedFiles()) {
            Shape s = new Shape(new FileResource(f));
            double thisPerim = PerimeterAssignmentRunner.getPerimeter(s);
            if(thisPerim > maxPerim) {
                maxPerim = thisPerim;
                largestFile = f;
            }
        }
    }

    public double getMaxPerim(){
        return maxPerim;
    }

    public File getLargestFile(){
        return largestFile;
    }
}