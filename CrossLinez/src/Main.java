import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
public class Main extends MIDlet implements Runnable{
private Menu m;
private int t;
private Display d;
public Main(){
d=Display.getDisplay(this);
m=new Menu(d,this);
new Thread(this).start();
}
public void startApp(){
d.setCurrent(m);
}
public void pauseApp(){
}
public void destroyApp(boolean b){
}
public void run(){
while(true){
if(t>30){
System.gc();
t=0;
}
t++;
try{
Thread.sleep(1000);
}catch(Exception e){
}
}
}
public void nd(){

notifyDestroyed();
}
}