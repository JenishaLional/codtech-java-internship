import java.util.*;

public class SimpleRecommendationSystem {

    static Map<String, List<String>> itemCategories = new HashMap<>();
    static Map<String, List<String>> userPreferences = new HashMap<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadSampleData();
        System.out.println("=== AI-Based Recommendation System ===\n");

        while (true) {
            System.out.print("Enter user name (or type 'exit' to quit): ");
            String user = scanner.nextLine().trim();

            if (user.equalsIgnoreCase("exit")) {
                System.out.println("Exiting program. Goodbye!");
                break;
            }

            if (!userPreferences.containsKey(user)) {
                System.out.println("User '" + user + "' not found.");
                System.out.print("Do you want to add new user '" + user + "'? (yes/no): ");
                String addUser = scanner.nextLine().trim().toLowerCase();
                if (addUser.equals("yes")) {
                    System.out.print("Enter preferences for " + user + " (comma separated): ");
                    String prefsLine = scanner.nextLine();
                    List<String> prefs = parsePreferences(prefsLine);
                    userPreferences.put(user, prefs);
                    System.out.println("User added with preferences: " + prefs + "\n");
                } else {
                    System.out.println("Okay, user not added.\n");
                    continue;
                }
            }

            List<String> prefs = userPreferences.get(user);
            System.out.println("\nHello, " + user + "!");
            System.out.println("Your current preferences: " + prefs);

            // Ask if want to add new preferences
            while (true) {
                System.out.print("Do you want to add new preferences? (yes/no): ");
                String response = scanner.nextLine().trim().toLowerCase();
                if (response.equals("yes")) {
                    System.out.print("Enter new preferences (comma separated): ");
                    String newPrefsLine = scanner.nextLine();
                    List<String> newPrefs = parsePreferences(newPrefsLine);
                    prefs.addAll(newPrefs);
                    // Remove duplicates
                    Set<String> set = new HashSet<>(prefs);
                    prefs = new ArrayList<>(set);
                    userPreferences.put(user, prefs);
                    System.out.println("Updated preferences: " + prefs);
                } else if (response.equals("no")) {
                    break;
                } else {
                    System.out.println("Please enter 'yes' or 'no'.");
                }
            }

            List<String> recommendations = getRecommendations(user);

            System.out.println("\nRecommended items for you:");
            if (recommendations.isEmpty()) {
                System.out.println("No recommendations found based on your preferences.");
            } else {
                for (String item : recommendations) {
                    List<String> matchedGenres = new ArrayList<>(itemCategories.get(item));
                    matchedGenres.retainAll(prefs);
                    String matchedStr = String.join(", ", matchedGenres);
                    System.out.printf(" • %-18s (matches: %s)%n", item, matchedStr);
                }
            }

            System.out.println("\n------------------------------------\n");
        }

        scanner.close();
    }

    public static List<String> parsePreferences(String input) {
        String[] parts = input.split(",");
        List<String> prefs = new ArrayList<>();
        for (String p : parts) {
            String trimmed = p.trim();
            if (!trimmed.isEmpty()) {
                // Capitalize first letter for consistency
                prefs.add(trimmed.substring(0,1).toUpperCase() + trimmed.substring(1).toLowerCase());
            }
        }
        return prefs;
    }

    public static void loadSampleData() {
        itemCategories.put("Inception", Arrays.asList("Sci-Fi", "Thriller"));
        itemCategories.put("The Matrix", Arrays.asList("Sci-Fi", "Action"));
        itemCategories.put("Titanic", Arrays.asList("Romance", "Drama"));
        itemCategories.put("Avengers", Arrays.asList("Action", "Superhero"));
        itemCategories.put("The Notebook", Arrays.asList("Romance", "Drama"));
        itemCategories.put("Interstellar", Arrays.asList("Sci-Fi", "Drama"));

        userPreferences.put("Alice", new ArrayList<>(Arrays.asList("Sci-Fi", "Action")));
        userPreferences.put("Bob", new ArrayList<>(Arrays.asList("Romance", "Drama")));
        userPreferences.put("Charlie", new ArrayList<>(Arrays.asList("Thriller", "Superhero")));
    }

    public static List<String> getRecommendations(String user) {
        List<String> likedGenres = userPreferences.get(user);
        List<String> recommended = new ArrayList<>();

        for (Map.Entry<String, List<String>> entry : itemCategories.entrySet()) {
            String item = entry.getKey();
            List<String> genres = entry.getValue();

            for (String genre : genres) {
                if (likedGenres.contains(genre)) {
                    recommended.add(item);
                    break;
                }
            }
        }

        return recommended;
    }
}
