package net.yuvideo.jgemstone.client.classes;

import javax.swing.text.NumberFormatter;
import java.text.NumberFormat;

public class CenaFormatter {
    public static NumberFormatter getDoubleFormater(){
        NumberFormat format = NumberFormat.getInstance();
        NumberFormatter numberFormatter = new NumberFormatter(format);
        numberFormatter.setValueClass(Double.class);
        numberFormatter.setMinimum(0.00);
        numberFormatter.setMaximum(Double.MAX_VALUE);
        numberFormatter.setAllowsInvalid(false);
        numberFormatter.setCommitsOnValidEdit(true);
        return numberFormatter;

    }

    public static Boolean CenaFormatterString(String input){
        if(input.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
            return true;
        }else{
            return false;
        }
    }
}
