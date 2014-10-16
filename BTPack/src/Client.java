// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

import java.util.Vector;
import javax.bluetooth.*;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;

public class Client
	implements Runnable, DiscoveryListener
{

	private static final String id = "25392539253925392539253925392539";
	private DiscoveryAgent agent;
	private Vector list;
	private ServiceRecord record;
	private int trans;
	private BTPack ins;

	public Client(BTPack btpack)
	{
		ins = btpack;
		list = new Vector();
		try
		{
			LocalDevice localdevice = LocalDevice.getLocalDevice();
			agent = localdevice.getDiscoveryAgent();
			(new Thread(this)).start();
		}
		catch (Exception exception)
		{
			ins.err(exception);
		}
	}

	public void run()
	{
		try
		{
			agent.startInquiry(0x9e8b33, this);
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
			ins.append1("inquiried\n");
			int i = list.size();
			UUID auuid[] = {
				new UUID("25392539253925392539253925392539", false)
			};
			for (int j = 0; j < i && record == null; j++)
			{
				ins.append1("searching at " + j + "\n");
				RemoteDevice remotedevice = (RemoteDevice)list.elementAt(j);
				trans = agent.searchServices(null, auuid, remotedevice, this);
				synchronized (this)
				{
					try
					{
						wait();
					}
					catch (Exception exception3)
					{
						ins.err(exception3);
					}
				}
				if (record == null)
					ins.append1("record==null!\n");
				ins.append1("finished searching\n");
			}

			if (record != null)
			{
				ins.append1("record!=null\n");
				String s = record.getConnectionURL(0, false);
				StreamConnection streamconnection = (StreamConnection)Connector.open(s);
				ins.append1("opened\n");
				ins.got(streamconnection);
			}
		}
		catch (Exception exception)
		{
			ins.err(exception);
		}
	}

	public void stopDiscover()
	{
		if (record == null)
			agent.cancelServiceSearch(trans);
		ins.append1("stopDiscover() called\n");
	}

	public void servicesDiscovered(int i, ServiceRecord aservicerecord[])
	{
		if (record == null)
			record = aservicerecord[0];
		ins.append1("servicesDiscovered() called\n");
	}

	public void serviceSearchCompleted(int i, int j)
	{
		synchronized (this)
		{
			notifyAll();
		}
		ins.append1("serviceSearchCompleted() called\n");
	}

	public void deviceDiscovered(RemoteDevice remotedevice, DeviceClass deviceclass)
	{
		list.addElement(remotedevice);
		ins.append1("deviceDiscovered() called\n");
	}

	public void inquiryCompleted(int i)
	{
		synchronized (this)
		{
			notifyAll();
		}
		ins.append1("inquiryCompleted() called\n");
	}
}
