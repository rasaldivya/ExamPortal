package com.exam.helper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.exam.entity.User;

@Service
public class TraineeHelper {
	
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	private String defaultPassword="IndArmy@2024";

	 //check that file is of excel type or not
    public static boolean checkExcelFormat(MultipartFile file) {

        String contentType = file.getContentType();

        if (contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
            return true;
        } else {
            return false;
        }

    }


    //convert excel to list of products

    public List<User> convertExcelToListOfProduct(InputStream is) {
        List<User> list = new ArrayList<>();

        try {


            XSSFWorkbook workbook = new XSSFWorkbook(is);

            XSSFSheet sheet = workbook.getSheet("data");

            int rowNumber = 0;
            Iterator<Row> iterator = sheet.iterator();
            boolean end=false;
            while (iterator.hasNext()) {
                Row row = iterator.next();
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                if (end){
                    break;
                }

                Iterator<Cell> cells = row.iterator();
                int cid = 0;

                User t=new User();
                while (cells.hasNext()) {
                    if(end)
                        break;
                    Cell cell = cells.next();

                    switch (cid) {
                        case 0:
                            if (cell.getStringCellValue().isEmpty())
                            {
                                end=true;
                                break;
                            }
                        	String armyno=cell.getStringCellValue();
                        	System.out.println("Army no value : "+armyno);
                            t.setArmyNo(cell.getStringCellValue());
                            break;
                        case 1:
                        	System.out.println("Rank value : "+cell.getStringCellValue());
                            t.setArmyRank(cell.getStringCellValue());
                            break;
                        case 2:
                        	System.out.println("firstName value : "+cell.getStringCellValue());
                            t.setFirstName(cell.getStringCellValue());
                            break;
                        case 3:
                        	System.out.println("lastName value : "+cell.getStringCellValue());
                            t.setLastName(cell.getStringCellValue());
                            break;    
                        case 4:
                        	System.out.println("Unit value : "+cell.getStringCellValue());
                            t.setUnit(cell.getStringCellValue());
                            break;
                        case 5:
                        	System.out.println("Trade value : "+cell.getStringCellValue());
                        	t.setTrade(cell.getStringCellValue());
                            break;
                        case 6:
                            System.out.println("Squad value : "+cell.getStringCellValue());
                            t.setSquad(cell.getStringCellValue());
                            break;
//                        case 5:
//                        	System.out.println("Phone no : "+Long.toString((long)cell.getNumericCellValue()) );
//                        
//                        	t.setPhone(Long.toString((long)cell.getNumericCellValue()));
//                            break;    
                            
                        default:
                            break;
                    }
                    cid++;

                }
                t.setUserName(t.getArmyNo());
                t.setPassword(bCryptPasswordEncoder.encode(defaultPassword));
                t.setRole("NORMAL");
                t.setProfile("default.png");
                System.out.println("Trainee present : "+t.getArmyNo());
                if(t.getArmyNo()!=null)
                 list.add(t);


            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    }




}
