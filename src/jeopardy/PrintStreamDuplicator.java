package jeopardy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

public class PrintStreamDuplicator extends PrintStream {

	PrintStream a;
	PrintStream b;
	
	public PrintStreamDuplicator(File file1, File file2) throws FileNotFoundException {
		super(new OutputStream() { @Override public void write(int b) throws IOException { } });
		a = new PrintStream(file1);
		b = new PrintStream(file2);
	}
	public PrintStreamDuplicator(File file1, String csn1, File file2, String csn2) throws FileNotFoundException, UnsupportedEncodingException {
		super(new OutputStream() { @Override public void write(int b) throws IOException { } });
		a = new PrintStream(file1, csn1);
		b = new PrintStream(file2, csn2);
	}
	public PrintStreamDuplicator(OutputStream outpuStream1, OutputStream outpuStream2) {
		super(new OutputStream() { @Override public void write(int b) throws IOException { } });
		a = new PrintStream(outpuStream1);
		b = new PrintStream(outpuStream2);
	}
	public PrintStreamDuplicator(OutputStream outpuStream1, boolean autoflush1, OutputStream outpuStream2, boolean autoflush2) {
		super(new OutputStream() { @Override public void write(int b) throws IOException { } });
		a = new PrintStream(outpuStream1, autoflush1);
		b = new PrintStream(outpuStream2, autoflush2);
	}
	public PrintStreamDuplicator(OutputStream outpuStream1, boolean autoflush1, String encoding1, OutputStream outpuStream2, boolean autoflush2, String encoding2) {
		super(new OutputStream() { @Override public void write(int b) throws IOException { } });
		a = new PrintStream(outpuStream1, autoflush1);
		b = new PrintStream(outpuStream2, autoflush1);
	}
	public PrintStreamDuplicator(String fileName1, String fileName2) throws FileNotFoundException {
		super(new OutputStream() { @Override public void write(int b) throws IOException { } });
		a = new PrintStream(fileName1);
		b = new PrintStream(fileName2);
	}
	public PrintStreamDuplicator(String fileName1, String csn1, String fileName2, String csn2) throws FileNotFoundException, UnsupportedEncodingException {
		super(new OutputStream() { @Override public void write(int b) throws IOException { } });
		a = new PrintStream(fileName1, csn1);
		b = new PrintStream(fileName2, csn2);
	}
	

	

	@Override
	public PrintStream append(char c) {
		a.append(c);
		b.append(c);
		return this;
	}
	@Override
	public PrintStream append(CharSequence c) {
		a.append(c);
		b.append(c);
		return this;
	}
	@Override
	public PrintStream append(CharSequence csq, int start, int end) {
		a.append(csq, start, end);
		b.append(csq, start, end);
		return this;
	}
	@Override
	public boolean checkError() {
		return a.checkError() && b.checkError();
	}
	@Override
	public void close() {
		a.close();
		b.close();
	}
	@Override
	public void flush() {
		a.flush();
		b.flush();
	}
	@Override
	public PrintStream format(Locale l, String format, Object... args) {
		a.format(l, format, args);
		b.format(l, format, args);
		return this;
	}
	@Override
	public PrintStream format(String format, Object... args) {
		a.format(format, args);
		b.format(format, args);
		return this;
	}
	@Override
	public void print(boolean x) {
		a.print(x);
		b.print(x);
	}
	@Override
	public void print(char x) {
		a.print(x);
		b.print(x);
	}
	@Override
	public void print(char[] x) {
		a.print(x);
		b.print(x);
	}
	@Override
	public void print(double x) {
		a.print(x);
		b.print(x);
	}
	@Override
	public void print(float x) {
		a.print(x);
		b.print(x);
	}
	@Override
	public void print(int x) {
		a.print(x);
		b.print(x);
	}
	@Override
	public void print(long x) {
		a.print(x);
		b.print(x);
	}
	@Override
	public void print(Object x) {
		a.print(x);
		b.print(x);
	}
	@Override
	public void print(String x) {
		a.print(x);
		b.print(x);
	}
	@Override
	public PrintStream printf(Locale l, String format, Object... args) {
		a.printf(l, format, args);
		b.printf(l, format, args);
		return this;
	}
	@Override
	public PrintStream printf(String format, Object... args) {
		a.printf(format, args);
		b.printf(format, args);
		return this;
	}
	@Override
	public void println() {
		a.println();
		b.println();
	}
	@Override
	public void println(boolean x) {
		a.println(x);
		b.println(x);
	}
	@Override
	public void println(char x) {
		a.println(x);
		b.println(x);
	}
	@Override
	public void println(char[] x) {
		a.println(x);
		b.println(x);
	}
	@Override
	public void println(double x) {
		a.println(x);
		b.println(x);
	}
	@Override
	public void println(float x) {
		a.println(x);
		b.println(x);
	}
	@Override
	public void println(int x) {
		a.println(x);
		b.println(x);
	}
	@Override
	public void println(long x) {
		a.println(x);
		b.println(x);
	}
	@Override
	public void println(Object x) {
		a.println(x);
		b.println(x);
	}
	@Override
	public void println(String x) {
		a.println(x);
		b.println(x);
	}
	@Override
	public void write(byte[] buf, int off, int len) {
		a.write(buf, off, len);
		b.write(buf, off, len);
	}
	
}
