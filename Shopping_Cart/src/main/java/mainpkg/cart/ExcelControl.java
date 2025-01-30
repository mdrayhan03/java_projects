package mainpkg.cart;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

public class ExcelControl {
    XSSFWorkbook workbook ;
    Sheet sheet ;
    String file = "src\\main\\resources\\Database.xlsx" ;

    public ExcelControl() throws IOException {
        this.create_file();
    }

    private void create_file() throws IOException {
        File f = new File(this.file) ;
        if (f.exists()) {
            FileInputStream file = new FileInputStream(f) ;
            workbook = new XSSFWorkbook(file) ;
            sheet = workbook.getSheetAt(0) ;
        }
        else {
            workbook = new XSSFWorkbook() ;
            sheet = workbook.createSheet("Item Database") ;

            Row header_row = sheet.createRow(0) ;
            String[] header_name = {"ID", "Name", "Description", "Category", "Unit", "Color", "Path", "Price", "Total", "Status"} ;

            for (int i=0 ; i < header_name.length ; i++) {
                header_row.createCell(i).setCellValue(header_name[i]);
            }
            this.save_file();
        }
    }

    public void insert_data(String name, String description, String catagory, String unit, String color, String path, int price, int total) {
        int lastrow = sheet.getLastRowNum();
        Row row = sheet.createRow(lastrow+1) ;

        row.createCell(0).setCellValue(lastrow+1);
        row.createCell(1).setCellValue(name);
        row.createCell(2).setCellValue(description);
        row.createCell(3).setCellValue(catagory);
        row.createCell(4).setCellValue(unit);
        row.createCell(5).setCellValue(color);
        row.createCell(6).setCellValue(path);
        row.createCell(7).setCellValue(price);
        row.createCell(8).setCellValue(total);
        row.createCell(9).setCellValue(1);

        this.save_file();
    }

    public ObservableList<Item> select_data() throws IOException {
        File f = new File(this.file) ;
        FileInputStream file = new FileInputStream(f) ;
        workbook = new XSSFWorkbook(file) ;
        sheet = workbook.getSheetAt(0) ;
        ObservableList<Item> itemObservableList = FXCollections.observableArrayList() ;
        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue ;
            }
            if ((int) row.getCell(9).getNumericCellValue() == 1) {
                System.out.println("Status: "+ Integer.toString((int) row.getCell(9).getNumericCellValue()));
                itemObservableList.add(new Item((int) row.getCell(0).getNumericCellValue(), row.getCell(1).getStringCellValue(), row.getCell(2).getStringCellValue(), row.getCell(3).getStringCellValue(), row.getCell(4).getStringCellValue(), row.getCell(5).getStringCellValue(), row.getCell(6).getStringCellValue(), (int) row.getCell(7).getNumericCellValue(), (int) row.getCell(8).getNumericCellValue())) ;
            }
        }
        return itemObservableList ;
    }

    public void update_data(int id, int value) {
        Row row = sheet.getRow(id) ;
        row.getCell(8).setCellValue(value) ;
        this.save_file() ;
    }

    public void delete_data(int id) throws IOException {
        Row row = sheet.getRow(id) ;
        row.getCell(9).setCellValue(0) ;
        this.save_file() ;
        workbook.close();
        new ExcelControl();
    }

    private void save_file() {
        try{
            workbook.write(new FileOutputStream(new File(this.file)));
//            workbook.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
