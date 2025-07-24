import java.io.*;
import java.util.Scanner;

/**
 * FileUtility - Enhanced version with Write, Read, Append, Delete, Rename, and Colors
 * Author: CODTECH Intern
 */
public class FileHandlingUtility {

    static Scanner scanner = new Scanner(System.in);
    static String filePath;

    // ANSI color codes (OnlineGDB supports them)
    static final String GREEN = "\u001B[32m";
    static final String RED = "\u001B[31m";
    static final String CYAN = "\u001B[36m";
    static final String YELLOW = "\u001B[33m";
    static final String RESET = "\u001B[0m";

    public static void main(String[] args) {
        System.out.print(CYAN + "Enter the file name to work with (e.g., notes.txt): " + RESET);
        filePath = scanner.nextLine().trim();

        int choice;
        do {
            System.out.println(YELLOW + "\n--- FILE HANDLING UTILITY ---" + RESET);
            System.out.println("1. Write to File");
            System.out.println("2. Read from File");
            System.out.println("3. Append to File");
            System.out.println("4. Exit");
            System.out.println("5. Delete File");
            System.out.println("6. Rename File");
            System.out.print(CYAN + "Enter your choice: " + RESET);

            while (!scanner.hasNextInt()) {
                System.out.print(RED + "Invalid input. Enter a number: " + RESET);
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> writeFile();
                case 2 -> readFile();
                case 3 -> appendFile();
                case 4 -> System.out.println(GREEN + "Exiting..." + RESET);
                case 5 -> deleteFile();
                case 6 -> renameFile();
                default -> System.out.println(RED + "Invalid choice! Try again." + RESET);
            }
        } while (choice != 4);
    }

    public static void writeFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            System.out.println("Enter content to write (type 'END' to finish):");
            StringBuilder content = new StringBuilder();
            String line;
            while (!(line = scanner.nextLine()).equals("END")) {
                content.append(line).append("\n");
            }
            writer.write(content.toString());
            System.out.println(GREEN + "File written successfully." + RESET);
        } catch (IOException e) {
            System.out.println(RED + "Error writing to file: " + e.getMessage() + RESET);
        }
    }

    public static void readFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            System.out.println(CYAN + "\n--- FILE CONTENT ---" + RESET);
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println(CYAN + "--- END OF FILE ---\n" + RESET);
        } catch (FileNotFoundException e) {
            System.out.println(RED + "File not found. Please write to it first." + RESET);
        } catch (IOException e) {
            System.out.println(RED + "Error reading file: " + e.getMessage() + RESET);
        }
    }

    public static void appendFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            System.out.println("Enter content to append (type 'END' to finish):");
            StringBuilder content = new StringBuilder();
            String line;
            while (!(line = scanner.nextLine()).equals("END")) {
                content.append(line).append("\n");
            }
            writer.newLine(); // Optional: add a blank line before appending
            writer.write(content.toString());
            System.out.println(GREEN + "Content appended successfully." + RESET);
        } catch (IOException e) {
            System.out.println(RED + "Error appending to file: " + e.getMessage() + RESET);
        }
    }

    public static void deleteFile() {
        File file = new File(filePath);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println(GREEN + "File deleted successfully." + RESET);
            } else {
                System.out.println(RED + "Failed to delete the file." + RESET);
            }
        } else {
            System.out.println(RED + "File does not exist." + RESET);
        }
    }

    public static void renameFile() {
        System.out.print("Enter new file name (e.g., newname.txt): ");
        String newFileName = scanner.nextLine().trim();
        File oldFile = new File(filePath);
        File newFile = new File(newFileName);

        if (!oldFile.exists()) {
            System.out.println(RED + "Original file does not exist." + RESET);
            return;
        }

        if (newFile.exists()) {
            System.out.println(RED + "A file with the new name already exists." + RESET);
            return;
        }

        if (oldFile.renameTo(newFile)) {
            System.out.println(GREEN + "File renamed successfully to " + newFileName + RESET);
            filePath = newFileName; // Update current file path
        } else {
            System.out.println(RED + "Failed to rename the file." + RESET);
        }
    }
}
