import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;

public class SecondLargest {
  public static void secondLargest(int[] list) {
    Integer failedResult = -1;

    if (!hasNumber(list)) {
      showResult(failedResult);
      return;
    }

    Integer[] integerList = Arrays.stream(list).boxed().toArray(Integer[]::new);
    HashSet<Integer> set = new HashSet<Integer>(Arrays.asList(integerList));

    if (!hasMultipleNumbers(set)) {
      showResult(failedResult);
      return;
    }
    int largest = Integer.MIN_VALUE;
    int secondLargest = Integer.MIN_VALUE;
    for (int n : set) {
      if (n > largest) {
        secondLargest = largest;
        largest = n;
      } else {
        if (n > secondLargest) {
          secondLargest = n;
        }
      }
    }
    showResult(secondLargest);
  }

  public static Boolean hasNumber(int[] list) {
    if (list == null) {
      return false;
    }
    return list.length > 0;
  }

  public static Boolean hasMultipleNumbers(HashSet<Integer> set) {
    if (set.size() > 1) {
      return true;
    }
    return false;
  }

  public static void showResult(int result) {
    System.out.println(result);
  }

  public static void main(String[] args) {
    Scanner cin = new Scanner(System.in);
    int n = cin.nextInt();
    int[] num = null;
    if (n > 0)
      num = new int[n];

    int i = 0;

    while (i < n)
      num[i++] = cin.nextInt();

    cin.close();

    secondLargest(num);
  }
}