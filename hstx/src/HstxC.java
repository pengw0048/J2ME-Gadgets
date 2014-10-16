import javax.microedition.lcdui.*;
public class HstxC extends Canvas implements Runnable{
private Graphics ig;
private Image icon[],hs;
private byte mode,len[],hss,str=1,step=16,jd=1;
private boolean rp=true,yd=true,gird,xzt=true,d1[],d2[],isbc[],isbd[];
private int h,w,ydx,ydy,mx,my,k,blen[],bch[][][];
private double sx=0.1,sy=0.1,xs[][],cs[][],d1z[],d2z[],bv[][][],mem[];
private StringBuffer sb;
private String ts;
private Font sf;
private BDS mb[];
public boolean need;
public HstxC(){
setFullScreenMode(true);
len=new byte[10];
blen=new int[10];
isbc=new boolean[10];
mb=new BDS[10];
isbd=new boolean[10];
bch=new int[10][20][4];
bv=new double[10][20][4];
mem=new double[20];
d1=new boolean[10];
d2=new boolean[10];
d1z=new double[10];
d2z=new double[10];
w=getWidth();
h=getHeight();
mx=ydx=w>>1;
my=ydy=h>>1;
xs=new double[10][10];
cs=new double[10][10];
icon=new Image[8];
try{
for(byte i=0;i<8;i++)
  icon[i]=Image.createImage("/"+i+".png");
hs=Image.createImage(w,h);
}catch(Exception e){
}
sf=Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);
ig=hs.getGraphics();
ig.setFont(sf);
}
public void addFunc(double txs[],double tcs[],byte tlen,boolean tb1,boolean tb2,double td1,double td2){
rp=true;
isbc[hss]=false;
isbd[hss]=false;
for(byte i=0;i<10;i++){
xs[hss][i]=txs[i];
cs[hss][i]=tcs[i];
}
d1[hss]=tb1;
d2[hss]=tb2;
d1z[hss]=td1;
d2z[hss]=td2;
len[hss]=tlen;
hss++;
setFullScreenMode(true);
repaint();
}
public void paint(Graphics g){
g.setFont(sf);
if(rp==true){
ig.setColor(255,0,0);
ig.fillRect(0,0,w,h);
if(gird==true){
for(int i=ydx%step;i<w;i+=step){
if((i-ydx)/step%5==0)ig.setColor(100,200,150);
else ig.setColor(180,100,255);
ig.drawLine(i,0,i,h);
}
for(int i=ydy%step;i<h;i+=step){
if((i-ydy)/step%5==0)ig.setColor(100,200,150);
else ig.setColor(180,100,255);
ig.drawLine(0,i,w,i);
}
}
ig.setColor(255,255,255);
ig.drawLine(0,ydy,w,ydy);
ig.drawLine(w-5,ydy-5,w,ydy);
ig.drawLine(w-5,ydy+5,w,ydy);
ig.drawLine(ydx,0,ydx,h);
ig.drawLine(ydx-5,5,ydx,0);
ig.drawLine(ydx+5,5,ydx,0);
ig.drawChar('x',w,ydy,g.RIGHT|g.TOP);
ig.drawChar('0',ydx,ydy,g.RIGHT|g.TOP);
ig.drawChar('y',ydx,0,g.RIGHT|g.TOP);
for(byte i=0;i<hss;i++){
ig.setColor(255-i*20,255-i*15,50+i*20);
boolean fst=true,nan=false;
double lx=0,ly=0,nx=0,ny=0,xz=0,yz=0,result=0,az=0,bz=0;
for(int j=0;j<w+jd;j+=jd){
nan=false;
nx=j;
xz=(nx-ydx)*sx;
yz=0;
if(isbd[i]){
  yz=mb[i].eval(xz);
}else if(isbc[i]){
for(int k=0;k<blen[i];k++){
  if(bch[i][k][1]==0)az=bv[i][k][0];
  else if(bch[i][k][1]==1)az=mem[(int)bv[i][k][0]];
  else az=xz;
  if(bch[i][k][2]==0)bz=bv[i][k][1];
  else if(bch[i][k][2]==1)bz=mem[(int)bv[i][k][1]];
  else bz=xz;
  if(bch[i][k][0]==0)result=az+bz;
  else if(bch[i][k][0]==1)result=az-bz;
  else if(bch[i][k][0]==2)result=az*bz;
  else if(bch[i][k][0]==3){
    if(bz==0){
      nan=true;
      break;
    }
    result=az/bz;
  }
  else if(bch[i][k][0]==4)result=pow(az,bz);
  else if(bch[i][k][0]==5){
    if(az<0){
      nan=true;
      break;
    }
  result=pow(az,0.5);
  }
  else if(bch[i][k][0]==6)result=Math.abs(az);
  else if(bch[i][k][0]==7)result=(double)((long)az);
  else if(bch[i][k][0]==8)result=Math.sin(az);
  else if(bch[i][k][0]==9)result=Math.cos(az);
  else if(bch[i][k][0]==10)result=Math.tan(az);
  else if(bch[i][k][0]==11)result=ln(az);
  if(bch[i][k][3]==0)mem[(int)bv[i][k][2]]=result;
  else{
    yz=result;
    break;
  }
}
}else{
for(byte k=0;k<len[i];k++){
  if((cs[i][k]==0.5&&xz<0)||(cs[i][k]==-1&&xz==0)){
    nan=true;
    break;
  }
  yz+=pow(xz,cs[i][k])*xs[i][k];
}
}
if(Double.isNaN(yz))nan=true;
ny=-yz/sy+ydy;
if(fst==true&&(!nan))fst=false;
else if(!nan){
if(d1[i]==true&&xz<d1z[i]);
else if(d2[i]==true&&xz>d2z[i]);
else ig.drawLine((int)lx,(int)(ly),(int)nx,(int)(ny));
}
if(nan)fst=true;
lx=nx;
ly=ny;
}
}
}
g.drawImage(hs,0,0,g.LEFT|g.TOP);
g.drawImage(icon[mode],0,h,g.LEFT|g.BOTTOM);
sb=new StringBuffer();
sb.append("当前坐标:");
sb.append((double)((long)((mx-ydx)*sx*10000))/10000);
sb.append(",");
sb.append(-(double)((long)((my-ydy)*sy*10000))/10000);
ts=sb.toString();
g.drawString(ts,0,0,g.LEFT|g.TOP);
sb=null;
ts=null;
if(mode==4)g.drawImage(icon[7],w>>1,h>>1,g.HCENTER|g.VCENTER);
String s="";
switch(str){
case 0:break;
case 1:s="上/右:放大 下/左:缩小";break;
case 2:
case 3:s="按方向键移动";break;
case 4:s="分别缩放";break;
case 5:s="设置";break;
case 6:s="缩放中心为原点";break;
case 7:s="缩放中心为鼠标";break;
case 8:s=(gird==true?"显示":"隐藏")+"网格";break;
case 9:s="最多10个函数";break;
}
str=0;
g.setColor(0,255,255);
g.drawString(s,w>>1,h,g.HCENTER|g.BOTTOM);
g.drawImage(icon[mode==2?5:6],mx,my,g.LEFT|g.TOP);
s=null;
}
public void keyPressed(int key){
key=getGameAction(key);
if(key==Canvas.FIRE){
mode++;
rp=false;
if(mode==5)mode=0;
if(mode==3)mode=4;
str=(byte)(mode+1);
if(mode==2)new Thread(this).start();
repaint();
return;
}
switch(mode){
case 0:
rp=true;
if(key==Canvas.LEFT||key==Canvas.DOWN){
sx+=sx;
sy+=sy;
if(yd==false){
ydx=(ydx+mx)/2;
ydy=(ydy+my)/2;
}
step/=2;
if(step==4)step=32;
repaint();
return;
}
if(key==Canvas.RIGHT||key==Canvas.UP){
sx/=2;
sy/=2;
if(yd==false){
ydx+=ydx-mx;
ydy+=ydy-my;
}
step+=step;
if(step==64)step=8;
repaint();
return;
}
return;
case 1:
rp=true;
if(key==Canvas.LEFT){
ydx+=w>>3;
repaint();
}
if(key==Canvas.RIGHT){
ydx-=w>>3;
repaint();
}
if(key==Canvas.UP){
ydy+=h>>3;
repaint();
}
if(key==Canvas.DOWN){
ydy-=h>>3;
repaint();
}
return;
case 2:
k=key;
return;
case 4:
if(key==Canvas.LEFT&&hss>0){
hss--;
rp=true;
repaint();
}
if(key==Canvas.RIGHT){
if(hss<10){
need=true;
return;
}
rp=false;
str=9;
repaint();
}
if(key==Canvas.UP){
yd=!yd;
str=(byte)(yd==true?6:7);
rp=false;
repaint();
}
if(key==Canvas.DOWN){
str=(byte)8;
gird=!gird;
rp=true;
repaint();
}
}
}
public void keyReleased(int key){
k=0;
}
public void run(){
while(mode==2){
int move=4;
while(k!=0){
move++;
if(k==Canvas.UP)my-=move>>2;
if(k==Canvas.DOWN)my+=move>>2;
if(k==Canvas.LEFT)mx-=move>>2;
if(k==Canvas.RIGHT)mx+=move>>2;
rp=false;
if(mx<10){rp=true;ydx+=10-mx;mx=10;}
if(mx>w-15){rp=true;ydx-=mx-(w-15);mx=w-15;}
if(my<10){rp=true;ydy+=10-my;my=10;}
if(my>h-15){rp=true;ydy-=my-(h-15);my=h-15;}
move++;
repaint();
try{
Thread.sleep(50);
}catch(Exception e){
}
}
try{
Thread.sleep(100);
}catch(Exception e){
}
}
}
private double pow1(double a,double b){
double tmp=1d,ab=Math.abs(b);
if(ab==0.5&&b>0)return Math.sqrt(a);
if(ab==0.5&&b<0)return 1/Math.sqrt(a);
for(int i=0;i<(int)ab;i++)tmp*=a;
if(b<0)tmp=1/tmp;
return tmp;
}
public void addBc(int tbch[][],double tbv[][],int tblen,boolean tb1,boolean tb2,double td1,double td2){
rp=true;

setFullScreenMode(true);
for(int i=0;i<20;i++){
for(int j=0;j<4;j++){
bch[hss][i][j]=tbch[i][j];
if(j<3)bv[hss][i][j]=tbv[i][j];
}
}
d1[hss]=tb1;
d2[hss]=tb2;
d1z[hss]=td1;
d2z[hss]=td2;
blen[hss]=tblen;
isbc[hss]=true;
isbd[hss]=false;
hss++;
repaint();
}
double pow2(double a, double b)  
{  
// true if base is greater than 1  
boolean gt1 = (Math.sqrt((a-1)*(a-1)) <= 1)? false:true;  
int oc = -1; // used to alternate math symbol (+,-)  
int iter = 20; // number of iterations  
double p, x, x2, sumX, sumY;  
// is exponent a whole number?
/*
if( (b-Math.floor(b)) == 0 )  
{  
// return base^exponent  
p = a;  
for( int i = 1; i < b; i++ )p *= a;  
return p;  
}  
*/
x = (gt1)?  
(a /(a-1)): // base is greater than 1  
(a-1); // base is 1 or less  
sumX = (gt1)?  
(1/x): // base is greater than 1  
x; // base is 1 or less  
for( int i = 2; i < iter; i++ )  
{  
// find x^iteration  
p = x;  
for( int j = 1; j < i; j++)p *= x;  
double xTemp = (gt1)?  
(1/(i*p)): // base is greater than 1  
(p/i); // base is 1 or less  
sumX = (gt1)?  
(sumX+xTemp): // base is greater than 1  
(sumX+(xTemp*oc)); // base is 1 or less  
oc *= -1; // change math symbol (+,-)  
}  
x2 = b * sumX;  
sumY = 1+x2; // our estimate  
for( int i = 2; i <= iter; i++ )  
{  
// find x2^iteration  
p = x2;  
for( int j = 1; j < i; j++)p *= x2;  
// multiply iterations (ex: 3 iterations = 3*2*1)  
int yTemp = 2;  
for( int j = i; j > 2; j-- )yTemp *= j;  
// add to estimate (ex: 3rd iteration => (x2^3)/(3*2*1) )  
sumY += p/yTemp;  
}  
return sumY; // return our estimate  
} 

double pow3(double x, double y)
{
//Convert the real power to a fractional form  
int den = 1024; //declare the denominator to be 1024  
/*Conveniently 2^10=1024, so taking the square root 10  
times will yield our estimate for n.　In our example  
n^3=8^2n^1024 = 8^683.*/ 
int num = (int)(y*den);// declare numerator  
int iterations = 10;/*declare the number of square root  
iterations associated with our denominator, 1024.*/ 
double n = Double.MAX_VALUE;/* we initialize our  
estimate, setting it to max*/ 
while( n >= Double.MAX_VALUE && iterations > 1)
{
/*　We try to set our estimate equal to the right  
hand side of the equation (e.g., 8^2048).　If this  
number is too large, we will have to rescale. */ 
n = x;
for( int i=1; i < num; i++ )n*=x;
/*here, we handle the condition where our starting  
point is too large*/ 
if( n >= Double.MAX_VALUE )
{
iterations--;/*reduce the iterations by one*/ 
den = (int)(den / 2);/*redefine the denominator*/ 
num = (int)(y*den);//redefine the numerator  
}
}
/*************************************************  
** We now have an appropriately sized right-hand-side.  
** Starting with this estimate for n, we proceed.  
**************************************************/ 
for( int i = 0; i < iterations; i++ )
{
n = Math.sqrt(n);  
}
// Return our estimate  
return n;
}
public static double pow4(final double a, final double b) {  
final int x = (int) (Double.doubleToLongBits(a) >> 32);  
final int y = (int) (b * (x - 1072632447) + 1072632447);  
return Double.longBitsToDouble(((long) y) << 32);  
} 



	public static final double MATH_E    = Math.E;
	public static final double MATH_PI   = Math.PI;
	public static final double EXP_40    = 2.35385266837019985408e17;
	public static final double EXP_20    = 4.85165195409790277969e8;
	public static final double EXP_10    = 2.20264657948067165196e4;
	public static final double EXP_5     = 1.48413159102576603421e2;
	public static final double EXP_2     = 7.38905609893065022723;
	public static final double EXP_1     = MATH_E;
	public static final double EXP_SQRT  = 1.64872127070012814684;
	
	private static double      MATH_PRE  = 1.0E-14;
	private static final int   MATH_CIR  = 40;

	
	static private double _ln(double x)
	{
    	int n = 1;
    	double r1 = x;
    	double step = x;
    	double temp = 0.0;
    	double rs = 0.0;

    	while (n < MATH_CIR)
    	{
        	rs += (r1 / (double)(n++));
        	r1 *= step;
        	rs -= (r1 / (double)(n++));
        	r1 *= step;
        	if (Math.abs(temp - rs) <= MATH_PRE)
        	{
        		break;
        	}
        	temp = rs;
    	}
    	
    	return rs;
    }
    
    static public double ln(double x)
    {
    	if (x <= 0.0)
    	{
//    		m_err = true;
    		return Double.NaN;
    	}
    	
    	double rs = 0.0;
    	double r1 = 0.0;

    	while (x >= 2.0)
    	{
        	x /= EXP_1;
        	r1 += 1.0;

   	     	if (x >= EXP_40)
    	    {
            	x /= EXP_40;
            	r1 += 40.0;
        	}
        	if (x >= EXP_20)
        	{
            	x /= EXP_20;
            	r1 += 20.0;
        	}
        	if (x >= EXP_10)
        	{
            	x /= EXP_10;
            	r1 += 10.0;
        	}
        	if (x >= EXP_5)
        	{
            	x /= EXP_5;
            	r1 += 5.0;
        	}
        	if (x >= EXP_2)
        	{
            	x /= EXP_2;
            	r1 += 2.0;
        	}
    	}
    
    	if (x == 1.0)
    	{
        	return r1;
    	}

    	if (x < 0.2)
    	{
        	return r1 - ln(1.0 / x);
    	}

    	if (x > 1.8)
    	{
        	return r1 - ln(1.0 / x);
    	}

    	x -= 1.0;
    	rs = _ln(x);
    	return (r1 + rs);
    }
    
    public static double log(double x, double y)
    {
    	if (x <= 0.0 || y <= 0.0 || 1.0 == x)
    	{
//    		m_err = true;
    		return Double.NaN;
    	}
    	
    	return ln(y) / ln(x);
    }
    
    public static double log10(double x)
    {
    	return ln(x) / 2.3025850929940456840179914546844;
    }
    
    
 	static double _exp(double x)
	{
    	int n = 1;
    	double r1 = x;
    	double step = x;
    	double tmp = 1.0;
    	double rs = 1.0;

    	while (n < MATH_CIR)
    	{
        	rs += r1;
        	r1 = (r1 * step) / (double)(++n);
        	
         	rs += r1;
        	r1 = (r1 * step) / (double)(++n);
        	
        	if (Math.abs(tmp - rs) <= MATH_PRE)
        	{
        		break;
        	}
    	}
    	return rs;
	}
	
	public static double exp(double x)
	{
    	double rs = 0.0;
    	double r1 = 1.0;
	
    	if (x < 0.0)
    	{
        	return 1.0 / exp(0.0 - x);
    	}

    	while (x > 1.0)
    	{
        	r1 *= EXP_1;
        	x -= 1.0;
        	if (x >= 40.0)
        	{
            	r1 *= EXP_40;
            	x -= 40.0;
        	}
        	if (x >= 20.0)
        	{
            	r1 *= EXP_20;
            	x -= 20.0;
        	}
        	if (x >= 10.0)
        	{
            	r1 *= EXP_10;
            	x -= 10;
        	}
        	if (x >= 5.0)
        	{
            	r1 *= EXP_5;
            	x -= 5.0;
        	}
        	if (x >= 2.0)
        	{
            	r1 *= EXP_2;
            	x -= 2.0;
        	}
    	}
    	if (x == 1.0)
    	{
        	return (r1 * MATH_E);
    	}
    	if (x > 0.5)
    	{
        	r1 *= EXP_SQRT;
        	x -= 0.5;
    	}
    	rs = _exp(x);
    	return (r1 * rs);
	}
	
	public static double pow(double x, double y)
	{
    	double rs;

    	if (x < 0.0)
    	{
        	if (y != Math.floor(y))
        	{
//            	m_err = true;
            	return Double.NaN;
        	}
        	else if (((long)y) % 2 == 1) 
            {
                return 0.0 - pow(0.0 - x, y);
            }
            x = 0.0 - x;
    	}
    	return exp(y * ln(x));
	}
	
	public static double pow10(double x)
	{
		return exp(x * 2.3025850929940456840179914546844);
	}
public void addBds(BDS tb){
rp=true;
isbd[hss]=true;
isbc[hss]=false;
mb[hss++]=tb;
repaint();
}
}