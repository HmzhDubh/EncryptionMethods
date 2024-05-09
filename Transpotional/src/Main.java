import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.println("Enter the word to Encrypt: ");
        String plainText = input.next();

        System.out.println("Enter Encryption Key: ");
        String key = input.next();

        String encrtyptText = encrypt(plainText, key);
        System.out.println("Hello world!");
        String plaintext = "HELLO WORLD";


        // Encrypt plaintext
        String ciphertext = encrypt(plaintext, key);
        System.out.println("Encrypted: " + ciphertext);

    }
    public static String encrypt(String plaintext, String key) {
        plaintext  = plaintext.replaceAll("\\s+","").toUpperCase();
        int columns = key.length();
        int rows = (int) Math.ceil((double) plaintext.length() / columns);
        char[][] grid = new char[rows][columns];

        // Fill the grid with characters from the plaintext
        int index = 0;
        for (int col = 0; col < columns; col++) {
            for (int row = 0; row < rows; row++) {
                if (index < plaintext.length()) {
                    grid[row][col] = plaintext.charAt(index);
                    index++;
                } else {
                    grid[row][col] = 'X'; // padding with x
                }
            }
        }

        // Read the grid column by column to create the ciphertext

        StringBuilder ciphertext = new StringBuilder();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                ciphertext.append(grid[row][col]);
            }
        }
        return ciphertext.toString();
    }
    public static String decrypt(String ciphertext, int key) {
        int columns = key;
        int rows = (int) Math.ceil((double) ciphertext.length() / key);
        char[][] grid = new char[rows][columns];

        // Fill the grid with characters from the ciphertext
        int index = 0;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                if (index < ciphertext.length()) {
                    grid[row][col] = ciphertext.charAt(index);
                    index++;
                } else {
                    grid[row][col] = ' ';
                }
            }
        }

        // Read the grid column by column to create the plaintext
        StringBuilder plaintext = new StringBuilder();
        for (int col = 0; col < columns; col++) {
            for (int row = 0; row < rows; row++) {
                plaintext.append(grid[row][col]);
            }
        }

        return plaintext.toString();
    }
}