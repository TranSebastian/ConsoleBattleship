import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.io.PrintWriter;
// this is the original main.java. Test.java was renamed to main
public class OldMain {

    public static void main(String[] args) throws IOException {

        SebastianTop10 test = new SebastianTop10 ();
        test.updateScoreList(10, "sebastian");
        System.out.println(test.names(0));
        System.out.println(test.scores(0));

        // Create file object
        File dataFile = new File("boats.txt");

        // Create scanner
        Scanner fileReader = new Scanner(dataFile);

        // Declare variables to see if boats are sunk
        boolean aSunk = false;
        boolean bSunk = false;
        boolean sSunk = false;
        boolean pSunk = false;
        boolean dSunk = false;
        
        // Create ArrayLists
        ArrayList <String> data = new ArrayList<String>();
        
        ArrayList <String> aircraftCarrier = new ArrayList <String> ();
        ArrayList <String> battleship = new ArrayList <String> ();
        ArrayList <String> submarine = new ArrayList <String> ();
        ArrayList <String> destroyer = new ArrayList <String> ();
        ArrayList <String> patrolBoat = new ArrayList <String> ();

        // Fill ArrayList with input at each line
        while(fileReader.hasNextLine()) {
            data.add( fileReader.nextLine() );
        }
        
        // Fill individual ship ArrayLists
        for(int i = 0;  i < data.size();    i++){
            switch(data.get(i).charAt(0)){
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

        // Output ArrayLists
        System.out.println(aircraftCarrier+"\n"+battleship+"\n"+submarine+"\n"+destroyer+"\n"+patrolBoat);

        // Take input
        Scanner reader = new Scanner (System.in);

        
        while (aircraftCarrier.size() != 0 || battleship.size() != 0 || submarine.size() != 0 || destroyer.size() != 0 || patrolBoat.size() != 0 )
        {

            System.out.print("Enter a coordinate: ");
            String coordinate = reader.nextLine();


            // if (aircraftCarrier.indexOf(coordinate) == -1 && battleship.indexOf(coordinate) == -1 && submarine.indexOf(coordinate) == -1  && patrolBoat.indexOf(coordinate) == -1  && destroyer.indexOf(coordinate) == -1 )
            if (!aircraftCarrier.contains(coordinate) && !battleship.contains(coordinate) && !submarine.contains(coordinate) && !patrolBoat.contains(coordinate) && !destroyer.contains(coordinate))
            {
                System.out.println("MISS!");
            }

            else
            {
                System.out.println("HIT!");
                findTarget( aircraftCarrier, coordinate );
                findTarget( battleship,coordinate );
                findTarget( submarine,  coordinate );
                findTarget( patrolBoat,  coordinate );
                findTarget( destroyer,  coordinate );
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

            // Output ArrayLists
            System.out.println(aircraftCarrier+"\n"+battleship+"\n"+submarine+"\n"+destroyer+"\n"+patrolBoat);
        }
        
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
            add += ""+(char)(inp.get(2).charAt(0)+i);
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
            add += ""+(char)(inp.get(2).charAt(0)-i);
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

}
