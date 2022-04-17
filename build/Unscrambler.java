import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Unscrambler {

    public static void main(String[] args) throws FileNotFoundException {

        ArrayList<String> dictionary = new ArrayList<>();
        Scanner scanner = new Scanner(new File("DL.txt"));
        while (scanner.hasNextLine()) {
            dictionary.add(scanner.nextLine());
        }

        String word = args[0];
        ArrayList<String> permutations = getPermutations(word);

        for (String permutation : permutations) {
            if (dictionary.contains(permutation)) {
                System.out.println(permutation);
                break;
            }
        }

    }

    private static ArrayList<String> getPermutations(String word) {
        ArrayList<String> permutations = new ArrayList<>();
        if (word.length() == 0) {
            permutations.add("");
            return permutations;
        }

        char first = word.charAt(0);
        String remainder = word.substring(1);
        ArrayList<String> words = getPermutations(remainder);
        for (String word1 : words) {
            for (int j = 0; j <= word1.length(); j++) {
                String s = insertCharAt(word1, first, j);
                permutations.add(s);
            }
        }

        return permutations;
    }

    private static String insertCharAt(String word, char c, int i) {
        String start = word.substring(0, i);
        String end = word.substring(i);
        return start + c + end;
    }
}

