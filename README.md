# Word Puzzle

Welcome to the Word Puzzle application! This program allows you to search for words in a given puzzle matrix and highlights the found words in the matrix.

## Features

- Reads a puzzle matrix from a file.
- Reads a list of words to search for from a file.
- Searches for words in all possible directions (up, down, left, right, and diagonals).
- Highlights the found words in the matrix.

## Requirements

- Java 8 or higher

## Setup

1. Clone the repository or download the source code.

2. Ensure you have Java installed on your system. You can download it from [here](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html).

## Files

- `Main.java`: The main entry point of the application.
- `WordSearcher.java`: Contains the logic for searching words in the puzzle matrix.
- `Parser.java`: Contains the logic for reading the puzzle and word files.

## Running the Application

1. Create a text file containing the puzzle matrix. Each row of the matrix should be on a new line, and characters should be separated by spaces. For example:

    ```
    H Y D A T P B V T U O U E J O Q H Z
    C W E L Z F E A T D R T D Q K U V B
    S Y C R S V S R E C D U N A R E A E
    W D I G F T K T I E E G Y C K U W V
    R B M F E N T W A M R C C O D E C R
    I F A B Y O L J O C E F R U Z D E X
    Y C L A E T M T P C K T O N U O I K
    N U S V K R Y E Y F J U E T B F Q Y
    O V Q F S R U I T E N F Q R I K N Y
    I C M Q A H R O X R F S G Y Y Y B C
    G S U F O H B A L M Y C Y X K C W H
    E A P Z V K P J C L Y K D W E A T L
    ```

2. Create a text file containing the words to search for. Each word should be on a new line. For example:

    ```
    PERIMETER
    GEOMETRY
    DECIMALS
    COUNTRY
    ORDER
    QUEUE
    STACK
    AREA
    ```

3. Compile the Java files:

    ```bash
    javac Main.java WordSearcher.java Parser.java
    ```

4. Run the application:

    ```bash
    java Main
    ```

5. Follow the on-screen prompts to enter the paths to the puzzle and word files.

## Example

Upon running the application, you will see output similar to the following:

