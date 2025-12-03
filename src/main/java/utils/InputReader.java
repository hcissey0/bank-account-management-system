package utils;

public interface InputReader {

    void waitForEnter();

    int readInt(String prompt, int min, int max);

    double readDouble(String prompt, double min);

    String readString(String prompt);

}
