package yunikorn.core.net;

import yunikorn.core.packet.*;
import yunikorn.core.packet.metainterfaces.PacketObservableFactory;
import java.util.List;
import java.net.URL;
public class HttpMediaStreamAcceptor<T extends VideoFrame,E extends AudioSequence> extends HttpStreamAcceptor<MediaStreamObservable<T,E>> {

	public HttpMediaStreamAcceptor(
			List<URL> sources,
			PacketObservableFactory<MediaStreamObservable<T, E>> factory) {
		super(sources, factory);
		// TODO Auto-generated constructor stub
	}

}
