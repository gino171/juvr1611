package de.ginosoft.juvr.read;

/**
 * Maintains the connection to the UVR device
 * 
 */
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class UVRConnection {

	protected Socket m_uvr;
	protected DataInputStream m_read;
	protected OutputStream m_write;

	public UVRConnection(String url, int port) throws IOException {
		m_uvr = new Socket(url, port);
		m_read = new DataInputStream(m_uvr.getInputStream());
		m_write = m_uvr.getOutputStream();
	}

	public void sendCommand(char cmd) throws IOException {
		m_write.write(cmd);
		m_write.flush();
	}

	public byte[] readBlock(int length) throws IOException {
		byte buf[] = new byte[length];
		int ptr = 0;
		for (int w = 100; w-- > 0 && ptr < length;)
			if (m_read.available() > 0) {
				byte b = m_read.readByte();
				buf[ptr] = b;
				ptr++;
			} else {
				try {
					Thread.sleep(100L);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		if (ptr < length) {
			throw new IOException(
					"Reading data from UVR host failed. No data available.");
		}
		return buf;
	}

	public DataInputStream getInputStream() {
		return m_read;
	}

	public OutputStream getOutputStream() {
		return m_write;
	}

	public void close() throws IOException {
		if (m_read == null) {
			// guard condition: connection already closed
			return;
		}
		int addbytes = 0;
		while (m_read.available() > 0) {
			addbytes++;
			m_read.read();
		}
		m_read.close();
		m_write.close();
		m_uvr.close();
		m_read = null;
		if (addbytes > 0) {
			throw new IOException("Unexpected data of " + addbytes
					+ " read while closing connection.");
		}
	}

}
