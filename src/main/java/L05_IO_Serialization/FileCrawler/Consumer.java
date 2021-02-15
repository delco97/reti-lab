package L05_IO_Serialization.FileCrawler;

import java.io.File;
import java.util.List;

public class Consumer implements Runnable {
	FoldersQueue targetFolders;
	
	public Consumer(FoldersQueue targetFolders) {
		this.targetFolders = targetFolders;
	}
	
	private void showDirContent(File f) {
		File[] content = null;
		
		if (!f.exists()) return;
		
		//If it's a directory print all its content.
		System.out.println("Folder " + f + " contains: ");
		if (f.isDirectory() && (content = f.listFiles()) != null && content.length > 0) {
			for (File fi : f.listFiles()) System.out.println(fi);
		}
	}
	
	@Override
	public void run() {
		File d = null;
		while ((d = targetFolders.pop()) != null) {
			showDirContent(d);
		}
	}
	
	
	
}
