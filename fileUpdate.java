import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Arrays;
class fileUpdate{
    //updating the top 10 file
    public static void main(String[] args)throws IOException{

        

        //create 2 file objects
        File top = new File("top10.txt");
        File newTop = new File("top10_2.txt");
        if(!top.exists()){
            top.createNewFile();
        }
        //create the 2nd file, a temperary file
        newTop.createNewFile();

        //scanner to put current file into an array
        Scanner topReader = new Scanner(top);
        String[] nums = new String[10];
        int count = 0;
        while(topReader.hasNextLine()){
            nums[count]=topReader.nextLine();
            count++;
        }

        //temperary to let run here :/
        Scanner reader = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String name = reader.nextLine();
        System.out.print("Enter your score: ");
        int score = reader.nextInt();

        System.out.println(Arrays.toString(nums));

        //makes the array into the new top 10 list
        new10(name,score,nums);
        //System.out.println(Arrays.toString(nums));

        //try to put the new top 10 into the file
        try{
            FileWriter fileWriter = new FileWriter(newTop);
            
            BufferedWriter buffWriter = new BufferedWriter(fileWriter);
            //FileReader fileReader = new FileReader("top10_2.txt");
            //BufferedReader buffReader = new BufferedReader(fileReader);
            //PrintWriter printWriter = new PrintWriter("top10_2.txt");

            //writes top 10 to "top10_2.txt"
            for(int i = 0;i<nums.length;i++){
                fileWriter.write(nums[i]+"\n");
                System.out.println(i);
            }
            //closes the buffered writer
            buffWriter.close();
            
            // renames "top10_2.txt" and deletes
            newTop.renameTo(top); 
            // System.out.println("File created?");

            
            // System.out.println("old file deleted");
        }
        //catch if you get an error writing to the file
        catch(IOException e){
            System.out.println("error occured");
        }
        
    }
    public static void new10(String name, int score, String[] nums){
        int spot = 0;
        if(score<Integer.parseInt(nums[9].substring(13))){
            //System.out.println(spot);
            for(int i = 0;i<nums.length;i++){
                if(score>Integer.parseInt(nums[i].substring(13))){
                    spot++;
                    //System.out.println(spot);
                }
            }
            for(int i = nums.length-1;i>=spot;i--){
                if(i!=0){
                   nums[i]=nums[i-1]; 
                }
            }
            if(name.length()!=10){
                for(int i = name.length();i<=10;i++){
                    name+=" ";
                }
               nums[spot]=name+": "+score; 
            }
            nums[spot]=name+": "+score;
        }
    }
}