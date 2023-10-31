package assignment03;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFileChooser;

public class OpenDirectory {

	private static final List<String> fileTypes = Arrays.asList(".TXT", ".TEXT", ".JAVA", ".C", ".CPP", ".H", ".HTM", ".HTML", ".JS", ".HS", ".XML", ".LOG");
	
	public static void listFiles(File file, String text) {
		try (PrintWriter pw = new PrintWriter("Output.txt")) {
			System.out.println(file);
			var iter = new FileCompositeIterator(file);
			while(iter.hasNext()) {
				File f = iter.next();
				pw.println(f);

				String type = " ";
				if(f.getName().lastIndexOf(".") > -1){
					type = f.getName().substring(f.getName().lastIndexOf(".")).toUpperCase();
				}
				if(fileTypes.contains(type)) {
					if(f.getName().toUpperCase().contains(text.toUpperCase())){
						System.out.println(f + " contains text " + text + " in its file name");
					}
					if(isTextPresent(f, pw, text))
						System.out.println(f + " contains text " + text + " inside the file");
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(
				new Runnable() {
					public void run() {
						// See https://stackoverflow.com/questions/10083447/selecting-folder-destination-in-java
						var chooser = new JFileChooser(); 
						chooser.setCurrentDirectory(new java.io.File("~"));
						chooser.setDialogTitle("Pick a starting directory");
						chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
						// disable the "All files" option.
						chooser.setAcceptAllFileFilterUsed(false);
						if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) { 
							System.out.println("getCurrentDirectory(): " 
									+  chooser.getCurrentDirectory());
							System.out.println("getSelectedFile() : " 
									+  chooser.getSelectedFile());

							// Getting text to search from user
							BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
							String text = "";
       						try {
								System.out.println("Enter the text you want to search");
							 	text = reader.readLine();
							} catch (IOException e) {
								e.printStackTrace();
							}
							listFiles(chooser.getSelectedFile(), text);
						} else {
							System.out.println("No Selection ");
						}
						System.out.println("DONE!");
					}
				});		
	}

	public static boolean isTextPresent(File f, PrintWriter pw, String text) {
		boolean isTextPresent = false;;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String line;
			while((line = reader.readLine()) != null) {
				String[] temp = line.split("\\s+");
				for(String s : temp)
					if(s.toLowerCase().contains(text)){
						isTextPresent = true;
						reader.close();
						return isTextPresent;
					} 
			}

			reader.close();

		} catch (Exception e) {
			pw.println("PROBLEM WITH " + f);
			e.printStackTrace(pw);
		}			
		return isTextPresent;
	}
}
