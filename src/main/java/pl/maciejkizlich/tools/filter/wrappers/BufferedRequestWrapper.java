package pl.maciejkizlich.tools.filter.wrappers;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import pl.maciejkizlich.tools.filter.streams.BufferedServletInputStream;

public final class BufferedRequestWrapper extends HttpServletRequestWrapper {

		private ByteArrayInputStream bais = null;
		
		private ByteArrayOutputStream baos = null;
		
		private BufferedServletInputStream bsis = null;
		
		private byte[] buffer = null;

		public BufferedRequestWrapper(HttpServletRequest req) throws IOException {
			super(req);
			final InputStream is = req.getInputStream();
			this.baos = new ByteArrayOutputStream();
			byte buf[] = new byte[1024];
			int l;
			while ((l = is.read(buf)) > 0) {
				this.baos.write(buf, 0, l);
			}
			this.buffer = this.baos.toByteArray();
		}

		@Override
		public ServletInputStream getInputStream() {
			this.bais = new ByteArrayInputStream(this.buffer);
			this.bsis = new BufferedServletInputStream(this.bais);
			return this.bsis;
		}

		public String getRequestBody() throws IOException {
			BufferedReader reader = new BufferedReader(new InputStreamReader(this.getInputStream()));
			String line = null;
			final StringBuilder inputBuffer = new StringBuilder();
			do {
				line = reader.readLine();
				if (null != line) {
					inputBuffer.append(line.trim());
				}
			} while (line != null);
			reader.close();
			return inputBuffer.toString().trim();
		}

	}