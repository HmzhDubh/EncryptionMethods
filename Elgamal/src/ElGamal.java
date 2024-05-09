import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Scanner;


public class ElGamal {

    private static final BigInteger TWO = BigInteger.valueOf(2);

    // Generate keys
    public static KeyPair generateKeys(int bitLength) {
        SecureRandom random = new SecureRandom();
        BigInteger p = BigInteger.probablePrime(bitLength, random);
        BigInteger g = getPrimitiveRoot(p);
        BigInteger privateKey = new BigInteger(bitLength, random).mod(p.subtract(TWO)).add(TWO); // private key in range [2, p-2]
        BigInteger publicKey = g.modPow(privateKey, p);
        return new KeyPair(publicKey, privateKey, p);
    }

    // Encrypt message
    public static BigInteger[] encrypt(BigInteger message, BigInteger publicKey, BigInteger p) {
        SecureRandom random = new SecureRandom();
        BigInteger g = getPrimitiveRoot(p);
        BigInteger k = new BigInteger(p.bitLength(), random).mod(p.subtract(TWO)).add(TWO); // random k in range [2, p-2]
        BigInteger c1 = g.modPow(k, p);
        BigInteger c2 = message.multiply(publicKey.modPow(k, p)).mod(p);
        return new BigInteger[]{c1, c2};
    }

    // Decrypt message
    public static BigInteger decrypt(BigInteger[] ciphertext, BigInteger privateKey, BigInteger p) {
        BigInteger c1 = ciphertext[0];
        BigInteger c2 = ciphertext[1];
        BigInteger s = c1.modPow(privateKey, p);
        BigInteger sInverse = s.modInverse(p);
        return c2.multiply(sInverse).mod(p);
    }

    // Get a primitive root of p
    private static BigInteger getPrimitiveRoot(BigInteger p) {
        BigInteger g = TWO;
        while (g.modPow(p.subtract(BigInteger.ONE).divide(TWO), p).equals(BigInteger.ONE)) {
            g = g.add(BigInteger.ONE);
        }
        return g;
    }

    // Key Pair class
    public static class KeyPair {
        public final BigInteger publicKey;
        public final BigInteger privateKey;
        public final BigInteger p;

        public KeyPair(BigInteger publicKey, BigInteger privateKey, BigInteger p) {
            this.publicKey = publicKey;
            this.privateKey = privateKey;
            this.p = p;
        }
    }
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.println("Do You have public and private keys (y/n) ?");
        String ans = input.next();
        if(ans.equalsIgnoreCase("Y")){

            System.out.println("Enter Bit length: ");
            int bitLength = input.nextInt();

            SecureRandom random = new SecureRandom();

            System.out.println("Enter Your Public key: ");
            BigInteger pblk = input.nextBigInteger();

            System.out.printf("Enter Your Private Key: ");
            BigInteger prvt = input.nextBigInteger();

            BigInteger p = BigInteger.probablePrime(bitLength, random);
            KeyPair keyPair = new KeyPair(pblk,prvt,p);

            // Message to encrypt
            System.out.println("Enter message to encrypt");
            BigInteger msg = (input.nextBigInteger());

            BigInteger[] ciphertext = encrypt(msg, pblk, p);
            BigInteger c1 = ciphertext[0];
            BigInteger c2 = ciphertext[1];
            System.out.println("Encrypted message: (" + c1 + ", " + c2 + ")");

            System.out.println("Do you want to decrypt it (y/n) ?");
            String ans2 = input.next();
            if (ans2.equalsIgnoreCase("y")){

                // Decrypt message
                BigInteger decryptedMessage = decrypt(ciphertext, prvt, p);
                System.out.println("Decrypted message: " + decryptedMessage);

            }else {
                System.out.println("Thank you! See You Later Gamal ;) ");
            }

        } else if (ans.equalsIgnoreCase("N")) {

            // Generate keys
            System.out.println("Starting keys generation ... ... ...");
            KeyPair keyPair = generateKeys(64);
            BigInteger publicKey = keyPair.publicKey;
            BigInteger privateKey = keyPair.privateKey;
            BigInteger p = keyPair.p;

            // MSG to encrypt
            System.out.println("Enter message to encrypt: ");
            BigInteger msg = input.nextBigInteger();

            System.out.println("1. Encryption");
            System.out.println("2. Decryption");
            System.out.println("Enter Your Choice: ");

            int ans2 = input.nextInt();

            BigInteger[] ciphertext = encrypt(msg, publicKey, p);
            if (ans2 == 1){

                // Encrypt message

                BigInteger c1 = ciphertext[0];
                BigInteger c2 = ciphertext[1];
                System.out.println("Encrypted message: (" + c1 + ", " + c2 + ")");

            } else if (ans2 == 2) {

                // Decrypt message
                BigInteger decryptedMessage = decrypt(ciphertext, privateKey, p);
                System.out.println("Decrypted message: " + decryptedMessage);
            }
        }
    }
}
