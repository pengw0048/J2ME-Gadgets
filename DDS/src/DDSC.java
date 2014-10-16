import javax.microedition.lcdui.*;
import java.util.*;
import javax.microedition.rms.*;
public class DDSC extends Canvas implements Runnable{
private int m[][]=new int[3][3],i,j,s=0,speed=5,t,tx,ty,fs,nb,nw;
private int fw[][]=new int[3][3],fl[][]=new int[3][3],ls[][]=new int[3][3];
private int dcs[]={1,3,1,4,7,1,1};
private Image img[]=new Image[7],img2[]=new Image[7],back[]=new Image[2],si[]=new Image[4];
private long st=-1,now=0;
private Random r;
private RecordStore rs;
private DDS father;
public DDSC(DDS dt){
father=dt;
for(i=0;i<3;i++)
for(j=0;j<3;j++){
m[i][j]=0;
fw[i][j]=0;
fl[i][j]=0;
}
try{
r=new Random();
this.setFullScreenMode(true);
}catch(Exception e){
father.alert(e.toString());
}
try{

rs=RecordStore.openRecordStore("dds1",false);
}catch(Exception e){
try{
rs=RecordStore.openRecordStore("dds1",true);
}catch(Exception e1){
}
try{
rs.addRecord(itb(0),0,4);
rs.addRecord(itb2(0),0,4);
}catch(Exception e1){
}
}
try{
nb=bti(rs.getRecord(1));
nw=bti2(rs.getRecord(2));
}catch(Exception e){
father.alert(e.toString());
}
try{
for(i=0;i<7;i++)img[i]=Image.createImage("/img"+i+".png");
for(i=0;i<7;i++)img2[i]=Image.createImage("/img2"+i+".png");
for(i=0;i<2;i++)back[i]=Image.createImage("/back"+i+".png");
for(i=0;i<4;i++)si[i]=Image.createImage("/s"+(i+1)+".png");
}catch(Exception e){
father.alert(e.toString());
}
Thread t=new Thread(this);
t.start();
}

public void paint(Graphics g){
g.setColor(255,255,255);
g.fillRect(0,0,320,320);
g.setColor(255,0,0);
if(now>=30){
g.drawString("Your score is="+fs,20,20,g.LEFT|g.TOP);
if(fs>nb)g.drawString("Good job",20,50,g.LEFT|g.TOP);
else if(fs<nw)g.drawString("You idiot",20,50,g.LEFT|g.TOP);
else g.drawString("Bad job",20,50,g.LEFT|g.TOP);
g.drawString("Press any key to replay",20,80,g.LEFT|g.TOP);
if(fs>=80&&nb<80)g.drawString("LY added",20,110,g.LEFT|g.TOP);
if(fs>=90&&nb<90)g.drawString("ZRQ added",20,130,g.LEFT|g.TOP);
if(fs>=100&&nb<100)g.drawString("ZH added",20,150,g.LEFT|g.TOP);
if(fs<=-50&&nw>-50)g.drawString("WP added",20,170,g.LEFT|g.TOP);
return;
}
for(i=0;i<3;i++)
for(j=0;j<3;j++){
g.drawImage(back[0],i*80,j*80,g.LEFT|g.TOP);
if(m[i][j]!=0){
g.drawRegion(img[fw[i][j]],0,0,60,m[i][j]>70?70:m[i][j],0,i*80+10,m[i][j]>70?j*80+10:j*80+80-m[i][j],g.LEFT|g.TOP);
}
if(ls[i][j]!=0)g.drawImage(img2[fw[i][j]],i*80+10,j*80+10,g.LEFT|g.TOP);
g.drawImage(back[1],i*80-1,j*80+58,g.LEFT|g.TOP);
}
if(nb>=80){
g.drawImage(si[0],280,200,g.LEFT|g.BOTTOM);
g.drawImage(si[0],0,310,g.LEFT|g.BOTTOM);
}
if(nb>=90){
g.drawImage(si[1],290,200,g.LEFT|g.BOTTOM);
g.drawImage(si[1],10,310,g.LEFT|g.BOTTOM);
}
if(nb>=100){
g.drawImage(si[2],300,200,g.LEFT|g.BOTTOM);
g.drawImage(si[2],20,310,g.LEFT|g.BOTTOM);
}
if(nw<=-50){
g.drawImage(si[3],310,200,g.LEFT|g.BOTTOM);
g.drawImage(si[3],30,310,g.LEFT|g.BOTTOM);
}
g.drawString("score="+s+"   t="+now,20,260,g.LEFT|g.TOP);
g.drawString("Best/Worst="+nb+"/"+nw,20,285,g.LEFT|g.TOP);
g.drawString("scr="+s,250,20,g.LEFT|g.TOP);
g.drawString("t="+now,250,60,g.LEFT|g.TOP);
g.drawString("hi="+nb,250,100,g.LEFT|g.TOP);
g.drawString("low="+nw,250,140,g.LEFT|g.TOP);
}

public void run(){
long start=0,end=0;
while(true){
start=System.currentTimeMillis();
if(st==-1)st=start;
now=(start-st)/1000;
if(now>=30){fs=s;
if(fs>nb){
try{
rs.setRecord(1,itb(fs),0,4);
}catch(Exception e){
}
}
if(fs<nw){
try{
rs.setRecord(2,itb2(fs),0,4);
}catch(Exception e){
System.out.println(e);
}
}
repaint();return;}
for(i=0;i<3;i++)
for(j=0;j<3;j++){
if(ls[i][j]!=0)ls[i][j]+=speed;
if(ls[i][j]>60)ls[i][j]=0;
if(m[i][j]!=0)m[i][j]+=speed;
if(m[i][j]>=150)m[i][j]=0;
}
t=r.nextInt(5);
if(t==0){
tx=r.nextInt(3);
ty=r.nextInt(3);
if(m[tx][ty]==0&&ls[tx][ty]==0){
m[tx][ty]=1;
t=r.nextInt(5);
if(t==3){
t=r.nextInt(3);
int tt=r.nextInt(2);
if(tt==0&&nb>=80)t=3;
if(tt==1&&nb>=90)t=4;
}else if(t==4){
t=r.nextInt(3);
int tt=r.nextInt(2);
if(tt==0&&nb>=100)t=5;
if(tt==1&&nw<=-50)t=6;
}
fw[tx][ty]=t;
fl[tx][ty]=dcs[t];
}
}
repaint();
if(now%8==0)System.gc();
end=System.currentTimeMillis();
if((end-start)<50){
try{
Thread.sleep(50-(end-start));
}catch(Exception e){
System.out.println(e);
}
}
}
}

public void keyPressed(int key){
t=key-49;
if(now>=30&&(System.currentTimeMillis()-st)/1000>31){
for(i=0;i<3;i++)
for(j=0;j<3;j++){
m[i][j]=0;
fw[i][j]=0;
fl[i][j]=0;
}
s=0;
st=-1;
new Thread(this).start();
return;
}
if(t<0||t>9)return;
tx=t%3;
ty=t/3;
if(m[tx][ty]!=0){
fl[tx][ty]--;
if(fl[tx][ty]==0){
m[tx][ty]=0;
if(fw[tx][ty]==0)s-=2;
else if(fw[tx][ty]==1)s+=3;
else if(fw[tx][ty]==2)s+=1;
else if(fw[tx][ty]==3)s+=5;
else if(fw[tx][ty]==4)s+=10;
else if(fw[tx][ty]==5)s+=r.nextInt(4)+1;
else if(fw[tx][ty]==6)s-=r.nextInt(4)+1;
ls[tx][ty]=1;
}
}
}


	public byte[] itb(int i)
	{
		byte abyte0[] = new byte[4];
		abyte0[3] = (byte)(0xff & i >> 24);
		abyte0[2] = (byte)(0xff & i >> 16);
		abyte0[1] = (byte)(0xff & i >> 8);
		abyte0[0] = (byte)(0xff & i >> 0);
		return abyte0;
	}

	public int bti(byte abyte0[])
	{
		int i = abyte0[3] & 0xff;
		i = (i << 8) + (abyte0[2] & 0xff);
		i = (i << 8) + (abyte0[1] & 0xff);
		i = (i << 8) + (abyte0[0] & 0xff);
		return i;
	}
	public byte[] itb2(int i)
	{
		byte abyte0[] = new byte[4];
abyte0[0]=(byte)i;
		return abyte0;
	}

	public int bti2(byte abyte0[])
	{
		return abyte0[0];
	}
public void pointerPressed(int x,int y){
if(x>=240||y>=240)return;
int a=y/80,b=x/80;
keyPressed(a*3+b+49);
}
}