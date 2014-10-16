// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;

public class Dzk extends MIDlet
{

	private Display d;
	private DzkC c;

	public Dzk()
	{
		d = Display.getDisplay(this);
		c = new DzkC();
	}

	public void startApp()
	{
		d.setCurrent(c);
	}

	public void pauseApp()
	{
	}

	public void destroyApp(boolean flag)
	{
	}
}
