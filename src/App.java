import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import pssst.PlayfairCipher;

/**
 * Name: Aaditya Bhandari
 * Date: 4/11/2025
 * Description: A driver class that takes a key and encrypts a plaintext file
 * using the Playfair cipher.
 * The program reads a plaintext file, encrypts it, and writes the resulting
 * ciphertext to a specified output file.
 * Usage: java -cp bin App -k key plaintextFile ciphertextFile
 * Bugs: None
 * Reflection: This project helped reinforce my understanding of breaking down
 * an algorithm into manageable steps.
 * The most challenging part was handling edge cases like repeated characters in
 * pairs and ensuring the key was processed correctly.
 * I also had to pay attention to proper string manipulation without using
 * classes like StringBuilder, which required extra care with string
 * concatenation.
 * Overall, debugging the logic step by step helped me better understand how to
 * implement a cipher securely and cleanly.
 */
public class App {

    /**
     * Main method to run the Playfair cipher encryption process.
     * 
     * This method takes in command-line arguments, verifies them, and processes
     * them to:
     * 1. Read the plaintext from the input file.
     * 2. Encrypt the plaintext using the Playfair cipher.
     * 3. Write the resulting ciphertext to the output file.
     * 
     * @param args Command-line arguments:
     *             -k key plaintextFile ciphertextFile
     */
    public static void main(String[] args) {
        // Check if correct number of arguments are provided
        if (args.length != 4 || !args[0].equals("-k")) {
            System.out.println("Usage: java -cp bin App -k key plaintextFile ciphertextFile");
            return;
        }

        // Assign arguments to respective variables
        String key = args[1];
        String plaintextFile = args[2];
        String ciphertextFile = args[3];

        try {
            // Initialize PlayfairCipher with the provided key
            PlayfairCipher cipher = new PlayfairCipher(key);

            // Read the plaintext from the input file
            String plaintext = readFile(plaintextFile);

            // Encrypt the plaintext using the Playfair cipher
            String ciphertext = cipher.encrypt(plaintext);

            // Write the ciphertext to the output file
            writeFile(ciphertextFile, ciphertext);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Reads the content of a file and returns it as a String.
     * 
     * @param filename The path to the file to be read.
     * @return A string containing the contents of the file.
     */
    private static String readFile(String filename) {
        String content = "";

        // Try-with-resources to automatically close the FileReader and Scanner
        try (FileReader fr = new FileReader(filename); Scanner scanner = new Scanner(fr)) {
            while (scanner.hasNextLine()) {
                content += scanner.nextLine(); // Append each line to content
            }
        } catch (IOException e) {
            // Handle file reading errors
            System.out.println("Error reading file: " + e.getMessage());
        }

        return content;
    }

    /**
     * Writes a given content to a specified file.
     * 
     * @param filename The path to the file where content will be written.
     * @param content  The content to be written to the file.
     */
    private static void writeFile(String filename, String content) {
        // Try-with-resources to automatically close the FileWriter
        try (FileWriter fw = new FileWriter(filename)) {
            fw.write(content); // Write content to the file
        } catch (IOException e) {
            // Handle file writing errors
            System.out.println("Error writing file: " + e.getMessage());
        }
    }
}