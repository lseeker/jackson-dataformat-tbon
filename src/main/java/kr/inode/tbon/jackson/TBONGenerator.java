package kr.inode.tbon.jackson;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;

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

	}

	@Override
	public void writeEndArray() throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeStartObject() throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeEndObject() throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeFieldName(String name) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeFieldName(SerializableString name) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeString(String text) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeString(char[] text, int offset, int len) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeString(SerializableString text) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeRawUTF8String(byte[] text, int offset, int length) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeUTF8String(byte[] text, int offset, int length) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeRaw(String text) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeRaw(String text, int offset, int len) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeRaw(char[] text, int offset, int len) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeRaw(char c) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeRawValue(String text) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeRawValue(String text, int offset, int len) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeRawValue(char[] text, int offset, int len) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeBinary(Base64Variant bv, byte[] data, int offset, int len) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public int writeBinary(Base64Variant bv, InputStream data, int dataLength) throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeNumber(int v) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeNumber(long v) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeNumber(BigInteger v) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeNumber(double v) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeNumber(float v) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeNumber(BigDecimal v) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeNumber(String encodedValue) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeBoolean(boolean state) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeNull() throws IOException {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isClosed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub

	}

}
