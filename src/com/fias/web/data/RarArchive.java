package com.fias.web.data;
//import net.lingala.zip4j.exception.ZipException;

//import net.lingala.zip4j.core.ZipFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.github.junrar.Archive;
import com.github.junrar.exception.RarException;
import com.github.junrar.impl.FileVolumeManager;
import com.github.junrar.rarfile.FileHeader;

public class RarArchive {
	public static void extract(String fileURL, String saveDir) {
		String filename = fileURL;
		File f = new File(filename);
		Archive a = null;
		try {
			a = new Archive(new FileVolumeManager(f));
		} catch (RarException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (a != null) {
			a.getMainHeader().print();
			FileHeader fh = a.nextFileHeader();
			while (fh != null) {
				try {
					String name = null;
					// File out = new File(saveDir +
					// fh.getFileNameString().trim());
					// System.out.println(out.getAbsolutePath());
					// FileOutputStream os = new FileOutputStream(out);
					// a.extractFile(fh, os);
					// System.out.println(a.nextFileHeader().toString());
					// //изменения
					name = fh.getFileNameString();
					if (name.startsWith("AS_ADDROBJ")) {
						File out = new File(saveDir + fh.getFileNameString().trim());
						System.out.println(out.getAbsolutePath());
						FileOutputStream os = new FileOutputStream(out);
						a.extractFile(fh, os);
						os.flush();
						os.close();
						os = null;
						System.gc();
						// os.flush();//
						// a.close(); //Добавил
						// File file2 = new File("AS_ADDROBJ_DELTA.XML");
						// out.renameTo(file2);
					}
					;
					// System.out.println(fh.getFileNameString()); //изменения
					// os.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (RarException e) {
					e.printStackTrace();
				} catch (IOException e) {
					// e.printStackTrace();
				}
				fh = a.nextFileHeader();
			}
		}
		try {
			a.close();
			a = null;
			System.gc();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
