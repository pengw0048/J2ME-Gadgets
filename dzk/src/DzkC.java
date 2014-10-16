// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

import java.util.Random;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.rms.RecordStore;

public class DzkC extends Canvas
	implements Runnable
{

	private Random r;
	private long start;
	private long end;
	private RecordStore rs;
	private int bx;
	private int rx;
	private int x;
	private int y;
	private int f;
	private int ix;
	private int iy;
	private int l;
	private int h;
	private int k;
	private int dx;
	private int dy;
	private int p;
	private int i;
	private int j;
	private int sp;
	private int tt;
	private int ttt;
	private int gd;
	private int kd;
	private int tx;
	private int ty;
	private boolean lz;
	private boolean b;
	private boolean u;
	private boolean it;
	private boolean fin;
	private boolean lo;
	private byte m[];

	public DzkC()
	{
		l = 3;
		p = 0;
		sp = 0;
		it = false;
		fin = false;
		lo = false;
		setFullScreenMode(true);
		gd = getHeight();
		kd = getWidth();
		r = new Random();
		m = new byte[96];
		try
		{
			rs = RecordStore.openRecordStore("Dzk", false);
		}
		catch (Exception exception)
		{
			for (i = 0; i < 96; i++)
				m[i] = (byte)Math.abs(r.nextInt() % 2);

			try
			{
				rs = RecordStore.openRecordStore("Dzk", true);
				rs.addRecord(m, 0, 96);
				rs.addRecord(itb(9999), 0, 4);
				rs.closeRecordStore();
			}
			catch (Exception exception2) { }
		}
		try
		{
			m = rs.getRecord(1);
			h = bti(rs.getRecord(2));
			rs.closeRecordStore();
		}
		catch (Exception exception1) { }
		init();
		if (h == 0)
			h = 9999;
		(new Thread(this)).start();
	}

	public void init()
	{
		bx = kd / 2;
		x = bx;
		rx = (kd / 2) * 10 + r.nextInt() % 5;
		y = gd - 5;
		dx = 0;
		dy = 0;
	}

	public void run()
	{
		do
		{
			if (fin || lo)
				break;
			start = System.currentTimeMillis();
			k = 0;
			for (i = 0; i < 96; i++)
				if (m[i] == 1)
					k++;

			if (p != 0)
				bx = bx + p;
			if (bx < 0)
				bx = 0;
			if (bx > kd)
				bx = kd;
			if (dy == 0)
			{
				x = bx;
				rx = bx * 10 + r.nextInt() % 5;
			}
			if (dy != 0)
			{
				y += dy;
				rx += dx;
				x = rx / 10;
				if (rx - x * 10 > 4)
					x++;
				if (x < 0)
				{
					rx = -4;
					x = 0;
					dx = -dx;
				}
				if (x > kd)
				{
					rx = kd * 10;
					x = kd;
					dx = -dx;
				}
				if (y < 0)
				{
					y = 0;
					dy = -dy;
				}
				if (y > gd - 3)
					if (x >= bx - 22 && x <= bx + 22)
					{
						y = gd - 5;
						dy = -dy;
						dx = (rx - bx * 10) / 7;
					} else
					{
						l--;
						if (l == 0)
						{
							repaint();
							lo = true;
						}
						for (i = 0; i < 0xf4240; i++)
							j++;

						init();
					}
			}
			tx = x / (kd / 16);
			ty = (y - gd / 3) / 5;
			if (tx < 16 && tx >= 0 && ty < 6 && ty >= 0 && m[ty * 16 + tx] == 1)
			{
				m[ty * 16 + tx] = 0;
				k--;
				int i1 = Math.abs(r.nextInt() % 10);
				if (i1 < 3 && !it)
				{
					it = true;
					ix = x;
					iy = y;
				}
				if (!u)
					if ((y - gd / 3) % 5 == 2 || (y - gd / 3) % 5 == 3)
						dx = -dx;
					else
						dy = -dy;
			}
			if (it)
				iy += 2;
			if (iy > gd - 5 && it)
			{
				it = false;
				if (ix >= bx - 22 && ix <= bx + 22)
				{
					int j1 = Math.abs(r.nextInt() % 4) + 1;
					sp = j1;
					if (j1 == 1)
						lz = true;
					if (j1 == 2)
						b = true;
					if (j1 == 3)
						u = true;
					if (j1 == 4)
						f -= 3000;
				}
			}
			if (dy != 0)
				f++;
			if (f % 300 == 0)
				sp = 0;
			if (k == 0)
			{
				repaint();
				fin = true;
			}
			repaint();
			end = System.currentTimeMillis();
			if (end - start < (long)(1600 / gd))
				try
				{
					Thread.sleep((long)(1600 / gd) - (end - start));
				}
				catch (Exception exception) { }
		} while (true);
		dy = 0;
		if (fin && f / 100 < h)
			try
			{
				rs = RecordStore.openRecordStore("Dzk", true);
				rs.setRecord(2, itb(f / 100), 0, 4);
				rs.closeRecordStore();
			}
			catch (Exception exception1) { }
		do
		{
			repaint();
			try
			{
				Thread.sleep(5L);
			}
			catch (Exception exception2) { }
		} while (true);
	}

	public void keyPressed(int i1)
	{
		if (i1 == 42 && dy != 0 && lz)
			Star();
		if (i1 == 35 && dy != 0 && b)
			Pound();
		i1 = getGameAction(i1);
		if (i1 == 2)
			p = -2;
		if (i1 == 5)
			p = 2;
		if (i1 == 8)
			dy = -1;
	}

	public void keyReleased(int i1)
	{
		p = 0;
	}

	public void paint(Graphics g)
	{
		if (!fin && !lo)
		{
			g.setColor(200, 255, 255);
			g.fillRect(0, 0, kd, gd);
			g.setColor(200, 200, 150);
			g.drawString(1000L / ((end - start) + 1L) + "fps " + k, 5, 5, 4 | 0x10);
			g.setColor(0, 0, 0);
			for (i = 0; i < 6; i++)
				for (j = 0; j < 16; j++)
					if (m[i * 16 + j] == 1)
						g.fillRect(j * (kd / 16), i * 5 + gd / 3, kd / 16 - 1, 4);


			g.fillRect(bx - 20, gd - 3, 40, 3);
			g.drawArc(x, y, 2, 2, 0, 360);
			if (it)
				g.drawRect(ix - 1, iy - 1, 3, 3);
			if (lz)
			{
				g.drawRect(5, gd - 80, 20, 20);
				g.drawChar('L', 15, gd - 60, 0x40 | 1);
				g.drawChar('*', 28, gd - 60, 0x40 | 4);
			}
			if (b)
			{
				g.drawRect(5, gd - 50, 20, 20);
				g.drawChar('B', 15, gd - 30, 0x40 | 1);
				g.drawChar('#', 28, gd - 30, 0x40 | 4);
			}
			if (u)
			{
				g.drawRect(5, gd - 110, 20, 20);
				g.drawChar('U', 15, gd - 90, 0x40 | 1);
			}
			g.drawString("T:" + f / 100, kd, gd - 60, 0x40 | 8);
			g.drawString("Life:" + l, kd, gd - 30, 0x40 | 8);
			g.drawString("H:" + h, kd, gd - 90, 0x40 | 8);
			switch (sp)
			{
			case 0: // '\0'
				break;

			case 1: // '\001'
				g.drawString("Laser", kd / 2, gd - 60, 1 | 0x40);
				break;

			case 2: // '\002'
				g.drawString("Bomb", kd / 2, gd - 60, 1 | 0x40);
				break;

			case 3: // '\003'
				g.drawString("Unstopped", kd / 2, gd - 60, 1 | 0x40);
				break;

			case 4: // '\004'
				g.drawString("Timer", kd / 2, gd - 60, 1 | 0x40);
				break;

			case 5: // '\005'
				g.setColor(255, 0, 0);
				g.fillRect(ttt - 1, 0, 3, gd);
				sp = 6;
				break;

			case 6: // '\006'
				g.setColor(255, 0, 0);
				g.fillRect(ttt - 2, 0, 5, gd);
				sp = 5;
				break;

			default:
				g.setColor(255, 255, 0);
				g.fillArc((tt - 2) * (kd / 16) - (sp - 6) / 2, ((ttt - 2) * 5 + gd / 3) - (sp - 6) / 2, sp - 6, sp - 6, 0, 360);
				sp++;
				if (sp > ((kd / 16) * 5) / 2)
					sp = 0;
				break;
			}
		}
		if (fin)
		{
			g.setColor(Math.abs(r.nextInt() % 256), Math.abs(r.nextInt() % 256), Math.abs(r.nextInt() % 256));
			g.fillRect(Math.abs(r.nextInt() % 16) * (kd / 16), Math.abs(r.nextInt() % (gd / 5 + 1)) * 5, kd / 16, 5);
			g.setColor(200, 255, 255);
			g.fillRect(kd / 2 - 60, gd / 2 - 30, 120, 60);
			g.setColor(0, 0, 0);
			g.drawString("You win!", kd / 2, gd / 2, 1 | 0x20);
			if (f / 100 < h)
				g.setColor(Math.abs(r.nextInt() % 256), Math.abs(r.nextInt() % 256), Math.abs(r.nextInt() % 256));
			g.drawString(f / 100 >= h ? "Work hard!" : "New record!", kd / 2, gd / 2, 1 | 0x10);
		}
		if (lo)
		{
			i = Math.abs(r.nextInt() % 256);
			g.setColor(i, i, i);
			g.fillRect(Math.abs(r.nextInt() % 16) * (kd / 16), Math.abs(r.nextInt() % (gd / 5 + 1)) * 5, kd / 16, 5);
			g.setColor(200, 255, 255);
			g.fillRect(kd / 2 - 60, gd / 2 - 30, 120, 60);
			g.setColor(0, 0, 0);
			g.drawString("You lose", kd / 2, gd / 2, 1 | 0x20);
			g.drawString("Work hard!", kd / 2, gd / 2, 1 | 0x10);
		}
	}

	public void pointerPressed(int i1, int j1)
	{
		if (i1 < 30)
		{
			if (j1 > gd - 85 && j1 < gd - 55 && dy != 0 && lz)
				Star();
			if (j1 > gd - 55 && j1 < gd - 25 && dy != 0 && b)
				Pound();
		}
	}

	public byte[] itb(int i1)
	{
		byte abyte0[] = new byte[4];
		abyte0[0] = (byte)(0xff & i1 >> 24);
		abyte0[1] = (byte)(0xff & i1 >> 16);
		abyte0[2] = (byte)(0xff & i1 >> 8);
		abyte0[3] = (byte)(0xff & i1 >> 0);
		return abyte0;
	}

	public int bti(byte abyte0[])
	{
		int i1 = abyte0[0] & 0xff;
		i1 = (i1 << 8) + (abyte0[1] & 0xff);
		i1 = (i1 << 8) + (abyte0[2] & 0xff);
		i1 = (i1 << 8) + (abyte0[3] & 0xff);
		return i1;
	}

	public void Star()
	{
		lz = false;
		sp = 5;
		tt = bx / (kd / 16);
		ttt = bx;
		if (tt < 16 && tt >= 0)
			for (i = 0; i < 6; i++)
				m[i * 16 + tt] = 0;

	}

	public void Pound()
	{
		b = false;
		sp = 7;
		for (i = -2; i <= 2; i++)
			for (j = -2; j <= 2; j++)
			{
				tt = x / (kd / 16) + j;
				ttt = (y - gd / 3) / 5 + i;
				if (tt < 16 && tt >= 0 && ttt < 6 && ttt >= 0)
					m[ttt * 16 + tt] = 0;
			}


	}
}
