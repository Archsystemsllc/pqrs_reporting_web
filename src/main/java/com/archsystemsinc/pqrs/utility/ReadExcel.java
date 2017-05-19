package com.archsystemsinc.pqrs.utility;

import org.apache.poi.ss.usermodel.Cell;

public class ReadExcel {
	
    public static String getCellValue(Cell cell) {

        String value = null;

        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                value = cell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                cell.setCellType(Cell.CELL_TYPE_STRING);
                value = cell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                value = String.valueOf(cell.getBooleanCellValue());
                break;

        }

        return value;

    }
	
    public static String prefixZeroForZipCode(String zipCode_){

        String prefixZipCode = "00000";

        if (zipCode_ != null) {
            switch (zipCode_.length()) {
                case 0: prefixZipCode = "00000"+zipCode_; break;
                case 1: prefixZipCode = "0000"+zipCode_; break;
                case 2: prefixZipCode = "000"+zipCode_; break;
                case 3: prefixZipCode = "00"+zipCode_; break;
                case 4: prefixZipCode = "0"+zipCode_; break;
                case 5: prefixZipCode = zipCode_; break;
                default: prefixZipCode = zipCode_.substring(0, 5);
            }
        }

        return prefixZipCode;

    }
    

}
