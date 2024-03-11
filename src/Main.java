/*
    Name : Syed Azmeer Bin Syed Azehar
    Purpose : Ideagen Coding Test
 */

import java.util.ArrayList;
import java.util.List;
import java.text.DecimalFormat;

public class Main {

    public static double calculate(String sum) {
        sum = sum.replaceAll("\\s", ""); // Remove whitespaces

        List<Double> numbers = new ArrayList<>();
        List<Character> operators = new ArrayList<>();

        for (int i = 0; i < sum.length(); i++) {
            char c = sum.charAt(i);
            if (Character.isDigit(c) || c == '.') { //To check if c is a number or a decimal
                StringBuilder sb = new StringBuilder();
                while (i < sum.length() && (Character.isDigit(sum.charAt(i)) || sum.charAt(i) == '.')) {
                    sb.append(sum.charAt(i++));
                }
                i--;
                numbers.add(Double.parseDouble(sb.toString()));
            } else if (isOperator(c)) { //To check if c is an operator
                while (!operators.isEmpty() && precedence(operators.get(operators.size() - 1)) >= precedence(c)) {
                    evaluate(numbers, operators);
                }
                operators.add(c);
            } else if (c == '(') {
                operators.add(c);
            } else if (c == ')') {
                while (operators.get(operators.size() - 1) != '(') {
                    evaluate(numbers, operators);
                }
                operators.remove(operators.size() - 1); // Remove '('
            }
        }

        while (!operators.isEmpty()) {
            evaluate(numbers, operators);
        }

        return numbers.get(0);
    }

    private static void evaluate(List<Double> numbers, List<Character> operators) {
        double b = numbers.remove(numbers.size() - 1);
        double a = numbers.remove(numbers.size() - 1);
        char op = operators.remove(operators.size() - 1);
        DecimalFormat df = new DecimalFormat("#.##");
        double result = 0;

        switch (op) {
            case '+':
                result = a + b;
                break;
            case '-':
                result = a - b;
                break;
            case '*':
                result = a * b;
                break;
            case '/':
                if (b != 0)
                    result = a / b;
                else
                    throw new ArithmeticException("Division by zero");
                break;
        }
        result = Double.parseDouble(df.format(result));
        numbers.add(result);
    }

    private static int precedence(char op) {
        switch (op) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
        }
        return -1;
    }

    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    public static void main(String[] args) {
        // Example usage:
        String expression1 = "1 + 1";
        System.out.println(expression1 + " = " + calculate(expression1));

        String expression2 = "2 * 2";
        System.out.println(expression2 + " = " + calculate(expression2));

        String expression3 = "1 + 2 + 3";
        System.out.println(expression3 + " = " + calculate(expression3));

        String expression4 = "6 / 2";
        System.out.println(expression4 + " = " + calculate(expression4));

        String expression5 = "11 + 23";
        System.out.println(expression5 + " = " + calculate(expression5));

        String expression6 = "11.1 + 23";
        System.out.println(expression6 + " = " + calculate(expression6));

        String expression7 = "1 + 1 * 23";
        System.out.println(expression7 + " = " + calculate(expression7));

        String expression8 = "( 11.5 + 15.4 ) * 10.1";
        System.out.println(expression8 + " = " + calculate(expression8));

        String expression9 = "23 - ( 29.3 - 12.5 )";
        System.out.println(expression9 + " = " + calculate(expression9));

        //Bonus
        String expression10 = "10 - (2 + 3 * ( 7 - 5 ) )";
        System.out.println(expression10 + " = " + calculate(expression10));
    }
}
