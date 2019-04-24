package net.yuvideo.jgemstone.client.Controllers;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import net.yuvideo.jgemstone.client.classes.Users;

public class KorisnikStampaPodaci implements Initializable, Printable {

  public TableView<Users> tblUsers;
  public TableColumn<Users, String> cID;
  public TableColumn<Users, String> cIME;
  public TableColumn<Users, String> cMESTO;
  public Button bStampa;
  private URL location;
  private ResourceBundle resources;

  private ObservableList<Users> users;
  private PrinterJob pj;
  private int[] pageBreaks = null;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resources = resources;

    tblUsers.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    cID.setCellValueFactory(new PropertyValueFactory<>("id"));
    cIME.setCellValueFactory(new PropertyValueFactory<>("ime"));
    cMESTO.setCellValueFactory(new PropertyValueFactory<>("mesto"));
  }

  public void setUsers(ObservableList<Users> users) {

    this.users = users;
    tblUsers.setItems(users);
  }

  public void stampaj(ActionEvent actionEvent) {

    pj = PrinterJob.getPrinterJob();
    pj.setPrintable(this);
    boolean ok = pj.printDialog();
    if (ok) {
      try {
        pj.print();
      } catch (PrinterException e) {
        e.printStackTrace();
      }
    }


  }




  @Override
  public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
      throws PrinterException {
    pageBreaks = null;
    Graphics2D g = (Graphics2D) graphics;
    g.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

    ObservableList<Users> selUsers = tblUsers.getSelectionModel().getSelectedItems();

    Font font = null;
    Font fontBold = null;
    FontMetrics metrics;
    try {
      font = Font.createFont(Font.TRUETYPE_FONT,
          new File(ClassLoader.getSystemResource("font/roboto/Roboto-Regular.ttf").getFile()))
          .deriveFont(10f);
      fontBold = Font.createFont(Font.TRUETYPE_FONT,
          new File(ClassLoader.getSystemResource("font/roboto/Roboto-Black.ttf").getFile()))
          .deriveFont(10f);

    } catch (FontFormatException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    g.setFont(font);

    metrics = g.getFontMetrics();
    int lineHeight = metrics.getHeight();
    String[] ime = null;
    String[] id = null;
    String[] userID = null;
    String[] mesto = null;

    if (pageBreaks == null) {
      //initTextLines();
      ime = new String[selUsers.size() + 1];
      id = new String[selUsers.size() + 1];
      userID = new String[selUsers.size() + 1];
      mesto = new String[selUsers.size() + 1];

      id[0] = "ID";
      userID[0] = "USERID";
      ime[0] = "IME";
      mesto[0] = "MESTO";
      for (int i = 0; i < selUsers.size(); i++) {
        Users user = selUsers.get(i);
        id[i + 1] = String.valueOf(user.getId());
        userID[i + 1] = user.getJbroj();
        ime[i + 1] = user.getIme();
        mesto[i + 1] = user.getMestoUsluge();
      }
      int linesPerPage = (int) (pageFormat.getImageableHeight() / lineHeight);
      int numBreaks = (ime.length - 1) / linesPerPage;
      pageBreaks = new int[numBreaks];
      for (int b = 0; b < numBreaks; b++) {
        pageBreaks[b] = (b + 1) * linesPerPage;
      }
      }

    if (pageIndex > pageBreaks.length) {
      return NO_SUCH_PAGE;
    }

    /* User (0,0) is typically outside the imageable area, so we must
     * translate by the X and Y values in the PageFormat to avoid clipping
     * Since we are drawing text we
     */

    /* Draw each line that is on this page.
     * Increment 'y' position by lineHeight for each line.
     */
    int y = 0;
    int start = (pageIndex == 0) ? 0 : pageBreaks[pageIndex - 1];
    int end = (pageIndex == pageBreaks.length)
        ? ime.length : pageBreaks[pageIndex];
    for (int line = start; line < end; line++) {
      y += lineHeight;
      if (line == 0) {
        g.setFont(fontBold);
      } else {
        g.setFont(font);
      }

      g.drawString(id[line], 0, y);
      g.drawString(userID[line], 40, y);
      g.drawString(ime[line], 120, y);
      g.drawString(mesto[line], 300, y);
    }

    /* tell the caller that this page is part of the printed document */

    return PAGE_EXISTS;
  }

}
