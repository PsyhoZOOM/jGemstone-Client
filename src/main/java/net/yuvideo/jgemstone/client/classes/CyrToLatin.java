package net.yuvideo.jgemstone.client.classes;

/**
 * Created by PsyhoZOOM@gmail.com on 9/29/17.
 */
public class CyrToLatin {
    public static String CirilicaToLatinica(String string) {
        //  Љ  Њ  Е  Р  Т  З  У  И  О  П  Ш  Ђ  Ж  А  С  Д  Ф  Г  Х  Ј  К  Л  Ч  Ћ  Ж  Џ  Ц  В  Б  Н  М
        //  LJ NJ E  R  T  Y  U  I  O  P  Š  Đ  Ž  A  S  D  F  G  H  J  K  L  Č  Ć  Ž  DŽ C  V  B  N  M
        System.out.println(string);
        string = string.replace("Љ", "Lj");
        string = string.replace("Њ", "Nj");
        string = string.replace("З", "Z");
        string = string.replace("У", "U");
        string = string.replace("И", "I");
        string = string.replace("П", "P");
        string = string.replace("Ш", "Š");
        string = string.replace("Ђ", "Đ");
        string = string.replace("С", "S");
        string = string.replace("Д", "D");
        string = string.replace("Ф", "F");
        string = string.replace("Г", "G");
        string = string.replace("Х", "H");
        string = string.replace("Л", "L");
        string = string.replace("Ч", "Č");
        string = string.replace("Ћ", "Ć");
        string = string.replace("Ж", "Ž");
        string = string.replace("Џ", "Dž");
        string = string.replace("Ц", "C");
        string = string.replace("В", "V");
        string = string.replace("Б", "B");
        string = string.replace("Н", "N");
        string = string.replace("Р", "R");

        System.out.println(string);
        return string;
    }
}
