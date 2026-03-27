package pssst;

/**
 * Name: Aaditya Bhandari
 * Date: 4/10/2025
 * 
 * Description:
 * This class implements the Playfair Cipher encryption algorithm.
 * It constructs a 5x5 key grid from a provided keyword and applies rules for
 * encrypting character pairs.
 * The cipher handles repeated characters, odd-length strings, and ignores
 * non-alphabetic characters.
 * 
 * Reflection:
 * Implementing the Playfair cipher helped solidify understanding of classical
 * cryptographic algorithms.
 * Challenges included accurately forming digraphs from the plaintext, handling
 * repeated letters,
 * and constructing the cipher grid with unique characters from the key.
 * Adjusting logic for character swapping instead of shifting in rows/columns
 * deepened attention to edge cases.
 */
public class PlayfairCipher {
    private char[][] grid = new char[5][5]; // 5x5 cipher grid
    private String key;

    /**
     * Constructs a PlayfairCipher object with a given key.
     * 
     * @param key The keyword used to create the cipher grid.
     * @throws IllegalArgumentException if the processed key exceeds 25 characters.
     */
    public PlayfairCipher(String key) {
        this.key = prepareKey(key);
        if (this.key.length() > 25) {
            throw new IllegalArgumentException("Processed key must be 25 unique characters or less.");
        }
        fillGrid();
    }

    /**
     * Processes the key by removing duplicates, replacing 'J' with 'I',
     * and appending remaining alphabet characters.
     * 
     * @param key The raw keyword input.
     * @return A 25-character string to be used in the cipher grid.
     */
    private String prepareKey(String key) {
        key = key.toUpperCase().replace('J', 'I');
        boolean[] seen = new boolean[26];
        String uniqueKey = "";

        for (char c : key.toCharArray()) {
            if (c >= 'A' && c <= 'Z' && !seen[c - 'A']) {
                seen[c - 'A'] = true;
                uniqueKey += c;
            }
        }

        for (char c = 'A'; c <= 'Z'; c++) {
            if (c != 'J' && !seen[c - 'A']) {
                seen[c - 'A'] = true;
                uniqueKey += c;
            }
        }

        return uniqueKey;
    }

    /**
     * Fills the 5x5 grid using the prepared key.
     */
    private void fillGrid() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                grid[i][j] = key.charAt(i * 5 + j);
            }
        }
    }

    /**
     * Encrypts the given plaintext using the Playfair cipher.
     * 
     * @param plaintext The input message to be encrypted.
     * @return The resulting ciphertext.
     */
    public String encrypt(String plaintext) {
        plaintext = plaintext.toUpperCase().replace('J', 'I').replaceAll("[^A-Z]", "");
        String processed = preparePairs(plaintext);
        String ciphertext = "";

        for (int i = 0; i < processed.length(); i += 2) {
            char a = processed.charAt(i);
            char b = processed.charAt(i + 1);
            ciphertext += encryptPair(a, b);
        }

        return ciphertext;
    }

    /**
     * Prepares the plaintext into character pairs (digraphs) according to Playfair
     * rules.
     * Adds 'X' between duplicate letters and at the end if length is odd.
     * 
     * @param text The sanitized input text.
     * @return Formatted string with paired characters.
     */
    private String preparePairs(String text) {
        String result = "";
        int i = 0;

        while (i < text.length()) {
            char a = text.charAt(i);
            char b;

            if (i + 1 < text.length()) {
                b = text.charAt(i + 1);
                if (a == b) {
                    b = 'X';
                    result += a;
                    result += b;
                    i++;
                } else {
                    result += a;
                    result += b;
                    i += 2;
                }
            } else {
                b = 'X';
                result += a;
                result += b;
                i++;
            }
        }

        return result;
    }

    /**
     * Encrypts a pair of characters based on their positions in the cipher grid.
     * - If in the same row or column: swaps the characters' positions.
     * - Otherwise: applies rectangle rule (swap columns).
     * 
     * @param first  The first character in the pair.
     * @param second The second character in the pair.
     * @return The encrypted character pair.
     */
    private String encryptPair(char first, char second) {
        int[] firstPos = findPosition(first);
        int[] secondPos = findPosition(second);

        if (firstPos[0] == secondPos[0]) { // Same row
            return "" + grid[firstPos[0]][secondPos[1]] + grid[secondPos[0]][firstPos[1]];
        } else if (firstPos[1] == secondPos[1]) { // Same column
            return "" + grid[secondPos[0]][firstPos[1]] + grid[firstPos[0]][secondPos[1]];
        } else { // Rectangle rule
            return "" + grid[firstPos[0]][secondPos[1]] + grid[secondPos[0]][firstPos[1]];
        }
    }

    /**
     * Finds the position of a character in the cipher grid.
     * 
     * @param c The character to find.
     * @return An array containing the row and column indices.
     */
    private int[] findPosition(char c) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (grid[i][j] == c) {
                    return new int[] { i, j };
                }
            }
        }
        return null;
    }
}