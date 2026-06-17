package com.example.dclassicbook.application.handler.ui.util;

import java.util.Locale;

public class FormatHelper {

    public static String formatUserCount(long count) {
        if (count >= 1_000_000) {
            double millions = count / 1_000_000.0;
            if (Math.floor(millions) == millions) {
                return String.format(Locale.US, "%.0fm", millions);
            }
            double truncated = Math.floor(millions * 10) / 10.0;
            return String.format(Locale.US, "%.1f", truncated).replace(".", ",") + "m";
        }
        if (count >= 1000) {
            double thousands = count / 1000.0;
            if (Math.floor(thousands) == thousands) {
                return String.format(Locale.US, "%.0fk", thousands);
            }
            double truncated = Math.floor(thousands * 10) / 10.0;
            return String.format(Locale.US, "%.1f", truncated).replace(".", ",") + "k";
        }
        return String.valueOf(count);
    }

    public static String formatPrice(long price) {
        String raw = String.valueOf(price);
        StringBuilder result = new StringBuilder();
        int count = 0;
        for (int i = raw.length() - 1; i >= 0; i--) {
            result.insert(0, raw.charAt(i));
            count++;
            if (count % 3 == 0 && i != 0) {
                result.insert(0, '.');
            }
        }
        return "Rp " + result;
    }
}
