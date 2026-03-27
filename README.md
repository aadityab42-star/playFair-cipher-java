# Playfair Cipher Encryption Tool (Java)

A command-line Java application that encrypts plaintext files using the Playfair cipher.

## Features

* Encrypt plaintext using a custom key
* File input (reads plaintext from file)
* File output (writes ciphertext to file)
* Command-line argument handling
* Error handling for invalid input

## Concepts Demonstrated

* File I/O (FileReader, FileWriter)
* String manipulation
* Encryption algorithms
* Command-line arguments
* Exception handling

## Technologies

* Java
* java.io (FileReader, FileWriter)
* Scanner

## Usage

java -cp bin App -k key plaintextFile ciphertextFile

## Example

Input file (plaintext.txt):
HELLO WORLD

Command:
java -cp bin App -k SECRET plaintext.txt output.txt

Output file (output.txt):
ENCRYPTEDTEXTHERE
