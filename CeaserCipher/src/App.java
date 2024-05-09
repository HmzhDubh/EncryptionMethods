import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);

        System.out.println("1: Encrypt a text");
        System.out.println("2: Decrypt a text");
        System.out.print("Enter Your Choice: ");
        int choice = input.nextInt();

        if (choice == 1) {

            System.out.print("Enter word to be encrypted >>> ");
            String plaintext = input.next();
            System.out.println();

            System.out.print("Enter the KEY >>> ");
            int key = input.nextInt();

            String cipherText = caesarCiphers(plaintext, key);

            System.out.println("Plaintext: " + plaintext);
            System.out.println("KEY : " + key);
            System.out.println("Encrypted Text: " + cipherText);

            input.close();
        }
        else if (choice == 2) {

            System.out.print("Enter word to be decrypted >>> ");
            String cipherText = input.next();
            System.out.println();

            System.out.print("Enter the KEY >>> ");
            int key = input.nextInt();

            String plaintext = deCaesarCiphers(cipherText, key);

            System.out.println("Encrypted Text: " + cipherText);
            System.out.println("KEY : " + key);
            System.out.println("Plaintext: " + plaintext);

            while (true) {

                System.out.print("Is It Decrypted correctly [Y/N] ");
                String ans = input.nextLine();

                if (ans.equalsIgnoreCase("y")) {

                    System.out.println("Congraionlations");
                    break;

                }
                else if (ans.equalsIgnoreCase("n")) {

                    System.out.println("enter a diffenrent key >>> ");

                    plaintext = deCaesarCiphers(cipherText, key);

                    System.out.println("Encrypted Text: " + cipherText);
                    System.out.println("KEY : " + key);
                    System.out.println("Plaintext: " + plaintext);

                }
            }

            input.close();

        }
    }

    public static String caesarCiphers(String plaintext, int key) {
        StringBuilder cipherText = new StringBuilder();

        for (int i = 0; i < plaintext.length(); i++) {
            char c = plaintext.charAt(i);

            // Encrypt uppercase letters
            if (Character.isUpperCase(c)) {
                char cipherChar = (char) ((c + key - 'A') % 26 + 'A');
                cipherText.append(cipherChar);
            }
            // Encrypt lowercase letters
            else if (Character.isLowerCase(c)) {
                char cipherChar = (char) ((c + key - 'a') % 26 + 'a');
                cipherText.append(cipherChar);
            }
            // Keep non-alphabetic characters unchanged
            else {
                cipherText.append(c);
            }
        }

        return cipherText.toString();
    }
    public static String deCaesarCiphers(String cipherText, int key) {
        StringBuilder plainText = new StringBuilder();

        for (int i = 0; i < cipherText.length(); i++) {
            char c = cipherText.charAt(i);

            // Decrypt uppercase letters
            if (Character.isUpperCase(c)) {
                char plainChar = (char) ((c - key - 'A' + 26) % 26 + 'A');
                plainText.append(plainChar);
            }
            // Decrypt lowercase letters
            else if (Character.isLowerCase(c)) {
                char plainChar = (char) ((c - key - 'a' + 26) % 26 + 'a');
                plainText.append(plainChar);
            }
            // Keep non-alphabetic characters unchanged
            else {
                plainText.append(c);
            }
        }

        return plainText.toString();
    }
}
