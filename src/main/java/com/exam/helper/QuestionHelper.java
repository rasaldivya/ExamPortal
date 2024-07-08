package com.exam.helper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.exam.entity.Question;

public class QuestionHelper {
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

    public static List<Question> convertExcelToListOfProduct(InputStream is,String trade) {
        List<Question> list = new ArrayList<>();

        try {


            XSSFWorkbook workbook = new XSSFWorkbook(is);

            XSSFSheet sheet = workbook.getSheet("Sheet1");

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
                Question q=new Question();

//                System.out.println("Row length"+row.getLastCellNum());
                
                for(int i=0;i<row.getLastCellNum();i++) {
                    if(end)
                        break;
                	String cellValue="";
                	if(row.getCell(i)!=null)
                		cellValue=row.getCell(i).toString();
                	
                	System.out.println(cellValue);
                	switch (i) {
                                case 0:
                                    if (cellValue.isEmpty())
                                    {
                                        end=true;
                                        break;
                                    }
            //                    	System.out.println("Question Content : "+cellValue);
                                    q.setContent(cellValue);
                                    break;
                                case 1:
            //                    	System.out.println("Option 1 : "+cellValue);
                                    q.setOption1(cellValue);
                                    break;
                                case 2:
            //                    	System.out.println("Option 2 : "+cellValue);
                                    q.setOption2(cellValue);
                                    break;
                                case 3:
            //                    	System.out.println("Option 3 : "+cellValue);
                                    q.setOption3(cellValue);
                                    break;
                                case 4:
            //                    	System.out.println("Option 4 : "+cellValue);
                                    q.setOption4(cellValue);
                                    break;
                                case 5:
            //                    	System.out.println("Topic : "+cellValue);
                                    q.setTopic(cellValue);
                                    break;
                                case 6:
            //                    	System.out.println("Type : "+cellValue);
                                    q.setType(cellValue);
                                    break;
                                case 7:
            //                    	System.out.println("Answer : "+cellValue);
                                    q.setAnswer(cellValue);
                                    break;
                                case 8:
            //                    	System.out.println("Answer : "+cellValue);
                                    q.setImage(cellValue);
                                    break;
            //
                                default:
                                    break;
                }
                	
                }
                q.setTrade(trade);
                if(q.getContent()!=null)
                    list.add(q);


            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    }


}
