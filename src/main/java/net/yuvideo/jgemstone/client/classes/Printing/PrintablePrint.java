package net.yuvideo.jgemstone.client.classes.Printing;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

public class PrintablePrint implements Printable {

  @Override
  public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
      throws PrinterException {
    if (pageIndex > 0) {
      return NO_SUCH_PAGE;
    }

//    javafx.print.Paper pap = javafx.print.Paper.A4;

    double margin = 0;
    //Paper paper  = new Paper();
//      paper.setImageableArea(0.25, 0.25, pap.getWidth(), pap.getHeight());
    //    pageFormat.getPaper().setImageableArea(0.25, 0.25, pap.getWidth(), pap.getHeight());

    // User (0,0) is typically outside the
    // imageable area, so we must translate
    // by the X and Y values in the PageFormat
    // to avoid clipping.

    //   pageFormat.getPaper().setImageableArea(margin, margin, pageFormat.getWidth()-margin * 2, pageFormat.getWidth()-margin * 2);

    Graphics2D g2d = (Graphics2D) graphics;
    g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

    // Now we perform our rendering
    g2d.drawString("Hello world!", 10, 10);
    System.out.println(pageFormat.getPaper().getWidth());
    System.out.println(pageFormat.getPaper().getImageableWidth());

    // tell the caller that this page is part
    // of the printed document
    return PAGE_EXISTS;
  }
}
