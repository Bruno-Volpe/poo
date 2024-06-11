import java.io.*;
import java.util.*;

public class UserManager {
    private List<User> users;
    private String userFile = "users.txt";
    private String logFile = "error.log";
    private User[] sessions = new User[5]; // Simulando sessões de usuário

    public UserManager() {
        users = new ArrayList<>();
        loadUsers();
    }

    public static void main(String[] args) {
        UserManager userManager = new UserManager();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        userManager.login(username, password);

        System.out.print("Enter user ID to fetch data: ");
        String userID = scanner.nextLine();
        userManager.fetchUserData(userID);

        // Exemplo de atualização de dados de usuário
        userManager.updateUserData(new User(username, password, "user@example.com"));
    }

    /**
     * Load users from a file
     */
    private void loadUsers() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(userFile));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                users.add(new User(parts[0], parts[1], parts[2]));
            }
        } catch (IOException e) {
            logError(e);
        } finally {
            try {
                if (reader != null) reader.close();
            } catch (IOException e) {
                logError(e);
            }
        }
    }

    /**
     * Simulate user login
     */
    public void login(String username, String password) {
        try {
            for (User user : users) {
                if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                    System.out.println("Login successful for user: " + username);
                    // Simulate session assignment
                    sessions[6] = user; // ArrayIndexOutOfBoundsException
                    return;
                }
            }
            throw new NullPointerException(); // Intentional NullPointerException
        } catch (NullPointerException e) {
            logError(e);
            System.out.println("User or password is incorrect.");
        } catch (ArrayIndexOutOfBoundsException e) {
            logError(e);
            System.out.println("Session management error. Please contact support.");
        }
    }

    /**
     * Fetch user data
     */
    public void fetchUserData(String userID) {
        BufferedReader reader = null;
        try {
            int id = Integer.parseInt(userID); // NumberFormatException
            reader = new BufferedReader(new FileReader(userFile));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (Integer.parseInt(parts[0]) == id) {
                    System.out.println("User found: " + parts[1]);
                    return;
                }
            }
            throw new NullPointerException(); // Intentional NullPointerException
        } catch (NullPointerException e) {
            logError(e);
            System.out.println("User not found.");
        } catch (NumberFormatException e) {
            logError(e);
            System.out.println("Invalid user ID format. Please enter a valid number.");
        } catch (IOException e) {
            logError(e);
        } finally {
            try {
                if (reader != null) reader.close();
            } catch (IOException e) {
                logError(e);
            }
        }
    }

    /**
     * Update user data
     */
    public void updateUserData(User user) {
        BufferedWriter writer = null;
        try {
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getUsername().equals(user.getUsername())) {
                    users.set(i, user);
                    break;
                }
            }
            writer = new BufferedWriter(new FileWriter(userFile));
            for (User u : users) {
                writer.write(u.toCSV() + "\n");
            }
        } catch (IOException e) {
            logError(e);
        } finally {
            try {
                if (writer != null) writer.close();
            } catch (IOException e) {
                logError(e);
            }
        }
    }

    /**
     * Log errors to a file
     */
    private void logError(Exception e) {
        try (BufferedWriter logWriter = new BufferedWriter(new FileWriter(logFile, true))) {
            logWriter.write(new Date().toString() + " - " + e.toString() + "\n");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

class User {
    private String username;
    private String password;
    private String email;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String toCSV() {
        return username + "," + password + "," + email;
    }
}
