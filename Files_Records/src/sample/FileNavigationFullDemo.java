package sample;

import java.io.File;
import java.io.IOException;

public class FileNavigationFullDemo {
	public static void main(String[]args)throws IOException{
		File folder=new File("ride_records");
		if(!folder.exists()) {
			folder.mkdir();
		}
		File logFile=new File(folder,"trip_logs.text");
		boolean created=logFile.createNewFile();
		System.out.println("File created : "+created);
		System.out.println("Exists:"+logFile.exists());
		System.out.println("Absolute Path: "+logFile.getAbsolutePath());
		System.out.println("Is Directory "+logFile.isDirectory());
		System.out.println("File size :"+logFile.length());
		
		logFile.delete();
		folder.delete();
	}

}
