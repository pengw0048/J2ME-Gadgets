public class BDS{
public int errn=0;
public int len=0;
public int type[]=new int[100];
public double dd[]=new double[100];
public char dc[]=new char[100];
public static BDS trans(String ts){
 BDS tr=new BDS();
 char s[]=ts.toCharArray(),stk[]=new char[100];
 int l=ts.length(),p=0,sl=0;
 if(s[0]=='-')s[0]='~';
 for(int i=1;i<l;i++)
  if(s[i]=='-'&&(s[i-1]<'0'||s[i-1]>'9')&&s[i-1]!='x')s[i]='~';
 for(int i=0;i<l;i++){
  if(s[i]>='0'&&s[i]<='9'){
   double td=s[i]-'0',mt=1.0;
   int hp=0;
   i++;
   while(i<l&&((s[i]>='0'&&s[i]<='9')||s[i]=='.')){
    if(s[i]=='.'){
     if(hp==1){
      tr.errn=1;
      return tr;
     }
     hp=1;
    }else{
     if(hp==1){
      mt/=10.0;
      td+=(s[i]-'0')*mt;
     }else td=td*10.0+(s[i]-'0');
    }
    i++;
   }
   i--;
   if(hp==1&&mt==1.0){
    tr.errn=1;
    return tr;
   }
   tr.type[p]=1;
   tr.dd[p]=td;
   p++;
   continue;
  }
  if(s[i]=='x'){
   tr.type[p]=3;
   p++;
   continue;
  }
  if(s[i]=='a'){
   if(i+3>=l||s[i+1]!='b'||s[i+2]!='s'||s[i+3]!='('){
    tr.errn=1;
    return tr;
   }
   stk[sl++]='a';
   i+=2;
   continue;
  }
  if(s[i]=='i'){
   if(i+3>=l||s[i+1]!='n'||s[i+2]!='t'||s[i+3]!='('){
    tr.errn=1;
    return tr;
   }
   stk[sl++]='i';
   i+=2;
   continue;
  }
  if(s[i]=='l'){
   if(i+2>=l||s[i+1]!='n'||s[i+2]!='('){
    tr.errn=1;
    return tr;
   }
   stk[sl++]='l';
   i++;
   continue;
  }
  if(s[i]=='s'){
   if(i+3>=l||s[i+1]!='i'||s[i+2]!='n'||s[i+3]!='('){
    if(i+4>=l||s[i+1]!='q'||s[i+2]!='r'||s[i+3]!='t'||s[i+4]!='('){
     tr.errn=1;
     return tr;
    }
    stk[sl++]='q';
    i+=3;
    continue;
   }
   stk[sl++]='s';
   i+=2;
   continue;
  }
  if(s[i]=='c'){
   if(i+3>=l||s[i+1]!='o'||s[i+2]!='s'||s[i+3]!='('){
    tr.errn=1;
    return tr;
   }
   stk[sl++]='c';
   i+=2;
   continue;
  }
  if(s[i]=='t'){
   if(i+3>=l||s[i+1]!='a'||s[i+2]!='n'||s[i+3]!='('){
    tr.errn=1;
    return tr;
   }
   stk[sl++]='t';
   i+=2;
   continue;
  }
  if(s[i]=='~'||s[i]=='('){
   stk[sl++]=s[i];
   continue;
  }
  if(s[i]=='+'||s[i]=='-'){
   while(sl>0&&stk[sl-1]!='('){
    tr.type[p]=2;
    tr.dc[p]=stk[--sl];
    p++;
   }
   stk[sl++]=s[i];
   continue;
  }
  if(s[i]=='*'||s[i]=='/'||s[i]=='^'){
   while(sl>0&&(stk[sl-1]!='('&&stk[sl-1]!='+'&&stk[sl-1]!='-')){
    tr.type[p]=2;
    tr.dc[p]=stk[--sl];
    p++;
   }
   stk[sl++]=s[i];
   continue;
  }
  if(s[i]==')'){
   while(sl>0&&stk[sl-1]!='('){
    tr.type[p]=2;
    tr.dc[p]=stk[--sl];
    p++;
   }
   sl--;
   if(sl<0){
    tr.errn=1;
    return tr;
   }
   continue;
  }
  tr.errn=1;
  return tr;
 }
 while(sl>0){
  if(stk[sl-1]=='('){
   tr.errn=1;
   return tr;
  }
  tr.type[p]=2;
  tr.dc[p]=stk[--sl];
  p++;
 }
 tr.len=p;
 tr.eval(1);
 return tr;
}
public double eval(double x){
 int sl=0;
 double stk[]=new double[100];
 for(int i=0;i<len;i++){
  if(type[i]==1)stk[sl++]=dd[i];
  else if(type[i]==3)stk[sl++]=x;
  else{
   if(dc[i]=='+'){
    if(sl<2){
     errn=1;
     return 0;
    }
    stk[sl-2]=stk[sl-2]+stk[sl-1];
    sl--;
   }
   if(dc[i]=='-'){
    if(sl<2){
     errn=1;
     return 0;
    }
    stk[sl-2]=stk[sl-2]-stk[sl-1];
    sl--;
   }
   if(dc[i]=='*'){
    if(sl<2){
     errn=1;
     return 0;
    }
    stk[sl-2]=stk[sl-2]*stk[sl-1];
    sl--;
   }
   if(dc[i]=='/'){
    if(sl<2){
     errn=1;
     return 0;
    }
    stk[sl-2]=stk[sl-2]/stk[sl-1];
    sl--;
   }
   if(dc[i]=='^'){
    if(sl<2){
     errn=1;
     return 0;
    }
    stk[sl-2]=HstxC.pow(stk[sl-2],stk[sl-1]);
    sl--;
   }
   if(dc[i]=='~'){
    if(sl<1){
     errn=1;
     return 0;
    }
    stk[sl-1]=-stk[sl-1];
   }
   if(dc[i]=='a'){
    if(sl<1){
     errn=1;
     return 0;
    }
    stk[sl-1]=Math.abs(stk[sl-1]);
   }
   if(dc[i]=='q'){
    if(sl<1){
     errn=1;
     return 0;
    }
    stk[sl-1]=Math.sqrt(stk[sl-1]);
   }
   if(dc[i]=='i'){
    if(sl<1){
     errn=1;
     return 0;
    }
    stk[sl-1]=Math.floor(stk[sl-1]);
   }
   if(dc[i]=='s'){
    if(sl<1){
     errn=1;
     return 0;
    }
    stk[sl-1]=Math.sin(stk[sl-1]);
   }
   if(dc[i]=='c'){
    if(sl<1){
     errn=1;
     return 0;
    }
    stk[sl-1]=Math.cos(stk[sl-1]);
   }
   if(dc[i]=='t'){
    if(sl<1){
     errn=1;
     return 0;
    }
    stk[sl-1]=Math.tan(stk[sl-1]);
   }
   if(dc[i]=='l'){
    if(sl<1){
     errn=1;
     return 0;
    }
    stk[sl-1]=HstxC.ln(stk[sl-1]);
   }
  }
 }
 if(sl!=1){
  errn=1;
  return 0;
 }
 return stk[0];
}
}
