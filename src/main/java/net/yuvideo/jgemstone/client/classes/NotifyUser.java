package net.yuvideo.jgemstone.client.classes;

import javafx.geometry.Pos;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 * Created by zoom on 1/28/17.
 */
public class NotifyUser {

    public static void NotifyUser(String header, String text, int type) {

        switch (type) {
            case 1:
                showInfo(header, text);
                break;
            case 2:
                showWarning(header, text);
                break;
            case 3:
                showError(header, text);

        }

    }

    private static void showError(String header, String text) {
        Notifications.create()
                .title(header)
                .text(text)
                .hideAfter(Duration.seconds(6))
                .position(Pos.BOTTOM_RIGHT)
                .showError();
    }

    private static void showWarning(String header, String text) {
        Notifications.create()
                .title(header)
                .text(text)
                .hideAfter(Duration.seconds(6))
                .position(Pos.BOTTOM_RIGHT)
                .showWarning();
    }

    private static void showInfo(String header, String text) {
        Notifications.create()
                .title(header)
                .text(text)
                .hideAfter(Duration.seconds(6))
                .position(Pos.BOTTOM_RIGHT)
                .showInformation();
    }


}
