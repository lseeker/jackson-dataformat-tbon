package kr.inode.tbon.jackson;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.json.PackageVersion;

public class TBONGenerator extends JsonGenerator {
	private ObjectCodec objectCodec;
	private int featureMask = Feature.collectDefaults();
	private final OutputStream out;
	private boolean closed = false;

	TBONGenerator(final OutputStream out) {
		this.out = out;
	}

	@Override
	public ObjectCodec getCodec() {
		return objectCodec;
	}

	@Override
	public JsonGenerator setCodec(ObjectCodec oc) {
		objectCodec = oc;
		return this;
	}

	@Override
	public Version version() {
		return PackageVersion.VERSION;
	}

	@Override
	public JsonGenerator enable(Feature f) {
		featureMask |= f.getMask();
		return this;
	}

	@Override
	public JsonGenerator disable(Feature f) {
		featureMask &= ~f.getMask();
		return this;
	}

	@Override
	public boolean isEnabled(Feature f) {
		return f.enabledIn(featureMask);
	}

	@Override
	public int getFeatureMask() {
		return featureMask;
	}

	@Override
	public JsonGenerator setFeatureMask(int values) {
		featureMask = values;
		return this;
	}

	@Override
	public JsonGenerator useDefaultPrettyPrinter() {
		// not supported, binary format
		return this;
	}

	@Override
	public void writeStartArray() throws IOException {
		out.write(0x5f);
		out.write(0x00);
	}

	@Override
	public void writeEndArray() throws IOException {
		out.write(0x1f);
	}

	@Override
	public void writeStartObject() throws IOException {
		out.write(0x6f);
		out.write(0x00);
	}

	@Override
	public void writeEndObject() throws IOException {
		out.write(0x1f);
	}

	@Override
	public void writeFieldName(String name) throws IOException {
		writeString(name);
	}

	private void write7bitInteger(int i) throws IOException {
		while (i > 0x7f) {
			out.write(i & 0x7f | 0x80);
			i >>= 7;
		}
		out.write(i & 0x7f);
	}

	private void write7bitInteger(long l) throws IOException {
		while (l > 0x7fL) {
			out.write(((int) (l & 0x7fL)) | 0x80);
			l >>= 7L;
		}
		out.write((int) (l & 0x7fL));
	}

	@Override
	public void writeFieldName(SerializableString name) throws IOException {
		writeString(name);
	}

	@Override
	public void writeString(String text) throws IOException {
		byte[] b = text.getBytes(StandardCharsets.UTF_8);
		writeRawUTF8String(b, 0, b.length);
	}

	@Override
	public void writeString(char[] text, int offset, int len) throws IOException {
		writeString(new String(text, offset, len));
	}

	@Override
	public void writeString(SerializableString text) throws IOException {
		byte[] b = text.asUnquotedUTF8();
		writeRawUTF8String(b, 0, b.length);
	}

	@Override
	public void writeRawUTF8String(byte[] text, int offset, int length) throws IOException {
		if (length < 63) {
			out.write(0xc + length);
		} else {
			out.write(0xff);
			write7bitInteger(length);
		}
		out.write(text, offset, length);
	}

	@Override
	public void writeUTF8String(byte[] text, int offset, int length) throws IOException {
		writeRawUTF8String(text, offset, length);
	}

	@Override
	public void writeRaw(String text) throws IOException {
		writeString(text);
	}

	@Override
	public void writeRaw(String text, int offset, int len) throws IOException {
		writeString(text.substring(offset, offset + len));
	}

	@Override
	public void writeRaw(char[] text, int offset, int len) throws IOException {
		writeString(new String(text, offset, len));
	}

	@Override
	public void writeRaw(char c) throws IOException {
		out.write(0x0f);
		out.write(Character.toString(c).getBytes(StandardCharsets.UTF_8));
	}

	@Override
	public void writeRawValue(String text) throws IOException {
		writeString(text);
	}

	@Override
	public void writeRawValue(String text, int offset, int len) throws IOException {
		writeString(text.substring(offset, offset + len));
	}

	@Override
	public void writeRawValue(char[] text, int offset, int len) throws IOException {
		writeString(new String(text, offset, len));
	}

	@Override
	public void writeBinary(Base64Variant bv, byte[] data, int offset, int len) throws IOException {
		if (len < 63) {
			out.write(0x80 + len);
		} else {
			out.write(0xbf);
			write7bitInteger(len);
		}
		out.write(data, offset, len);
	}

	@Override
	public int writeBinary(Base64Variant bv, InputStream data, int dataLength) throws IOException {
		if (dataLength == -1) {
			// infinite stream mode
			out.write(0xbf);
			out.write(0x00);
			byte[] buffer = new byte[8192];
			int read = 0;
			for (;;) {
				int r = data.read(buffer);
				if (r == -1) {
					out.write(0x1f);
					return read;
				}
				write7bitInteger(r);
				out.write(buffer, 0, r);
				read += r;
			}
		} else if (dataLength < 63) {
			out.write(0x80 + dataLength);
		} else {
			out.write(0xbf);
			write7bitInteger(dataLength);
		}
		byte[] buffer = new byte[Math.min(dataLength, 8192)];
		int read = 0;
		do {
			int r = data.read(buffer, read, dataLength - read);
			if (r == -1) {
				throw new IOException("eof on remains : " + (dataLength - read));
			}
			out.write(buffer, 0, r);
			read += r;
		} while (read < dataLength);
		return read;
	}

	@Override
	public void writeNumber(int v) throws IOException {
		if (v == 0) {
			out.write(0x10);
		} else if (v < -0x1000000 || v > 0xffffff) {
			out.write(0x11);
			ByteBuffer b = ByteBuffer.allocate(4);
			b.putInt(v);
			out.write(b.array());
		} else if (v > 0) {
			out.write(0x12);
			write7bitInteger(v);
		} else {
			out.write(0x13);
			write7bitInteger(-v);
		}
	}

	@Override
	public void writeNumber(long v) throws IOException {
		if (v == 0) {
			out.write(0x18);
		} else if (v < -0x100000000000000L || v > 0xffffffffffffffL) {
			out.write(0x19);
			ByteBuffer b = ByteBuffer.allocate(8);
			b.putLong(v);
			out.write(b.array());
		} else if (v > 0) {
			out.write(0x1a);
			write7bitInteger(v);
		} else {
			out.write(0x1b);
			write7bitInteger(-v);
		}
	}

	@Override
	public void writeNumber(BigInteger v) throws IOException {
		byte[] b = v.toByteArray();
		out.write(0x2f);
		write7bitInteger(b.length);
		out.write(b);
	}

	@Override
	public void writeNumber(double v) throws IOException {
		out.write(0x22);
		ByteBuffer b = ByteBuffer.allocate(8);
		b.putDouble(v);
		out.write(b.array());
	}

	@Override
	public void writeNumber(float v) throws IOException {
		out.write(0x21);
		ByteBuffer b = ByteBuffer.allocate(4);
		b.putDouble(v);
		out.write(b.array());
	}

	@Override
	public void writeNumber(BigDecimal v) throws IOException {
		
		// TODO Auto-generated method stub

	}

	@Override
	public void writeNumber(String encodedValue) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void writeBoolean(boolean state) throws IOException {
		out.write(state ? 0x03 : 0x02);
	}

	@Override
	public void writeNull() throws IOException {
		out.write(0x00);
	}

	@Override
	public void writeObject(Object pojo) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeTree(TreeNode rootNode) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public JsonStreamContext getOutputContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void flush() throws IOException {
		out.flush();
	}

	@Override
	public boolean isClosed() {
		return closed;
	}

	@Override
	public void close() throws IOException {
		closed = true;
	}

}
