package com.karolis.tool;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ExcelWorker {

    public ExcelWorker() {
    }

    private DataFormatter dataFormatter = new DataFormatter();

    public void findDuplicates(String name, String name1) throws IOException {
        Workbook firstWorkbook = createWorkbook(name);
        Workbook secondWorkbook = createWorkbook(name1);

        Sheet firstSheet = firstWorkbook.getSheetAt(0);
        Sheet secondSheet = secondWorkbook.getSheetAt(0);

        List<String> output = readFromExcel(firstSheet).stream()
                .filter(readFromExcel(secondSheet)::contains)
                .collect(Collectors.toList());

        System.out.println("Output size: " + output.size());

        firstWorkbook.close();
        secondWorkbook.close();

        writeToExcel(output);
    }

    public void removeDuplicates(String name, String name1) throws IOException {
        Workbook firstWorkbook = createWorkbook(name);
        Workbook secondWorkbook = createWorkbook(name1);

        Sheet firstSheet = firstWorkbook.getSheetAt(0);
        Sheet secondSheet = secondWorkbook.getSheetAt(0);

        List<String> output = readFromExcel(firstSheet);
        output.removeAll(readFromExcel(secondSheet));

        System.out.println("Output size: " + output.size());

        firstWorkbook.close();
        secondWorkbook.close();

        writeToExcel(output);
    }

    private void writeToExcel(List<String> output) throws IOException {
        Workbook book = new XSSFWorkbook();

        Sheet sheet = book.createSheet("Companies");
        int i = 0;
        for (String s : output) {
            Row row = sheet.createRow(i);
            row.createCell(0).setCellValue(s);
            i++;
        }

        // Write output to file
        FileOutputStream out = new FileOutputStream("output" + System.currentTimeMillis() + ".xlsx");
        book.write(out);
        out.close();

        book.close();
    }

    private List<String> readFromExcel(Sheet sheet) {
        List<String> companies = new ArrayList<>();
        sheet.forEach(row ->
                row.forEach(cell -> companies.add(dataFormatter.formatCellValue(cell))));
        return companies;
    }

    private Workbook createWorkbook(String name) throws IOException {
        return WorkbookFactory.create(
                new File(FileSystems.getDefault().getPath(name).toAbsolutePath().toString())
        );
    }
}
