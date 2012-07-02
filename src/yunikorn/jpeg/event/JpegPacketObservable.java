/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package yunikorn.jpeg.event;
import java.io.InputStream;
import yunikorn.jpeg.JpegAudioSequence;
import yunikorn.jpeg.JpegVideoFrame;
import yunikorn.core.packet.MediaStreamObservable;
import yunikorn.core.StreamParser;
import java.util.Set;
import yunikorn.core.packet.MediaStreamListener;
import yunikorn.core.StreamListener;
import yunikorn.utils.ConcurrentHashSet;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.MemoryCacheImageInputStream;
import java.awt.image.BufferedImage;
import java.io.IOException;
/**
 *
 * @author testi
 */
public class JpegPacketObservable implements MediaStreamObservable<JpegVideoFrame, JpegAudioSequence>, StreamParser {

	final private Set<MediaStreamListener<? super JpegVideoFrame, ? super JpegAudioSequence>> observers;
	final private Set<StreamListener> streamListener;

        public JpegPacketObservable() {
        observers = new ConcurrentHashSet<MediaStreamListener<? super JpegVideoFrame, ? super JpegAudioSequence>>();
        streamListener = new ConcurrentHashSet<StreamListener>();
        }

    public void addStreamListener(StreamListener listener) {
        streamListener.add(listener);
    }

    public void readStream(InputStream source) {


    //String imageDelimiter = "\n--" + yunikorn.multipart.MJPEGHttpHandler.BOUNDARY;
    
    ImageReader reader = ImageIO.getImageReadersByFormatName("jpeg").next();
    MemoryCacheImageInputStream iis = new MemoryCacheImageInputStream(source);
    reader.setInput(iis);


    int i = 0;
    try {
    //java.util.Iterator<javax.imageio.IIOImage> iterator = reader.readAll(null);
    
    long lastIndex = 0;
    while (true) {
    

    BufferedImage image = reader.read(i);
    iis.flushBefore(lastIndex);
    lastIndex = iis.getStreamPosition();
    i++;

    //BufferedImage image = (BufferedImage)(iterator.next().getRenderedImage());
    

    

    
    //try{Thread.sleep(1000);}catch(Exception ex){}
    
    //ImageIO.write(image, "jpeg", new java.io.File("/home/testi/success.jpg"));
    notifyObservers(new JpegVideoFrame(image,System.currentTimeMillis()));
    }
    } catch (IOException ex) {
    System.err.println(ex);
    } finally {
        System.err.println("Closing stream after reading " + i + " frames");
        notifyEndOfStream();}
    }

    /*private byte[] readChunk(InputStream source, String delimiter) {
    source.
    }*/

	private void notifyEndOfStream() {
		synchronized (observers) {
			for (MediaStreamListener<? super JpegVideoFrame, ? super JpegAudioSequence> t : observers) {
				t.onEndOfStream();
			}
		}
		synchronized(streamListener)
		{
			for (StreamListener t : streamListener)
			{
				t.onEndOfStream();
			}
		}
	}
    	private void notifyObservers(JpegVideoFrame frame) {
		synchronized (observers) {
			for (MediaStreamListener<? super JpegVideoFrame, ? super JpegAudioSequence> t : observers) {
				t.onVideoFrame(frame);
			}
		}
	}

    public void removeStreamListener(StreamListener listener) {
        streamListener.remove(listener);
    }

    public void addListener(MediaStreamListener<? super JpegVideoFrame, ? super JpegAudioSequence> listener) {
        observers.add(listener);
    }

    public void removeListener(MediaStreamListener<? super JpegVideoFrame, ? super JpegAudioSequence> listener) {
        observers.remove(listener);
    }

}
