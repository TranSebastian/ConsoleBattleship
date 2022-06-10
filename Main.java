import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
        // Create file object
        File dataFile = new File("boats.txt");

        // Title screen
        System.out.println("██████╗░░█████╗░████████╗████████╗██╗░░░░░███████╗░██████╗██╗░░██╗██╗██████╗░\n██╔══██╗██╔══██╗╚══██╔══╝╚══██╔══╝██║░░░░░██╔════╝██╔════╝██║░░██║██║██╔══██╗\n██████╦╝███████║░░░██║░░░░░░██║░░░██║░░░░░█████╗░░╚█████╗░███████║██║██████╔╝\n██╔══██╗██╔══██║░░░██║░░░░░░██║░░░██║░░░░░██╔══╝░░░╚═══██╗██╔══██║██║██╔═══╝░\n██████╦╝██║░░██║░░░██║░░░░░░██║░░░███████╗███████╗██████╔╝██║░░██║██║██║░░░░░\n╚═════╝░╚═╝░░╚═╝░░░╚═╝░░░░░░╚═╝░░░╚══════╝╚══════╝╚═════╝░╚═╝░░╚═╝╚═╝╚═╝░░░░░");
        System.out.println("For the best experience, make the console as large as possible.\nPress Enter to play.");
        Scanner reader = new Scanner (System.in);
        reader.nextLine();
        System.out.print("\033[H\033[2J");
        
        System.out.flush();

        // Create scanner
        Scanner fileReader = new Scanner(dataFile);
        
        // Declare boolean variables that show if the boats are alive
        boolean aSunk = false;
        boolean bSunk = false;
        boolean sSunk = false;
        boolean pSunk = false;
        boolean dSunk = false;

        //parallel 2d arrays
        boolean[][] missileFired = new boolean[10][10]; //stored which coordinates are occupied
        boolean[][] occupied = new boolean[10][10];     //stores which coordinates have a boat on them
        
        //stores your score!
        int score = 0;
        
        // Create ArrayLists
        ArrayList<String> data = new ArrayList<String>();  //this one is just store individual boats first
        
        //all of the boats
        ArrayList<String> aircraftCarrier = new ArrayList<String> (); 
        ArrayList<String> battleship = new ArrayList<String> ();
        ArrayList<String> submarine = new ArrayList<String> ();
        ArrayList<String> destroyer = new ArrayList<String> ();
        ArrayList<String> patrolBoat = new ArrayList<String> ();

        // Fill ArrayList with input at each line
        while(fileReader.hasNextLine()) {
            data.add(fileReader.nextLine());
        }
        
        // Fill individual ship ArrayLists
        for(int i = 0; i < data.size(); i++){
            switch(data.get(i).charAt(0)){
                
                //call a method that will fill the array list
                case 'A':
                    aircraftCarrier = direction (data.get(i));
                    break;
                case 'B': 
                    battleship = direction(data.get(i));
                    break;
                case 'S': 
                    submarine = direction(data.get(i));
                    break;
                case 'P': 
                    patrolBoat = direction(data.get(i));
                    break;
                case 'D':
                    destroyer = direction(data.get(i));
                    break;
            }
        }

        //call a method to set up the board
        occupied = updateOccupied (occupied, aircraftCarrier, battleship, submarine, destroyer, patrolBoat);

        

        //loop as long as the boats still exist
        while (aircraftCarrier.size() != 0 || battleship.size() != 0 || submarine.size() != 0 || destroyer.size() != 0 || patrolBoat.size() != 0 )
        {
            //output the board
            basicOutput(occupied, missileFired);

            //prompt for input
            String coordinate = "";
            boolean lockedIn = true;

            do
            {
                while(!(coordinate.length()>1&&coordinate.length()<4)){
                    System.out.print("Enter a coordinate: ");
                    coordinate = reader.nextLine().toUpperCase();
                    System.out.println(coordinate);
                }

                if (Integer.parseInt(coordinate.substring(1)) > 10 ||  Integer.parseInt(coordinate.substring(1)) -1 < 0  || (int)coordinate.charAt(0)-65 < 0 || (int)coordinate.charAt(0)-65 > 10 )
                {
                    System.out.println("Not valid coordinate!");
                }

                else if ( missileFired[Integer.parseInt(coordinate.substring(1))-1][(int)coordinate.charAt(0)-65])
                {
                    System.out.println("You entered that input already!");
                }

                else 
                {
                    lockedIn = false;    
                }

            }
            while (lockedIn);

            score++;
            
            //update the location where the misile hit
            missileFired[Integer.parseInt(coordinate.substring(1)) -1 ][(int)coordinate.charAt(0)-65] = true;

            System.out.print("\033[H\033[2J");
            System.out.flush();

            //check if the location matches a coordinate without a boat
            if (!aircraftCarrier.contains(coordinate) && !battleship.contains(coordinate) && !submarine.contains(coordinate) && !patrolBoat.contains(coordinate) && !destroyer.contains(coordinate))
            {
                System.out.println("MISS! "+ coordinate);
            }
            //if it doesn't you have a hit!
            else
            {
                System.out.println("HIT! " + coordinate);

                //we don't know which one though, so check if each boat has been hit, and update it
                findTarget(aircraftCarrier, coordinate);
                findTarget(battleship,coordinate);
                findTarget(submarine,  coordinate);
                findTarget(patrolBoat,  coordinate);
                findTarget(destroyer,  coordinate);

                //finally find out which one has been sunk
                if (aircraftCarrier.size() == 0 && !aSunk)
                {
                    System.out.println("You sank the aircraft carrier.");
                    aSunk = true;
                }
                else if (battleship.size() == 0 && !bSunk)
                {
                    System.out.println("You sank the battleship.");
                    bSunk = true;
                }
                else if (submarine.size() == 0 && !sSunk)
                {
                    System.out.println("You sank the submarine.");
                    sSunk = true;
                }
                else if (patrolBoat.size() == 0 && !pSunk)
                {
                    System.out.println("You sank the patrol boat.");
                    pSunk = true;
                }
                else if (destroyer.size() == 0 && !dSunk)
                {
                    System.out.println("You sank the destroyer.");
                    dSunk = true;
                }
            }

        }
        

        //output when all the boats are gone
        basicOutput(occupied, missileFired);

        // Create SebastianTop10 object
        SebastianTop10 theSebastianTop10=new SebastianTop10();
        
        String name = "";

        do 
        {
            System.out.println("Enter your name!");
            name = reader.nextLine();

            if (name.length() > 10)
            {
                System.out.println("Your name is too long, shorten it to 10 characters");
            }
        }
        while (name.length() > 10);

        theSebastianTop10.updateScoreList(score, name);
        
        System.out.println(theSebastianTop10.returnScoreList());
        
    }
    
    // North
    public static ArrayList<String> north(String input){
        
        //makes and fills arraylist with data
        ArrayList<String> inp = new ArrayList<String>();
        Scanner stringReader = new Scanner(input);
        stringReader.useDelimiter(",");
        
        //fills arraylist with data
        while(stringReader.hasNext()){
            inp.add(stringReader.next());
        }
        
        // ArrayList for new spaces
        ArrayList<String> space = new ArrayList<String>();
        
        String add = "";

        //for loop filling spaces taken up
        for(int i = 0;i<shipLength(inp.get(0));i++){
            
            //attach static x coordinate
            add = ""+(char)(inp.get(1).charAt(0)-i) ;

            //changes the coordinate based on direction
            add += inp.get(2); 
            space.add(add);
        }

        return space;
    }
    
    // South
    public static ArrayList<String> south(String input){
        
        //makes and fills arraylist with data
        ArrayList<String> inp = new ArrayList<String>();
        Scanner stringReader = new Scanner(input);
        stringReader.useDelimiter(",");
        
        //fills arraylist with data
        while(stringReader.hasNext()){
            inp.add(stringReader.next());
        }
        
        //fills arraylist with data
        while(stringReader.hasNext()){
            inp.add(stringReader.next());
        }
        
        // ArrayList for new spaces
        ArrayList<String> space = new ArrayList<String>();
        
        String add = "";

        //for loop filling spaces taken up
        for(int i = 0;i<shipLength(inp.get(0));i++){
            
            //attach static x coordinate
            add = ""+(char)(inp.get(1).charAt(0)+i);
            
            //changes the coordinate based on direction
            add += inp.get(2);
            space.add(add);
        }

        return space;
    }
   
    // East
    public static ArrayList<String> east(String input){
        
        //makes and fills arraylist with data
        ArrayList<String> inp = new ArrayList<String>();
        Scanner stringReader = new Scanner(input);
        stringReader.useDelimiter(",");
        
        //fills arraylist with data
        while(stringReader.hasNext()){
            inp.add(stringReader.next());
        }
        
        // ArrayList for new spaces
        ArrayList<String> space = new ArrayList<String>();
        
        String add = "";

        //for loop filling spaces taken up
        for(int i = 0;i<shipLength(inp.get(0));i++){
            
            //attach static y coordinate
            add = inp.get(1);
            
            //changes the coordinate based on direction
            add += ""+(Integer.parseInt(inp.get(2).substring(0))+i);
            space.add(add);
        }

        return space;
    }

    // West
   public static ArrayList<String> west(String input){
        
        //makes and fills arraylist with data
        ArrayList<String> inp = new ArrayList<String>();
        Scanner stringReader = new Scanner(input);
        stringReader.useDelimiter(",");
        
        //fills arraylist with data
        while(stringReader.hasNext()){
            inp.add(stringReader.next());
        }
        
        // ArrayList for new spaces
        ArrayList<String> space = new ArrayList<String>();
        
        String add = "";

        //for loop filling spaces taken up
        for(int i = 0;i<shipLength(inp.get(0));i++){
            
            //attach static y coordinate
            add = inp.get(1);
            
            //changes the coordinate based on direction
            add += ""+(Integer.parseInt(inp.get(2).substring(0))-i);
            space.add(add);
        }

        return space;
    }

    // Calculate length of ship
    public static int shipLength (String ship)
    {
        int length = 0;
        switch (ship)
        {
            //
            case "Aircraft Carrier":
                length = 5;
                break;         

            case "Battleship":
                length = 4;
                break;

            case "Submarine":

            case "Destroyer":
                length = 3;
                break;
                
            case "Patrol Boat":
                length = 2;
                break;
            
            default:
                System.out.println("Error");
                break;
        }

        return length;
    }

    // Find correct direction
    public static ArrayList<String> direction(String inp) {

        switch(inp.charAt(inp.length()-1)) {
                case 'N':
                    return north(inp);
                case 'S': 
                    return south(inp);
                case 'E':
                    return east(inp);
                case 'W':
                    return west(inp);

                default:
                    System.out.println("Error");
                    ArrayList<String> errorList=new ArrayList<String>();
                    return errorList;
            }
    }
    
    // Remove the coordinate from the ship
    public static void findTarget(ArrayList<String> ship, String target){
        if (ship.indexOf(target) != -1)
        {
            ship.remove(target);
        }
    }

    //updates an array, given locations of ship array
    public static boolean[][] updateOccupied (boolean[][] input,  ArrayList<String> aircraftCarrier, ArrayList<String> battleship,ArrayList<String> submarine, ArrayList<String> destroyer, ArrayList<String> patrolBoat)
    {
        //move right down x axis
        for (int x = 0; x < input.length; x++)
        {
            //move down y axis 
            for (int y = 0; y < input[x].length; y++)
            {
                //turn xy into checkable coordinate for method
                String coordinate = ((char) (y+65))+""+(x+1);  
                
                //check if each location is an element of a ship array
                if (aircraftCarrier.indexOf(coordinate) == -1 && battleship.indexOf(coordinate) == -1 && submarine.indexOf(coordinate) == -1 && patrolBoat.indexOf(coordinate) == -1 && destroyer.indexOf(coordinate) == -1)
                {
                    //mark the location
                    input[x][y] = false;
                }
                else
                {
                    input[x][y] = true;
                }
            }
        }

        return input;
    }


    public static void  basicOutput (boolean occupied[][], boolean[][] missileFired)
    {
        System.out.print("\t1\t2\t3\t4\t5\t6\t7\t8\t9\t10\n\n"); //output numbers at the top

        //cycle through element indexes
        for (int y = 0; y < occupied[0].length; y++)
        {
            for (int x = 0; x < occupied.length; x++)
            {   
                //display the correct letter
                if (x == 0)
                {
                    System.out.print(((char)(y+65))+"\t");
                }
                if (occupied[x][y] && missileFired[x][y])
                {
                    System.out.print("X");
                }
                else if(!missileFired[x][y] && !occupied [x] [y])
                {
                    System.out.print("-");
                }
                else if(missileFired[x][y] && !occupied [x] [y])
                {
                    System.out.print("O");
                }
                else
                {
                    System.out.print("-");
                }
                System.out.print("\t");
            }
            System.out.print("\n\n");
        }
    }
}

