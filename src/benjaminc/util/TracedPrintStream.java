package benjaminc.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

public class TracedPrintStream extends PrintStream{
	
	private boolean fullStackTrace;
	
	public TracedPrintStream(File file) throws FileNotFoundException {
		super(file);
	}
	public TracedPrintStream(File file, String csn) throws FileNotFoundException, UnsupportedEncodingException {
		super(file, csn);
	}
	public TracedPrintStream(OutputStream outpuStream) {
		super(outpuStream);
	}
	public TracedPrintStream(OutputStream outpuStream, boolean autoflush, String encoding) throws UnsupportedEncodingException {
		super(outpuStream, autoflush, encoding);
	}
	public TracedPrintStream(String fileName) throws FileNotFoundException {
		super(fileName);
	}
	public TracedPrintStream(String fileName, String csn) throws FileNotFoundException, UnsupportedEncodingException {
		super(fileName, csn);
	}
	
	public TracedPrintStream(File file, boolean full) throws FileNotFoundException {
		super(file);
		fullStackTrace = full;
	}
	public TracedPrintStream(File file, String csn, boolean full) throws FileNotFoundException, UnsupportedEncodingException {
		super(file, csn);
		fullStackTrace = full;
	}
	public TracedPrintStream(OutputStream outpuStream, boolean full) {
		super(outpuStream);
		fullStackTrace = full;
	}
	public TracedPrintStream(OutputStream outpuStream, boolean autoflush, boolean full) {
		super(outpuStream, autoflush);
		fullStackTrace = full;
	}
	public TracedPrintStream(OutputStream outpuStream, boolean autoflush, String encoding, boolean full) throws UnsupportedEncodingException {
		super(outpuStream, autoflush, encoding);
		fullStackTrace = full;
	}
	public TracedPrintStream(String fileName, boolean full) throws FileNotFoundException {
		super(fileName);
		fullStackTrace = full;
	}
	public TracedPrintStream(String fileName, String csn, boolean full) throws FileNotFoundException, UnsupportedEncodingException {
		super(fileName, csn);
		fullStackTrace = full;
	}
	

	@SuppressWarnings("unused")
	private String stackTrace() {
		return stackTrace(0, 1);
	}
	@SuppressWarnings("unused")
	private String stackTrace(int rel) {
		return stackTrace(rel, 1);
	}
	private String stackTrace(int rel, int step) {
		StackTraceElement loc = Thread.currentThread().getStackTrace()[2 + step];
		if(fullStackTrace) {
			String st = "";
			for(int i = Thread.currentThread().getStackTrace().length - 1; i > step + 2; i--) {
				String filename = Thread.currentThread().getStackTrace()[i].getFileName();
				if(filename != null) {
					st = st + "(" + filename.substring(0,  filename.lastIndexOf('.')) + ":" + (Thread.currentThread().getStackTrace()[i].getLineNumber()) + ")>";
				}
			}
			return Thread.currentThread().getName() + " " + st + " " + loc.getClassName() + "." + loc.getMethodName() + "(" + loc.getFileName() + ":" + (loc.getLineNumber() + rel) + ")";
		} else {
			return loc.getClassName() + "." + loc.getMethodName() + "(" + loc.getFileName() + ":" + (loc.getLineNumber() + rel) + ")";
		}
	}
	
	@Override
	public PrintStream append(char c) {
		super.append(c);
		return this;
	}
	@Override
	public PrintStream append(CharSequence c) {
		super.append(c);
		return this;
	}
	@Override
	public PrintStream append(CharSequence csq, int start, int end) {
		super.append(csq, start, end);
		return this;
	}
	@Override
	public boolean checkError() {
		return super.checkError();
	}
	@Override
	public void close() {
		super.close();
	}
	@Override
	public void flush() {
		super.flush();
	}
	@Override
	public PrintStream format(Locale l, String format, Object... args) {
		super.format(l, format, args);
		return this;
	}
	@Override
	public PrintStream format(String format, Object... args) {
		super.format(format, args);
		return this;
	}
	@Override
	public void print(boolean x) {
		super.print(x);
	}
	@Override
	public void print(char x) {
		super.print(x);
	}
	@Override
	public void print(char[] x) {
		super.print(x);
	}
	@Override
	public void print(double x) {
		super.print(x);
	}
	@Override
	public void print(float x) {
		super.print(x);
	}
	@Override
	public void print(int x) {
		super.print(x);
	}
	@Override
	public void print(long x) {
		super.print(x);
	}
	@Override
	public void print(Object x) {
		super.print(x);
	}
	@Override
	public void print(String x) {
		super.print(x);
	}
	@Override
	public PrintStream printf(Locale l, String format, Object... args) {
		super.printf(l, format, args);
		return this;
	}
	@Override
	public PrintStream printf(String format, Object... args) {
		super.printf(format, args);
		return this;
	}
	@Override
	public void println() {
		//throw new NullPointerException();
		super.println();
	}
	@Override
	public void println(boolean x) {
		super.println(getTime() + stackTrace(0, 1) + ": " +x);
	}
	@Override
	public void println(char x) {
		super.println(getTime() + stackTrace(0, 1) + ": " +x);
	}
	@Override
	public void println(char[] x) {
		super.println(getTime() + stackTrace(0, 1) + ":");
		super.println(x);
	}
	@Override
	public void println(double x) {
		super.println(getTime() + stackTrace(0, 1) + ": " +x);
	}
	@Override
	public void println(float x) {
		super.println(getTime() + stackTrace(0, 1) + ": " +x);
	}
	@Override
	public void println(int x) {
		super.println(getTime() + stackTrace(0, 1) + ": " +x);
	}
	@Override
	public void println(long x) {
		super.println(getTime() + stackTrace(0, 1) + ": " +x);
	}
	@Override
	public void println(Object x) {
		super.println(getTime() + stackTrace(0, 1) + ": " +x);
	}
	@Override
	public void println(String x) {
		super.println(getTime() + stackTrace(0, 1) + ": " +x);
	}
	@Override
	public void write(byte[] buf, int off, int len) {
		super.write(buf, off, len);
	}
	
	private String getTime() {
		return "[" + System.currentTimeMillis() + "] ";
	}
}
