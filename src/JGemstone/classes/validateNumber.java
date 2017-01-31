package JGemstone.classes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zoom on 1/23/17.
 */
public class validateNumber {
    public static boolean validatePort(final String port) {
        Pattern pattern;
        Matcher matcher;
        String PORT_PATERN = "^([0-9]+)$";
        pattern = Pattern.compile(PORT_PATERN);
        matcher = pattern.matcher(port);
        return matcher.matches();

    }

}