import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.FileWriter; 
// https://docs.oracle.com/javase/8/docs/api/java/io/File.html
class SebastianTop10
{
    private ArrayList <String> names = new ArrayList <String>();
    private ArrayList <Integer> scores = new ArrayList <Integer>();

    //constructor
    public SebastianTop10 () throws IOException
    {
        //test if the file exists
        File file = new File ("top10.txt");
        if (!file.exists())
        {
            //create a new one if it doesnt
            PrintWriter create = new PrintWriter ("top10.txt");
            for (int count = 1; count <= 10; count++)
            {
                create.println("----------: 999");
            }
            create.close();

        }

        //put data into arraylists
        Scanner fileReader = new Scanner (new File ("top10.txt"));

        while (fileReader.hasNextLine())
        {
            String[] convert = fileReader.nextLine().split(": ");
            names.add(convert[0]);
            scores.add(Integer.parseInt(convert[1]));
        }

        fileReader.close();
            
    }

    //setters

    //takes in a score and a name to update them in the file
    public void updateScoreList (int score, String name) throws IOException
    {   
        //test if input is smaller at any point
        for (int index = 0; index < scores.size(); index++)
        {
            if (score <= scores.get(index))
            {
                //add new elements
                scores.add(index, score);
                names.add(index, name);

                //copensate for the array getting larger
                scores.remove(10);
                names.remove(10);
                break;
            }
        }
        
        //create a new file to update results
        PrintWriter reallyOk = new PrintWriter("top10.txt");

        //print new data to file
        for (int count = 0; count < scores.size(); count++)
        {
            reallyOk.println(names.get(count)+": "+scores.get(count));
        }

        reallyOk.close();
    }

    //getters
    public String returnScoreList ()throws IOException
    {
        Scanner reader = new Scanner (new File ("top10.txt"));
        String table = "";
        
        while (reader.hasNextLine())
        {
            table += reader.nextLine()+"\n";
        }

        reader.close();
        return table.trim();
    }

    public ArrayList <String> names ()
    {
        return names;
    }

    public String names (int index)
    {
        return names.get(index);
    }

    public ArrayList <Integer> scores ()
    {
        return scores;
    }

    public Integer scores (int index)
    {
        return scores.get(index);
    }

    


    
}