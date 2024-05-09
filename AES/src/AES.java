import java.util.ArrayList;
import java.util.Scanner;

public class AES {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        // The plaintext message to be encrypted (64 bits) and the key are entered by the user
        System.out.println("Enter Plain Text (HEX): ");
        String plainText = input.nextLine();
        System.out.println("Enter Key (HEX): ");
        String  key = input.nextLine();


        // Encrypt the plaintext using AES encryption for one round
        byte[] encrypted = AES_one_round(plainText, key);

        // Print the plain text and the encrypted text
        System.out.println("Plaintext: " + plainText);
        System.out.println("Encrypted: " + encrypted);

        input.close();

    }

    public static byte[] AES_one_round(String plainText, String key){

        byte[] textB = plainText.getBytes();
        byte[] keyB = key.getBytes();

        // Initial permutation (IP)
        byte[] permuted = addRoundKey(textB, keyB);  //Done

        // Permutation (P-box)

        byte[] permutedResult = subBytes(permuted); // Done

        // Expansion permutation (E)
        byte[] shifted = shiftRows(permutedResult); // To_do

        // Substitution (S-box)
        byte[] mixed = mixColumns(shifted); //To_do

        return mixed;

    }
    // STEP 1 is to XOR the plain text with the key after form both of them into a matrix (4 x 4)
    private static byte[] addRoundKey(byte[] plainText, byte[] key) {

        byte[] result = new byte[plainText.length];

        for (int i = 0; i < plainText.length; i++) {
            result[i] = (byte) (plainText[i] ^ key[i]); // Perform XOR operation
        }

        return result;

    }
    // STEP 2 is to use S-Box to sub bytes 
    private static byte[] subBytes(byte[] permuted) {

        byte[] result = new byte[permuted.length];

        // S-box (you can replace it with your desired S-box)
        byte[] sBox = {
                // S-box contents...
                (byte) 0xe1, (byte) 0xf3, (byte) 0xCC, (byte) 0xa31, (byte) 0xb31,
                (byte) 0xDB, (byte) 0xfA1, (byte) 0x76, (byte) 0xc31, (byte) 0xd31,
        };

        // Perform SubBytes operation
        for (int i = 0; i < permuted.length; i++) {
            int row = (permuted[i] & 0xF0) >>> 4; // Extract upper 4 bits
            int col = permuted[i] & 0x0F; // Extract lower 4 bits
            result[i] = sBox[row * 16 + col];
        }
        return result;

    }

    private static byte[] shiftRows(byte[] permutedResult) {

        return permutedResult;
    }
    private static byte[] mixColumns(byte[] shifted) {

        return shifted;
    }

}
