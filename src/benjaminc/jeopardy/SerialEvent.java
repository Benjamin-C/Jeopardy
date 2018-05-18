package benjaminc.jeopardy;

import java.io.IOException;

public interface SerialEvent {

	public abstract void onDataAvaliable(byte data);
	public abstract void IOException(IOException e);
}
