package pl.maciejkizlich.tools.filter.streams;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;

import org.apache.commons.io.output.TeeOutputStream;

	public class TeeServletOutputStream extends ServletOutputStream {

		private final TeeOutputStream teeOutputStream;

		public TeeServletOutputStream(OutputStream first, OutputStream second) {
			teeOutputStream = new TeeOutputStream(first, second);
		}

		@Override
		public void write(int arg0) throws IOException {
			this.teeOutputStream.write(arg0);
		}

		public void flush() throws IOException {
			super.flush();
			this.teeOutputStream.flush();
		}

		public void close() throws IOException {
			super.close();
			this.teeOutputStream.close();
		}
	}