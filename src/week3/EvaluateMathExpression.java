import java.util.Scanner;

class MathExpression {
  int firstNumber;
  String operator;
  int secondNumber;

  MathExpression(int firstNumber, String operator, int secondNumber) {
    this.firstNumber = firstNumber;
    this.operator = operator;
    this.secondNumber = secondNumber;
  }

  static MathExpression createExpression(String input) {
    String divider = " ";
    String[] strings = input.split(divider);
    if (strings.length != 3) {
      throw new IllegalArgumentException("Must have 3 tokens separated by spaces");
    }
    String firstString = strings[0];
    String operator = strings[1];
    String secondString = strings[2];

    int firstNumber = 0;
    for (int i = 0; i < firstString.length(); i++) {
      char character = firstString.charAt(i);
      if (character < '0' || character > '9') {
        throw new NumberFormatException("Can't convert character to digit: " + character);
      }
      int digit = character - '0';
      firstNumber += digit * Math.pow(10, firstString.length() - i - 1);
    }

    int secondNumber = 0;
    for (int i = 0; i < secondString.length(); i++) {
      char character = secondString.charAt(i);
      if (character < '0' || character > '9') {
        throw new NumberFormatException("Can't convert character to digit: " + character);
      }
      int digit = character - '0';
      secondNumber += digit * Math.pow(10, secondString.length() - i - 1);
    }

    return new MathExpression(firstNumber, operator, secondNumber);
  }
}

public class EvaluateMathExpression {
    public static float evaluateMathExpression(String s) {
      MathExpression expression = MathExpression.createExpression(s);
      return calculateNumbers(expression);
  }

  public static float calculateNumbers(MathExpression expression) {
    int number1 = expression.firstNumber;
    int number2 = expression.secondNumber;
    if (expression.operator.equals("+")) {
      return number1 + number2;
    } else if (expression.operator.equals("-")) {
      return number1 - number2;
    } else if (expression.operator.equals("*")) {
      return number1 * number2;
    } else if (expression.operator.equals("/")) {
      checkExpressionDividable(number2);
      return (float) number1 / (float) number2;
    } else {
      throw new NumberFormatException("Can't convert character to operator: +, -, /, *");
    }
  }

  public static void checkExpressionDividable(int number2) {
    if (number2 == 0) {
      throw new IllegalArgumentException("Can't divide by 0");
    }
  }
  
  public static void main(String[] args) {
	  Scanner cin = new Scanner(System.in);
    String s = cin.nextLine();
    cin.close();
    try {
		  float result = evaluateMathExpression(s);	
		  System.out.println(result);
	  } catch (Exception e) {
      System.out.println(e.getClass().getSimpleName() + " " + e.getMessage());
    }
  }
}