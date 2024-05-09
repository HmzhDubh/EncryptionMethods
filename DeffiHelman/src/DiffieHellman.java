import java.math.BigInteger;
import java.security.SecureRandom;

public class DiffieHellman {

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

    // Get a primitive root of p
    private static BigInteger getPrimitiveRoot(BigInteger p) {
        BigInteger g = TWO;
        while (g.modPow(p.subtract(BigInteger.ONE).divide(TWO), p).equals(BigInteger.ONE)) {
            g = g.add(BigInteger.ONE);
        }
        return g;
    }

    // Compute shared secret
    public static BigInteger computeSharedSecret(BigInteger ownPrivateKey, BigInteger otherPublicKey, BigInteger p) {
        return otherPublicKey.modPow(ownPrivateKey, p);
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
        // Generate Alice's keys
        KeyPair aliceKeys = generateKeys(64);
        BigInteger alicePublicKey = aliceKeys.publicKey;
        BigInteger alicePrivateKey = aliceKeys.privateKey;
        BigInteger p = aliceKeys.p;

        // Generate Bob's keys
        KeyPair bobKeys = generateKeys(64);
        BigInteger bobPublicKey = bobKeys.publicKey;
        BigInteger bobPrivateKey = bobKeys.privateKey;

        // Alice sends her public key to Bob
        // Bob sends his public key to Alice
        // Now Alice and Bob can compute the shared secret

        // Compute shared secret for Alice
        BigInteger aliceSharedSecret = computeSharedSecret(alicePrivateKey, bobPublicKey, p);
        System.out.println("Alice's shared secret: " + aliceSharedSecret);

        // Compute shared secret for Bob
        BigInteger bobSharedSecret = computeSharedSecret(bobPrivateKey, alicePublicKey, p);
        System.out.println("Bob's shared secret: " + bobSharedSecret);

        // Alice and Bob should have the same shared secret
        if (aliceSharedSecret.equals(bobSharedSecret)) {
            System.out.println("Shared secrets match!");
        } else {
            System.out.println("Shared secrets do not match!");
        }
    }
}
