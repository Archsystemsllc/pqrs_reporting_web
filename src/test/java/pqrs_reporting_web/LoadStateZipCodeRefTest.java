package pqrs_reporting_web;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.archsystemsinc.pqrs.PersistenceConfig;
import com.archsystemsinc.pqrs.model.StateZipCodeRef;
import com.archsystemsinc.pqrs.repository.StateZipCodeRefRepository;
import com.archsystemsinc.pqrs.utility.ReadExcel;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistenceConfig.class })
//@Transactional
//@TransactionConfiguration
public class LoadStateZipCodeRefTest {

    @Autowired
    StateZipCodeRefRepository stateZipCodeRefRepository;

    @Before
    public void init() {
    	
    }
    
    //@Test
    public void storeStateJSONObject() {

        String excelFilePath = "C:\\Users\\MurugarajKandaswam\\git\\pqrs_reporting_web\\src\\test\\resources\\ZipCode_PA.xlsx";
        try {

            FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet firstSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = firstSheet.iterator();

            while (iterator.hasNext()) {
                Row nextRow = iterator.next();

                String stateCode = ReadExcel.getCellValue(nextRow.getCell(4));
                if (stateCode != null && stateCode.trim().equalsIgnoreCase("State Code")) continue;
                String stateName = ReadExcel.getCellValue(nextRow.getCell(3));
                String county = ReadExcel.getCellValue(nextRow.getCell(2));
                String city = ReadExcel.getCellValue(nextRow.getCell(1));
                String zipCode = ReadExcel.prefixZeroForZipCode(ReadExcel.getCellValue(nextRow.getCell(0)));
                System.out.println("Zip Code	City	County	State Name	State Code:"+zipCode+city+ county+stateName+stateCode);

                StateZipCodeRef stateZipCodeRef = new StateZipCodeRef(stateName, stateCode, zipCode, city, county);
                stateZipCodeRefRepository.save(stateZipCodeRef);
            }
            
        }catch(Exception e){
            e.printStackTrace();
            assert(false);
        }
        
    }
    
	
}
