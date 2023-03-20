//Postal Code Database
//@author Andrey Skvortsov
//Allows user to add, delete, change or display addresses stored in a database
//March 2023

package u1a3.postal.code.database;
    import java.util.Scanner;

public class U1A3PostalCodeDatabase {
    //Global variable declaration.
        static boolean test; //used at various points throughout the code to avoid declaring a new boolean variable each time.
        static Scanner QWE = new Scanner (System.in); //for strings (userPost, userAddress).
        
        static String [][] database = new String [10][11]; //10 postal codes, each with 10 addresses maximum. Number 11 is used because the postal code itself takes up one index so in reality this allows for 10 addresses to be stored per postal code.

        static String userPost = null; //user's inputted postal codes.
        static String userAddress = null; //user's inputted addresses.
        static String userMenuChoice = null; //user's choice in the main menu.
        
      
        
    //Main method.
    public static void main(String[] args) {
        for (int a = 0; a < database.length; a++)       //initializes entire 2D array so that every element is " ".
           {for (int b = 0; b < database.length + 1; b++)
               {database[a][b] = " ";}
           }
        
        System.out.println("Welcome to the Skvortsov Postal Code Database.");
        boolean x = true;
        while (x) //x set to false when user chooses to quit the program, thus quitting the program.
           {System.out.println("\nPlease select an option:  \n" //main option menu.
                           + "   ADD = add an address\n"
                           + "   DELETE = delete an address\n"
                           + "   EDIT = change an address\n"
                           + "   DISPLAY = display the database\n"
                           + "   QUIT = close the program");
            userMenuChoice = QWE.nextLine();

            switch (userMenuChoice) //calls whichever method the user chooses.
               {case "ADD": case "add": case "Add":
                    addMethod();
                    break;
                
                case "DELETE": case "delete": case "Delete":
                    test = checkEmpty();
                    if (test == true) //if there is nothing to display, database is empty. Program returns to option menu.
                       {System.out.println("\nDatabase Empty. Returning to main menu.\n-----");}
                    else
                       {deleteMethod();
                        System.out.println("\nReturning to main menu.\n-----");}
                    break;
                    
                case "EDIT": case "edit": case "Edit":
                    test = checkEmpty();
                    if (test == true) //if there is nothing to display, database is empty. Program returns to option menu.
                       {System.out.println("\nDatabase Empty. Returning to main menu.\n-----");}
                    else
                       {editMethod();
                        System.out.println("\n\nReturning to main menu.\n-----");}
                    break;
                    
                case "DISPLAY": case "display": case "Display":
                    test = checkEmpty();
                    if (test == true) //if there is nothing to display, database is empty. Program returns to option menu.
                       {System.out.println("\nDatabase Empty. Returning to main menu.\n-----");}
                    else
                       {displayMethod();
                        System.out.println("\n\nReturning to main menu.\n-----");}
                    break;
                    
                case "QUIT": case "quit": case "Quit":
                    System.out.println("Program Closing.");
                    x = false;
                    break;
                    
                default:
                    break;
               }
           }
       
    }
    
    
    
    //Method to add an address to the database.
    public static void addMethod(){
        //First section asks for and receives userPost (a postal code).
        test = true;
        while (test)
           {while (true) //loop broken when valid input is given.
               {try 
                   {do {System.out.println("\nPlease enter the address' postal code in the form A1A 1A1 or enter 'X' to return to the main menu: ");
                        userPost = QWE.nextLine();}
                        while ((userPost.length() != 7 || userPost.charAt(3) != ' ') && !userPost.equals("X") && !userPost.equals("x")); //7 chars + space at third character in 'A1A 1A1' format. if these condtions aren't met, input is invalid. 'do-while' loops repeat to get valid input. If user inputs 'X' or 'x', satisfies conditional loop and triggers main menu return option.
                        break;} //If user inputs less than three chars (i.e. A1) for userPost, second 'while' condition above throws indexoutofboundsexception. 'try-catch' will catch this exception and continue the input loop. If no exception is thrown, input is considered valid and 'break;' command breaks the input loop.
                catch (Exception IndexOutofBoundsException){}
               }
           
            //menu return option.
            if (userPost.equals("X") || userPost.equals("x")) 
               {return;}
            
            test = checkPostCode(userPost); //calls on method to check validity of inputted postal code.
           }
        System.out.println("\nPostal code is valid. Proceeding.");
        
        
        //Second section asks for and receives userAddress (an address). 
        test = true;
        while (test)
           {System.out.println("\nPlease enter the address itself in the form 123 Stellar Drive: "
                             + "\nRequirements: 1) if the street name includes more than one word (i.e. 'Roy Harper' avenue), include a hyphen between each word."
                             + "\n              2) the address must have a specific street type, spelt out fully (i.e. street, boulevard)."
                             + "\nExample:   161 Roy-Harper boulevard = valid.   161 Roy Harper blvd = invalid.");
            userAddress = QWE.nextLine();
            test = spaceCounter(userAddress); //calls on method to check validity of inputted address.
           } 
        System.out.println("\nAddress is valid. Proceeding.");
        
            
        //Third section is an algorithm that stores the newly inputted userPost & userAddress variables.
        test = true;
        for (int a = 0; a < database.length; a++)
           {//before running actual code, conditional below checks if element is empty. If it is empty, avoids iterating through all remaining elements in the array (since they must also be empty) and instead skips to third part of the for loop to automatically enter the new address into the next available location.
            if (database[a][0].equals(" "))    
               {a = database.length - 1;}
            
            //Second part of main for loop checks if userPost already exists in database and stores userAddress under the existing userPost.
            if (database[a][0].equals(userPost))         
               {test = false; //prevents second part of main for loop (used to store new, unexisting userPost) from running since the userPost entered already exists in database.
                for (int b = 1; b < database.length + 1; b++) //(database.length + 1) is used as conditional because database.length = 10 (since database[10][11], length() only sees the 10), but for the second dimension of the array there are 11 indices and the addresses are stored in indices 1 through 11 while index 0 stores the postal code. This loop aims to store addresses and so it must start at 1 and loop while b < database + 1 = 11.
                   {if (database[a][b].equals(" ")) //"if index empty, store new address here." Translation: stores new address in next available location.
                       {database[a][b] = userAddress;
                        break;}
                    else if (b == database.length)
                       {System.out.println("Postal code " + userPost + " is full, address " + userAddress + " cannot be added. Returning to main menu.");}
                   }
                a = database.length;    //this line will ensure loop breaks as userPost and userAddress have been successfully stored or userPost is full and user must be returned to main menu.
               }
            
            //Third part of main for loop only runs if userPost does not already exist in the database. It stores the new userPost and userAddress variables in a new location.
            if ((a == database.length - 1) && (test == true))
               {for (int b = 0; b < database.length; b++) //iterates through database to find available location for new userPost & userAddress.
                   {if (database[b][0].equals(" "))
                       {database[b][0] = userPost;
                        database[b][1] = userAddress; //since this is a new postal code, it has no assigned addresses yet so no need to look for an empty index to store new address; address takes the available first spot which is coordinate [b][1].
                        break;}
                    else if (b == database.length - 1)
                       {System.out.println("Database is full and new postal code " + userPost + " cannot be added. Returning to main menu.");}
                   }
               }
           }
        
    }
 
    
    
    //Method to delete an existing address from the database.
    public static void deleteMethod(){
        String deleteQueryPost = " ", deleteQueryAddress = " ";
        int deleteQueryPostNumber = -1, deleteQueryAddressNumber = -1; //-1 ensures that, when invalid input is given, input loop while conditional continues asking for input.
        
        //First section asks user which address they want to delete.
        test = true;
        while (test)
           {do
               {try
                   {displayMethod(); //displays entire database for user to choose.
                    System.out.println("\nPlease enter two numbers:"
                                     + "\n1. The index of the postal code in which lies the address you would like to delete."
                                     + "\n2. The index of the address itself."
                                     + "\nYou can also enter '0' to return to the main menu. ");
                    deleteQueryPost = QWE.nextLine();
                    deleteQueryPostNumber = Integer.valueOf(deleteQueryPost);

                    //menu return option.
                    if (deleteQueryPostNumber == 0) 
                       {return;}

                    deleteQueryAddress = QWE.nextLine();
                    deleteQueryAddressNumber = Integer.valueOf(deleteQueryAddress);

                    //menu return option.
                    if (deleteQueryAddressNumber == 0) 
                       {return;}}
                catch (Exception NumberFormatException){System.out.println("Invalid input.");} //catch if user inputs anything other than just an integer.
               }
            while (deleteQueryPostNumber > database.length 
                   || deleteQueryAddressNumber > database.length
                   || deleteQueryPostNumber < 0
                   || deleteQueryAddressNumber < 0);   //if user inputs a negative number or a number greater than what is stored in the database, input request loop repeats.
            
            test = false; //once input loop is complete with valid input, this will stop main while loop before next conditional confirms whether or not main while loop must rerun for input.
           
            //If user chooses a database index that is empty, input loop will be forced to repeat by setting "test" back equal to true.
            if (database[deleteQueryPostNumber - 1][deleteQueryAddressNumber].equals(" ") && test == false)
               {System.out.println("The chosen index is already empty.");
                test = true;}
           }
        
        
        //Second section deletes requested address then moves all addresses in the postal code one spot over to fill empty spot created by the deletion.
        database[deleteQueryPostNumber - 1][deleteQueryAddressNumber] = " "; 
        System.out.println("Address cleared.");
        
        for (int a = 0; a < (database.length - deleteQueryAddressNumber); a++) //to avoid IndexoutofBoundsException, a must reach a maximum of the difference between the address' index and the total # of indices.
           {database[deleteQueryPostNumber - 1][deleteQueryAddressNumber + a] = database[deleteQueryPostNumber - 1][deleteQueryAddressNumber + a + 1];
            
            if (database[deleteQueryPostNumber - 1][deleteQueryAddressNumber + a].equals(" ")) //if index [a] is empty after moving index [a + 1] to index [a], this would mean that all further indices are also empty so no point continuing the for loop and carrying empty spots over.
               {break;}
           }
        
        
        //Third section carries out  same function as second section's for loop but only if the postal code of focus is empty (has no more addresses stored in it). The postal code will thus also be deleted by moving all postal codes after it one spot over.
        if (database[deleteQueryPostNumber - 1][1].equals(" "))
           {System.out.println("Postal code " +  database[deleteQueryPostNumber - 1][0] + " is now empty and will also be deleted.");
            
            //Shifts over each postal code.
            for (int a = 0; a < database.length - (deleteQueryPostNumber - 1); a++)
               {database[deleteQueryPostNumber - 1 + a][0] = database[deleteQueryPostNumber + a][0];
                
                //Shifts over each postal code's addresses.
                for (int b = 1; b < database.length; b++)  
                   {database[deleteQueryPostNumber - 1 + a][b] = database[deleteQueryPostNumber + a][b];
                   }
                
                if (database[deleteQueryPostNumber - 1 + a][0].equals(" ")) //if index [a] is empty after moving index [a + 1] to index [a], this would mean that all further indices are also empty so no point continuing the for loop and carrying empty spots over. 'Break' stops this looped process.
                   {break;}
               }
           }
        
    }
    
    
    
    //Method to change an existing address stored in the database.
    public static void editMethod(){
        String editQueryPost = " ", editQueryAddress = " "; 
        int editQueryPostNumber = -1, editQueryAddressNumber = -1; //-1 ensures that, when invalid input is given, first while conditional in second section of this method continues asking for input.
        String choice = " "; int choiceNumber = 0;
        
        //First input loop asks if user wants to change a postal code or an address itself.
        do
           {System.out.println("\nEnter 1 to change a postal code or enter 2 to change an address."
                                 + "\nYou can also enter '0' to return to the main menu.");
            choice = QWE.nextLine();
            try
               {choiceNumber = Integer.valueOf(choice);
                
                //menu return option.
                if (choiceNumber == 0)
                   {return;}}
            catch (Exception NumberFormatException){System.out.println("Invalid input.");} //catch if user inputs anything other than just an integer
           }
        while (choiceNumber != 1 && choiceNumber != 2);   
        
        
        //Runs either postal code editor (case 1) or address editor (default case) based on what number user inputs in the loop above.
        switch (choiceNumber)
           {case 1:
                //Same input loop as in deleteMethod but with a different variable (editQueryPost instead of deleteQueryPost) + this loop does not ask for address index since user only wishes to change a postal code.
                test = true;
                while (test)
                   {do
                       {try
                           {displayMethod(); //displays entire database for user to choose an address from.
                            System.out.println("\nPlease enter the index of the postal code you would like to change: ");
                            editQueryPost = QWE.nextLine(); 
                            editQueryPostNumber = Integer.valueOf(editQueryPost);

                            //menu return option.
                            if (editQueryPostNumber == 0) 
                               {return;}}
                        catch (Exception NumberFormatException){System.out.println("Invalid input.");} //if user inputs anything other than just an integer.
                       }
                    while (editQueryPostNumber > database.length || editQueryPostNumber < 0); //if user inputs a negative number or a number greater than what is stored in the database, input request loop repeats.
                    
                    test = false; //once input loop is complete with valid input, this will stop main while loop before next conditional confirms whether or not main while loop must rerun for input.
                    
                    //If user chooses a database index that is empty, input loop will be forced to repeat by setting "test" back equal to true.
                    if (database[editQueryPostNumber - 1][0].equals(" "))
                       {System.out.println("The chosen index is empty.");
                        test = true;}
                   }   
                
                
                //Asks for new, 'edited' postal code.
                test = true;
                while (test)
                    {while (true) //loop broken when valid input is given.
                       {try 
                           {do {System.out.println("\nPlease enter the new postal code in the form A1A 1A1: ");
                                userPost = QWE.nextLine();}
                                while ((userPost.length() != 7 || userPost.charAt(3) != ' ')); //7 chars + space at third character in 'A1A 1A1' format. if these condtions aren't met, input is invalid. 'do-while' loops repeat to get valid input. If user inputs 'X' or 'x', satisfies conditional loop and triggers main menu return option.
                                break;} //If user inputs less than three chars (i.e. A1) for userPost, second 'while' condition above throws indexoutofboundsexception. 'try-catch' will catch this exception and continue the input loop. If no exception is thrown, input is considered valid and 'break;' command breaks the input loop.
                        catch (Exception IndexOutofBoundsException){}
                       }
           
                    test = checkPostCode(userPost); //calls on method to check validity of inputted postal code.
                   }
                
                
                //Final step. Replaces old postal code with new one.
                System.out.println("\nNew postal code is valid. Proceeding. ");
                database[editQueryPostNumber - 1][0] = userPost;
                break;
                
            default:
                //Same input loop as in deleteMethod but with different variables (editQueryPost & editQueryAddress instead of deleteQueryPost & deleteQueryAddress).
                test = true;
                while (test)
                   {do
                       {try
                           {displayMethod(); //displays entire database for user to choose an address from.
                            System.out.println("\nPlease enter two numbers:"
                                             + "\n1. The index of the postal code in which lies the address you would like to change."
                                             + "\n2. The index of the address itself."
                                             + "\nYou can also enter '0' to return to the main menu: ");
                            editQueryPost = QWE.nextLine(); 
                            editQueryPostNumber = Integer.valueOf(editQueryPost);

                            //menu return option.
                            if (editQueryPostNumber == 0) 
                               {return;}

                            editQueryAddress = QWE.nextLine();
                            editQueryAddressNumber = Integer.valueOf(editQueryAddress);

                            //menu return option.
                            if (editQueryAddressNumber == 0) 
                               {return;}}
                        catch (Exception NumberFormatException){System.out.println("Invalid input.");} //if user inputs anything other than just an integer.
                       }
                    while (editQueryPostNumber > database.length 
                           || editQueryAddressNumber > database.length
                           || editQueryPostNumber < 0
                           || editQueryAddressNumber < 0);   //if user inputs a negative number or a number greater than what is stored in the database, input request loop repeats.
                    
                    test = false; //once input loop is complete with valid input, this will stop main while loop before next conditional confirms whether or not main while loop will rerun for input.
                    
                    //If user chooses a database index that is empty, input loop will be forced to repeat by setting "test" back equal to true.
                    if (database[editQueryPostNumber - 1][editQueryAddressNumber].equals(" "))
                       {System.out.println("The chosen index is empty.");
                        test = true;}
                   }   
                
                
                //Asks for new, 'edited' address
                test = true;
                while (test)
                   {System.out.println("\nPlease enter the new address in the form 123 Stellar Drive: "
                                     + "\nRequirements: 1) if the street name includes more than one word (i.e. 'Roy Harper' avenue), include a hyphen between each word."
                                     + "\n              2) the address must have a specific street type, spelt out fully (i.e. street, boulevard)."
                                     + "\nExample:   161 Roy-Harper boulevard = valid.   161 Roy Harper blvd = invalid.");
                    userAddress = QWE.nextLine();
                    test = spaceCounter(userAddress); //calls on method to check validity of inputted address.
                   } 
                
                
                //Final step. Replaces old address with new one.
                System.out.println("\nNew address is valid. Proceeding.");
                database[editQueryPostNumber - 1][editQueryAddressNumber] = userAddress;
                break;
           }
        
    }
    
    
    
    //Method to display all existing addresses in the database.
    public static void displayMethod(){
        //Main loop loops through all postal codes.
        for (int b = 0; b < database.length; b++)
           {//Checks if postal code index is empty. If postal code index is empty, all further indices in database are empty so stop displaying things.
            if (database[b][0].equals(" "))         
               {break;}
           
            System.out.println("\n" + (b+1) + ". " + database[b][0]);      //displays each postal code as a heading.
            
            //Second loop loops through all addresses in each postal code.
            for (int c = 1; c < database.length + 1; c++)
               {if (database[b][c].equals(" "))
                   {break;}
               
                System.out.println("     " + c + ". " + database[b][c]); //displays each address as subheading of its postal code.
               }     
           }
    
    }
    
    
    
    //Method checking for valid adress input.
    public static boolean spaceCounter(String address) {
        //First section splits address into its three parts (house #, street name, street type).
        String [] spaces = address.split(" ", -1); 
        
        //Second section checks to ensure user's address consists of a number, street name and street type (i.e. avenue, drive) and nothing more.
        if (spaces.length == 3 && (spaces[2].equalsIgnoreCase("CIRCLE")     || spaces[2].equalsIgnoreCase("SQUARE")     || 
                                   spaces[2].equalsIgnoreCase("CRESCENT")   || spaces[2].equalsIgnoreCase("ROW")        ||      
                                   spaces[2].equalsIgnoreCase("COURT")      || spaces[2].equalsIgnoreCase("RUN")        ||          
                                   spaces[2].equalsIgnoreCase("CURVE")      || spaces[2].equalsIgnoreCase("PARK")       ||         
                                   spaces[2].equalsIgnoreCase("CROSSING")   || spaces[2].equalsIgnoreCase("BYPASS")     ||      
                                   spaces[2].equalsIgnoreCase("STREET")     || spaces[2].equalsIgnoreCase("TRAIL")      ||        
                                   spaces[2].equalsIgnoreCase("ROAD")       || spaces[2].equalsIgnoreCase("WAY")        ||          
                                   spaces[2].equalsIgnoreCase("DRIVE")      || spaces[2].equalsIgnoreCase("HIGHWAY")    ||      
                                   spaces[2].equalsIgnoreCase("AVENUE")     || spaces[2].equalsIgnoreCase("RIDGE")      ||        
                                   spaces[2].equalsIgnoreCase("BOULEVARD")  || spaces[2].equalsIgnoreCase("TURN")       ||         
                                   spaces[2].equalsIgnoreCase("ALLEY")      || spaces[2].equalsIgnoreCase("TURNPIKE")   ||     
                                   spaces[2].equalsIgnoreCase("PARKWAY")    || spaces[2].equalsIgnoreCase("VALLEY")     ||       
                                   spaces[2].equalsIgnoreCase("LINE")       || spaces[2].equalsIgnoreCase("ESTATES")    ||      
                                   spaces[2].equalsIgnoreCase("LANE")       || spaces[2].equalsIgnoreCase("MOTORWAY")   ||    
                                   spaces[2].equalsIgnoreCase("LOOP")       || spaces[2].equalsIgnoreCase("LANDING")    ||     
                                   spaces[2].equalsIgnoreCase("SIDEROAD")   || spaces[2].equalsIgnoreCase("PLAZA")
                                  )) //massive conditional ensures street type is an actual street type.
           {try
               {int test = Integer.valueOf(spaces[0]); 
                return false;} //if user address has the three required parts and the first part (house #) is an integer, returning false stops the input request loop and proceeds with the valid user input.
            catch (Exception InputMismatchException){return true;}}//if house number is not an integer, 'try' will throw this exception and returning true continues input request loop in whatever method called this method.
        
        else {return true;} //if user address does not have the three required parts, returning true continues input request loop.
    }
    
    
    
    //Method checking for valid postal code input.
    public static boolean checkPostCode(String myCode) {
        //First section condenses postal code from "A1A 1A1" to "A1A1A1" for easier error checking via character iteration.
        myCode = myCode.replaceFirst(" ", "");
        
        //Second section runs until every char in the postal code has been checked.
        for (int i = 0; i < myCode.length(); i++)
           {if (i % 2 == 0) //checks if character is odd or even. If even, it should be a letter.
               {switch (myCode.charAt(i)) //checks for letter.
                   {case 'A':case 'B':case 'C':case 'D':case 'E':case 'F':case 'G':case 'H':case 'I':case 'J':case 'K':case 'L':case 'M':case 'N':case 'O':case 'P':case 'Q':case 'R':case 'S':case 'T':case 'U':case 'V':case 'W':case 'X':case 'Y':case 'Z':
                        break;
                    default:
                        i = myCode.length(); //forces for loop to break since character is not a capital letter, user will have to input again.
                        break; 
                   } 
               } 
                            
            else 
               {switch (myCode.charAt(i)) //checks for number.
                   {case '0':case '1':case '2':case '3':case '4':case '5':case '6':case '7':case '8':case '9':
                        break;
                    default:
                        i = myCode.length();  //forces for loop to break since char is not a number, user will have to input again.
                        break; 
                   }
               }
                            
            if (i == myCode.length() - 1) {return false;} //this is only reached if character being read by for loop is valid. to translate the code: "if this part is reached when the for loop has iterated through the whole string already; return false since no errors have been found in the string." This will return saying postal code inputted is valid.           
           }
        return true; //if returns true, input was invalid, need new input. If 'if' conditional above is satisfied, method returns false and input is considered valid.
    }
    
    
    
    //Method to check if database is empty.
    public static boolean checkEmpty(){
        if (database[0][0].equals(" ")) //if first index is empty, all indices are empty. Only need to look at the first index.
           {return true;}
        return false; //no need to put "else". This will only run if "if" is unsatisfied anyways.
          
    }
    
    
    
    
    
    
}
