// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

import javax.microedition.lcdui.*;

public class Jsb
	implements CommandListener
{

	private Form f;
	private BTPack btp;
	private ChoiceGroup cg;
	private Command c,back;
	private boolean waiting;
	private boolean ar;
	private int money;
	private int ns;
	private int nr;
	private int win;
	private int lose;

	public Jsb(BTPack btpack, boolean flag, int i, boolean flag1)
	{
		waiting = false;
		ar = false;
		win = 0;
		lose = 0;
		f = new Form("剪刀石头布");
		f.append("money=" + i + "\nwin=" + win + "\nlose" + lose + "\n");
		cg = new ChoiceGroup("出什么?", 1);
		cg.append("剪刀", null);
		cg.append("石头", null);
		cg.append("布", null);
		f.append(cg);
		c = new Command("发送", 4, 1);
back=new Command("返回",Command.BACK,99);
		f.addCommand(c);
f.addCommand(back);
		f.setCommandListener(this);
		btp = btpack;
		money = i;
		btpack.d.setCurrent(f);
	}

	public void commandAction(Command command, Displayable displayable)
	{
if(command==back){
btp.send(28);
btp.returnMoney(true,1);
return;
}
		if (!waiting)
		{
			waiting = true;
			f.deleteAll();
			f.append("money=" + money + "\nwin=" + win + "\nlose" + lose + "\n");
			f.append(cg);
			f.addCommand(c);
			ns = cg.getSelectedIndex();
			f.append("\n你出了" + cg.getString(ns));
			f.append("\n等待对方出牌");
			btp.send(ns);
			if (ar)
			{
				waiting = false;
				f.append("\n对方出了" + cg.getString(nr));
				pd();
			}
		}
		ar = false;
	}

	public void pd()
	{
		if (ns - nr == 1 || ns - nr == -2)
			f.append("\n你赢了");
		else
		if (ns == nr)
			f.append("\n平局");
		else
			f.append("\n你输了");
		if (win == 10 || lose == 10)
			btp.returnMoney(win == 10, money + 10);
	}

	public void received(int i)
	{
if(i==28){
btp.send(28);
btp.returnMoney(true,1);
return;
}
		if (waiting)
		{
			waiting = false;
nr=i;
			f.append("\n对方出了" + cg.getString(i));
			pd();
		} else
		{
			ar = true;
nr=i;
		}
	}
}
