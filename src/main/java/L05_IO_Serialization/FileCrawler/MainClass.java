package L05_IO_Serialization.FileCrawler;

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class MainClass {
	
	public static void main(String[] args) {
		int nCons = 5;
		File startFolder = new File("src/L05_IO_Serialization/FileCrawler/Test");
		FoldersQueue targetFolders = new FoldersQueue();
		//Create prducers and consumers
		new Thread(new Producer(startFolder, targetFolders)).start();
		for (int i = 0; i < nCons; i++)
			new Thread(new Consumer(targetFolders)).start();
		
		
	}
	
}
