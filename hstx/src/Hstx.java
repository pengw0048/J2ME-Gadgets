import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
public class Hstx extends MIDlet implements CommandListener,ItemCommandListener,Runnable{
private Display d;
private TextField tf1,tf2,btf1,btf2,btf3;
private TextBox d1,d2,bd;
private Form f1,f2,f3,bf;
private StringItem si[];
private double xs[],cs[],d1z,d2z;
private byte len,edtIdx;
private boolean xzt=true,d1b,d2b,hjd=true;
private ChoiceGroup bc1,bc2,bc3,bc4;
private Command add,edit,del,ok1,back1,ok2,back2,zt,dom1,dom2,back3,ok3,jd,bc,badd,bok1,bok2,bback2,bdel,bds,bdsok;
private List l;
private HstxC h;
private int bch[][];
private double bv[][];
private static final String ys[]={"+","-","*","/","^","sqrt","abs","[]","sin","cos","tan","ln"},lx[]={"����","����","x"},cr[]={"����","y"};
private int times,blen;
public Hstx(){
d=Display.getDisplay(this);
h=new HstxC();
bch=new int[20][4];
bv=new double[20][3];
Thread t=new Thread(this);
t.start();
commonCreate();
}
private void commonCreate(){
len=0;
xs=new double[10];
cs=new double[10];
add=edit=del=ok1=back1=ok2=back2=ok3=back3=zt=jd=dom1=dom2=null;
add=new Command("������",Command.ITEM,1);
edit=new Command("�༭",Command.ITEM,2);
del=new Command("ɾ��",Command.ITEM,2);
ok1=new Command("ȷ��",Command.OK,1);
back1=new Command("����",Command.SCREEN,1);
ok2=new Command("ȷ��",Command.OK,1);
back2=new Command("����",Command.SCREEN,1);
ok3=new Command("ȷ��",Command.OK,1);
bdsok=new Command("ȷ��",Command.OK,1);
back3=new Command("����",Command.SCREEN,1);
//zt=new Command("��ʾ��С����",Command.ITEM,1);
//jd=new Command("��ͼ���ȣ���",Command.ITEM,1);
dom1=new Command("���ö������½�",Command.ITEM,1);
dom2=new Command("���ö������Ͻ�",Command.ITEM,1);
bc=new Command("ָ��ģʽ",Command.ITEM,1);
bds=new Command("������ʽ",Command.ITEM,1);
updateForm();
}
private void updateForm(){
si=null;
si=new StringItem[10];
f1=null;
f1=new Form("�½�����");
f1.append("y=");
for(byte i=0;i<len;i++){
si[i]=new StringItem(null,(i!=0.0&&xs[i]>=0.0?'+':' ')+""+xs[i]+"x^"+cs[i]);
si[i].addCommand(edit);
si[i].addCommand(del);
si[i].setItemCommandListener(this);
f1.append(si[i]);
}
f1.append(new StringItem("\n  ������",(d1b==true?d1z+"":"��")+"~"+(d2b==true?d2z+"":"��")));
f1.addCommand(ok1);
f1.addCommand(add);
f1.addCommand(back1);
//f1.addCommand(zt);
//f1.addCommand(jd);
f1.addCommand(dom1);
f1.addCommand(dom2);
f1.addCommand(bc);
f1.addCommand(bds);
f1.setCommandListener(this);
d.setCurrent(f1);
//System.gc();
}
public void startApp(){
d.setCurrent(f1);
}
public void pauseApp(){
}
public void destroyApp(boolean _){
}
public void commandAction(Command cmd,Displayable dis){
if(cmd==bdsok){
if(bd.getString()=="")return;
BDS t=BDS.trans(bd.getString());
if(t.errn==1){
showAlert("���ʽ����");
return;
}
//System.out.println(t.len);
/*for(int i=0;i<t.len;i++){
//System.out.print(i+" "+t.type[i]+" ");
if(t.type[i]==1)System.out.print(t.dd[i]+" ");
if(t.type[i]==2)System.out.print(t.dc[i]+" ");
if(t.type[i]==3)System.out.print("x ");
System.out.println();
}*/
h.addBds(t);
d.setCurrent(h);
return;
}
if(cmd==bds){
bd=new TextBox("y=","",100,TextField.ANY);
bdsok=new Command("ȷ��",Command.OK,1);
bd.addCommand(bdsok);
bd.setCommandListener(this);
d.setCurrent(bd);
return;
}
if(cmd==bok1){
h.addBc(bch,bv,blen,d1b,d2b,d1z,d2z);
d.setCurrent(h);
}
if(cmd==bok2){
int tc1,tc2,tc3,tc4;
double tt1,tt2,tt3;
tc1=bc1.getSelectedIndex();
tc2=bc2.getSelectedIndex();
tc3=bc3.getSelectedIndex();
tc4=bc4.getSelectedIndex();
try{
tt1=Double.parseDouble(btf1.getString());
tt2=Double.parseDouble(btf2.getString());
tt3=Double.parseDouble(btf3.getString());
}catch(Exception e){
showAlert("��������");
return;
}
if(tc2==1&&((int)tt1<0||(int)tt1>19)){
showAlert("a�±�Խ��");
return;
}
if(tc3==1&&((int)tt2<0||(int)tt2>19)){
showAlert("b�±�Խ��");
return;
}
if(tc4==0&&((int)tt3<0||(int)tt3>19)){
showAlert("���ֵ�±�Խ��");
return;
}
/*if(tc1==4&&(tt2<0||(double)((long)tt2)!=tt2)){
showAlert("������������Ȼ��");
return;
}*/
bch[blen][0]=tc1;
bch[blen][1]=tc2;
bch[blen][2]=tc3;
bch[blen][3]=tc4;
bv[blen][0]=tt1;
bv[blen][1]=tt2;
bv[blen][2]=tt3;
StringBuffer tsb=new StringBuffer();
if(tc2==1){
tsb.append("(");
tsb.append((int)tt1);
tsb.append(")");
}
else if(tc2==2)tsb.append("x");
else tsb.append(tt1);
tsb.append(" ");
tsb.append(ys[tc1]);
tsb.append(" ");
if(tc3==1){
tsb.append("(");
tsb.append((int)tt2);
tsb.append(")");
}
else if(tc3==2)tsb.append("x");
else tsb.append(tt2);
tsb.append(" -> ");
if(tc4==0){
tsb.append("(");
tsb.append((int)tt3);
tsb.append(")");
}
else tsb.append("y");
l.append(tsb.toString(),null);
tsb=null;
blen++;
d.setCurrent(l);
}
if(cmd==bdel){
if(blen==0)return;
blen--;
l.delete(blen);
d.setCurrent(l);
return;
}
if(cmd==bback2){
d.setCurrent(l);
return;
}
if(cmd==badd){
if(blen>19){
showAlert("���20��");
return;
}
bf=null;
bf=new Form("���");
bok2=null;
bback2=null;
bok2=new Command("ȷ��",Command.OK,1);
bback2=new Command("����",Command.BACK,2);
bc1=bc2=bc3=bc4=null;
bc1=new ChoiceGroup("����",Choice.POPUP,ys,null);
bc2=new ChoiceGroup("����a����",Choice.POPUP,lx,null);
btf1=btf2=btf3=null;
btf1=new TextField("a��ֵ���±�","0",10,TextField.DECIMAL);
bc3=new ChoiceGroup("����b����",Choice.POPUP,lx,null);
btf2=new TextField("b��ֵ���±�","0",10,TextField.DECIMAL);
bc4=new ChoiceGroup("����������",Choice.POPUP,cr,null);
btf3=new TextField("�±�","0",10,TextField.DECIMAL);
bf.append(bc1);
bf.append(bc2);
bf.append(btf1);
bf.append(bc3);
bf.append(btf2);
bf.append(bc4);
bf.append(btf3);
bf.addCommand(bok2);
bf.addCommand(bback2);
bf.setCommandListener(this);
d.setCurrent(bf);
}
if(cmd==bc){
blen=0;
l=null;
l=new List("ָ��ģʽ",List.IMPLICIT);
bok1=bdel=badd=null;
bok1=new Command("ȷ��",Command.OK,1);
badd=new Command("���",Command.ITEM,1);
bdel=new Command("ɾ�����һ��",Command.ITEM,2);
l.addCommand(bok1);
l.addCommand(badd);
l.addCommand(bdel);
l.setCommandListener(this);
d.setCurrent(l);
}
if(cmd==dom1){
d1=null;
d1=new TextBox("��������Сֵ",d1b==false?"":""+d1z,20,TextField.DECIMAL);
d1.addCommand(ok3);
d1.addCommand(back3);
d1.setCommandListener(this);
d.setCurrent(d1);
return;
}
if(cmd==dom2){
d2=null;
d2=new TextBox("���������ֵ",d2b==false?"":""+d2z,20,TextField.DECIMAL);
d2.addCommand(ok3);
d2.addCommand(back3);
d2.setCommandListener(this);
d.setCurrent(d2);
return;
}
if(cmd==ok3){
if(dis==d1){
try{
d1z=Double.parseDouble(d1.getString());
d1b=true;
}catch(Exception e){
d1z=0d;
d1b=false;
}
}else{
try{
d2z=Double.parseDouble(d2.getString());
d2b=true;
}catch(Exception e){
d2z=0d;
d2b=false;
}
}
updateForm();
d.setCurrent(f1);
return;
}
if(cmd==back3){
d.setCurrent(f1);
return;
}
if(cmd==add){
if(len<10){
edtIdx=(byte)50;
newF2();
}
else showAlert("���10��");
}
if(cmd==back1){
d.setCurrent(h);
}
if(cmd==ok1){
if(len>0){
h.addFunc(xs,cs,len,d1b,d2b,d1z,d2z);
d.setCurrent(h);
}
else showAlert("������1��");
}
if(cmd==ok2){
if(tf1.getString()==""||tf2.getString()=="")return;
double ttt=Double.parseDouble(tf2.getString());
/*if(ttt!=(double)((int)ttt)&&Math.abs(ttt)!=0.5){
showAlert("Ϊ�˱�֤�����ٶȣ�������������������0.5��ʾ������������ʾ������");
return;
}*/
byte tlen=(edtIdx==50?len:edtIdx);
if(Double.parseDouble(tf1.getString())!=0){
xs[tlen]=Double.parseDouble(tf1.getString());
cs[tlen]=Double.parseDouble(tf2.getString());
if(edtIdx==50)len++;
updateForm();
}
else showAlert("ϵ������Ϊ0");
}
if(cmd==back2){
d.setCurrent(f1);
}
}
private void newF2(){
f2=null;
f2=new Form("������");
tf1=tf2=null;
tf1=new TextField("ϵ��",null,20,TextField.DECIMAL);
tf2=new TextField("����",null,20,TextField.DECIMAL);
f2.append(tf1);
f2.append(tf2);
if(edtIdx!=50){
tf1.setString(xs[edtIdx]+"");
tf2.setString(cs[edtIdx]+"");
}
f2.addCommand(ok2);
f2.addCommand(back2);
f2.setCommandListener(this);
d.setCurrent(f2);
}
public void commandAction(Command cmd,Item item){
item=(StringItem)item;
byte i,j;
for(i=0;i<10;i++)
if(item==si[i])break;
if(cmd==edit){
edtIdx=i;
newF2();
}
if(cmd==del){
for(j=i;j<len-1;j++){
cs[j]=cs[j+1];
xs[j]=xs[j+1];
}
len--;
updateForm();
}
}
private void showAlert(String alt){
Alert a=new Alert("��ʾ",alt,null,AlertType.INFO);
a.setTimeout(Alert.FOREVER);
d.setCurrent(a);
}
public void run(){
while(true){
if(h!=null){
if(h.need==true){
d1b=false;
d2b=false;
d1z=0d;
d2z=0d;
commonCreate();
h.need=false;
}
}
times++;
if(times>15){
times=0;
System.gc();
}
try{
Thread.sleep(1000);
}catch(Exception e){
}
}
}
}