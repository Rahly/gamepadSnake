import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.File;


public class ScoreFile {

	static int score;
	private static Scanner in;
	
	public static void savetoFile() throws FileNotFoundException 
	{
		score = Gameplay.bestscore;
		PrintWriter zapis = new PrintWriter("score.txt");
		  zapis.println(score);
		  zapis.close();
	}
	
	public static void readFromFile() throws FileNotFoundException
	{
		File file = new File("score.txt");
		in = new Scanner(file);

		Gameplay.bestscore = in.nextInt();
	}

}
