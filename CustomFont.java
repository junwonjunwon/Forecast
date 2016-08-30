package teamproject;

import java.awt.*;

public class CustomFont {
    public static Font defaultFont;

    public static Font getDefaultFont() {
        if (CheckOSInfo.isMac()) defaultFont = new Font("Apple SD Gothic", Font.PLAIN, 13);
        else if (CheckOSInfo.isWindows()) defaultFont = new Font("나눔고딕", Font.PLAIN, 13);
        else defaultFont = new Font("", Font.PLAIN, 13);

        return defaultFont;
    }
}