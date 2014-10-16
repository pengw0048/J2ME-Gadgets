// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

import java.io.PrintStream;
import javax.microedition.lcdui.*;

public class TQ extends Canvas
{

	private int mm[][] = {
		{
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
			0, 0, 0, 0
		}, {
			0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 
			0, 0, 0, 0
		}, {
			0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 
			0, 0, 0, 0
		}, {
			0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 
			0, 0, 0, 0
		}, {
			0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 
			0, 0, 0, 0
		}, {
			0, 2, 2, 2, 2, 0, 0, 0, 0, 0, 
			3, 3, 3, 3
		}, {
			0, 2, 2, 2, 0, 0, 0, 0, 0, 0, 
			3, 3, 3, 0
		}, {
			0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 
			0, 3, 3, 0
		}, {
			0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 
			0, 3, 0, 0
		}, {
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
			0, 0, 0, 0
		}, {
			0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 
			0, 5, 0, 0
		}, {
			0, 0, 4, 4, 0, 0, 0, 0, 0, 0, 
			0, 5, 5, 0
		}, {
			0, 4, 4, 4, 0, 0, 0, 0, 0, 0, 
			5, 5, 5, 0
		}, {
			0, 4, 4, 4, 4, 0, 0, 0, 0, 0, 
			5, 5, 5, 5
		}, {
			0, 0, 0, 0, 0, 6, 6, 6, 6, 0, 
			0, 0, 0, 0
		}, {
			0, 0, 0, 0, 0, 0, 6, 6, 6, 0, 
			0, 0, 0, 0
		}, {
			0, 0, 0, 0, 0, 0, 6, 6, 0, 0, 
			0, 0, 0, 0
		}, {
			0, 0, 0, 0, 0, 0, 0, 6, 0, 0, 
			0, 0, 0, 0
		}
	};
	private int mx;
	private int my;
	private int i;
	private int j;
	private int sx;
	private int sy;
	private int m[][];
	private boolean selected;
	private boolean name;
	private boolean turn;
	private BTPack btp;
	private Image back;
	private Image mouse[];
	private Image qz[];
	private Image iqz;
	private Image imouse;
private boolean fh;

	public TQ(BTPack btpack, boolean flag, int k, boolean flag1)
	{
		mx = 1;
		my = 1;
		m = new int[14][18];
		selected = false;
		turn = true;
		mouse = new Image[2];
		qz = new Image[15];
		for (i = 0; i <= 13; i++)
			for (j = 0; j <= 17; j++)
				m[i][j] = mm[j][i];


		btp = btpack;
		name = flag;
		setFullScreenMode(true);
		try
		{
			imouse = Image.createImage("/TQ/mouse.png");
			iqz = Image.createImage("/TQ/qz.png");
			back = Image.createImage("/TQ/back.png");
			for (i = 0; i < 7; i++)
				for (j = 0; j < 2; j++)
					qz[j * 7 + i + 1] = Image.createImage(iqz, i * 20, j * 20, 20, 20, 0);


			mouse[0] = Image.createImage(imouse, 0, 0, 20, 20, 0);
			mouse[1] = Image.createImage(imouse, 20, 0, 20, 20, 0);
		}
		catch (Exception exception)
		{
			System.out.println(exception);
		}
	}

	public void paint(Graphics g)
	{
		g.drawImage(back, 0, 0, 20);
		for (i = 1; i <= 13; i++)
			for (j = 1; j <= 17; j++)
				if (m[i][j] != 0)
					g.drawImage(qz[m[i][j]], 15 + i * 15 + ((j + 1) % 2) * 7, 43 + 13 * j, 3);


		g.drawImage(mouse[0], 15 + mx * 15 + ((my + 1) % 2) * 7, 43 + 13 * my, 3);
		if (selected)
			g.drawImage(mouse[1], 15 + sx * 15 + ((sy + 1) % 2) * 7, 43 + 13 * sy, 3);
g.setColor(0);
if(fh)g.drawString("Press again to return",0,0,20);
	}

	public void keyPressed(int k)
	{
if(k==Canvas.KEY_STAR){
if(!fh){
fh=true;
repaint();
return;
}else{
btp.send(28);
received(28);
return;
}
}
if(fh==true){
fh=false;
return;
}
		k = getGameAction(k);
		if (turn != name)
			return;
		byte byte0 = 1;
		if (k == 1)
			byte0 = 1;
		if (k == 6)
			byte0 = 2;
		if (k == 2)
			byte0 = 3;
		if (k == 5)
			byte0 = 4;
		if (k == 8)
			byte0 = 5;
		btp.send(byte0);
		received(byte0);
	}

	public void received(int k)
	{
if(k==28)btp.returnMoney(true, 1);
		switch (k)
		{
		default:
			break;

		case 1: // '\001'
			my--;
			break;

		case 2: // '\002'
			my++;
			break;

		case 3: // '\003'
			mx--;
			break;

		case 4: // '\004'
			mx++;
			break;

		case 5: // '\005'
			if (selected)
			{
				turn = !turn;
				selected = false;
				m[mx][my] = m[sx][sy];
				m[sx][sy] = 0;
				break;
			}
			if (m[mx][my] != 0)
			{
				selected = true;
				sx = mx;
				sy = my;
			}
			break;
		}
		repaint();
	}
}
