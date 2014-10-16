
import javax.microedition.lcdui.*;

public class SquareC extends Canvas
{

	private Image imouse;
	private Image in[];
	private Image ib;
	private int i;
	private int j;
	private int k;
	private int l;
	private int a;
	private int b;
	private int c;
	private int d;
	private int e;
	private int f;
	private int mx;
	private int my;
	private int m[][];
	private boolean n[][][];
	private boolean mouse;

	public SquareC()
	{
		in = new Image[10];
		mx = 1;
		my = 1;
		m = new int[10][10];
		n = new boolean[10][10][10];
		mouse = true;
		setFullScreenMode(true);
		try
		{
			imouse = Image.createImage("/mouse.png");
			for (i = 0; i <= 9; i++)
				in[i] = Image.createImage("/i" + i + ".png");

			ib = Image.createImage("/m1.png");
		}
		catch (Exception exception) { }
		for (i = 1; i <= 9; i++)
			for (j = 1; j <= 9; j++)
			{
				m[i][j] = 0;
				for (k = 1; k <= 9; k++)
					n[i][j][k] = true;

			}


	}

	public void paint(Graphics g)
	{
		g.setColor(200, 255, 255);
		g.fillRect(0, 0, 240, 320);
		for (i = 1; i <= 9; i++)
			for (j = 1; j <= 9; j++)
			{
				g.drawImage(ib, i * 15, j * 15, 4 | 0x10);
				if (m[i][j] > 0)
				{
					g.drawImage(in[m[i][j]], i * 15, j * 15, 4 | 0x10);
				}
			}


		if (mouse)
		{
			g.drawImage(imouse, mx * 15 + 10, my * 15 + 10, 4 | 0x10);
		}
		g.setColor(125, 0, 0);
		for (i = 0; i <= 3; i++)
		{
			g.fillRect(i * 45 + 14, 14, 2, 135);
			g.fillRect(14, i * 45 + 14, 135, 2);
		}
g.setColor(0);
if(trying)g.drawString("伪红学家正在工作",5,150,g.LEFT|g.TOP);
if(win){g.drawString("伪红学家花了"+(end-start)+"ms",5,170,g.LEFT|g.TOP);System.out.println(start+" "+end);}
if(fin)g.drawString("jgg:"+(end1-start1)+"ms",5,190,g.LEFT|g.TOP);

	}

	public void keyPressed(int i1)
	{
if(i1==Canvas.KEY_STAR&&(mx!=1||my!=1)){
mx--;
if(mx<1){
mx=9;
my--;
}
repaint();
return;
}
		int j1 = 0;
		if (mouse)
		{
			switch (i1)
			{
			case 49: // '1'
				j1 = 1;
				break;

			case 50: // '2'
				j1 = 2;
				break;

			case 51: // '3'
				j1 = 3;
				break;

			case 52: // '4'
				j1 = 4;
				break;

			case 53: // '5'
				j1 = 5;
				break;

			case 54: // '6'
				j1 = 6;
				break;

			case 55: // '7'
				j1 = 7;
				break;

			case 56: // '8'
				j1 = 8;
				break;

			case 57: // '9'
				j1 = 9;
				break;
			}
			m[mx][my] = j1;
			k = j1;
			for (a = 1; a <= 9; a++)
				n[a][my][k] = false;

			for (b = 1; b <= 9; b++)
				n[mx][b][k] = false;

			c = (mx - 1) / 3;
			d = (my - 1) / 3;
			for (a = c * 3 + 1; a <= c * 3 + 3; a++)
				for (b = d * 3 + 1; b <= d * 3 + 3; b++)
					n[a][b][k] = false;


			mx++;
			if (mx > 9)
			{
				mx -= 9;
				my++;
			}
			if (my > 9)
			{
				mouse = false;
start1=System.currentTimeMillis();
				jgg();
end1=System.currentTimeMillis();
fin=true;
repaint();
whxj();

			}
		}
		repaint();
	}
private long start1,end1;
private boolean fin;
	public void jgg()
	{
		for (l = 1; l <= 200; l++)
		{
			for (i = 1; i <= 9; i++)
				for (j = 1; j <= 9; j++)
				{
					int i1 = 0;
					for (k = 1; k <= 9; k++)
						if (n[i][j][k])
							i1++;

					if (i1 != 1 || m[i][j] != 0)
						continue;
label0:
					for (k = 1; k <= 9; k++)
					{
						if (!n[i][j][k])
							continue;
						m[i][j] = k;
						for (a = 1; a <= 9; a++)
							n[a][j][k] = false;

						for (b = 1; b <= 9; b++)
							n[i][b][k] = false;

						c = (i - 1) / 3;
						d = (j - 1) / 3;
						a = c * 3 + 1;
						do
						{
							if (a > c * 3 + 3)
								continue label0;
							for (b = d * 3 + 1; b <= d * 3 + 3; b++)
								n[a][b][k] = false;

							a++;
						} while (true);
					}

					serviceRepaints();
					repaint();
				}


			for (i = 1; i <= 3; i++)
				for (j = 1; j <= 3; j++)
label1:
					for (k = 1; k <= 9; k++)
					{
						int j1 = 0;
						for (e = 1; e <= 3; e++)
							for (f = 1; f <= 3; f++)
								if (m[(i * 3 - 3) + e][(j * 3 - 3) + f] == 0 && n[(i * 3 - 3) + e][(j * 3 - 3) + f][k])
									j1++;


						if (j1 != 1)
							continue;
						e = 1;
						do
						{
							if (e > 3)
								continue label1;
label2:
							for (f = 1; f <= 3; f++)
							{
								if (m[(i * 3 - 3) + e][(j * 3 - 3) + f] != 0 || !n[(i * 3 - 3) + e][(j * 3 - 3) + f][k])
									continue;
								m[(i * 3 - 3) + e][(j * 3 - 3) + f] = k;
								for (a = 1; a <= 9; a++)
									n[a][(j * 3 - 3) + f][k] = false;

								for (b = 1; b <= 9; b++)
									n[(i * 3 - 3) + e][b][k] = false;

								c = (((i * 3 - 3) + e) - 1) / 3;
								d = (((j * 3 - 3) + f) - 1) / 3;
								a = c * 3 + 1;
								do
								{
									if (a > c * 3 + 3)
										continue label2;
									for (b = d * 3 + 1; b <= d * 3 + 3; b++)
										n[a][b][k] = false;

									a++;
								} while (true);
							}

							e++;
						} while (true);
					}



			for (i = 1; i <= 9; i++)
				for (k = 1; k <= 9; k++)
				{
					int k1 = 0;
					for (f = 1; f <= 9; f++)
						if (m[i][f] == 0 && n[i][f][k])
							k1++;

					if (k1 != 1)
						continue;
label3:
					for (f = 1; f <= 9; f++)
					{
						if (m[i][f] != 0 || !n[i][f][k])
							continue;
						m[i][f] = k;
						for (a = 1; a <= 9; a++)
							n[a][f][k] = false;

						for (b = 1; b <= 9; b++)
							n[i][b][k] = false;

						c = (i - 1) / 3;
						d = (f - 1) / 3;
						a = c * 3 + 1;
						do
						{
							if (a > c * 3 + 3)
								continue label3;
							for (b = d * 3 + 1; b <= d * 3 + 3; b++)
								n[a][b][k] = false;

							a++;
						} while (true);
					}

				}


			for (j = 1; j <= 9; j++)
				for (k = 1; k <= 9; k++)
				{
					int l1 = 0;
					for (e = 1; e <= 9; e++)
						if (m[e][j] == 0 && n[e][j][k])
							l1++;

					if (l1 != 1)
						continue;
label4:
					for (e = 1; e <= 9; e++)
					{
						if (m[e][j] != 0 || !n[e][j][k])
							continue;
						m[e][j] = k;
						for (a = 1; a <= 9; a++)
							n[a][j][k] = false;

						for (b = 1; b <= 9; b++)
							n[e][b][k] = false;

						c = (e - 1) / 3;
						d = (j - 1) / 3;
						a = c * 3 + 1;
						do
						{
							if (a > c * 3 + 3)
								continue label4;
							for (b = d * 3 + 1; b <= d * 3 + 3; b++)
								n[a][b][k] = false;

							a++;
						} while (true);
					}

				}


		}

	}

private boolean win,trying,line[][],row[][],square[][];
private int m1[][];
private long start,end;
private void whxj(){}
private void whxj1(){
start=System.currentTimeMillis();
trying=true;
line=new boolean[9][9];
row=new boolean[9][9];
square=new boolean[9][9];
m1=new int[9][9];
repaint();
for(int q=0;q<9;q++)
for(int r=0;r<9;r++)
m1[q][r]=m[q+1][r+1]-1;
for(int q=0;q<9;q++){
for(int r=0;r<9;r++){
int tmp=m1[q][r];
if(tmp!=-1){
line[r][tmp]=true;
row[q][tmp]=true;
square[r/3*3+q/3][tmp]=true;
}
}
}
next(0);
for(int q=0;q<9;q++)
for(int r=0;r<9;r++)
m[q+1][r+1]=m1[q][r]+1;
win=true;
end=System.currentTimeMillis();
repaint();
}
private boolean found;
private void next(int sub){
if(sub>80){
found=true;
return;
}
int x=sub%9,y=sub/9,back=m1[x][y];
if(m1[x][y]!=-1){
next(sub+1);
}else{
for(int q=0;q<9;q++){
if(line[y][q]||row[x][q]||square[y/3*3+x/3][q])continue;
m1[x][y]=q;
line[y][q]=row[x][q]=square[y/3*3+x/3][q]=true;
next(sub+1);
if(found)return;
line[y][q]=row[x][q]=square[y/3*3+x/3][q]=false;
}
}
m1[x][y]=back;
}
}
