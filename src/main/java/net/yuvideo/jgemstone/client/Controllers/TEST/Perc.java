package net.yuvideo.jgemstone.client.Controllers.TEST;

public class Perc {

  public static void main(String[] arg) {
    double perc = 20;
    double cena = 2000;

    double pdv = cena * perc / 100;
    double cenapdv = pdv + cena;
    System.out.println(pdv);
    System.out.println((cenapdv * perc) / (100 + perc));


  }
}
