package L05_IO_Serialization.FileCrawler;

import java.io.File;
import java.util.List;

public class Producer implements Runnable {
	File startFolder;
	FoldersQueue targetFolders;
	
	public Producer(File startFolder, FoldersQueue targetFolders) {
		this.startFolder = startFolder;
		this.targetFolders = targetFolders;
	}
	
	private void findFolders(File f) {
		File[] content = null;
		if(f.isDirectory()) targetFolders.push(f);
		//If it's a directory perform a recursive search to find other folders inside it.
		if (f.isDirectory() && (content = f.listFiles()) != null && content.length > 0) {
			for (File fi : content)
				if(fi.isDirectory()) targetFolders.push(fi);
		}
	}
	
	@Override
	public void run() {
		System.out.println("Start search of folders in " + startFolder.getAbsolutePath());
		//Check directory
		if (!startFolder.exists()) {
			System.out.println(startFolder.getAbsolutePath() + " dosen't exist!");
			return;
		}
		if (!startFolder.isDirectory()) {
			System.out.println(startFolder.getAbsolutePath() + " is not a directory!");
			return;
		}
		//startFolder is valid
		findFolders(startFolder);
		targetFolders.finishSearch();
	}
	
}
