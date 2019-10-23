package runner;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.zip.GZIPInputStream;


public class Main {
	
	public static void main(String args[]) throws IOException {
		if(args.length < 2) {
			System.out.println("Required both input and output file");
			System.exit(1);
		}
		
		String inputFileName = args[0];
		String outputFileName = args[1];
		System.out.println("Reading input file" + inputFileName + " and producing output file" + outputFileName);
    	BufferedReader in = null;

		try {
			InputStream rawin = new FileInputStream(inputFileName);
			if (inputFileName.endsWith(".gz"))
				rawin = new GZIPInputStream(rawin);
			in = new BufferedReader(new InputStreamReader(rawin));
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			e.printStackTrace();
			System.exit(1);
		}

		BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName));
		String line = in.readLine();
		int count = 0;
		HashSet<Integer> labels = new HashSet<Integer>();
		while(line!=null) {
			//System.out.print(line);
			String [] tokens = line.split(",");
			int parseInt1 = Integer.parseInt(tokens[1]);
			parseInt1 /= 10;
			int parseInt2 = Integer.parseInt(tokens[3]);
			parseInt2 /= 10;
			labels.add(parseInt1);
			labels.add(parseInt2);
			String newLine = tokens[0] + "," + parseInt1 + "," + tokens[2] +","+ parseInt2 +"," + tokens[4];
			writer.write(newLine+"\n");
			if(count++%100000 == 0) {
				writer.flush();
			}
			line = in.readLine();
		}
		in.close();
		writer.flush();
		writer.close();
		System.out.println(labels);
	}
}
