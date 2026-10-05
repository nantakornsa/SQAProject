package com.example.algorithm2;

/**
 * Placeholder for AlgorithmName2's implementation.
 * Replace the logic below with your actual algorithm.
 */
public class Algorithm2 {

    public int run(int[] input) {
        int product = 1;
        for (int value : input) {
            product *= value;
        }
        return product;
    }

    public void process(String input, int number) {
        // Logic จำลองที่จะมี Bug ให้ Fuzzer ค้นพบ
        if (input != null && input.length() > 5) {
            if (number == 42) {
                throw new RuntimeException("Found bug!");
            }
        }
    }


}
