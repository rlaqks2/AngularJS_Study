package com.jordanec.tradersapp.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.jordanec.tradersapp.model.ExcelData;


@Controller
public class ExcelController {

	@GetMapping("/excel")
	public String main() { // 1
		return "excel";
	}


	@PostMapping("/excel/read")
	public String readExcel(@RequestParam("file") MultipartFile file, Model model)
			throws IOException { // 2

		List<ExcelData> dataList = new ArrayList<>();

		String extension = FilenameUtils.getExtension(file.getOriginalFilename()); // 3

		if (!extension.equals("xlsx") && !extension.equals("xls")) {
			throw new IOException("엑셀파일만 업로드 해주세요.");
		}


		XSSFWorkbook	workbook = new XSSFWorkbook(file.getInputStream());


		XSSFSheet worksheet = workbook.getSheetAt(0);

		for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) { // 4

			XSSFRow row = worksheet.getRow(i);

			ExcelData data = new ExcelData();

			data.setNum((int) row.getCell(0).getNumericCellValue());
			data.setName(row.getCell(1).getStringCellValue());
			data.setEmail(row.getCell(2).getStringCellValue());

			dataList.add(data);
		}

		model.addAttribute("datas", dataList); // 5

		return "excelList";

	}
}