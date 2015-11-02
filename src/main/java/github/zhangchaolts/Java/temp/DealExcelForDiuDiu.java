package github.zhangchaolts.Java.temp;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

import jxl.*;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

class Cell {
	public String hanzi;
	public String pinyin;

	public Cell(String hanzi, String pinyin) {
		this.hanzi = hanzi;
		this.pinyin = pinyin;
	}
}

class ComparatorCell implements Comparator {

	public int compare(Object arg0, Object arg1) {
		Cell user0 = (Cell) arg0;
		Cell user1 = (Cell) arg1;
		return (0 - user0.hanzi.compareTo(user1.hanzi));
	}

}

public class DealExcelForDiuDiu {

	public static void main(String[] args) throws Exception {

		File excelFile = new File("C:\\Users\\zhangchao\\Desktop\\词表.xls");
		Workbook workbook = Workbook.getWorkbook(excelFile);
		Sheet sheet = workbook.getSheet(0);

		OutputStream os = new FileOutputStream(
				"C:\\Users\\zhangchao\\Desktop\\词表2.xls");
		WritableWorkbook wwb = Workbook.createWorkbook(os);
		WritableSheet ws = wwb.createSheet("Sheet_1", 0);

		int allCol = sheet.getColumns();
		int allRow = sheet.getRows();

		System.out.println("allCol:" + allCol);
		System.out.println("allRow:" + allRow);

		HashMap<String, Cell> hanziMap = new HashMap<String, Cell>();

		int new_r1 = 0;
		for (int r = 0; r < allRow; r++) {
			for (int c = 0; c < allCol; c++) {
				if (sheet.getCell(c, r).getContents() != "") {
					String word = sheet.getCell(c, r).getContents().trim();
					if (word.equals("A") == true || word.equals("B") == true
							|| word.equals("C") == true
							|| word.equals("D") == true
							|| word.equals("E") == true
							|| word.equals("F") == true
							|| word.equals("G") == true
							|| word.equals("H") == true
							|| word.equals("I") == true
							|| word.equals("J") == true
							|| word.equals("K") == true
							|| word.equals("L") == true
							|| word.equals("M") == true
							|| word.equals("N") == true
							|| word.equals("O") == true
							|| word.equals("P") == true
							|| word.equals("Q") == true
							|| word.equals("R") == true
							|| word.equals("S") == true
							|| word.equals("T") == true
							|| word.equals("U") == true
							|| word.equals("V") == true
							|| word.equals("W") == true
							|| word.equals("X") == true
							|| word.equals("Y") == true
							|| word.equals("Z") == true) {
						continue;
					} else {
						for (int i = 0; i < word.length(); i++) {
							String hanzi = word.substring(i, i + 1);
							//System.out.println(hanzi);
							//System.out.println(pinyin);
							if (hanziMap.containsKey(hanzi) == false) {
								hanziMap.put(hanzi, new Cell(hanzi, ""));
								ws.addCell(new Label(0, new_r1++, hanzi));
							}
						}
					}
				}
			}
			System.out.println();
		}
		/*
		ArrayList<Cell> hanziList = new ArrayList<Cell>();
		Iterator<String> itr = hanziMap.keySet().iterator();
		while (itr.hasNext()) {
			String hanzi = itr.next();
			hanziList.add(hanziMap.get(hanzi));
		}
		
		Collections.sort(hanziList, new ComparatorCell());
		
		int new_r = 0;
		for (Cell cell : hanziList) {
			ws.addCell(new Label(0, new_r++, cell.hanzi));
		}
		*/
		workbook.close();
		wwb.write();
		wwb.close();
	}
}
