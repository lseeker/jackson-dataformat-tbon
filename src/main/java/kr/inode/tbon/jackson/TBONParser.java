package kr.inode.tbon.jackson;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.json.PackageVersion;

public class TBONParser extends JsonParser {
	private ObjectCodec objectCodec;
	
	TBONParser(InputStream in) {
		
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

	}

	@Override
	public boolean isClosed() {
		// TODO Auto-generated method stub
		return false;
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

	@Override
	public JsonToken nextToken() throws IOException {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Number getNumberValue() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NumberType getNumberType() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getIntValue() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getLongValue() throws IOException {
		// TODO Auto-generated method stub
		return 0;
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
