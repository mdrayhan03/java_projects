package mainpkg.cart;


import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Locale;

public class PDFControl {
    PdfWriter writer ;
    PdfDocument pdf  ;
    Document document ;
    String name, pN, email ;
    ObservableList<CartItem> list ;

    public PDFControl(String name, String pN, String email, ObservableList<CartItem> list) throws FileNotFoundException {
        this.name = name;
        this.pN = pN;
        this.email = email;
        this.list = list;
        String filename = this.name.substring(this.name.length()-2) + this.pN.substring(this.pN.length()-2) + this.email.substring(this.email.length()-2) ;
        this.writer = new PdfWriter("src\\main\\resources\\pdf\\"+filename+".pdf");
        this.pdf = new PdfDocument(this.writer) ;
        this.document = new Document(this.pdf) ;
        this.createPdf() ;
    }

    private void createPdf() {
        Text text = new Text("Money Receipt").setFontSize(22).setBold();
        Paragraph paragraph = new Paragraph().add(text).setTextAlignment(TextAlignment.CENTER) ;
        document.add(paragraph) ;

        document.add(new Paragraph("\n\n\n")) ;

        Table infotable = new Table(4) ;
        infotable.setWidth(UnitValue.createPercentValue(100)) ;
        infotable.addCell(new Cell().add(new Paragraph("Name")).setBorder(Border.NO_BORDER));
        infotable.addCell(new Cell().add(new Paragraph(this.name)).setBorder(Border.NO_BORDER));
        infotable.addCell(new Cell().add(new Paragraph(LocalDate.now().toString()).setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER));
        infotable.addCell(new Cell().add(new Paragraph("Date").setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER));

        infotable.addCell(new Cell().add(new Paragraph("Phone No")).setBorder(Border.NO_BORDER));
        infotable.addCell(new Cell().add(new Paragraph(this.pN)).setBorder(Border.NO_BORDER));
        infotable.addCell(new Cell().add(new Paragraph(this.email).setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER));
        infotable.addCell(new Cell().add(new Paragraph("Email").setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER));

        document.add(infotable) ;

        document.add(new Paragraph("\n\n\n")) ;

        Table table = new Table(4);
        table.setWidth(UnitValue.createPercentValue(100)) ;

        // Add table header
        table.addCell(new Cell().add(new Paragraph(new Text("ID").setBold())).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph(new Text("Name").setBold())).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph(new Text("Price Per Unit").setBold())).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph(new Text("Price").setBold())).setBorder(Border.NO_BORDER));
        int total = 0 ;
        for (CartItem cart : this.list) {
            table.addCell(new Cell().add(new Paragraph(Integer.toString(cart.getItem().getId()))));
            table.addCell(new Cell().add(new Paragraph(cart.getItem().getName())));
            table.addCell(new Cell().add(new Paragraph(Integer.toString(cart.getItem().getPrice()))));
            table.addCell(new Cell().add(new Paragraph(Integer.toString(cart.getTotal()))));
            total += cart.getTotal() ;
        }

        table.addCell(new Cell().add(new Paragraph()).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph()).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph("Total")).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph(Integer.toString(total))).setBorder(Border.NO_BORDER));

        // Add the table to the document
        document.add(table);

        document.add(new Paragraph("\n\n\n")) ;

        document.add(new Paragraph("Thank you. Come again. Good Luck to you.")) ;


        document.close();
    }
}

