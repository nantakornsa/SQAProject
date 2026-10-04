package com.example.algorithm2;

/**
 * Placeholder for AlgorithmName2's implementation.
 * Replace the logic below with your actual algorithm.
 */
public class Algorithm2 {

    public int run(int[] input) {
        // TODO: implement algorithm logic
        int product = 1;
        for (int value : input) {
            product *= value;
        }
        return product;
    }

    // ในกรณีที่ต้องการนำ CGF มาใช้หาบั๊กในคลาสที่มีอยู่
    public void process(String input, int number) {
        // Logic จำลองที่จะมี Bug ให้ Fuzzer ค้นพบ
        if (input != null && input.length() > 5) {
            if (number == 42) {
                // สมมติว่าเป็นโค้ดที่เกิดข้อผิดพลาด
                throw new RuntimeException("Found bug!");
            }
        }
    }


}
