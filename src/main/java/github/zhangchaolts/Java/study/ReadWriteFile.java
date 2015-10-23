package github.zhangchaolts.Java.study;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ReadWriteFile {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader("data/study_ReadWriteFile_input.txt"));
        BufferedWriter bw = new BufferedWriter(new FileWriter("data/study_ReadWriteFile_output.txt"));
        
		String line;
		while ((line = br.readLine()) != null) {
			System.out.println(line);
			bw.write(line + "#\n");
		}
    
		br.close();
		bw.close();

    }

}
