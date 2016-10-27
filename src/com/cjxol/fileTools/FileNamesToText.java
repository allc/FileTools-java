/**
 * Record file names in a directory into a text file, one file per line
 * args: [input directory] [output text file]
 * 
 * @author Allen
 */
package com.cjxol.fileTools;

import java.io.*;
import java.util.Scanner;

public class FileNamesToText {
	
	public static final String DEFAULT_OUTPUT_FILE_NAME = "file list.txt";
	
	/**
	 * 
	 * @param args [input directory] [output text file]
	 */
	public static void main(String[] args) {
		String inDirectoryString;
		String outFileString;
		
		/* get input directory and output file from args
		 * 
		 * default output file name is defined
		 * default input directory is current working directory
		 */
		if (args.length >= 1) {
			inDirectoryString = args[0];
			outFileString = inDirectoryString + "/" + DEFAULT_OUTPUT_FILE_NAME;
			if (args.length > 1) {
				outFileString = args[1];
			}
		} else {
			inDirectoryString = System.getProperty("user.dir");
			outFileString = inDirectoryString + "/" + DEFAULT_OUTPUT_FILE_NAME;
		}
		
		// input directory
		File inDirectory = new File(inDirectoryString);
		File[] filesInDirectory = inDirectory.listFiles();
		// check is input directory valid
		if (filesInDirectory == null) {
			showErr("No such directory or it is a file");
			return;
		}
		
		// output file
		File outFile = new File(outFileString);
		// if output file already exists or is a directory
		if (outFile.exists()) {
			if (outFile.isDirectory()) {
				showErr(outFile + " is a directory");
				return;
			} else {
				boolean overwriteConfirmResponded = false;
				Scanner scanner = new Scanner(System.in);
				String overwriteConfirm;
				while (!overwriteConfirmResponded) {
					System.out.print(outFile + " already exists, are you sure you want to overwrite?(y/n)");
					overwriteConfirm = scanner.nextLine();
					if (overwriteConfirm.toLowerCase().equals("y") || overwriteConfirm.toLowerCase().equals("YES")) {
						overwriteConfirmResponded = true;
						scanner.close();
					} else if (overwriteConfirm.toLowerCase().equals("n") || overwriteConfirm.toLowerCase().equals("NO")) {
							System.out.println("Canceled by user");
							scanner.close();
							return;
					} else {
						System.out.println("Cannot recongnise, please try again");
					}
				}
			}
		}
		
		String fileListString = "";
		for (File file : filesInDirectory) {
			fileListString += file.getName() + "\n";
		}
		BufferedWriter bufferedWriter = null;
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(outFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			bufferedWriter.write(fileListString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			bufferedWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// output to console
		String consoleOutput;
		if (outFile.toString().indexOf(" ") >= 0) {
			consoleOutput =  "Written the following to \"" + outFile + "\": \n" + fileListString;
		} else {
			consoleOutput =  "Written the following to " + outFile + ": \n" + fileListString;
		}
		System.out.println(consoleOutput);
	}
	
	public static void showErr(String errMessage) {
		System.out.println("Error: " + errMessage);
	}

}
