// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

import java.io.DataInputStream;
import java.io.DataOutputStream;
import javax.microedition.io.StreamConnection;

public class Connection
	implements Runnable
{

	private StreamConnection sconn;
	private DataOutputStream dos;
	private DataInputStream dis;
	private boolean c;
	private Thread st;
	private Thread rt;
	private BTPack ins;
	private int data;
	private int x;

	public Connection(StreamConnection streamconnection, BTPack btpack)
	{
		sconn = streamconnection;
		ins = btpack;
		try
		{
			dis = sconn.openDataInputStream();
			dos = sconn.openDataOutputStream();
		}
		catch (Exception exception)
		{
			ins.err(exception);
		}
		c = true;
		rt = new Thread(this);
		st = new Thread(this);
		rt.start();
		st.start();
	}

	public void send(int i)
	{
		data = i;
		synchronized (this)
		{
			notifyAll();
		}
		synchronized (this)
		{
			try
			{
				wait();
			}
			catch (Exception exception1)
			{
				ins.err(exception1);
			}
		}
	}

	public void close()
	{
		c = false;
		synchronized (this)
		{
			notifyAll();
		}
	}

	public void run()
	{
		do
		{
			if (!c)
				break;
			try
			{
				if (Thread.currentThread() == st)
				{
					synchronized (this)
					{
						try
						{
							wait();
						}
						catch (Exception exception1)
						{
							ins.err(exception1);
						}
					}
					if (dos != null)
						dos.writeInt(data);
					flush();
					synchronized (this)
					{
						notifyAll();
					}
				} else
				if (Thread.currentThread() == rt && dis != null)
				{
					x = dis.readInt();
					ins.receiveMessage(x);
				}
			}
			catch (Exception exception)
			{
				ins.err(exception);
			}
		} while (true);
	}

	public void flush()
	{
		try
		{
			dos.flush();
		}
		catch (Exception exception) { }
	}
}
