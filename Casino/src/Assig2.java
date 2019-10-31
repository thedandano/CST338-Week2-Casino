import java.util.Scanner;

public class Assig2
{
   public static void main(String []args)
   {
      /*ThreeString test = new ThreeString();
      System.out.println("this is a test get should be blank: " + test.getString1());
      System.out.println("set to null: " + test.setString1(null));
      System.out.println("this should say be blank: " + test.getString1());
      System.out.println("set to hola: " + test.setString1("hola"));
      System.out.println("this should say hola: " + test.getString1());
      System.out.println("toString(): " + test.toString());
      
      test.saveWinnings(5);
      System.out.println(test.displayWinnings());
      */

      getBet();
   }
   
   /**
    * Prompts the user for their bet and validates the bet. 
    * @return The users Bet.
    */
   static int getBet()
   {
      Scanner in = new Scanner(System.in);
      int userBet;
      boolean invalidBet = false;
      
      do 
      {
         System.out.print("Place your bet! Must be 1-100 or enter 0 to exit: ");
         userBet = in.nextInt();
         System.out.println("You bet: " + userBet);
         
         //validates user input and provides appropriate responses
         if(userBet < 0)
         {
            System.out.println("Invalid Bet! Try again...\n");
            invalidBet = true;
         }
         else if(userBet > 100)
         {
            System.out.println("Invalid Bet! Try again...\n");
            invalidBet = true;
         }
         else if(userBet == 0)
         {
            System.out.println("Thanks for playing!");
            System.exit(0);
         }
         else
         {
            System.out.println("Great!");
            invalidBet = false;
         }
         
      }while(invalidBet);
      
      return userBet; 
   }
}

class ThreeString
{
   public static final int MAX_LENS = 20;
   public static final int MAX_PULLS = 40;
   private static int numPulls = 0;
   //array to track winnings
   public static int[] pullWinnings = new int[MAX_PULLS];
   //Instance Variables
   private String string1, string2, string3;
   
   /**
    * Constructor initializes instance variables
    */
   ThreeString()
   {
      string1 = "";
      string2 = "";
      string3 = "";
   }
   
   /**
    * Helper method returns true if the string is not null and if the string
    * length is less than or equal to the MAX_LENS which is 20 chars.
    * @param String
    * @return true if the String is not null and <= MAX_LENS (20 chars)
    */
   private boolean validString(String str)
   {
      if(str != null && str.length() <= MAX_LENS)
         return true;
      
      return false;
   }
   
   /**
    * Accessor for string1
    * @return string1
    */
   public String getString1()
   {
      return string1;
   }
   /**
    * Accessor for string2
    * @return string2
    */
   public String getString2()
   {
      return string2;
   }
   /**
    * Accessor for string3
    * @return string3
    */
   public String getString3()
   {
      return string3;
   }
   
   /**
    * Mutator for string1
    * @param a string parameter
    * @return true if the string passed is valid
    * @return false if the string passed is invalid
    */
   public Boolean setString1(String s1)
   {
      if(validString(s1) == true)
      {
         string1 = s1;
         return true;
      }
      
      return false; 
   }
   /**
    * Mutator for string2
    * @param a string parameter
    * @return true if the string passed is valid
    * @return false if the string passed is invalid
    */
   public Boolean setString2(String s2)
   {
      if(validString(s2) == true)
      {
         string2 = s2;
         return true;
      }
      
      return false; 
   }
   /**
    * Mutator for string3
    * @param a string parameter
    * @return true if the string passed is valid
    * @return false if the string passed is invalid
    */
   public Boolean setString3(String s3)
   {
      if(validString(s3) == true)
      {
         string3 = s3;
         return true;
      }
      
      return false; 
   }
   
   /**
    * toString() method to return private string variables as a string
    * @return string variables as a string
    */
   public String toString()
   {      
      return "String 1: " + string1 + 
         " String2: " + string2 + 
         " String3: " + string3;   
   }
   
   public boolean saveWinnings(int winnings)
   {
      pullWinnings[numPulls] = winnings;
      return false;
   }
   /**
    * Displays the Winnings Score
    * @return String representation of Winnings
    */
   public String displayWinnings()
   {
      String winnings = "";
      int sum = 0;
      
      for(int x = 0; x < pullWinnings.length; x++) 
      {
         sum += pullWinnings[x];
         winnings += pullWinnings[x];        
      }

      return winnings + " Total Winnings: $" + sum;
   }
}
