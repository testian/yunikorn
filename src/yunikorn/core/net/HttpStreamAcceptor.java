package yunikorn.core.net;

import java.net.ServerSocket;
import java.net.Socket;


import yunikorn.core.packet.metainterfaces.PacketObservable;
import yunikorn.core.packet.metainterfaces.PacketObservableFactory;
import yunikorn.core.packet.metainterfaces.PacketStreamListener;
import yunikorn.core.*;
import yunikorn.utils.*;

import java.net.*;
import java.util.*;
import java.io.*;


public class HttpStreamAcceptor<T extends PacketObservable<?> & StreamParser> {

	//private int port;
	private PacketObservableFactory<T> factory;
	private Set<PacketStreamListener<InetAddress, T>> listeners;
        private List<URL> sources;
        private static int retryDelay = 30;

        
	public HttpStreamAcceptor(List<URL> sources, PacketObservableFactory<T> factory) {
		super();
		this.sources = sources;
		this.factory = factory;
		listeners = new ConcurrentHashSet<PacketStreamListener<InetAddress, T>>();

	}
	private class ReaderThread implements Runnable
	{
		private StreamParser parser;
		private URL streamSource;

		public ReaderThread(StreamParser parser, URL streamSource) {
			super();
			this.parser = parser;
			this.streamSource = streamSource;
		}

		public void run() {
                    while (true) {
                        try {
                        InputStream is = streamSource.openStream();
                        notifyListeners(InetAddress.getByName(streamSource.getHost()), (T)parser);
                        parser.readStream(is);
                        } catch (Exception ex) {System.err.println(ex);}
                        System.err.println(streamSource + " disconnected, retrying in " + retryDelay + " seconds");
                        try {
                        Thread.sleep(1000 * retryDelay);
                        
                        } catch (InterruptedException ex) {System.err.println(ex);}
                    }
		}
		
		
	}
	
        /**
         * Starts listening
         * @throws java.io.IOException when
         */
        public void startListening() throws IOException
	{
                        
                        
                        
                        
                        
                        
                        


                        for (URL u : sources) {
                        
                        System.out.println("Connecting to " + u);
			T observable = factory.createPacketObservable();
                        
			new Thread(new ReaderThread(observable, u)).start();
                        
                        }

			
			
                }/* catch (BindException ex) {

	}
        /**
         * Stops listening (leaving opened connections opened)
         * Does nothing if startListening wasn't called beforehand
         * 
         */
        /*public void stopListening() throws IOException {
        if (listenSocket != null) {
            
            listenSocket.close();
            
        }
        }*/
        
	public void addStreamListener(PacketStreamListener<InetAddress, T> listener)
	{
		listeners.add(listener);
	}
	public void removeStreamListener(PacketStreamListener<InetAddress, T> listener)
	{
		listeners.remove(listener);
	}
	private void notifyListeners(InetAddress address, T observable)
	
	{
	for (PacketStreamListener<InetAddress, T> t: listeners)
	{
		t.onIncomingStream(address, observable);
	}
	}
	
	
}
