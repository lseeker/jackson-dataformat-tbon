package kr.inode.tbon.jackson;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.json.PackageVersion;

public class TBONParser extends JsonParser {
	private static interface ValueConverter {
		String toString();

	}

	private ObjectCodec objectCodec;

	private final InputStream in;
	private boolean closed;

	private boolean isPrimitive;

	private JsonToken currentToken;
	private byte byteValue;
	private short shortValue;
	private int intValue;
	private long longValue;
	private float floatValue;
	private double doubleValue;
	private Object objectValue;

	TBONParser(InputStream in) {
		this.in = in;
	}

	@Override
	public ObjectCodec getCodec() {
		return objectCodec;
	}

	@Override
	public void setCodec(ObjectCodec c) {
		objectCodec = c;
	}

	@Override
	public Version version() {
		return PackageVersion.VERSION;
	}

	@Override
	public void close() throws IOException {
		closed = true;
	}

	@Override
	public boolean isClosed() {
		return closed;
	}

	@Override
	public JsonStreamContext getParsingContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonLocation getTokenLocation() {
		return JsonLocation.NA;
	}

	@Override
	public JsonLocation getCurrentLocation() {
		return JsonLocation.NA;
	}

	private int readVPInt() throws IOException {
		int i = 0;
		int shift = 0;
		int read;
		do {
			read = in.read();
			if (read == -1) {
				throw new IOException("EOS on VInt read");
			}
			i |= read << shift;
			shift += 7;
		} while ((read & 0x80) == 0x80);
		return i;
	}

	private long readVPLong() throws IOException {
		long l = 0;
		int shift = 0;
		int read;
		do {
			read = in.read();
			if (read == -1) {
				throw new IOException("EOS on VInt read");
			}
			l |= read << shift;
			shift += 7;
		} while ((read & 0x80) == 0x80);
		return l;
	}

	private byte[] readOctet(int len) throws IOException {
		byte[] b = new byte[len];
		int read = 0;
		while (read < len) {
			int r = in.read(b, read, len - read);
			if (r == -1) {
				throw new IOException("EOS on octet read");
			}
			read += r;
		}
		return b;
	}

	private byte[] readVLengthOctet() throws IOException {
		return readOctet(readVPInt());
	}

	@Override
	public JsonToken nextToken() throws IOException {
		int b = in.read();
		if ((b & 0xc0) == 0) {
			switch (b & 0xf8) {
			case 0x00:
				currentToken = JsonToken.VALUE_NUMBER_INT;
				intValue = 0;
				break;
			case 0x08:
				currentToken = JsonToken.VALUE_NUMBER_INT;
				break;
			case 0x10:
				currentToken = JsonToken.VALUE_NUMBER_INT;
				break;
			case 0x18:
				currentToken = JsonToken.VALUE_NUMBER_INT;
				break;
			case 0x20:
				currentToken = JsonToken.VALUE_NUMBER_FLOAT;
			case 0x28:
				break;
			default: // Custom type
				currentToken = JsonToken.VALUE_EMBEDDED_OBJECT;
				int len = b & 0x0f;
				if (len == 0x0f) {
					// stream length
					objectValue = new String(readVLengthOctet(), StandardCharsets.UTF_8);
				} else {
					// read length to string

				}

			}
		} else if ((b & 0x80) == 0) {
			// array, object
		} else {
			// octet, string

		}

		return currentToken;
	}

	@Override
	public JsonToken nextValue() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonParser skipChildren() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonToken getCurrentToken() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getCurrentTokenId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasCurrentToken() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasTokenId(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasToken(JsonToken t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clearCurrentToken() {
		// TODO Auto-generated method stub

	}

	@Override
	public JsonToken getLastClearedToken() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void overrideCurrentName(String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getCurrentName() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getText() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public char[] getTextCharacters() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTextLength() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTextOffset() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasTextCharacters() {
		return false;
	}

	@Override
	public Number getNumberValue() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NumberType getNumberType() throws IOException {
		return null;
	}

	@Override
	public int getIntValue() throws IOException {
		return intValue;
	}

	@Override
	public long getLongValue() throws IOException {
		return longValue;
	}

	@Override
	public BigInteger getBigIntegerValue() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getFloatValue() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getDoubleValue() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BigDecimal getDecimalValue() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] getBinaryValue(Base64Variant bv) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getValueAsString(String def) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
