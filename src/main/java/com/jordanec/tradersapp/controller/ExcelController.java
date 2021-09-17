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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jordanec.tradersapp.model.ExcelData;

@RestController
public class ExcelController {

	@RequestMapping(value = "excel", method = RequestMethod.GET)
	public String main() { // 1
		return "excel";
	}

	@RequestMapping(value = "excel/read", method = RequestMethod.POST)
	public List<ExcelData> readExcel(@RequestParam("file") MultipartFile file, Model model)
			throws IOException {

		List<ExcelData> dataList = new ArrayList<>();
		
		/*
		* 파일 이름에서 확장자만 추출
		* apache library 중 common 에서 제공
		* */
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());

		/*
		* 엑셀 파일인지 확인 로직
		* */
		if (!extension.equals("xlsx") && !extension.equals("xls")) {
			throw new IOException("엑셀파일만 업로드 해주세요.");
		}


		XSSFWorkbook	workbook = new XSSFWorkbook(file.getInputStream());


		/*
		* Excel 파일 안에있는 정보를 추출
		* */
		XSSFSheet worksheet = workbook.getSheetAt(0);

		/*
		* Excel 데이터를 row 단위로 추출
		* i를 1부터 가져온 이유는 첫번째 row는 data가 아니고 명칭이기에
		* 0번째가 아닌 첫번째 부터 가져온다.
		* */
		for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {

			// row 데이터 추출
			XSSFRow row = worksheet.getRow(i);

			// excel object 생성하여 list 로 저장.
			ExcelData data = new ExcelData();

			data.setNum((int) row.getCell(0).getNumericCellValue());
			data.setName(row.getCell(1).getStringCellValue());
			data.setEmail(row.getCell(2).getStringCellValue());

			dataList.add(data);
		}

		return dataList;

	}
}