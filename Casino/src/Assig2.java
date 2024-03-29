import java.util.Random;
import java.util.Scanner;

/**
 * This Program simulates a casino slots game. The user places a bet then
 * the program performs a "pull." It then calculates the winnings (if any)
 * and informs the user if they won or lost.
 * @author Dan Sedano
 * @version 11/5/19
 *
 */

public class Assig2
{
   //Global Scanner Class
   static Scanner in = new Scanner(System.in);

   public static void main(String []args)
   {
      Scanner in = new Scanner(System.in);
      ThreeString pullString = new ThreeString();
      Boolean keepPlaying = true;

      //loops through the game until the player enters 0 or the array is full.
      do 
      {
         //prompts the user for input and stores into a variable
         int bet = getBet();

         //Breaks the loop if the user enter 0
         if (bet == 0)
            break;

         //performs the "pull" and stores the results in a variable
         ThreeString pull = pull();

         //Receives the "pull" and calculates the pay out (if any) then stores
         //in a variable
         int winnings = getPayMultiplier(pull) * bet;

         //calls the display method 
         display(pull,winnings); 

         //calls the saveWinnings method and checks if the array is full
         boolean stopPlaying = pullString.saveWinnings(winnings);

         //Breaks the loop if the saveWinnings return true because the array
         //is full.
         if (stopPlaying == true)
            keepPlaying = false;

      }while(keepPlaying == true);

      //prints out the players winnings summary
      System.out.println(pullString.displayWinnings());
      //Closes scanner class
      in.close();
      //exits the program
      System.exit(0);

   }

   /**
    * Prompts the user for their bet and validates the bet. 
    * @return The users Bet.
    */
   static int getBet()
   {
      int userBet;
      boolean invalidBet = false;

      do 
      {
         System.out.print("Place your bet! Must be 1-100 or enter 0 to exit: ");
         userBet = in.nextInt();

         //validates user input and provides appropriate responses
         if(userBet < 0)
         {
            invalidBet = true;
         }
         else if(userBet > 100)
         {
            invalidBet = true;
         }
         else
         {
            invalidBet = false;
         }

      }while(invalidBet);

      return userBet; 
   }
   /**
    * This method instantiates the ThreeStrings class and fills it with 3
    * Strings.
    * @return a ThreeString Object filled with 3 random Strings consisting of
    * "Space", "cherries", "BAR", or "7".
    */
   static ThreeString pull()
   {
      ThreeString threeString = new ThreeString();

      //fills fills each String in the ThreeString to a random String
      threeString.setString1(randString());
      //if mutator call returns false then a default value is used
      if(!threeString.setString1(randString()))
         threeString.setString1("(SPACE)");

      threeString.setString2(randString());
      if(!threeString.setString2(randString()))
         threeString.setString2("(SPACE)");

      threeString.setString3(randString());
      if(!threeString.setString3(randString()))
         threeString.setString3("(SPACE)");

      return threeString;

   }
   /**
    * A Private helper method that generates a random String
    * @return A random string consisting of either "7", "cherries", "Bar", 
    * or "space."
    */
   private static String randString()
   {
      Random random = new Random();
      double randomString = random.nextDouble();

      //checks the random double generated and returns the correct string
      //when true. Added an error statement just in case.
      if(randomString <= .50)
         return "(SPACE)";
      else if(randomString <= .75)
         return "CHERRIES";
      else if(randomString <= .875)
         return "BAR";
      else if(randomString > .875)
         return "7";
      else
         return "ERROR";

      //Ran a test 10000 times for probability: Space appeared 49.48%, 
      //Cherries 25.24%, Bar 13.11%, and 12.18% of the time
   }
   /*
    * This method analysis the players play and calculates what they pay out
    * multiplier will be i.e., how much the bet will be multiplied by.
    * @param ThreeString object
    * @return the pay out multiplier
    */
   static int getPayMultiplier(ThreeString thePull)
   {

      String stringPull1 = thePull.getString1();
      String stringPull2 = thePull.getString2();
      String stringPull3 = thePull.getString3();

      //cherries [not cherries] [any]
      if(stringPull1.equals("CHERRIES") 
         && !stringPull2.equals("CHERRIES"))
         return 5;
      //cherries cherries [not cherries]
      else if(stringPull1.equals("CHERRIES") 
         && stringPull2.equals("CHERRIES") 
         && !stringPull3.equals("CHERRIES"))
         return 15;
      // cherries cherries cherries
      else if(stringPull1.equals("CHERRIES")
         && stringPull2.equals("CHERRIES")
         && stringPull3.equals("CHERRIES"))
         return 30;
      // BAR BAR BAR
      else if(stringPull1.equals("BAR")
         && stringPull2.equals("BAR")
         && stringPull3.equals("BAR"))
         return 50;
      // 7 7 7
      else if(stringPull1.equals("7")
         && stringPull2.equals("7")
         && stringPull3.equals("7"))
         return 100;

      // no winning combinations   
      return 0;
   }
   /**
    * This method displays the winnings in dollars and displays the three
    * Strings inside thePull. Additionally it will display whether the player 
    * won or lost.
    */
   static void display(ThreeString thePull, int winnings)
   {
      //displays thePull
      System.out.println("What did you expect, noise?");
      System.out.println("Fine...");
      System.out.println("Whiirll...spin...beep...bonk...bonk...bonk...");
      System.out.println(thePull.toString());

      if(winnings > 0)
         System.out.println("Yay...you won... $" + winnings + "\n");
      else
         System.out.println("Sorry(not sorry), your money is mine!\n");
   }

}

/**
 * The ThreeString class
 * @author Dan Sedano
 * @version 11/1/19
 *
 */
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
      return string1 + " " + string2 + " " + string3;   
   }

   /**
    * This method will have the players pay out in the array IFF there are
    * less than or equal to 40 pulls.
    * @param winnings
    * @return true if the numPulls variable is over 40 pulls
    */
   public boolean saveWinnings(int winnings)
   {

      //up the numPulls
      numPulls++;

      //checks the numPull variable if it is 40 it will return true 
      //thus making it the last turn
      if(numPulls == MAX_PULLS)
         return true;

      pullWinnings[numPulls] = winnings;

      return false;
   }

   /**
    * Displays the Winnings Score
    * @return String representation of Winnings
    */
   public String displayWinnings()
   {

      String winningsOutput = "";
      int sum = 0;
      final int NEW_LINE = 20;

      for(int x = 0; x < numPulls; x++) 
      {
         //adds the winnings to a int variable
         sum += pullWinnings[x];

         //adds a new line after 20 entries
         if(x == NEW_LINE)
            winningsOutput += "\n";

         //adds the array values to the string variable
         winningsOutput += "$" + pullWinnings[x] + " ";        
      }

      return "\nThank goodness you're done! Go home!\n" 
      + winningsOutput + "\nTotal Winnings: $" + sum;
   }
}
/*****************************OUTPUT 1******************************
Place your bet! Must be 1-100 or enter 0 to exit: 25
What did you expect, noise?
Fine...
Whiirll...spin...beep...bonk...bonk...bonk...
7 (SPACE) CHERRIES
Sorry(not sorry), your money is mine!

Place your bet! Must be 1-100 or enter 0 to exit: -1
Place your bet! Must be 1-100 or enter 0 to exit: 150
Place your bet! Must be 1-100 or enter 0 to exit: 25
What did you expect, noise?
Fine...
Whiirll...spin...beep...bonk...bonk...bonk...
7 (SPACE) 7
Sorry(not sorry), your money is mine!

Place your bet! Must be 1-100 or enter 0 to exit: 45
What did you expect, noise?
Fine...
Whiirll...spin...beep...bonk...bonk...bonk...
(SPACE) (SPACE) (SPACE)
Sorry(not sorry), your money is mine!

Place your bet! Must be 1-100 or enter 0 to exit: 12
What did you expect, noise?
Fine...
Whiirll...spin...beep...bonk...bonk...bonk...
(SPACE) (SPACE) (SPACE)
Sorry(not sorry), your money is mine!

Place your bet! Must be 1-100 or enter 0 to exit: 78
What did you expect, noise?
Fine...
Whiirll...spin...beep...bonk...bonk...bonk...
(SPACE) 7 (SPACE)
Sorry(not sorry), your money is mine!

Place your bet! Must be 1-100 or enter 0 to exit: 65
What did you expect, noise?
Fine...
Whiirll...spin...beep...bonk...bonk...bonk...
(SPACE) CHERRIES (SPACE)
Sorry(not sorry), your money is mine!

Place your bet! Must be 1-100 or enter 0 to exit: 32
What did you expect, noise?
Fine...
Whiirll...spin...beep...bonk...bonk...bonk...
(SPACE) (SPACE) BAR
Sorry(not sorry), your money is mine!

Place your bet! Must be 1-100 or enter 0 to exit: 98
What did you expect, noise?
Fine...
Whiirll...spin...beep...bonk...bonk...bonk...
BAR 7 (SPACE)
Sorry(not sorry), your money is mine!

Place your bet! Must be 1-100 or enter 0 to exit: 12
What did you expect, noise?
Fine...
Whiirll...spin...beep...bonk...bonk...bonk...
(SPACE) (SPACE) (SPACE)
Sorry(not sorry), your money is mine!

Place your bet! Must be 1-100 or enter 0 to exit: 45
What did you expect, noise?
Fine...
Whiirll...spin...beep...bonk...bonk...bonk...
CHERRIES CHERRIES BAR
Yay...you won... $675

Place your bet! Must be 1-100 or enter 0 to exit: 25
What did you expect, noise?
Fine...
Whiirll...spin...beep...bonk...bonk...bonk...
(SPACE) CHERRIES (SPACE)
Sorry(not sorry), your money is mine!

Place your bet! Must be 1-100 or enter 0 to exit: 36
What did you expect, noise?
Fine...
Whiirll...spin...beep...bonk...bonk...bonk...
(SPACE) 7 CHERRIES
Sorry(not sorry), your money is mine!

Place your bet! Must be 1-100 or enter 0 to exit: 14
What did you expect, noise?
Fine...
Whiirll...spin...beep...bonk...bonk...bonk...
(SPACE) (SPACE) 7
Sorry(not sorry), your money is mine!

Place your bet! Must be 1-100 or enter 0 to exit: 74
What did you expect, noise?
Fine...
Whiirll...spin...beep...bonk...bonk...bonk...
CHERRIES 7 CHERRIES
Yay...you won... $370

Place your bet! Must be 1-100 or enter 0 to exit: 85
What did you expect, noise?
Fine...
Whiirll...spin...beep...bonk...bonk...bonk...
(SPACE) (SPACE) (SPACE)
Sorry(not sorry), your money is mine!

Place your bet! Must be 1-100 or enter 0 to exit: 96
What did you expect, noise?
Fine...
Whiirll...spin...beep...bonk...bonk...bonk...
(SPACE) (SPACE) CHERRIES
Sorry(not sorry), your money is mine!

Place your bet! Must be 1-100 or enter 0 to exit: 36
What did you expect, noise?
Fine...
Whiirll...spin...beep...bonk...bonk...bonk...
(SPACE) BAR (SPACE)
Sorry(not sorry), your money is mine!

Place your bet! Must be 1-100 or enter 0 to exit: 45
What did you expect, noise?
Fine...
Whiirll...spin...beep...bonk...bonk...bonk...
BAR BAR 7
Sorry(not sorry), your money is mine!

Place your bet! Must be 1-100 or enter 0 to exit: 12
What did you expect, noise?
Fine...
Whiirll...spin...beep...bonk...bonk...bonk...
BAR 7 BAR
Sorry(not sorry), your money is mine!

Place your bet! Must be 1-100 or enter 0 to exit: 78
What did you expect, noise?
Fine...
Whiirll...spin...beep...bonk...bonk...bonk...
7 CHERRIES CHERRIES
Sorry(not sorry), your money is mine!

Place your bet! Must be 1-100 or enter 0 to exit: 96
What did you expect, noise?
Fine...
Whiirll...spin...beep...bonk...bonk...bonk...
(SPACE) (SPACE) (SPACE)
Sorry(not sorry), your money is mine!

Place your bet! Must be 1-100 or enter 0 to exit: 22
What did you expect, noise?
Fine...
Whiirll...spin...beep...bonk...bonk...bonk...
CHERRIES (SPACE) (SPACE)
Yay...you won... $110

Place your bet! Must be 1-100 or enter 0 to exit: 54
What did you expect, noise?
Fine...
Whiirll...spin...beep...bonk...bonk...bonk...
(SPACE) (SPACE) (SPACE)
Sorry(not sorry), your money is mine!

Place your bet! Must be 1-100 or enter 0 to exit: 85
What did you expect, noise?
Fine...
Whiirll...spin...beep...bonk...bonk...bonk...
BAR (SPACE) CHERRIES
Sorry(not sorry), your money is mine!

Place your bet! Must be 1-100 or enter 0 to exit: 96
What did you expect, noise?
Fine...
Whiirll...spin...beep...bonk...bonk...bonk...
7 (SPACE) BAR
Sorry(not sorry), your money is mine!

Place your bet! Must be 1-100 or enter 0 to exit: 0

Thank goodness you're done! Go home!
$0 $0 $0 $0 $0 $0 $0 $0 $0 $675 $0 $0 $0 $370 $0 $0 $0 $0 $0 $0 
$0 $110 $0 $0 $0 
Total Winnings: $1155
 ********************************************************************/

/*****************************OUTPUT 2******************************
Place your bet! Must be 1-100 or enter 0 to exit: 99
What did you expect, noise?
Fine...
Whiirll...spin...beep...bonk...bonk...bonk...
CHERRIES (SPACE) (SPACE)
Yay...you won... $495

Place your bet! Must be 1-100 or enter 0 to exit: 999
Place your bet! Must be 1-100 or enter 0 to exit: -9
Place your bet! Must be 1-100 or enter 0 to exit: 12
What did you expect, noise?
Fine...
Whiirll...spin...beep...bonk...bonk...bonk...
(SPACE) (SPACE) (SPACE)
Sorry(not sorry), your money is mine!

Place your bet! Must be 1-100 or enter 0 to exit: 45
What did you expect, noise?
Fine...
Whiirll...spin...beep...bonk...bonk...bonk...
BAR (SPACE) (SPACE)
Sorry(not sorry), your money is mine!

Place your bet! Must be 1-100 or enter 0 to exit: 78
What did you expect, noise?
Fine...
Whiirll...spin...beep...bonk...bonk...bonk...
CHERRIES (SPACE) (SPACE)
Yay...you won... $390

Place your bet! Must be 1-100 or enter 0 to exit: 98
What did you expect, noise?
Fine...
Whiirll...spin...beep...bonk...bonk...bonk...
(SPACE) CHERRIES (SPACE)
Sorry(not sorry), your money is mine!

Place your bet! Must be 1-100 or enter 0 to exit: 65
What did you expect, noise?
Fine...
Whiirll...spin...beep...bonk...bonk...bonk...
CHERRIES CHERRIES CHERRIES
Yay...you won... $1950

Place your bet! Must be 1-100 or enter 0 to exit: 32
What did you expect, noise?
Fine...
Whiirll...spin...beep...bonk...bonk...bonk...
CHERRIES BAR (SPACE)
Yay...you won... $160

Place your bet! Must be 1-100 or enter 0 to exit: 14
What did you expect, noise?
Fine...
Whiirll...spin...beep...bonk...bonk...bonk...
7 7 CHERRIES
Sorry(not sorry), your money is mine!

Place your bet! Must be 1-100 or enter 0 to exit: 25
What did you expect, noise?
Fine...
Whiirll...spin...beep...bonk...bonk...bonk...
(SPACE) 7 (SPACE)
Sorry(not sorry), your money is mine!

Place your bet! Must be 1-100 or enter 0 to exit: 36
What did you expect, noise?
Fine...
Whiirll...spin...beep...bonk...bonk...bonk...
(SPACE) (SPACE) (SPACE)
Sorry(not sorry), your money is mine!

Place your bet! Must be 1-100 or enter 0 to exit: 96
What did you expect, noise?
Fine...
Whiirll...spin...beep...bonk...bonk...bonk...
(SPACE) CHERRIES CHERRIES
Sorry(not sorry), your money is mine!

Place your bet! Must be 1-100 or enter 0 to exit: 85
What did you expect, noise?
Fine...
Whiirll...spin...beep...bonk...bonk...bonk...
BAR CHERRIES (SPACE)
Sorry(not sorry), your money is mine!

Place your bet! Must be 1-100 or enter 0 to exit: 74
What did you expect, noise?
Fine...
Whiirll...spin...beep...bonk...bonk...bonk...
(SPACE) (SPACE) (SPACE)
Sorry(not sorry), your money is mine!

Place your bet! Must be 1-100 or enter 0 to exit: 87
What did you expect, noise?
Fine...
Whiirll...spin...beep...bonk...bonk...bonk...
CHERRIES CHERRIES (SPACE)
Yay...you won... $1305

Place your bet! Must be 1-100 or enter 0 to exit: 54
What did you expect, noise?
Fine...
Whiirll...spin...beep...bonk...bonk...bonk...
CHERRIES CHERRIES 7
Yay...you won... $810

Place your bet! Must be 1-100 or enter 0 to exit: 21
What did you expect, noise?
Fine...
Whiirll...spin...beep...bonk...bonk...bonk...
7 CHERRIES (SPACE)
Sorry(not sorry), your money is mine!

Place your bet! Must be 1-100 or enter 0 to exit: 89
What did you expect, noise?
Fine...
Whiirll...spin...beep...bonk...bonk...bonk...
(SPACE) 7 (SPACE)
Sorry(not sorry), your money is mine!

Place your bet! Must be 1-100 or enter 0 to exit: 56
What did you expect, noise?
Fine...
Whiirll...spin...beep...bonk...bonk...bonk...
(SPACE) CHERRIES (SPACE)
Sorry(not sorry), your money is mine!

Place your bet! Must be 1-100 or enter 0 to exit: 23
What did you expect, noise?
Fine...
Whiirll...spin...beep...bonk...bonk...bonk...
(SPACE) BAR CHERRIES
Sorry(not sorry), your money is mine!

Place your bet! Must be 1-100 or enter 0 to exit: 48
What did you expect, noise?
Fine...
Whiirll...spin...beep...bonk...bonk...bonk...
CHERRIES CHERRIES (SPACE)
Yay...you won... $720

Place your bet! Must be 1-100 or enter 0 to exit: 15
What did you expect, noise?
Fine...
Whiirll...spin...beep...bonk...bonk...bonk...
BAR CHERRIES (SPACE)
Sorry(not sorry), your money is mine!

Place your bet! Must be 1-100 or enter 0 to exit: 26
What did you expect, noise?
Fine...
Whiirll...spin...beep...bonk...bonk...bonk...
(SPACE) BAR (SPACE)
Sorry(not sorry), your money is mine!

Place your bet! Must be 1-100 or enter 0 to exit: 73
What did you expect, noise?
Fine...
Whiirll...spin...beep...bonk...bonk...bonk...
(SPACE) (SPACE) (SPACE)
Sorry(not sorry), your money is mine!

Place your bet! Must be 1-100 or enter 0 to exit: 92
What did you expect, noise?
Fine...
Whiirll...spin...beep...bonk...bonk...bonk...
(SPACE) (SPACE) CHERRIES
Sorry(not sorry), your money is mine!

Place your bet! Must be 1-100 or enter 0 to exit: 0

Thank goodness you're done! Go home!
$495 $0 $0 $390 $0 $1950 $160 $0 $0 $0 $0 $0 $0 $1305 $810 $0 $0 $0 $0 $720 
$0 $0 $0 $0 
Total Winnings: $5830

 ********************************************************************/



