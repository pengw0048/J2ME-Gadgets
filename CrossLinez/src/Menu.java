import javax.microedition.lcdui.*;
import javax.microedition.rms.*;
public class Menu extends Canvas{
private Display d;
private int item,hi[];
private Image menu;
private Game g;
private Font sf;
private Main ins;
private RecordStore rs;
private boolean sh,ab;
public Menu(Display d,Main ins){
//if(getHeight()<280||getWidth()<230)
d.setCurrent(new Alert("Error","This game cannot run with screen smaller than 240*320!",null,AlertType.ERROR));
sf=Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);
setFullScreenMode(true);
hi=new int[3];
this.ins=ins;
this.d=d;
try{
menu=Image.createImage("/menu.png");
}catch(Exception e){
}
try{
rs=RecordStore.openRecordStore("hi",false);
}catch(Exception e){
try{
rs=RecordStore.openRecordStore("hi",true);
rs.addRecord(itb(0),0,4);
rs.addRecord(itb(0),0,4);
rs.addRecord(itb(0),0,4);
}catch(Exception e1){
}
}
try{
for(int i=1;i<4;i++)hi[i-1]=bti(rs.getRecord(i));
}catch(Exception e1){
}
}
public void paint(Graphics g){
g.setFont(sf);
if(ab){
ab=false;
g.setColor(0);
g.fillRect(20,200,200,90);
g.setColor(0xffffff);
g.drawString("Make no lines",120,220,33);
g.drawString("across to win.",120,250,33);
g.drawString("hi.baidu.com/Î±ºìÑ§¼Ò",120,280,33);
return;
}
if(item<6){
g.drawImage(menu,0,0,20);
g.setColor(0xff0000);
g.fillRect(70,210+item*16,4,4);
g.fillRect(170,210+item*16,4,4);
}
if(sh){
sh=false;
g.setColor(0xff0000);
for(int i=0;i<3;i++){
g.drawString(""+hi[i],180,216+i*16,68);
}
}
}
public void keyPressed(int key){
key=getGameAction(key);
if(key==Canvas.FIRE){
if(item<3){
g=null;
g=new Game(item,this);
d.setCurrent(g);
}else if(item==5){
ins.nd();
}else if(item==3){
sh=true;
repaint();
}else if(item==4){
ab=true;
repaint();
}
}else if(key==Canvas.UP){
item--;
if(item<0)item=5;
repaint();
}else if(key==Canvas.DOWN){
item++;
if(item>5)item=0;
repaint();
}
}
public void rtn(){
d.setCurrent(this);
g=null;
}
	public byte[] itb(int i)
	{
		byte abyte0[] = new byte[4];
		abyte0[0] = (byte)(0xff & i >> 24);
		abyte0[1] = (byte)(0xff & i >> 16);
		abyte0[2] = (byte)(0xff & i >> 8);
		abyte0[3] = (byte)(0xff & i >> 0);
		return abyte0;
	}

	public int bti(byte abyte0[])
	{
		int i = abyte0[0] & 0xff;
		i = (i << 8) + (abyte0[1] & 0xff);
		i = (i << 8) + (abyte0[2] & 0xff);
		i = (i << 8) + (abyte0[3] & 0xff);
		return i;
	}
public boolean fen(int score,int nd){
if(score>hi[nd]){
hi[nd]=score;
try{
rs.setRecord(nd+1,itb(score),0,4);
}catch(Exception e1){
}
return true;
}else{
return false;
}
}
}