import java.util.Random;

import CalculatorModule.CalculatorPOA;

public class CalculatorImpl extends CalculatorPOA {

    @Override
    public int add(int num1, int num2) {
        return num1 + num2;
    }

    @Override
    public int subtract(int num1, int num2) {
        return num1 - num2;
    }

    @Override
    public int multiply(int num1, int num2) {
        return num1 * num2;
    }

    @Override
    public float divide(int num1, int num2) {
        return num1 / num2;
    }

    public static void main(String[] args) {

        System.out.println((int) (Math.random() * (100 - 50 + 1)) + 50);
    }

}
