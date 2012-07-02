package launch;
import java.io.*;

import yunikorn.multipart.*;
import yunikorn.core.net.*;
import yunikorn.jpeg.*;
import yunikorn.jpeg.factory.*;;
import java.util.List;
import java.util.ArrayList;
import java.net.URL;




public class ChainStreamer {
    
        public static HttpMediaStreamAcceptor<JpegVideoFrame, JpegAudioSequence> acceptor=null;
	public static void main(String[] args)
	{
            /*try {
                        Runtime.getRuntime().addShutdownHook(new Thread() {
                        public void run() {
                        
                            System.out.println("Shutdown");
                            //acceptor.stopListening();
                            System.exit(0);


                        
                        }
                        });
            }
                        catch (IllegalStateException ex) {
                        //Don't care if already shutting down
                        }*/
            
            
            
		//int yukonPort=42803;
		int httpPort=8000;
		if (args.length>0){
			try {
				httpPort=Integer.parseInt(args[0]);
			}
			catch (NumberFormatException ex){}
		}
                
		
		try {

                        BufferedReader urlFile = new BufferedReader(new FileReader("url.list"));

                        String line = null;
                        List<URL> urlList = new ArrayList<URL>();
                        while ((line = urlFile.readLine()) != null){
                        urlList.add(new URL(line));
                        }
                        

			System.out.println("Starting Yunikorn JPEG-Streamchain-Server on port " + httpPort);


                        //urlList.add(new URL("http://localhost:3022/localhost/forcedheadless"));
                        acceptor = new HttpMediaStreamAcceptor<JpegVideoFrame,JpegAudioSequence>(urlList, new JpegPacketObservableFactory());
			
			MJPEGCaster<JpegVideoFrame, JpegAudioSequence> myCaster = new MJPEGCaster<JpegVideoFrame, JpegAudioSequence>(httpPort);
			acceptor.addStreamListener(myCaster);

                        acceptor.startListening();
			
		} catch (IOException ex)
		{
			System.err.println("Failed to setup server: " + ex);
                        System.exit(0);
			//ex.printStackTrace();
		}

		
		
	}
	
}
