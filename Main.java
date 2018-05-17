import java.awt.Color;
import java.awt.Toolkit;
import java.io.FileNotFoundException;

import javax.swing.JFrame;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Controller;
import org.lwjgl.input.Controllers;

public class Main {
	
	static Controller controller; 


	public static void main(String[] args) throws FileNotFoundException, LWJGLException {
		JFrame obj = new JFrame();
		Gameplay gameplay = new Gameplay(); 
		
		
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		
		obj.setBounds(10, 10, width, height);
		
		obj.setTitle("SKN Telin - snake");
		obj.setLocation(0,0);	
		obj.setBackground(Color.LIGHT_GRAY);
		obj.setResizable(false);
		obj.setVisible(true);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		obj.add(gameplay);
		
		/*Controllers.create();
		
		
		Controllers.poll();
		
		for(int i=0; i<Controllers.getControllerCount(); i++)
		{
			controller = Controllers.getController(i);
			System.out.println(controller.getName());
		}*/
	}

}
