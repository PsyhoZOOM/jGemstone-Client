package net.yuvideo.jgemstone.client.classes;

import static java.lang.String.format;

public class BytesTo_KB_MB_GB_TB {

  public static String getFormatedString(int transfered) {
    String unit = "bps";
    int bytes = 0;
    if (transfered < 1024) {
      unit = "b";
      bytes = transfered;
    }
    if (transfered > 1024 && transfered < 1024 * 1000) {
      unit = "Kb";
      bytes = transfered / 1000;
    }
    if (transfered > 1024 * 1000 && transfered < 1024 * 1000000) {
      unit = "Mb";
      bytes = transfered / 1000000;
    }
    if (transfered > 1024 * 1000000 && transfered < 1024 * 1000000000) {
      unit = "Gb";
      bytes = transfered / 1000000000;
    }
    if (transfered > 1024 * 1000000000) {
      unit = "Tb";
      bytes = 1;
    }
    return format("%d%s", bytes, unit);
  }

}
