// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

import java.io.PrintStream;
import javax.microedition.lcdui.*;

public class KFC extends Canvas
{

	private int mm[][] = {
		{
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
			0
		}, {
			0, 1, 2, 3, 4, 5, 4, 3, 2, 1, 
			0
		}, {
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
			0
		}, {
			0, 0, 6, 0, 0, 0, 0, 0, 6, 0, 
			0
		}, {
			0, 7, 0, 7, 0, 7, 0, 7, 0, 7, 
			0
		}, {
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
			0
		}, {
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
			0
		}, {
			0, 14, 0, 14, 0, 14, 0, 14, 0, 14, 
			0
		}, {
			0, 0, 13, 0, 0, 0, 0, 0, 13, 0, 
			0
		}, {
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
			0
		}, {
			0, 8, 9, 10, 11, 12, 11, 10, 9, 8, 
			0
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

	public KFC(BTPack btpack, boolean flag, int k, boolean flag1)
	{
		mx = 1;
		my = 1;
		m = new int[11][11];
		selected = false;
		turn = true;
		mouse = new Image[2];
		qz = new Image[15];
		for (i = 0; i <= 10; i++)
			for (j = 0; j <= 10; j++)
				m[i][j] = mm[j][i];


		btp = btpack;
		name = flag;
		setFullScreenMode(true);
		try
		{
			imouse = Image.createImage("/XQ/mouse.png");
			iqz = Image.createImage("/XQ/qz.png");
			back = Image.createImage("/XQ/back.png");
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
		for (i = 1; i <= 9; i++)
			for (j = 1; j <= 10; j++)
				if (m[i][j] != 0)
					g.drawImage(qz[m[i][j]], 20 + 20 * i, 50 + 20 * j, 3);


		g.drawImage(mouse[0], 20 + 20 * mx, 50 + 20 * my, 3);
		if (selected)
			g.drawImage(mouse[1], 20 + 20 * sx, 50 + 20 * sy, 3);
	}

	public void keyPressed(int k)
	{
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
if(sy==my&&sx==mx){
selected=false;
break;
}
				turn = !turn;
				selected = false;
				if (m[mx][my] == 5 || m[mx][my] == 12)
					btp.returnMoney(true, 1);
				m[mx][my] = m[sx][sy];
				m[sx][sy] = 0;
				break;
			}
			else if (m[mx][my] != 0)
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
