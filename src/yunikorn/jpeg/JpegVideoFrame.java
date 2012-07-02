/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package yunikorn.jpeg;
import java.io.File;
import java.io.IOException;
import yunikorn.core.packet.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
/**
 *
 * @author testi
 */
public class JpegVideoFrame implements VideoFrame {

    private BufferedImage image;
    private long timestamp;

    public JpegVideoFrame(BufferedImage image, long timestamp) {
    this.image = image;
    this.timestamp = timestamp;
    }
    /*private void loadImage() {
    if (image != null) return;
    ByteArrayInputStream bis = new ByteArrayInputStream(jpegData);
    ImageIO.read(jpegData);

    }*/


    public int[] getARGBBuffer() throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public byte[] getBuffer() throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public BufferedImage getBufferedImage() throws IOException {
        return image;
    }

    public int getHeight() {
        return image.getHeight();
    }

    public int getWidth() {
        return image.getWidth();
    }

    public void writeJpg(File jpgfile) throws IOException {
        ImageIO.write(image, "jpeg", jpgfile);
    }

    public long getTimestamp() {
        return timestamp;
    }

}
