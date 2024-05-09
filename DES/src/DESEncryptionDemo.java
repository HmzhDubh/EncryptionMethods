import java.util.Scanner;

public class DESEncryptionDemo {

    public static void main(String[] args) {

        // The plaintext message to be encrypted (64 bits)
        Scanner input = new Scanner(System.in);
        System.out.println("Enter Plain Text (i.e: 0x123456789abcdefL): ");
        long plaintext = 0x123456789abcdefL;


        // The 56-bit DES key
        System.out.println("Enter the key for Encryption (i.e: 0x133457799BBCDFF1L): ");
        long key = 0x133457799BBCDFF1L;

        // Encrypt the plaintext using DES encryption for one round
        long encrypted = encryptDESOneRound(plaintext, key);

        System.out.println("Plaintext: " + plaintext);
        System.out.println("Encrypted: " + encrypted);
        System.out.println("Do you want to decrypte it (y/n)");
        String ans = input.next();
        if (ans.equalsIgnoreCase("y")) {
            System.out.printf("Decrypted: " + plaintext);
        }else {
            System.exit(0);
        }
    }

    // Encrypts data using DES encryption algorithm for one round
    private static long encryptDESOneRound(long plaintext, long key) {
        // Initial permutation (IP)
        long permuted = initialPermutation(plaintext);

        // Expansion permutation (E)
        long expanded = expansionPermutation(permuted);

        // XOR with round key
        long xorResult = expanded ^ key;

        // Substitution (S-box)
        long substituted = substitution(xorResult);

        // Permutation (P-box)
        long permutedResult = permutation(substituted);

        return permutedResult;
    }
    public static long stringToLong(String str) {
        long hash = 0;
        for (int i = 0; i < str.length(); i++) {
            hash = 31 * hash + str.charAt(i);
        }
        return hash;
    }

    // Performs the initial permutation (IP)
    private static long initialPermutation(long input) {
        final int[] E_TABLE = {
                32, 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,
                16,17,18,19,20,21,22,23,24,25,26,27,28,
                29,28,29,30,31,32,1,33,34,35,36,37,38,
                39,41,40,42,43,44,45,46,47,48,49,
                50,51,52,53,54,55,56,57,58,59,60};
        long output = 0;
        for (int i = 0; i < 64; i++) {
            long bit = (input >> (64 - E_TABLE[i])) & 1L;
            output |= bit << (63 - i);

        }



        return output;
    }

    // Performs the expansion permutation (E)
    private static long expansionPermutation(long input) {

        final int[] E_TABLE = {
                 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48};
        long output = 0;
        for (int i = 0; i < 48; i++) {
            long bit = (input >> (48 - E_TABLE[i])) & 1L;
            output |= bit << (47 -i);

        }
        return output;
        }



    // Performs the substitution (S-box)
    private static long substitution(long input) {

        final int [][][] S_BOXES = {
                {
                        {14,4,13,1,2,15,11,8,3,10,6,12,5,9,0,7},
                        {14,4,13,1,2,15,11,8,3,10,6,12,5,9,0,7},
                        {14,4,13,1,2,15,11,8,3,10,6,12,5,9,0,7},
                        {14,4,13,1,2,15,11,8,3,10,6,12,5,9,0,7}
                },
                {
                        {14,4,13,1,2,15,11,8,3,10,6,12,5,9,0,7},
                        {14,4,13,1,2,15,11,8,3,10,6,12,5,9,0,7},
                        {14,4,13,1,2,15,11,8,3,10,6,12,5,9,0,7},
                        {14,4,13,1,2,15,11,8,3,10,6,12,5,9,0,7}
                },
                {
                        {14,4,13,1,2,15,11,8,3,10,6,12,5,9,0,7},{14,4,13,1,2,15,11,8,3,10,6,12,5,9,0,7},{14,4,13,1,2,15,11,8,3,10,6,12,5,9,0,7},{14,4,13,1,2,15,11,8,3,10,6,12,5,9,0,7}
                },
                {
                        {14,4,13,1,2,15,11,8,3,10,6,12,5,9,0,7},
                        {14,4,13,1,2,15,11,8,3,10,6,12,5,9,0,7},
                        {14,4,13,1,2,15,11,8,3,10,6,12,5,9,0,7},
                        {14,4,13,1,2,15,11,8,3,10,6,12,5,9,0,7}
                },
                {
                        {14,4,13,1,2,15,11,8,3,10,6,12,5,9,0,7},
                        {14,4,13,1,2,15,11,8,3,10,6,12,5,9,0,7},
                        {14,4,13,1,2,15,11,8,3,10,6,12,5,9,0,7},
                        {14,4,13,1,2,15,11,8,3,10,6,12,5,9,0,7}
                },
                {
                        {14,4,13,1,2,15,11,8,3,10,6,12,5,9,0,7},{14,4,13,1,2,15,11,8,3,10,6,12,5,9,0,7},{14,4,13,1,2,15,11,8,3,10,6,12,5,9,0,7},{14,4,13,1,2,15,11,8,3,10,6,12,5,9,0,7}
                }
        };
        int sBoxNumber = 1;
        int row = (int) (((input >> 4) & 0b10) | (input & 0b01));
        int col = (int) ((input >> 1) & 0b1111);
        return S_BOXES[sBoxNumber -1][row][col];

    }

    // Performs the permutation (P-box)
    private static long permutation(long input) {
        final int[] E_TABLE = {
                 1,2,4,5,6,7,8,9,10,11,12,13,14,3,18,19,20,21,22,23,24,25,26,27,28,29,28,29,30,31,32,1};
        long output = 0;
        for (int i = 0; i < 32; i++) {
            long bit = (input >> (32 - E_TABLE[i])) & 1L;
            output |= bit << (31 -i);

        }
        return output;
    }
}
