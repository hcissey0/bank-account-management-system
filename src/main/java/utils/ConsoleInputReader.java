package utils;

import java.util.Scanner;

public class ConsoleInputReader implements InputReader {

    Scanner scanner;

    @Override
    public void waitForEnter() {
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }

}
