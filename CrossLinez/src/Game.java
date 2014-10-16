import javax.microedition.lcdui.*;
import java.util.*;
public class Game extends Canvas implements Runnable{
private int nd,gs=6,x[],y[],l[][],al[],alen,ts,cur,k,mx=120,my=160,h,score,jf;
private long last,start,end;
private Menu ins;
private Random r;
private Font sf;
private Image m;
private Thread t;
private String str;
private boolean fin,sel,win,used,pjl;
public Game(int nd,Menu ins){
setFullScreenMode(true);
sf=Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);
this.nd=nd;
this.ins=ins;
r=new Random();
try{
m=Image.createImage("/mouse.png");
}catch(Exception e){
}
init();
t=new Thread(this);
t.start();
}
private void init(){
  str=null;
  str="第"+(gs-5)+"关 "+score;
  l=null;
  x=y=al=null;
  x=new int[gs];
  y=new int[gs];
  l=new int[gs][5];
  al=new int[gs];
  for(int i=0;i<gs;i++){
    l[i][0]=2;
    l[i][1]=i-1;
    l[i][2]=i+1;
  }
  l[0][1]=gs-1;
  l[gs-1][2]=0;
  int a,b,ra,rb,sw;
  for(int i=0;i<gs*(nd+1)/4;i++){
    alen=0;
    for(int j=0;j<gs;j++){
      if(l[j][0]<4){
        al[alen]=j;
        alen++;
      }
    }
    a=r.nextInt(alen);
    do{
      b=r.nextInt(alen);
      ra=al[a];
      rb=al[b];
    }while(ra==rb);
    l[ra][0]++;
    l[ra][l[ra][0]]=rb;
    l[rb][0]++;
    l[rb][l[rb][0]]=ra;
  }
  shuffle();
  h=getHeight();
  used=false;
  repaint();
  start=System.currentTimeMillis();
}
public void paint(Graphics g){
g.setFont(sf);
if(win){
g.setColor(0);
g.fillRect(20,80,200,90);
g.setColor(0xffffff);
g.drawString("过关！确定键进下关",120,110,33);
g.drawString("用时："+(end-start)/1000+"s",120,140,33);
g.drawString("得分："+jf,120,170,33);
g.setColor(0xff0000);
if(pjl)g.drawString("破记录！",220,170,40);
return;
}
g.setColor(0xffffff);
g.fillRect(0,0,240,320);
if(hasPointerEvents()){
g.setColor(0xffff00);
g.fillRect(0,h-10,10,10);
g.fillRect(230,h-10,10,10);
}
g.setColor(0);
g.drawString(str,0,0,20);
g.drawString("*:打乱",0,h,36);
g.drawString("#:主菜单",240,h,40);
for(int i=0;i<gs;i++){
for(int j=1;j<=l[i][0];j++){
g.drawLine(x[i],y[i],x[l[i][j]],y[l[i][j]]);
}
}
for(int i=0;i<gs;i++){
g.setColor(0);
g.fillArc(x[i]-5,y[i]-5,10,10,0,360);
g.setColor(0xff);
g.fillArc(x[i]-3,y[i]-3,6,6,0,360);
}
if(sel){
g.setColor(0xff0000);
g.fillArc(x[cur]-3,y[cur]-3,6,6,0,360);
}else{
for(int i=0;i<gs;i++){
if(Math.abs(mx-x[i])<7&&Math.abs(my-y[i])<7){
g.setColor(0xffff);
g.fillArc(x[i]-3,y[i]-3,6,6,0,360);
break;
}
}
}
g.drawImage(m,mx,my,20);
}
public void keyPressed(int key){
if(key==Canvas.KEY_STAR){
shuffle();
return;
}
if(key==Canvas.KEY_POUND){
ins.rtn();
return;
}
key=getGameAction(key);
if(key==Canvas.RIGHT){
k=1;
}else if(key==Canvas.LEFT){
k=2;
}else if(key==Canvas.UP){
k=3;
}else if(key==Canvas.DOWN){
k=4;
}else if(key==Canvas.FIRE){
if(win){
win=sel=false;
gs++;
init();
return;
}
if(sel){
check();
}else{
for(int i=0;i<gs;i++){
if(Math.abs(mx-x[i])<7&&Math.abs(my-y[i])<7){
sel=true;
cur=i;
repaint();
break;
}
}
int mi=0,mv=0xfffffff,t;
for(int i=0;i<gs;i++){
t=(x[i]-mx)*(x[i]-mx)+(y[i]-my)*(y[i]-my);
if(t<mv){
mv=t;
mi=i;
}
}
mx=x[mi];
my=y[mi];
repaint();
}
}
}
public void keyReleased(int key){
ts=k=0;
}
public void run(){
while(!fin){
if(k!=0){
if(sel){
if(k==1){
x[cur]+=ts>>1;
if(x[cur]>235)x[cur]=235;
}else if(k==2){
x[cur]-=ts>>1;
if(x[cur]<5)x[cur]=5;
}else if(k==3){
y[cur]-=ts>>1;
if(y[cur]<5)y[cur]=5;
}else if(k==4){
y[cur]+=ts>>1;
if(y[cur]>h-5)y[cur]=h-5;
}
}
if(k==1){
mx+=ts>>1;
if(mx>240)mx=240;
}else if(k==2){
mx-=ts>>1;
if(mx<0)mx=0;
}else if(k==3){
my-=ts>>1;
if(my<0)my=0;
}else if(k==4){
my+=ts>>1;
if(my>h)my=h;
}
ts+=2;
repaint();
}
try{
Thread.sleep(40);
}catch(Exception e){
}
}
}
private boolean pd(int aa,int bb,int cc,int dd){
    return Math.max(x[aa],x[bb])>=Math.min(x[cc],x[dd])
    && Math.max(y[aa],y[bb])>=Math.min(y[cc],y[dd])
    && Math.max(x[cc],x[dd])>=Math.min(x[aa],x[bb])
    && Math.max(y[cc],y[dd])>=Math.min(y[aa],y[bb])
    && mult(cc,bb,aa)*mult(bb,dd,aa)>0
    && mult(aa,dd,cc)*mult(dd,bb,cc)>0;
}
private long mult(int a,int b,int c){
       return (x[a]-x[c])*(y[b]-y[c])-(x[b]-x[c])*(y[a]-y[c]);
}
private void shuffle(){
int a,b,sw;
for(int i=0;i<gs;i++){
x[i]=(int)(Math.cos(i*Math.PI*2/gs)*100+120);
y[i]=(int)(Math.sin(i*Math.PI*2/gs)*100+160);
}
for(int i=0;i<300;i++){
a=r.nextInt(gs);
b=r.nextInt(gs);
for(int j=0;j<5;j++){
sw=x[a];
x[a]=x[b];
x[b]=sw;
sw=y[a];
y[a]=y[b];
y[b]=sw;
}
repaint();
}
}
public void pointerPressed(int px,int py){
used=true;
mx=px;
my=py;
if(win){
win=sel=false;
gs++;
init();
return;
}
for(int i=0;i<gs;i++){
if(Math.abs(px-x[i])<7&&Math.abs(py-y[i])<7){
sel=true;
last=System.currentTimeMillis();
cur=i;
repaint();
return;
}
}
if(py>h-12){
if(px<12)shuffle();
else if(px>228)ins.rtn();
}
}
public void pointerDragged(int px,int py){
if(sel){
x[cur]=px;
y[cur]=py;
}
mx=px;
my=py;
if(System.currentTimeMillis()-last>50){
repaint();
last=System.currentTimeMillis();
}
}
public void pointerReleased(int px,int py){
mx=px;
my=py;
if(sel)check();
}
private void check(){
for(int j1=0;j1<gs;j1++){
for(int i=1;i<=l[j1][0];i++){
for(int j=0;j<gs;j++){
if(j==j1)continue;
for(int i1=1;i1<=l[j][0];i1++){
if(j1==i1||i==j)continue;
if(pd(j1,l[j1][i],j,l[j][i1])){
sel=false;
repaint();
return;
}
}
}
}
}
win=true;
end=System.currentTimeMillis();
jf=gs*gs*2-(int)((end-start)/1000)*(used?3:2)/2;
if(jf<gs)jf=gs;
score+=jf;
pjl=ins.fen(score,nd);
repaint();
}
}