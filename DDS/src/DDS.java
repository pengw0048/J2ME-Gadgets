import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

public class DDS extends MIDlet{
private Display d;
private DDSC ddsc;
public DDS(){
d=Display.getDisplay(this);
ddsc=new DDSC(this);
}
public void startApp(){
d.setCurrent(ddsc);
}
public void pauseApp(){
}
public void destroyApp(boolean _b){
}
public void alert(String s){
Alert a=new Alert(s);
a.setTimeout(-2);
d.setCurrent(a);
}
}