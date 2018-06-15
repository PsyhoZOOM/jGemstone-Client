package net.yuvideo.jgemstone.client.classes;


public class BytesTo_KB_MB_GB_TB {

  public static String getFormatedString(Long bytes) {
    String[] fileSizeUnits = {"bytes", "KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB"};

    String sizeToReturn = "";
    int index;
    for (index = 0; index < fileSizeUnits.length; index++) {
      if (bytes < 1024) {
        break;
      }
      bytes = bytes / 1024;
    }
    System.out.println("Systematic file size: " + bytes + " " + fileSizeUnits[index]);
    sizeToReturn = String.valueOf(bytes) + " " + fileSizeUnits[index];
    return sizeToReturn;

  }

}
