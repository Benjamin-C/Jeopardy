package jeopardy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

public class TracedPrintStream extends PrintStream{
	
	public TracedPrintStream(File file) throws FileNotFoundException {
		super(file);
	}
	public TracedPrintStream(File file, String csn) throws FileNotFoundException, UnsupportedEncodingException {
		super(file, csn);
		
	}
	public TracedPrintStream(OutputStream outpuStream) {
		super(outpuStream);
		
	}
	public TracedPrintStream(OutputStream outpuStream, boolean autoflush) {
		super(outpuStream, autoflush);
		
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
		return loc.getClassName() + "." + loc.getMethodName() + "(" + loc.getFileName() + ":" + (loc.getLineNumber() + rel) + ")";
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
		super.println(stackTrace(0, 1) + ": " +x);
	}
	@Override
	public void println(char x) {
		super.println(stackTrace(0, 1) + ": " +x);
	}
	@Override
	public void println(char[] x) {
		super.println(stackTrace(0, 1) + ": " +x);
	}
	@Override
	public void println(double x) {
		super.println(stackTrace(0, 1) + ": " +x);
	}
	@Override
	public void println(float x) {
		super.println(stackTrace(0, 1) + ": " +x);
	}
	@Override
	public void println(int x) {
		super.println(stackTrace(0, 1) + ": " +x);
	}
	@Override
	public void println(long x) {
		super.println(stackTrace(0, 1) + ": " +x);
	}
	@Override
	public void println(Object x) {
		super.println(stackTrace(0, 1) + ": " +x);
	}
	@Override
	public void println(String x) {
		super.println(stackTrace(0, 1) + ": " +x);
	}
	@Override
	public void write(byte[] buf, int off, int len) {
		super.write(buf, off, len);
	}
}
