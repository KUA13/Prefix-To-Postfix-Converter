
/**
 * This file contains the main method, as well as some additional methods 
 * needed to convert prefix expressions into postfix expressions via 
 * recursion. No other files are necessary for this program to work. The 
 * user is prompted for the file paths of the input file and the output file. 
 * Blank lines in the input file are ignored. White space in each read line 
 * is removed when prior to evaluating the validity of each prefix expression. 
 * 
 * All inputs and outputs are added into a single String variable, which is 
 * then written to the output file.
 * 
 * @author Kamal Abro
 */

import java.util.Scanner;
import java.io.*;

public class PreToPostRecursive {

   public static void main(String[] args) {

      // Gets the file paths for the input file and the output file
      Scanner scan = new Scanner(System.in);
      System.out.print("Enter the input file path: ");
      String inputName = scan.nextLine();
      // Echoes the file path in the terminal
      System.out.println("Reading From: " + inputName);
      System.out.println();
      System.out.print("Enter the output file path: ");
      String outputName = scan.nextLine();
      // Echoes the file path in the terminal
      System.out.println("Writing To: " + outputName);
      System.out.println();

      String outputStr = ""; // String that will hold all output to be written
      outputStr += "Input File Path: " + inputName + "\n" + "\n";
      outputStr += "Output File Path: " + outputName + "\n";
      outputStr += "\n" + "\n";

      try {
         BufferedReader br = new BufferedReader(new FileReader(inputName));
         // Variable that will hold the current line being read
         String line = "";

         // Iterates through every line in input file
         while ((line = br.readLine()) != null) {
            if (line.isBlank()) { // Skips blank lines
               continue;
            }
            // Echoes the input expression in the output
            outputStr += "Input: " + line + "\n";

            // Removes white space from the current line being read and stores
            // in new variable
            String fixedLine = remWhiteSpace(line);

            // Initialize recursive variable that will be used to iterate
            // through the prefix expression and convert it into a postfix
            // expression
            int[] n = new int[1];
            n[0] = 0;

            // Checks if current line is a valid prefix expression
            if (isValidPre(fixedLine)) {
               // Converts to postfix expression and stores postfix in output
               String postfix = preToPost(fixedLine, n);
               if (fixedLine.length() != postfix.length()) {
                  postfix = "INVALID PREFIX EXPRESSION";
               }
               outputStr += "Output: " + postfix + "\n";

            } else {
               // Writes an error message to the output
               outputStr += "Output: INVALID PREFIX EXPRESSION!" + "\n";
            }
            outputStr += "\n";
         } // end while

         // Writes the output string to the output file
         BufferedWriter bw = new BufferedWriter(new FileWriter(outputName));
         bw.write(outputStr);

         System.out.println("Postfix expressions generated!");

         bw.close();
         br.close();
         scan.close();
      } // end try

      // Throws error message if invalid file names are entered by the user
      catch (IOException e) {
         e.getMessage();
      } // end catch()

   }// end main()

   /**
    * Evaluates whether a given character is an operator or not
    * 
    * @param c The character being evaluated
    * @return True if character is one a valid operator
    */
   public static boolean isOperator(char c) {
      if ((c == '+') || (c == '-') || (c == '*') || (c == '/') || (c == '$')
            || (c == '^')) {
         return true;
      }
      return false;
   }// end isOperator()

   /**
    * Takes a string and returns the same string without any white space
    * 
    * @param line The string that is having white space removed
    * @return A string without any white space
    */
   public static String remWhiteSpace(String line) {
      String tempLine = "";
      for (int i = 0; i < line.length(); i++) {
         String curChar = Character.toString(line.charAt(i));
         if (curChar.equals(" ")) {
            continue;
         }
         tempLine += curChar;
      }
      line = tempLine;
      return line;
   }// end remWhiteSpace()

   /**
    * Converts a prefix expression into a postfix expression
    * 
    * @param prefix A prefix expression as a String
    * @param n An array containing a single value, used to iterate through 
    * the prefix expression recursively
    * @return A prefix expression as a String
    */
   public static String preToPost(String prefix, int[] n) {
      if (n[0] == prefix.length() + 1) {
         return "";
      }
      char curChar = prefix.charAt(n[0]);

      if (!isOperator(curChar)) {
         return Character.toString(curChar);
      } else {
         n[0]++;
         String left = preToPost(prefix, n);
         n[0]++;
         String right = preToPost(prefix, n);
         String postfix = left + right + curChar;

         return postfix;
      }
   }// end preToPost()

   /**
    * Determines if a String is a valid prefix expression
    * 
    * @param prefix The String being evaluated
    * @return True if the string is a valid prefix expression
    */
   public static boolean isValidPre(String prefix) {
      int operatorCount = 0;
      int operandCount = 0;

      // Returns false if the last char in expression is and operator
      if (isOperator(prefix.charAt(prefix.length() - 1))) {
         return false;
      }

      // Iterates through the expression and counts the number of operators
      // and operands
      for (int i = 0; i < prefix.length(); i++) {
         if (isOperator(prefix.charAt(i))) {
            operatorCount++;
         } else {
            operandCount++;
         }
      }
      // Returns true if the the number of operators is one less than the 
      // number of operands
      if (operatorCount + 1 == operandCount) {
         return true;
      }
      return false;
   }// end isValidPre
}// end PreToPostRecursive class
