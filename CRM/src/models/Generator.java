package models;


import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author user1
 */
public class Generator {
     File file = new File("invoice.pdf");
     Document document = new Document();
     Font black = FontFactory.getFont(FontFactory.TIMES_BOLD, 10, Font.NORMAL, BaseColor.BLACK);
     Font soc = FontFactory.getFont(FontFactory.TIMES_BOLD, 10, Font.NORMAL, BaseColor.GRAY);
     Font big = FontFactory.getFont(FontFactory.TIMES_BOLD, 20, Font.BOLD, BaseColor.BLACK);
     Font medium = FontFactory.getFont(FontFactory.TIMES_BOLD, 15, Font.BOLD, BaseColor.GRAY);
     PdfWriter writer = null;
     public Generator(){
         this.createFile();
         try{
             writer = PdfWriter.getInstance(document, new FileOutputStream(file.getAbsolutePath()));
             document.open();
         }catch(Exception e){
             
         }
     }
    public void header(String website,String address, String telephone, String email, String facebook, String twitter, 
            String instagram){
        try{
            //create header
            Image image = Image.getInstance(getClass().getClassLoader().getResource("logo.png").toExternalForm());
            image.setAlignment(1);
            image.scaleAbsolute(80, 43);
            document.add(image);
            Paragraph addr = new Paragraph(address,black);
            addr.setAlignment(1);
            Paragraph tel = new Paragraph(telephone,black);
            tel.setAlignment(1);
            Paragraph emai = new Paragraph(email,black);
            emai.setAlignment(1);
            Paragraph web = new Paragraph("visit our Website: "+website,soc);
            web.setAlignment(1);
            Paragraph social = new Paragraph(facebook+"  "+twitter+"  "+instagram,soc);
            social.setAlignment(1);
            document.add(addr);
            document.add(tel);
            document.add(emai);
            document.add(web);
            document.add(social);
            PdfPTable ruleTable = new PdfPTable(1);
            ruleTable.setSpacingAfter(20f);
            ruleTable.setSpacingBefore(20f);
            ruleTable.setWidthPercentage(100);
            PdfPCell rule = new PdfPCell();
            rule.setBorder(Rectangle.BOTTOM);
            rule.setBorderWidth(2);
            rule.setUseBorderPadding(true);
            ruleTable.addCell(rule);
            document.add(ruleTable);
            
            Paragraph subtitle = new Paragraph("Invoice",big);
            subtitle.setAlignment(1);
            document.add(subtitle);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void body(String shippingName, String street,String city,String state,String country,String postal,
            int number){
        try{
            
            PdfPTable table = new PdfPTable(2);
            table.getDefaultCell().setBorder(0);
            table.setWidthPercentage(100);
            table.setSpacingAfter(20f);
            table.setSpacingBefore(20f);
            //set columns width
            float[]columnWidths = {1f,1f};
            table.setWidths(columnWidths);
            PdfPTable list1 = new PdfPTable(1);
            PdfPCell list11 = new PdfPCell(new Paragraph("Client Details:",medium));
            list11.setBorder(0);
            list1.addCell(list11);
            PdfPCell list12 = new PdfPCell(new Paragraph(shippingName,black));
            list12.setBorder(0);
            list1.addCell(list12);
            PdfPCell list13 = new PdfPCell(new Paragraph(street,black));
            list13.setBorder(0);
            list1.addCell(list13);
            PdfPCell list14 = new PdfPCell(new Paragraph(city+","+state,black));
            list14.setBorder(0);
            list1.addCell(list14);
            PdfPCell list15 = new PdfPCell(new Paragraph(country,black));
            list15.setBorder(0);
            list1.addCell(list15);
            PdfPCell list16 = new PdfPCell(new Paragraph(postal,black));
            list16.setBorder(0);
            list1.addCell(list16);
            table.addCell(list1);
            
            PdfPTable list2 = new PdfPTable(1);
            PdfPCell list24 = new PdfPCell(new Paragraph("Invoice No: "+number ,black));
            list24.setBorder(0);
            list24.setHorizontalAlignment(2);
            list2.addCell(list24);
            PdfPCell list21 = new PdfPCell(new Paragraph("Date: "+new java.util.Date() ,black));
            list21.setBorder(0);
            list21.setHorizontalAlignment(2);
            list2.addCell(list21);
            PdfPCell list22 = new PdfPCell(new Paragraph("Subscription status: Active",black));
            list22.setBorder(0);
            list22.setHorizontalAlignment(2);
            list2.addCell(list22);
            PdfPCell list23 = new PdfPCell(new Paragraph("Payment Status: Paid",black));
            list23.setBorder(0);
            list23.setHorizontalAlignment(2);
            list2.addCell(list23);
            PdfPCell list25 = new PdfPCell(new Paragraph("Payment Method: Debit/Credit",black));
            list25.setBorder(0);
            list25.setHorizontalAlignment(2);
            list2.addCell(list25);
            table.addCell(list2);
            
            document.add(table);
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void footer(String plan, double price, double discount, double shippingCost, double total){
        try{
            
            PdfPTable table = new PdfPTable(1);
            table.getDefaultCell().setBorder(1);
            table.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);
            table.setWidthPercentage(100);
            table.setSpacingAfter(20f);
            table.setSpacingBefore(20f);
            
            PdfPTable header = new PdfPTable(4);
            header.getDefaultCell().setBorder(0);
            PdfPCell header1 = new PdfPCell(new Paragraph("Subscrption Plan" ,black));
            header1.setHorizontalAlignment(2);
            header1.setBorder(0);
            header1.setPadding(5);
            header.addCell(header1);
            PdfPCell header2 = new PdfPCell(new Paragraph("Price($)" ,black));
            header2.setHorizontalAlignment(2);
            header2.setBorder(0);
            header2.setPadding(5);
            header.addCell(header2);
            PdfPCell header3 = new PdfPCell(new Paragraph("Discount($)" ,black));
            header3.setHorizontalAlignment(2);
            header3.setBorder(0);
            header3.setPadding(5);
            header.addCell(header3);
            PdfPCell header4 = new PdfPCell(new Paragraph("Shipping Cost($)" ,black));
            header4.setHorizontalAlignment(2);
            header4.setBorder(0);
            header4.setPadding(5);
            header.addCell(header4);
            table.addCell(header);
            
            
            PdfPTable middle = new PdfPTable(4);
            middle.getDefaultCell().setBorder(0);
            PdfPCell middle1 = new PdfPCell(new Paragraph(plan ,black));
            middle1.setHorizontalAlignment(2);
            middle1.setBorder(0);
            middle1.setPadding(5);
            middle.addCell(middle1);
            PdfPCell middle2 = new PdfPCell(new Paragraph(String.valueOf(price) ,black));
            middle2.setHorizontalAlignment(2);
            middle2.setBorder(0);
            middle2.setPadding(5);
            middle.addCell(middle2);
            PdfPCell middle3 = new PdfPCell(new Paragraph(String.valueOf(discount) ,black));
            middle3.setHorizontalAlignment(2);
            middle3.setBorder(0);
            middle3.setPadding(5);
            middle.addCell(middle3);
            PdfPCell middle4 = new PdfPCell(new Paragraph(String.valueOf(shippingCost) ,black));
            middle4.setHorizontalAlignment(2);
            middle4.setBorder(0);
            middle4.setPadding(5);
            middle.addCell(middle4);
            table.addCell(middle);
            
            
            PdfPTable bottom = new PdfPTable(4);
            PdfPCell bottom1 = new PdfPCell(new Paragraph("Total Cost" ,black));
            bottom1.setHorizontalAlignment(2);
            bottom1.setBorder(0);
            bottom1.setPadding(5);
            bottom1.setColspan(3);
            bottom.addCell(bottom1);
            PdfPCell bottom2 = new PdfPCell(new Paragraph(String.valueOf(total) ,black));
            bottom2.setHorizontalAlignment(2);
            bottom2.setBorder(0);
            bottom2.setPadding(5);
            bottom2.setColspan(1);
            bottom.addCell(bottom2);
            table.addCell(bottom);
            
            document.add(table);
            Paragraph last = new Paragraph("Motor Crate Gift Box",soc);
            last.setAlignment(1);
            document.add(last);
            
            
            PdfPTable sign = new PdfPTable(2);
            sign.setWidthPercentage(40);
            sign.getDefaultCell().setBorder(0);
            sign.setSpacingBefore(80f);
            PdfPCell sign1 = new PdfPCell(new Paragraph("Signature" ,black));
            sign1.setHorizontalAlignment(2);
            sign1.setBorder(0);
            sign1.setBorderWidthTop(1);
            sign1.setPadding(5);
            sign1.setColspan(2);
            sign.addCell(sign1);
            document.add(sign);
            
            
            PdfPTable end = new PdfPTable(2);
            end.setWidthPercentage(100);
            end.getDefaultCell().setBorder(0);
            end.setSpacingBefore(80f);
            PdfPCell end1 = new PdfPCell(new Paragraph("this is an automated invoice if you find any error"
                    + " please let us know immediately. thanks for your patronage" ,black));
            end1.setHorizontalAlignment(1);
            end1.setBorder(0);
            end1.setBorderWidthTop(1);
            end1.setPadding(5);
            end1.setColspan(2);
            end.addCell(end1);
            document.add(end);
            
            //start new page
            document.newPage();
        }catch(Exception e){
            
        }
    }
    public String closeFile(){
        try{
            document.close();
            writer.close();
        }catch(Exception e){
           e.printStackTrace();
        }
        return file.getAbsolutePath().toString();
    }
    
    private void createFile(){
        try{
            if(!file.exists()){
                file.createNewFile();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
}
