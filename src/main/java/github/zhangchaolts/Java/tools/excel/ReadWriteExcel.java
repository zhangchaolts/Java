package github.zhangchaolts.Java.tools.excel;

import java.io.*;

import jxl.*;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ReadWriteExcel {

	public static void main(String[] args) throws Exception {

		File excelFile = new File("data/tools_excel_ReadWriteExcel_input.xls");
		Workbook workbook = Workbook.getWorkbook(excelFile);
		Sheet sheet = workbook.getSheet(0);
		
		OutputStream os = new FileOutputStream("data/tools_excel_ReadWriteExcel_output.xls");
		WritableWorkbook wwb = Workbook.createWorkbook(os);
		WritableSheet ws = wwb.createSheet("Sheet_1", 0);

		int allCol = sheet.getColumns();
		int allRow = sheet.getRows();

		System.out.println("allCol:" + allCol);
		System.out.println("allRow:" + allRow);

		for (int r = 0; r < allRow; r++) {
			for (int c = 0; c < allCol; c++) {
				if (sheet.getCell(c, r).getContents() != "") {
					System.out.print(sheet.getCell(c, r).getContents() + ",");
					ws.addCell(new Label(r, c, sheet.getCell(c, r).getContents()));
				}
			}
			System.out.println();
		}

		workbook.close();
		wwb.write();
		wwb.close();
	}
}
