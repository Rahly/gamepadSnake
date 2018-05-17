import org.lwjgl.LWJGLException;
import org.lwjgl.input.Controller;
import org.lwjgl.input.Controllers;

public class useController {
	
	static Controller controller; 

	
	public static void initController() throws LWJGLException
	{
		Controllers.create();
		
		
		Controllers.poll();
		
		for(int i=0; i<Controllers.getControllerCount(); i++)
		{
			controller = Controllers.getController(i);
		}
		//controller = Controllers.getController(1);
	}
	
}



