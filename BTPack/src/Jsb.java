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
		f = new Form("����ʯͷ��");
		f.append("money=" + i + "\nwin=" + win + "\nlose" + lose + "\n");
		cg = new ChoiceGroup("��ʲô?", 1);
		cg.append("����", null);
		cg.append("ʯͷ", null);
		cg.append("��", null);
		f.append(cg);
		c = new Command("����", 4, 1);
back=new Command("����",Command.BACK,99);
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
			f.append("\n�����" + cg.getString(ns));
			f.append("\n�ȴ��Է�����");
			btp.send(ns);
			if (ar)
			{
				waiting = false;
				f.append("\n�Է�����" + cg.getString(nr));
				pd();
			}
		}
		ar = false;
	}

	public void pd()
	{
		if (ns - nr == 1 || ns - nr == -2)
			f.append("\n��Ӯ��");
		else
		if (ns == nr)
			f.append("\nƽ��");
		else
			f.append("\n������");
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
			f.append("\n�Է�����" + cg.getString(i));
			pd();
		} else
		{
			ar = true;
nr=i;
		}
	}
}
