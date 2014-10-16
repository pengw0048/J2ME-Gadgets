// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

import javax.microedition.lcdui.*;

public class Jq2 extends Canvas
{

	private BTPack ins;
	private Image q[];
	private Image back;
	private Image qp;
	private Image cur1;
	private Image cur2;
	private boolean ready;
	private boolean my;
	private boolean got;
	private boolean hq;
	private boolean orh;
	private boolean mo;
	private boolean oo;
	private boolean rr;
	private boolean zb;
	private int map[][] = {
		{
			12, 0, 3, 6, 7, 9, 12, 12, 12, 12, 
			12, 12
		}, {
			12, 1, 4, 6, 8, 10, 12, 12, 12, 12, 
			12, 12
		}, {
			12, 2, 4, 6, 8, 10, 12, 12, 12, 12, 
			12, 12
		}, {
			12, 2, 5, 7, 8, 10, 12, 12, 12, 12, 
			12, 12
		}, {
			12, 3, 5, 7, 9, 11, 12, 12, 12, 12, 
			12, 12
		}
	};
	private int cx;
	private int cy;
	private int gx;
	private int gy;
	private int ml;
	private int ol;
	private int turns;
	private int money;
	private int who;
	private int yet;
	private int tmsg[];
	private int sx;
	private int sy;
	private int tsx;
	private int tsy;

	public Jq2(BTPack btpack, boolean flag, int i, boolean flag1)
	{
		ml = 25;
		ol = 25;
		yet = 5;
		ins = btpack;
		my = flag;
		money = i;
		setFullScreenMode(true);
		q = new Image[12];
		tmsg = new int[6];
		try
		{
			for (int j = 0; j < 12; j++)
				q[j] = Image.createImage("/jq/" + j + ".png");

			back = Image.createImage("/jq/back.png");
			qp = Image.createImage("/jq/qp.png");
			cur1 = Image.createImage("/jq/cur1.png");
			cur2 = Image.createImage("/jq/cur2.png");
		}
		catch (Exception exception) { }
		cy = 1;
		repaint();
	}

	public void paint(Graphics g)
	{
		if (hq)
		{
			g.setColor(0);
			g.fillRect(40, 145, 160, 30);
			g.setColor(0xffffff);
			g.drawString("正等待对方回应", 120, 175, 33);
		} else
		if (orh)
		{
			g.setColor(0);
			g.fillRect(40, 130, 160, 60);
			g.setColor(0xffffff);
			g.drawString("对方请求和棋", 120, 160, 33);
			g.drawString("按选择键接受", 120, 190, 33);
		} else
		if (who != 0)
		{
			g.setColor(0);
			g.fillRect(40, 130, 160, 60);
			g.setColor(0xffffff);
			g.drawString(who != 1 ? who != -1 ? "平局" : "失败" : "胜利", 120, 160, 33);
			g.drawString("战斗结果:" + gj(), 120, 190, 33);
		} else
		if (mo)
		{
			g.setColor(0);
			g.fillRect(40, 80, 160, 30);
			g.setColor(0xffffff);
			g.drawString("等待对方", 120, 110, 33);
		} else
		{
			g.drawImage(qp, 0, 0, 20);
			g.setColor(0);
			if (!ready)
				g.fillRect(0, 0, 240, 160);
			for (int i = 0; i < 5; i++)
			{
				for (int j = 0; j < 12; j++)
					if (Math.abs(map[i][j]) != 12)
						g.drawImage(map[i][j] >= 0 && (!ready || i != sx || j != sy || zb) ? q[map[i][j]] : back, i * 40 + 40, j >= 6 ? j * 25 + 20 : j * 25 + 25, 3);

			}

			g.drawImage(cur1, cx * 40 + 40, cy >= 6 ? cy * 25 + 20 : cy * 25 + 25, 3);
			if (got)
				g.drawImage(cur2, gx * 40 + 40, gy >= 6 ? gy * 25 + 20 : gy * 25 + 25, 3);
			g.setColor(0);
			g.drawString("*:和棋", 0, 320, 36);
			if (ready && !my)
				g.drawString("等待对方下棋", 0, 0, 20);
		}
	}

	public void keyPressed(int i)
	{
		if (i == 97)
		{
			zb = !zb;
			repaint();
			return;
		}
		if (ready && !my && who == 0 && !orh)
			return;
		if (orh)
		{
			if (getGameAction(i) == 8)
			{
				hq = orh = false;
				who = 2539;
				ins.send(0xf0000002);
				repaint();
			} else
			{
				hq = orh = false;
				ins.send(0xf0000001);
				repaint();
			}
		} else
		{
			if (who != 0)
			{
				ins.returnMoney(who == 1, gj() + money);
				repaint();
				return;
			}
			if (i == 42)
			{
				hq = true;
				ins.send(0xf0000000);
				repaint();
				return;
			}
			i = getGameAction(i);
			if (i == 1 && !got)
			{
				cy--;
				if (cy < 0)
					cy = 11;
				repaint();
				if (ready)
					ins.send(0xa0000000 + (cx << 16) + (cy << 12));
			} else
			if (i == 1)
			{
				gy--;
				if (gy < 0)
					gy = 11;
				repaint();
				if (ready)
					ins.send(0x90000000 + (gx << 16) + (gy << 12));
			} else
			if (i == 2 && !got)
			{
				cx--;
				if (cx < 0)
					cx = 4;
				repaint();
				if (ready)
					ins.send(0xa0000000 + (cx << 16) + (cy << 12));
			} else
			if (i == 2)
			{
				gx--;
				if (gx < 0)
					gx = 4;
				repaint();
				if (ready)
					ins.send(0x90000000 + (gx << 16) + (gy << 12));
			} else
			if (i == 5 && !got)
			{
				cx++;
				if (cx > 4)
					cx = 0;
				repaint();
				if (ready)
					ins.send(0xa0000000 + (cx << 16) + (cy << 12));
			} else
			if (i == 5)
			{
				gx++;
				if (gx > 4)
					gx = 0;
				repaint();
				if (ready)
					ins.send(0x90000000 + (gx << 16) + (gy << 12));
			} else
			if (i == 6 && !got)
			{
				cy++;
				if (cy > 11)
					cy = 0;
				repaint();
				if (ready)
					ins.send(0xa0000000 + (cx << 16) + (cy << 12));
			} else
			if (i == 6)
			{
				gy++;
				if (gy > 11)
					gy = 0;
				repaint();
				if (ready)
					ins.send(0x90000000 + (gx << 16) + (gy << 12));
			} else
			if (i == 8)
				if (!got)
				{
					if (ready&&map[cx][cy] > 0 || ready && cx == sx && cy == sy)
						return;
					if (ready && Math.abs(map[cx][cy]) > 9)
						return;
					if (Math.abs(map[cx][cy]) == 12 )
						return;
					got = true;
					if (ready)
					{
						gx = cx;
						gy = cy;
					}
					if (ready)
						ins.send(0x90000000 + (gx << 16) + (gy << 12));
					repaint();
				} else
				if (!ready)
				{
					got = false;
					if (gx == 1 && gy == 7 || gx == 3 && gy == 7 || gx == 2 && gy == 8 || gx == 1 && gy == 9 || gx == 3 && gy == 9)
					{
						repaint();
						return;
					}
					if (map[cx][cy] == 11 && (gx != 1 || gy != 11) && (gx != 3 || gy != 11))
					{
						repaint();
						return;
					}
					if (map[cx][cy] == 10 && gy < 10)
					{
						repaint();
						return;
					}
					if (map[gx][gy] == 12)
					{
						map[gx][gy] = map[cx][cy];
						if (cx != gx || cy != gy)
							map[cx][cy] = 12;
						if (cy < 6 && gy > 5)
							ml--;
						if (cy > 5 && gy < 6)
							ml++;
						if (map[gx][gy] == 0)
						{
							sx = gx;
							sy = gy;
						}
					}
					if (ml == 0)
					{
						ml = 25;
						mo = true;
						ins.send(0xe0000000 + (sx << 16) + (sy << 8));
						for (int j = 6; j < 12; j++)
							ins.send((map[0][j] << 16) + (map[1][j] << 12) + (map[2][j] << 8) + (map[3][j] << 4) + map[4][j]);

						if (oo)
						{
							for (int k = 0; k < 6; k++)
							{
								map[0][k] = -(tmsg[k] & 0xf);
								map[1][k] = -(tmsg[k] >> 4 & 0xf);
								map[2][k] = -(tmsg[k] >> 8 & 0xf);
								map[3][k] = -(tmsg[k] >> 12 & 0xf);
								map[4][k] = -(tmsg[k] >> 16 & 0xf);
							}

							ready = true;
							sx = 4 - tsx;
							sy = 11 - tsy;
							mo = oo = rr = false;
							repaint();
							return;
						}
					}
					repaint();
				} else
				{
					if (cx == gx && cy == gy)
					{
						got = false;
						repaint();
						return;
					}
					if (map[cx][cy] == 0)
						ins.send(0xc0000000 + (gx << 8) + gy);
					pd(cx, cy, gx, gy);
					ins.send(0xd0000000 + (cx << 16) + (cy << 12) + (gx << 8) + (gy << 4));
					if (sx < 13 && map[sx][sy] != 0)
						sx = sy = 13;
					my = got = false;
					repaint();
				}
		}
	}

	private int gj()
	{
		return 0;
	}

	public void received(int i)
	{
		if (rr)
		{
			tmsg[yet] = i;
			yet--;
			if (yet < 0 && mo)
			{
				for (int j = 0; j < 6; j++)
				{
					map[0][j] = -(tmsg[j] & 0xf);
					map[1][j] = -(tmsg[j] >> 4 & 0xf);
					map[2][j] = -(tmsg[j] >> 8 & 0xf);
					map[3][j] = -(tmsg[j] >> 12 & 0xf);
					map[4][j] = -(tmsg[j] >> 16 & 0xf);
				}

				ready = true;
				sx = 4 - tsx;
				sy = 11 - tsy;
				rr = mo = oo = false;
				repaint();
				return;
			} else
			{
				repaint();
				return;
			}
		}
		if (i == 0xf0000001)
		{
			hq = orh = false;
			repaint();
		} else
		if (i == 0xf0000002)
		{
			hq = orh = false;
			who = 2539;
			repaint();
		} else
		if (i == 0xf0000004)
		{
			who = -1;
			repaint();
		} else
		if (i == 0xf0000000)
		{
			orh = true;
			repaint();
		} else
		if ((i & 0xe0000000) == 0xe0000000)
		{
			oo = rr = true;
			tsx = i >> 16 & 0xf;
			tsy = i >> 8 & 0xf;
		} else
		if ((i & 0xd0000000) == 0xd0000000)
		{
			pd(4 - (i >> 16 & 0xf), 11 - (i >> 12 & 0xf), 4 - (i >> 8 & 0xf), 11 - (i >> 4 & 0xf));
			cx = 4 - (i >> 8 & 0xf);
			cy = 11 - (i >> 4 & 0xf);
			if (sx < 13 && map[sx][sy] != 0)
				sx = sy = 13;
			my = true;
			got = false;
			repaint();
		} else
		if ((i & 0xc0000000) == 0xc0000000)
		{
			sx = 4 - (i >> 8 & 0xf);
			sy = 11 - (i & 0xf);
		} else
		if ((i & 0xa0000000) == 0xa0000000)
		{
			cx = 4 - (i >> 16 & 0xf);
			cy = 11 - (i >> 12 & 0xf);
			repaint();
		} else
		if ((i & 0x90000000) == 0x90000000)
		{
			got = true;
			gx = 4 - (i >> 16 & 0xf);
			gy = 11 - (i >> 12 & 0xf);
			repaint();
		}
	}

	private void pd(int i, int j, int k, int l)
	{
		int i1 = Math.abs(map[k][l]);
		int j1 = Math.abs(map[i][j]);
		if (i1 == 11 && my)
		{
			who = 1;
			ins.send(0xf0000004);
			repaint();
			return;
		}
		if (i1 == 11)
			return;
		if (i1 == 12)
		{
			map[k][l] = map[i][j];
			map[i][j] = 12;
			return;
		}
		if (j1 == 9 || i1 == 9)
		{
			map[k][l] = 12;
			map[i][j] = 12;
			return;
		}
		if (i1 == 10 && j1 == 8)
		{
			map[k][l] = map[i][j];
			map[i][j] = 12;
			return;
		}
		if (i1 == 10)
		{
			map[i][j] = 12;
			return;
		}
		if (i1 == j1)
			map[i][j] = map[k][l] = 12;
		else
		if (i1 < j1)
		{
			map[i][j] = 12;
		} else
		{
			map[k][l] = map[i][j];
			map[i][j] = 12;
		}
	}
}
