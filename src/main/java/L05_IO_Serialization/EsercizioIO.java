package L05_IO_Serialization;

import java.io.File;

/**
 * Scrivere un programma Java che, a partire dal percorso di una directory (es.
 * "/path/to/dir/"), recupera il contenuto della directory e delle eventuali sottodirectory.
 * Il programma scrive in un file di nome “directories” il nome delle directory che incontra
 * e nel file “files” il nome dei file.
 */

public class EsercizioIO {
	
	public static void printFileInfo(File f, int level) {
		for (int i = 0; i < level; i++) System.out.print("  ");
		if (f.isDirectory()) System.out.print("[D] ");
		else System.out.print("[F] ");
		System.out.println(f);
	}
	
	public static void showContent(File f, int level) {
		File[] content = null;
		
		if (!f.exists()) return;
		printFileInfo(f, level);
		
		//If it's a directory perform a recursive search
		if (f.isDirectory() && (content = f.listFiles()) != null && content.length > 0) {
			for (File fi : f.listFiles()) showContent(fi, level + 1);
		}
	}
	
	public static void main(String[] args) {
		File targetFolder = new File("src/L05_IO_Serialization/Test");
		System.out.println("Start search in " + targetFolder.getAbsolutePath());
		//Check directory
		if (!targetFolder.exists()) {
			System.out.println(targetFolder.getAbsolutePath() + " dosen't exist!");
			return;
		}
		if (!targetFolder.isDirectory()) {
			System.out.println(targetFolder.getAbsolutePath() + " is not a directory!");
			return;
		}
		showContent(targetFolder, 0);
	}
	
}
