package github.zhangchaolts.Java.study;

import java.io.File;

public class DirOperation {
	public static void main(String args[]) {
		try {
			String sdir = "data";
			File Fdir1 = new File(sdir);
			if (Fdir1.exists() && Fdir1.isDirectory()) {
				
				System.out.println("Directory " + sdir + " exists.\n");
				
				for (int i = 0; i < Fdir1.list().length; i++) {
					System.out.println((Fdir1.list())[i]);
				}
				System.out.println();
				
				File Fdir2 = new File("data/tmpDir");
				if (!Fdir2.exists())
					Fdir2.mkdir();
	
				System.out.println("Now the new list after create a new dir:");
				for (int i = 0; i < Fdir1.list().length; i++) {
					System.out.println((Fdir1.list())[i]);
				}
				
				Fdir2.delete();
			}		
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
}
