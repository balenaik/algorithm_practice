import java.util.Scanner;

public class EvaluateMathExpression {
    public static float evaluateMathExpression(String s) {
    String[] s0 = s.split(" ");
    if (s0.length != 3) {
     throw new IllegalArgumentException("Must have 3 tokens separated by spaces: " + s);
    }
    // s1 is first number.
    String s1 = s0[0];
    // s2 is math operator (e.g. '+').
    String s2 = s0[1];
    // s3 is second number.
    String s3 = s0[2];

    // first number
    int v1 = 0;
    for (int i = 0; i < s1.length(); i++) {
      char c = s1.charAt(i);
      if (c < '0' || c > '9') {
    	  throw new NumberFormatException("Can't convert character to digit: " + c);
      }
      int digit = c - '0';
      v1 += digit * Math.pow(10, s1.length() - i - 1);
    }

    // second number
    int v2 = 0;
    for (int i = 0; i < s3.length(); i++) {
      char c = s3.charAt(i);
      if (c < '0' || c > '9') {
    	  throw new NumberFormatException("Can't convert character to digit: " + c);
      }
      int digit = c - '0';
      v2 += digit * Math.pow(10, s3.length() - i - 1);
    }
    return calculateNumbers(v1, s2, v2);
  }

  public static float calculateNumbers(int number1,  String operator, int number2) {
    if (operator.equals("+")) {
      return number1 + number2;
    } else if (operator.equals("-")) {
      return number1 - number2;
    } else if (operator.equals("*")) {
      return number1 * number2;
    } else if (operator.equals("/")) {
      return number1 / number2;
    } else {
      throw new NumberFormatException("Can't convert character an operator: +, -, /, *");
    }
  }
  
	public static void main(String[] args) {
		Scanner cin = new Scanner(System.in);
		String s = cin.nextLine();
		cin.close();
		try {
			float result = evaluateMathExpression(s);	
			System.out.println(result);
		}
		catch(NumberFormatException e) {
			System.out.println("NumberFormatException");
		}
		catch(IllegalArgumentException e) {
			System.out.println("IllegalArgumentException");
		}
	}
}