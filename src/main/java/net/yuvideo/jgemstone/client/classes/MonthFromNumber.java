package net.yuvideo.jgemstone.client.classes;


public class MonthFromNumber {

  public static String getMonthOfNumber(String number) {
    String month;
    switch (number) {
      case "1":
        month = "Januar";
        break;
      case "2":
        month = "Februar";
        break;
      case "3":
        month = "Mart";
        break;
      case "4":
        month = "April";
        break;
      case "5":
        month = "Maj";
        break;
      case "6":
        month = "Jun";
        break;
      case "7":
        month = "Jul";
        break;
      case "8":
        month = "Avgust";
        break;
      case "9":
        month = "Septembar";
        break;
      case "10":
        month = "Oktobar";
        break;
      case "11":
        month = "Novembar";
        break;
      case "12":
        month = "Decembar";
        break;
      default:
        month = "";

    }

    return month;

  }
}
