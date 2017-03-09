package JavaApplication5;



import java.util.Scanner;

/**
 * @author Dell
 * 
 */
public class SubstringMatch {

    public static String sequence;  // sequence to be searched
    public static int[][] DFA;  // deterministic finite automata machine
    public static int lenghtOfPattern;  // length of sequence
    public static int numberOfInputs;  //possible number of inputs

    public SubstringMatch(String substring, int inputs) {

        SubstringMatch.sequence = substring;
        SubstringMatch.lenghtOfPattern = substring.length();
        SubstringMatch.numberOfInputs = inputs;
        SubstringMatch.DFA = new int[inputs][lenghtOfPattern];

    }

    public static void makeDFA() {

        DFA[sequence.charAt(0)][0] = 1;  
        for (int X = 0, j = 1; j < lenghtOfPattern; j++) {

            
            for (int a = 0; a < numberOfInputs; a++) {
                DFA[a][j] = DFA[a][X];
            }
            
            DFA[sequence.charAt(j)][j] = j + 1;
            
            X = DFA[sequence.charAt(j)][X];

        }
    }

    public static void search(String string) {
        int i, j;
        int N = string.length();
        for (i = 0, j = 0; i < N && j < lenghtOfPattern; i++) {
            j = DFA[string.charAt(i)][j];
        }
        if (j == lenghtOfPattern) {
            // if sequence found
            System.out.println("Substring Found");
        } else {
            // if sequence not found
            System.out.println("Substring Not Found");
        }
    }

    public static void main(String[] args) {

        System.out.print("Enter the substring you want to find: ");
        String substring;
        Scanner input = new Scanner(System.in);
        substring = input.next();
        SubstringMatch code = new SubstringMatch(substring, 256);
        makeDFA();
        int I = 0;
        while (I == 0) {
            System.out.print("Enter the full string: ");
            String string;

            string = input.next();
            if (!string.equals("END")) {
                search(string);
            } else {
                I = 1;
            }
            System.out.println();
        }

    }
}