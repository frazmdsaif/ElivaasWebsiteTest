package org.elivaas.utils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.Instant;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Fetches login OTPs from the OTP API instead of reading SMS via ADB.
 *
 * API contract: GET {@code <otp.api.url>} returns JSON like
 * {@code {"otp":"906187","received_at":"2026-09-29T10:23:20.682Z"}}.
 *
 * The API URL is read from {@code src/test/resources/config.properties}
 * (key {@code otp.api.url}) so it can be changed without code edits.
 * No JSON library is used on purpose — the response shape is fixed and
 * parsed with a simple regex to avoid adding new dependencies.
 */
public class OtpApiClient {

    private static final String DEFAULT_API_URL =
            "https://chump-footpath-excursion.ngrok-free.dev/api/otp";

    private static final long POLL_INTERVAL_MS = 2000;

    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    /** Latest OTP the API knows about, plus when it was received. */
    public record OtpResponse(String otp, Instant receivedAt) {
    }

    /** OTP API URL from config, falling back to the default if not configured. */
    public static String apiUrl() {
        try {
            String configured = PropertiesLoader.loadProperty("otp.api.url");
            if (configured != null && !configured.isBlank()) {
                return configured.trim();
            }
        } catch (IOException e) {
            System.out.println("Could not read otp.api.url from config, using default. " + e.getMessage());
        }
        return DEFAULT_API_URL;
    }

    /** One-shot fetch of the latest OTP known to the API. */
    public static OtpResponse fetchLatest() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder(URI.create(apiUrl()))
                .header("ngrok-skip-browser-warning", "true")
                .timeout(Duration.ofSeconds(15))
                .GET()
                .build();

        HttpResponse<String> response =
                HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("OTP API returned HTTP " + response.statusCode()
                    + " from " + apiUrl());
        }

        String body = response.body();
        String otp = extract(body, "\"otp\"\\s*:\\s*\"(\\d+)\"");
        if (otp == null) {
            throw new IOException("OTP API response has no otp field: " + body);
        }
        String receivedAtRaw = extract(body, "\"received_at\"\\s*:\\s*\"([^\"]+)\"");
        Instant receivedAt = null;
        if (receivedAtRaw != null) {
            try {
                receivedAt = Instant.parse(receivedAtRaw);
            } catch (Exception e) {
                System.out.println("Could not parse received_at '" + receivedAtRaw + "', ignoring. "
                        + e.getMessage());
            }
        }
        return new OtpResponse(otp, receivedAt);
    }

    /**
     * Polls the API until it reports an OTP received at or after {@code since},
     * or {@code timeoutSeconds} elapse. Falls back to the latest OTP seen
     * during polling so a stale-but-valid code is returned rather than
     * failing outright.
     */
    public static String waitForFreshOtp(Instant since, long timeoutSeconds)
            throws IOException, InterruptedException {
        long deadline = System.currentTimeMillis() + timeoutSeconds * 1000;
        OtpResponse latest = null;

        while (System.currentTimeMillis() < deadline) {
            try {
                latest = fetchLatest();
                if (latest.receivedAt() != null && !latest.receivedAt().isBefore(since)) {
                    return latest.otp();
                }
            } catch (IOException e) {
                System.out.println("OTP API poll failed, retrying: " + e.getMessage());
            }
            Thread.sleep(POLL_INTERVAL_MS);
        }

        if (latest != null) {
            System.out.println("Timed out waiting for a fresh OTP; returning latest OTP from API.");
            return latest.otp();
        }
        throw new IOException("Timed out waiting for OTP from " + apiUrl());
    }

    private static String extract(String body, String regex) {
        Matcher matcher = Pattern.compile(regex).matcher(body);
        return matcher.find() ? matcher.group(1) : null;
    }
}
