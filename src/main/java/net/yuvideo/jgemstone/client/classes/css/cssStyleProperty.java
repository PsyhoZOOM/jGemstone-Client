package net.yuvideo.jgemstone.client.classes.css;

public class cssStyleProperty {

  public  static String getBorder(double top, double right, double bottom, double left){
    return  String.format("-fx-border-style: solid; -fx-border-color: black; -fx-border-width: %f %f %f %f;",top, right, bottom, left);
  }
}
