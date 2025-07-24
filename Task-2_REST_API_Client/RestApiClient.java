import java.io.*;
import java.net.*;
import java.util.Scanner;

public class RestApiClient {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String choice;

        do {
            System.out.println("--- Fetching Current ISS Location ---");
            fetchAndDisplayISSLocation();

            System.out.print("\nFetch again? (yes/no): ");
            choice = scanner.nextLine().trim().toLowerCase();
        } while (choice.equals("yes") || choice.equals("y"));

        System.out.println("\nThank you for using the REST API Client.");
        System.out.println("Completion certificate will be issued on your internship end date.");
        scanner.close();
    }

    public static void fetchAndDisplayISSLocation() {
        try {
            String urlStr = "http://api.open-notify.org/iss-now.json";

            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            String json = content.toString();

            // Manual parsing for latitude and longitude
            String lat = extractValue(json, "\"latitude\":\"", "\"");
            String lon = extractValue(json, "\"longitude\":\"", "\"");
            String timestamp = extractValue(json, "\"timestamp\":", ",");

            System.out.println("Current ISS Position:");
            System.out.println("  Latitude : " + lat);
            System.out.println("  Longitude: " + lon);
            System.out.println("  Timestamp: " + timestamp);

        } catch (Exception e) {
            System.out.println("Error fetching ISS location:");
            e.printStackTrace();
        }
    }

    // Helper method to extract substring between start and end markers
    public static String extractValue(String text, String startMarker, String endMarker) {
        int start = text.indexOf(startMarker);
        if (start == -1) return "N/A";
        start += startMarker.length();
        int end = text.indexOf(endMarker, start);
        if (end == -1) return "N/A";
        return text.substring(start, end);
    }
}
