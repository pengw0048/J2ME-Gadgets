// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

import java.io.PrintStream;
import javax.microedition.io.StreamConnection;
import javax.microedition.lcdui.*;
import javax.microedition.midlet.MIDlet;
import javax.microedition.rms.RecordStore;

public class BTPack extends MIDlet
	implements CommandListener, ItemCommandListener
{

	private static final int gn = 1;
	public Display d;
	private RecordStore rs;
	private TextBox tb2;
	private Image logo;
	private Image icon[];
	private Ticker t;
	private Form f1;
	private Form f2;
	private List l;
	private int mm;
	private int om;
	private int num;
	private int numo;
	private boolean chose;
	private boolean is;
	private boolean oy;
	private boolean cu;
	private boolean ingame;
	private Server s;
	private Client c;
	private Connection co;
	private Jsb g0;
	private KFC g1;
	private Jq g2;
	private TQ g3;
	private Jq2 g4;
	private Command conn;
	private Command ok;
	private Command play;
	private Alert a;
	private StringItem si1;
	private StringItem si2;

	public BTPack()
	{
		d = Display.getDisplay(this);
		ok = new Command("OK", 4, 1);
		icon = new Image[10];
		try
		{
			logo = Image.createImage("/logo.png");
			for (int i = 0; i < 5; i++)
				icon[i] = Image.createImage("/" + i + ".png");

		}
		catch (Exception exception) { }
/*		try
		{
			rs = RecordStore.openRecordStore("std", false);
		}
		catch (Exception exception1)
		{
			try
			{
				rs = RecordStore.openRecordStore("std", true);
				rs.addRecord(itb(50), 0, 4);
			}
			catch (Exception exception3) { }
		}
		try
		{
			mm = bti(rs.getRecord(1));
			startForm();
		}
		catch (Exception exception2) { }
*/		startForm();
	}

	private void startForm()
	{
		f1 = new Form("BTPack");
			f2 = new Form("Connecting");
		ImageItem imageitem = new ImageItem(null, logo, 515, null);
		t = new Ticker("Money:" + mm);
		f1.setTicker(t);
		si1 = new StringItem(null, "Server", 2);
		si2 = new StringItem(null, "Client", 2);
		conn = new Command("Connect", 4, 1);
		si1.addCommand(conn);
		si1.setDefaultCommand(conn);
		si1.setItemCommandListener(this);
		si2.addCommand(conn);
		si2.setDefaultCommand(conn);
		si2.setItemCommandListener(this);
		f1.append(imageitem);
		f1.append(si1);
		f1.append(si2);
		f1.setCommandListener(this);
		d.setCurrent(f1);
	}

	public void commandAction(Command command, Displayable displayable)
	{
		if (chose)
			return;
		if (command == ok)
		{
/*			if (Integer.parseInt(tb2.getString()) > mm)
			{
				a = null;
				a = new Alert("Error", "Not enough!", null, AlertType.ERROR);
				a.setTimeout(1500);
				d.setCurrent(a);
				return;
			}
*/			send(2539);
			chose = true;
			tb2.setTicker(t);
			if (oy)
			{
				chose = false;
				oy = false;
				real();
			}
		}
		if (command == play)
		{
			num = l.getSelectedIndex();
			send(num + 0x5f5e100);
			t.setString("Waiting...");
			chose = true;
			if (oy)
			{
				chose = false;
				oy = false;
				start();
			}
		}
	}

	public void commandAction(Command command, Item item)
	{
		if (item == si1)
		{
			is = true;
			s = new Server(this);
			d.setCurrent(f2);
/*			f1 = null;
			si1 = null;
			si2 = null;
*/			return;
		} else
		{
			is = false;
			c = new Client(this);
			d.setCurrent(f2);
/*			f1 = null;
			si1 = null;
			si2 = null;
*/			return;
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

	public int bti(byte abyte0[])
	{
		int i = abyte0[0] & 0xff;
		i = (i << 8) + (abyte0[1] & 0xff);
		i = (i << 8) + (abyte0[2] & 0xff);
		i = (i << 8) + (abyte0[3] & 0xff);
		return i;
	}

	public void got(StreamConnection streamconnection)
	{
		l = new List("Games", 3);
		l.append("剪刀石头布", icon[0]);
		l.append("象棋    支书", icon[2]);
		l.append("军棋    伪红学家", icon[1]);
		l.append("跳棋    支书", icon[4]);
		l.append("反军棋  伪红学家", icon[3]);
		play = new Command("Play", 4, 1);
		l.setTicker(t);
		l.addCommand(play);
		l.setSelectCommand(play);
		l.setCommandListener(this);
		d.setCurrent(l);
/*		s = null;
		c = null;
		f2 = null;
*/		co = new Connection(streamconnection, this);
		System.gc();
	}

	public void append1(String s1)
	{
		f2.append(s1);
	}

	public void startApp()
	{
	}

	public void pauseApp()
	{
	}

	public void destroyApp(boolean flag)
	{
	}

	public void returnMoney(boolean flag, int i)
	{
		ingame = false;
		d.setCurrent(l);
		g0 = null;
		g1 = null;
		g2 = null;
		g3 = null;
		mm += i;
/*		try
		{
			rs.setRecord(1, itb(mm), 0, 4);
		}
		catch (Exception exception) { }
*/		t.setString("Money:" + mm);
		System.gc();
	}

	public void send(int i)
	{
		System.out.println("send");
		co.send(i);
	}

	public void receiveMessage(int i)
	{
		if (!ingame)
		{
			if (i == 2539)
			{
				oy = true;
				if (chose)
				{
					oy = false;
					chose = false;
					real();
				}
				return;
			}
			if (i >= 0x5f5e100)
			{
				oy = true;
				numo = i - 0x5f5e100;
				oy = true;
				if (chose)
				{
					chose = false;
					oy = false;
					start();
				}
			}
			return;
		}
		if (g0 != null)
			g0.received(i);
		if (g1 != null)
			g1.received(i);
		if (g2 != null)
			g2.received(i);
		if (g3 != null)
			g3.received(i);
	}

	public void err(Exception exception)
	{
		f2.append("Error:" + exception.toString());
	}

	public void start()
	{
		if (num != numo)
		{
			a = null;
			a = new Alert("Error", "Games are different!", null, AlertType.ERROR);
			a.setTimeout(1500);
			d.setCurrent(a);
			return;
		} else
		{
			real();
			return;
		}
	}

	public void real()
	{
		ingame = true;
		if (num == 0)
		{
			g0 = new Jsb(this, is, 0, true);
			tb2 = null;
		} else
		if (num == 1)
		{
			g1 = new KFC(this, is, 0, true);
			tb2 = null;
			d.setCurrent(g1);
		} else
		if (num == 2)
		{
			g2 = new Jq(this, is, 0, true);
			tb2 = null;
			d.setCurrent(g2);
		} else
		if (num == 3)
		{
			g3 = new TQ(this, is, 0, true);
			tb2 = null;
			d.setCurrent(g3);
		} else
		if (num == 4)
		{
			g4 = new Jq2(this, is, 0, true);
			tb2 = null;
			d.setCurrent(g4);
		}
	}
}
