package net.yuvideo.jgemstone.client.classes;


public class BytesTo_KB_MB_GB_TB {

  public static String getFormatedString(Long bytes) {
    String[] fileSizeUnits = {"bits", "Kbit", "Mbit", "Gbit", "Tbit", "Pbit", "Ebit", "Zbit",
        "Ybit"};

    String sizeToReturn = "";
    int index;
    for (index = 0; index < fileSizeUnits.length; index++) {
      if (bytes < 1024) {
        break;
      }
      bytes = bytes / 1024;
    }
    sizeToReturn = String.valueOf(bytes) + " " + fileSizeUnits[index];
    return sizeToReturn;

  }

}
