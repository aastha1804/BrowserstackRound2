package org.browserstack.assignment.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class TranslatorUtil {

    private static final String API_KEY = "YOUR_API_KEY_HERE";

    public static String translateToEnglish(String text) {
        try {
            URL url = new URL("https://translation.googleapis.com/language/translate/v2?key=" + API_KEY);
            String body = "{\"q\": \"" + text + "\", \"target\": \"en\"}";

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(body.getBytes());
            }

            Scanner sc = new Scanner(conn.getInputStream());
            String response = sc.useDelimiter("\\A").next();

            JsonObject jsonResponse = JsonParser.parseString(response).getAsJsonObject();
            return jsonResponse
                    .getAsJsonObject("data")
                    .getAsJsonArray("translations")
                    .get(0).getAsJsonObject()
                    .get("translatedText").getAsString();

        } catch (Exception e) {
            e.printStackTrace();
            return "[Translation Failed]";
        }
    }
}
