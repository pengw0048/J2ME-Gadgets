
import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;

public class Square extends MIDlet
{

	private Display d;
	private SquareC square;

	public Square()
	{
		d = Display.getDisplay(this);
		square = new SquareC();
	}

	public void startApp()
	{
		d.setCurrent(square);
	}

	public void pauseApp()
	{
	}

	public void destroyApp(boolean flag)
	{
	}
}
