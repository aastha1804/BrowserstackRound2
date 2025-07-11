package org.browserstack.assignment.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.net.URL;

public class TranslatorUtil {

    public static String translateToEnglish(String text) {
        try {
            String urlStr = "https://translate.googleapis.com/translate_a/single?client=gtx&sl=es&tl=en&dt=t&q=" +
                    URLEncoder.encode(text, "UTF-8");
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine = in.readLine(); // Gets the translated text
            in.close();

            if (inputLine != null && inputLine.startsWith("[[[")) {
                return inputLine.split("\"")[1];
            }

        } catch (Exception e) {
            System.err.println("Translation failed: " + e.getMessage());
        }
        return "[Translation failed]";
    }
}
