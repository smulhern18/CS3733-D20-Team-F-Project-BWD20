package edu.wpi.teamF.ModelClasses.Account;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordHasher {

  @SuppressWarnings("serial")
  public static class InvalidHashException extends Exception {
    public InvalidHashException(String message) {
      super(message);
    }

    public InvalidHashException(String message, Throwable source) {
      super(message, source);
    }
  }

  @SuppressWarnings("serial")
  public static class CannotPerformOperationException extends Exception {
    public CannotPerformOperationException(String message) {
      super(message);
    }

    public CannotPerformOperationException(String message, Throwable source) {
      super(message, source);
    }
  }

  public static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";

  // These constants may be changed without breaking existing hashes.
  public static final int SALT_BYTE_SIZE = 24;
  public static final int HASH_BYTE_SIZE = 18;
  public static final int PBKDF2_ITERATIONS = 64000;

  // These constants define the encoding and may not be changed.
  public static final int HASH_SECTIONS = 5;
  public static final int HASH_ALGORITHM_INDEX = 0;
  public static final int ITERATION_INDEX = 1;
  public static final int HASH_SIZE_INDEX = 2;
  public static final int SALT_INDEX = 3;
  public static final int PBKDF2_INDEX = 4;

  /**
   * Creates a hash from a string
   *
   * @param password the string to create the hash from
   * @return the hashed string
   * @throws CannotPerformOperationException if cannot perform the hash
   */
  public static String createHash(String password) throws CannotPerformOperationException {
    return createHash(password.toCharArray());
  }

  /**
   * Creates a hash with a salted string from a char array
   *
   * @param password the password to convert
   * @return the hashed string
   * @throws CannotPerformOperationException
   */
  public static String createHash(char[] password) throws CannotPerformOperationException {
    // Generate a random salt
    SecureRandom random = new SecureRandom();
    byte[] salt = new byte[SALT_BYTE_SIZE];
    random.nextBytes(salt);

    // Hash the password
    byte[] hash = pbkdf2(password, salt, PBKDF2_ITERATIONS, HASH_BYTE_SIZE);
    int hashSize = hash.length;

    // format: algorithm:iterations:hashSize:salt:hash
    String parts =
        "sha1:" + PBKDF2_ITERATIONS + ":" + hashSize + ":" + toBase64(salt) + ":" + toBase64(hash);
    return parts;
  }

  /**
   * Verifies a string password and returns true if they are the same, false otherwise
   *
   * @param password the password to check
   * @param correctHash the hask to check the password against
   * @return the verification boolean
   * @throws CannotPerformOperationException
   * @throws InvalidHashException
   */
  public static boolean verifyPassword(String password, String correctHash)
      throws CannotPerformOperationException, InvalidHashException {
    return verifyPassword(password.toCharArray(), correctHash);
  }

  public static boolean verifyPassword(char[] password, String correctHash)
      throws CannotPerformOperationException, InvalidHashException {
    // Decode the hash into its parameters
    String[] params = correctHash.split(":");
    if (params.length != HASH_SECTIONS) {
      throw new InvalidHashException("Fields are missing from the password hash.");
    }

    // Currently, Java only supports SHA1.
    if (!params[HASH_ALGORITHM_INDEX].equals("sha1")) {
      throw new CannotPerformOperationException("Unsupported hash type.");
    }

    int iterations = 0;
    try {
      iterations = Integer.parseInt(params[ITERATION_INDEX]);
    } catch (NumberFormatException ex) {
      throw new InvalidHashException("Could not parse the iteration count as an integer.", ex);
    }

    if (iterations < 1) {
      throw new InvalidHashException("Invalid number of iterations. Must be >= 1.");
    }

    byte[] salt = null;
    try {
      salt = fromBase64(params[SALT_INDEX]);
    } catch (IllegalArgumentException ex) {
      throw new InvalidHashException("Base64 decoding of salt failed.", ex);
    }

    byte[] hash = null;
    try {
      hash = fromBase64(params[PBKDF2_INDEX]);
    } catch (IllegalArgumentException ex) {
      throw new InvalidHashException("Base64 decoding of pbkdf2 output failed.", ex);
    }

    int storedHashSize = 0;
    try {
      storedHashSize = Integer.parseInt(params[HASH_SIZE_INDEX]);
    } catch (NumberFormatException ex) {
      throw new InvalidHashException("Could not parse the hash size as an integer.", ex);
    }

    if (storedHashSize != hash.length) {
      throw new InvalidHashException("Hash length doesn't match stored hash length.");
    }

    // Compute the hash of the provided password, using the same salt,
    // iteration count, and hash length
    byte[] testHash = pbkdf2(password, salt, iterations, hash.length);
    // Compare the hashes in constant time. The password is correct if
    // both hashes match.
    return slowEquals(hash, testHash);
  }

  /**
   * Checks byte by byte to see if the hashes are the same using XOR
   *
   * @param a the first byte array
   * @param b the second byte array
   * @return if the two byte arrays are equal
   */
  private static boolean slowEquals(byte[] a, byte[] b) {
    int diff = a.length ^ b.length;
    for (int i = 0; i < a.length && i < b.length; i++) diff |= a[i] ^ b[i];
    return diff == 0;
  }

  /**
   * Generates the hashed bytes array
   *
   * @param password the char array password to use
   * @param salt the salt byte array
   * @param iterations the iterations to use to hash
   * @param bytes the amount of bytes for the hash to be
   * @return the hash itself
   * @throws CannotPerformOperationException
   */
  private static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int bytes)
      throws CannotPerformOperationException {
    try {
      PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
      SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
      return skf.generateSecret(spec).getEncoded();
    } catch (NoSuchAlgorithmException ex) {
      throw new CannotPerformOperationException("Hash algorithm not supported.", ex);
    } catch (InvalidKeySpecException ex) {
      throw new CannotPerformOperationException("Invalid key spec.", ex);
    }
  }

  /**
   * Gets a byte array from the Hex Hash
   *
   * @param hex the hash
   * @return the byte array
   * @throws IllegalArgumentException should anything go wrong
   */
  private static byte[] fromBase64(String hex) throws IllegalArgumentException {
    return Base64.getDecoder().decode(hex);
  }

  /**
   * Converts a byte array to a hash string
   *
   * @param array the array to convert
   * @return the hash string
   */
  private static String toBase64(byte[] array) {
    return Base64.getEncoder().encodeToString(array);
  }
}
