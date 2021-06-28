package uor.fot.canteenMS.helpers;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import uor.fot.canteenMS.entities.Orders;
import uor.fot.canteenMS.entities.Product;
import uor.fot.canteenMS.services.ProductService;
import uor.fot.canteenMS.services.SendEmailService;
import uor.fot.canteenMS.services.UserService;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.List;
import java.io.IOException;
import java.util.ArrayList;

@Controller
public class InvoicePDFExporter {
    private ArrayList<Orders> orders;
    private String date;
    private float total =0;
    private String reg;

    @Autowired
    private ProductService product;
    @Autowired
    private SendEmailService sendEmailService;
    @Autowired
    private UserService userService;

    //mail veriables
    String to = "";
    String title = "";
    String body = "";


    public void  fillValues(ArrayList<Orders> orders, String date,String reg)
    {
        this.orders = orders;
        this.date = date;
        this.reg = reg;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.white);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.black);

        cell.setPhrase(new Phrase("Item", font));

        table.addCell(cell);

        cell.setPhrase(new Phrase("Quantity", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Total Price", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Ordered At", font));
        table.addCell(cell);

    }

    private void writeTableData(PdfPTable table) {
        for (Orders order : orders) {
            table.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(product.getItemName(order.getItem_id()));
            table.addCell(order.getQuantity().toString());
            table.addCell(order.getUnit_price()+" LKR/-");
            table.addCell(order.getOrdered_at().toString());
        }
    }
    public String export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        Font font_date = FontFactory.getFont(FontFactory.HELVETICA);
        Font font_header = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        Font font_terms = FontFactory.getFont(FontFactory.HELVETICA);
        font.setSize(24);
        font_date.setSize(10);
        font_header.setSize(12);
        font_terms.setSize(12);
        font_terms.setColor(Color.RED);
        font.setColor(Color.BLACK);

        //Adding image
        Image jpg = Image.getInstance("src/main/resources/static/assets/img/logo.png");
        jpg.setAlignment(Element.ALIGN_LEFT);
        jpg.scaleAbsolute(60,60);
        document.add(jpg);

        //Add address
        Paragraph p_address = new Paragraph("FOT Canteen\nFaculty of Technology\nUniversity of Ruhuna\n+94 77 163 7551", font_date);

        Paragraph p = new Paragraph("Invoice", font);
        Paragraph p_date = new Paragraph("Document Printed : "+date, font_date);
        Paragraph p_reg = new Paragraph("Items ordered customer : "+reg, font_date);


        //Alignments
        p_date.setAlignment(Paragraph.ALIGN_RIGHT);
        p.setAlignment(Paragraph.ALIGN_CENTER);
        p_address.setAlignment(Paragraph.ALIGN_LEFT);
        p_reg.setAlignment(Paragraph.ALIGN_LEFT);

        //Add items
        document.add(p_address);
        document.add(p_date);
        document.add(p);
        document.add(p_reg);

        //creating table
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {3.5f, 1.5f, 3.0f, 3.0f});
        table.setSpacingBefore(10);


        //calculating sum
        for (Orders ordered : orders) {

            total += ordered.getUnit_price();
        }
        //drawing table
        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        //drawing grand total
        Paragraph p_total = new Paragraph("\nGrand Total : "+total+" LKR/-", font_date);
        p_total.setAlignment(Paragraph.ALIGN_RIGHT);
        document.add(p_total);

        //drawing terms and conditions
        Paragraph p_terms = new Paragraph("\nTerms and Conditions", font_terms);
        p_total.setAlignment(Paragraph.ALIGN_LEFT);
        document.add(p_terms);

        Paragraph p_para = new Paragraph("\n*The above items can be paid via cash only\n*The payment once made can not be refunded", font_date);
        p_total.setAlignment(Paragraph.ALIGN_LEFT);
        document.add(p_para);

        document.close();

        //Email Section
        to = userService.getEmail(reg);
        title = "Regarding your Invoice";
        body = "Dear "+reg+",\n\nIn bellow you can see your order details and there prices.\n\n";

        for (Orders ordered : orders) {
            body += product.getItemName(ordered.getItem_id())+" - "+ordered.getUnit_price()+"LKR/-\n";
        }

        body +="\nGrand total : "+total+"LKR/-\n";
        body +="\nThank you for your attention.\n";
        body +="\nFOT Canteen\nFaculty of Technology\nUniversity of Ruhuna\nGam Udawa\nKamburupitiya\nMatara";
        sendEmailService.sendEmial(to,body,title);

        return "redirect:/dashboard?paybills_done";
    }
}
