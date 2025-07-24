import java.util.Scanner;

public class ChatSimulation {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input user names
        System.out.print("Enter name for User 1: ");
        String user1 = scanner.nextLine();

        System.out.print("Enter name for User 2: ");
        String user2 = scanner.nextLine();

        System.out.println("\n=== Chat Started Between " + user1 + " and " + user2 + " ===");
        System.out.println("Type your messages one by one. To end the chat, type 'END'.\n");

        String message;
        boolean isChatActive = true;
        String currentUser = user1;

        while (isChatActive) {
            System.out.print(currentUser + ", enter your message: ");
            message = scanner.nextLine();

            if (message.equalsIgnoreCase("END")) {
                System.out.println(currentUser + " has ended the chat.");
                isChatActive = false;
                break;
            }

            // Display the message
            System.out.println(currentUser + ": " + message);
            System.out.println();

            // Switch to the other user
            currentUser = currentUser.equals(user1) ? user2 : user1;
        }

        System.out.println("\n=== Chat Ended ===");
    }
}
