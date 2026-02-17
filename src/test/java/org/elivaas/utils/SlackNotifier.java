package org.elivaas.utils;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SlackNotifier {

    private static final String SLACK_WEBHOOK_URL =
            "https://hooks.slack.com/services/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";

    public static void send(String message) {
        try {
            URL url = new URL(SLACK_WEBHOOK_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setDoOutput(true);

            // Escape newlines and quotes for JSON
            String escapedMessage = message.replace("\n", "\\n").replace("\"", "\\\"");
            String payload = "{\"text\":\"" + escapedMessage + "\"}";
            System.out.println("Payload: " + payload);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = payload.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            System.out.println("Slack response code: " + responseCode);
            
            if (responseCode == 200) {
                System.out.println("✅ Slack notification sent successfully");
            } else {
                System.err.println("❌ Slack notification failed. HTTP Code: " + responseCode);
                // Read error response for more details
                try (java.io.BufferedReader br = new java.io.BufferedReader(
                        new java.io.InputStreamReader(conn.getErrorStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    System.err.println("Error response: " + response);
                }
            }

        } catch (Exception e) {
            System.err.println("❌ Slack notification failed: " + e.getMessage());
            e.printStackTrace();
        }
    }


}

