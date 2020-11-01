import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;

public class Autocomplete {

  public static void main(String[] args) {
    final Scanner sc = new Scanner(System.in);

    final int n = sc.nextInt();
    List<String> dict = new ArrayList<>(n);
    sc.nextLine();
    for (int i = 0; i < n; ++i) {
      dict.add(sc.nextLine());
    }

    final int q = sc.nextInt();
    List<String> queries = new ArrayList<>(q);
    sc.nextLine();
    for (int i = 0; i < q; ++i) {
      queries.add(sc.nextLine());
    }

    solve(dict, queries);
  }


  static private void solve(List<String> dict, List<String> queries) {
    Trie trie = new Trie();
    for (String word : dict) {
      trie.insert(word);
    }
    for (String query : queries) {
      ArrayList<String> result = trie.getAutoCompleteResults(query, null, true);
      String printResult = result.isEmpty() ? "<no matches>" : toPrintString(result);
      System.out.println(printResult);
    }
  }

  static String toPrintString(ArrayList<String> array) {
    String arrayString = Arrays.toString(array.toArray());
    return arrayString.substring(1, arrayString.length() - 1).replace(',', ' ');
  }
}

class TrieNode {
  HashMap<Character, TrieNode> children = new HashMap<>();
  boolean isWordEnd;
}

class Trie {
  TrieNode root = new TrieNode();

  // If not present, inserts key into trie.  If the 
  // key is prefix of trie node, just marks leaf node
  void insert(String key) {
    TrieNode pointNode = root;
    for (char character : key.toCharArray()) {
      TrieNode nextNode;
      if (pointNode.children.containsKey(character)) {
        nextNode = pointNode.children.get(character);
      } else {
        nextNode = new TrieNode();
        pointNode.children.put(character, nextNode);
      }
      pointNode = nextNode;
    }
    pointNode.isWordEnd = true;
  }

  ArrayList<String> getAutoCompleteResults(String query, ArrayList<String> results, boolean allowsTypo) {
    // Returns all the words in the trie whose common 
    // prefix is the given key thus listing out all  
    // the suggestions for autocomplete. 
    TrieNode pointNode = root;
    boolean notFound = false;
    if (results == null) {
      results =  new ArrayList<String>();
    }

    for (int i = 0; i < query.toCharArray().length; i++) {
      if (allowsTypo) {
        for (char character : pointNode.children.keySet()) {
          String alterString = replaceWord(query, i, character);
          getAutoCompleteResults(alterString, results, false);
        } 
      }
      char character = query.toCharArray()[i];
      if (pointNode.children.get(character) == null) {
        notFound = true;
        break;
      }
      pointNode = pointNode.children.get(character);
    }

    if (notFound == true) {
      return results;
    }

    traverseTrie(results, pointNode, query);
    return results;
  }

  void traverseTrie(ArrayList<String> results, TrieNode node, String query) {
    // Method to recursively traverse the trie
    // and return a whole word.
    if (node.isWordEnd) {
      results.add(query);
    }
    node.children.forEach((character, nextNode) -> {
      traverseTrie(results, nextNode, query + character);
    });
  }

  String replaceWord(String originalWord, int replacingIndex, char replacingCharacter) {
    char[] characters = originalWord.toCharArray();
    characters[replacingIndex] = replacingCharacter;
    return String.valueOf(characters);
  }
}