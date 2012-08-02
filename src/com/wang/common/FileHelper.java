package com.wang.common;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileHelper {

	public static List<String> getAllFilesByExt(String basepath, String ext) {
		List<String> list = new ArrayList<String>();
		try {
			File directory = null;
			if (basepath != null) {
				directory = new File(basepath);
				directory.getAbsolutePath();
			} else {
				File temp = new File("data");
				directory = new File(temp.getCanonicalPath());
			}
			File[] files = directory.listFiles();

			for (int i = 0; i < files.length; i++) {

				String path = files[i].getAbsolutePath();
				int index = path.lastIndexOf('.');
				if (index != -1) {
					String fileext = path.substring(index + 1);
					if (ext.equalsIgnoreCase(fileext)) {
						list.add(path);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	public static String getFileNameNoExt(String path){
		File file = new File(path);
		String filename = file.getName();
		int index = filename.lastIndexOf('.');
		if (index != -1) filename = filename.substring(0, index);
		return filename;
	}

}
