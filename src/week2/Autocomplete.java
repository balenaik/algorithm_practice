import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;
import java.util.TreeSet;

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
      String[] result = trie.getAutoCompleteResults(query);
      
      String printResult = result.length == 0 ? "<no matches>" : toPrintString(result);
      System.out.println(printResult);
    }
  }

  static String toPrintString(String[] array) {
    String arrayString = Arrays.toString(array);
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
  
  String[] getAutoCompleteResults(String query) {
    TreeSet<String> result = autoCompleteResults(query, null, true);
    int maxArraySize = 10;
    int arraySize = result.size() < maxArraySize ? result.size() : maxArraySize;
    return result.toArray(new String[arraySize]);
  }

  TreeSet<String> autoCompleteResults(String query, TreeSet<String> results, boolean allowsTypo) {
    // Returns all the words in the trie whose common 
    // prefix is the given key thus listing out all  
    // the suggestions for autocomplete. 
    TrieNode pointNode = root;
    boolean notFound = false;
    if (results == null) {
      results =  new TreeSet<String>();
    }

    for (int i = 0; i < query.toCharArray().length; i++) {
      if (allowsTypo) {
        for (char character : pointNode.children.keySet()) {
          String alterString = replaceWord(query, i, character);
          autoCompleteResults(alterString, results, false);
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

  void traverseTrie(Set<String> results, TrieNode node, String query) {
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