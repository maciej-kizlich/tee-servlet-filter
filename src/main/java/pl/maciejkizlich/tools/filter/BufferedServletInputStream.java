package pl.maciejkizlich.tools.filter;

import java.io.ByteArrayInputStream;

import javax.servlet.ServletInputStream;

	final class BufferedServletInputStream extends ServletInputStream {

		private ByteArrayInputStream byteArrayInputStream;

		public BufferedServletInputStream(ByteArrayInputStream bais) {
			this.byteArrayInputStream = bais;
		}

		@Override
		public int available() {
			return this.byteArrayInputStream.available();
		}

		@Override
		public int read() {
			return this.byteArrayInputStream.read();
		}

		@Override
		public int read(byte[] buf, int off, int len) {
			return this.byteArrayInputStream.read(buf, off, len);
		}

	}