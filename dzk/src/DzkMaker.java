// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

import java.io.PrintStream;
import java.util.Random;
import javax.microedition.lcdui.*;
import javax.microedition.midlet.MIDlet;
import javax.microedition.rms.RecordStore;

public class DzkMaker extends MIDlet
	implements CommandListener
{

	private Form f;
	private Command c1;
	private Command c2;
	private Alert a;
	private RecordStore rs;
	private Random r;

	public DzkMaker()
	{
		f = new Form("DzkMaker");
		r = new Random();
		c1 = new Command("���ɵ�ͼ", 8, 1);
		c2 = new Command("�����¼", 8, 1);
		f.addCommand(c1);
		f.addCommand(c2);
		f.setCommandListener(this);
	}

	public void startApp()
	{
		Display.getDisplay(this).setCurrent(f);
	}

	public void pauseApp()
	{
	}

	public void destroyApp(boolean flag)
	{
	}

	public void commandAction(Command command, Displayable displayable)
	{
		if (command == c1)
		{
			byte abyte0[] = new byte[96];
			for (int i = 0; i < 96; i++)
				abyte0[i] = (byte)Math.abs(r.nextInt() % 2);

			try
			{
				RecordStore.deleteRecordStore("Dzk");
			}
			catch (Exception exception2)
			{
				System.out.println(exception2.toString());
			}
			try
			{
				rs = RecordStore.openRecordStore("Dzk", true);
				rs.addRecord(abyte0, 0, 96);
				rs.addRecord(itb(9999), 0, 4);
				rs.closeRecordStore();
				a = new Alert("�ɹ�", "������ϣ�", null, AlertType.INFO);
				a.setTimeout(1000);
				Display.getDisplay(this).setCurrent(a);
			}
			catch (Exception exception3)
			{
				a = new Alert("Error", "����" + exception3.toString(), null, AlertType.INFO);
				a.setTimeout(1000);
				Display.getDisplay(this).setCurrent(a);
			}
		} else
		{
			try
			{
				rs = RecordStore.openRecordStore("Dzk", false);
			}
			catch (Exception exception)
			{
				a = new Alert("Error", "���󣬿���û�е�ͼ��" + exception.toString(), null, AlertType.INFO);
				a.setTimeout(1000);
				Display.getDisplay(this).setCurrent(a);
			}
			try
			{
				rs.setRecord(2, itb(9999), 0, 4);
				rs.closeRecordStore();
				a = new Alert("�ɹ�", "����ɹ���", null, AlertType.INFO);
				a.setTimeout(1000);
				Display.getDisplay(this).setCurrent(a);
			}
			catch (Exception exception1)
			{
				a = new Alert("Error", "����" + exception1.toString(), null, AlertType.INFO);
				a.setTimeout(1000);
				Display.getDisplay(this).setCurrent(a);
			}
		}
	}

	public byte[] itb(int i)
	{
		byte abyte0[] = new byte[4];
		abyte0[0] = (byte)(0xff & i >> 24);
		abyte0[1] = (byte)(0xff & i >> 16);
		abyte0[2] = (byte)(0xff & i >> 8);
		abyte0[3] = (byte)(0xff & i >> 0);
		return abyte0;
	}
}
