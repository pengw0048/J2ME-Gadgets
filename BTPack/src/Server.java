// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

import javax.bluetooth.LocalDevice;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnectionNotifier;

public class Server
	implements Runnable
{

	private static final String id = "25392539253925392539253925392539";
	private StreamConnectionNotifier s;
	private BTPack ins;

	public Server(BTPack btpack)
	{
		(new Thread(this)).start();
		ins = btpack;
	}

	public void run()
	{
		UUID uuid = new UUID("25392539253925392539253925392539", false);
		try
		{
			LocalDevice localdevice = LocalDevice.getLocalDevice();
			ins.append1(localdevice.getBluetoothAddress() + "\n");
			localdevice.setDiscoverable(0x9e8b33);
			ins.append1("set discoverable\n");
			ins.append1("btspp://localhost:" + uuid.toString() + ";name=Weihong;authorize=false;authenticate=false;encrypt=false");
			s = (StreamConnectionNotifier)Connector.open("btspp://localhost:" + uuid.toString() + ";name=Weihong;authorize=false;authenticate=false;encrypt=false");
			ins.append1("opened\n");
			javax.microedition.io.StreamConnection streamconnection = s.acceptAndOpen();
			ins.append1("accept and open\n");
			ins.got(streamconnection);
		}
		catch (Exception exception)
		{
			ins.err(exception);
		}
	}
}
