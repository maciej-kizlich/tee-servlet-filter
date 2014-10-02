package pl.maciejkizlich.tools.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class BufferedResponseWrapper extends HttpServletResponseWrapper {

	private final static String EMPTY_STRING = "";

	private HttpServletResponse original;

	private TeeServletOutputStream tee;

	private ByteArrayOutputStream bos;

	public BufferedResponseWrapper(HttpServletResponse response) {
		super(response);
		original = response;
	}

	public String getContent() {
		if (bos != null) {
			return bos.toString();
		}

		return EMPTY_STRING;
	}

	public PrintWriter getWriter() throws IOException {
		return original.getWriter();
	}

	public ServletOutputStream getOutputStream() throws IOException {
		if (tee == null) {
			bos = new ByteArrayOutputStream();
			tee = new TeeServletOutputStream(original.getOutputStream(), bos);
		}
		return tee;
	}
}