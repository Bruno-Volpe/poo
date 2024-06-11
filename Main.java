public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        game.printWelcome();
        
        // Example moves
        game.goRoom("east");
        game.goRoom("west");
        game.goRoom("down"); // Should move to the cellar
        game.goRoom("up");   // Should move back to the office
    }
}
