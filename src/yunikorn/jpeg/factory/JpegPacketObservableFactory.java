/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package yunikorn.jpeg.factory;
import yunikorn.core.packet.metainterfaces.PacketObservableFactory;
import yunikorn.core.packet.*;
import yunikorn.jpeg.event.JpegPacketObservable;
import yunikorn.jpeg.JpegAudioSequence;
import yunikorn.jpeg.JpegVideoFrame;
/**
 *
 * @author testi
 */
public class JpegPacketObservableFactory implements PacketObservableFactory<MediaStreamObservable<JpegVideoFrame,JpegAudioSequence>> {

    public MediaStreamObservable<JpegVideoFrame, JpegAudioSequence> createPacketObservable() {
    return new JpegPacketObservable();
    }

}
