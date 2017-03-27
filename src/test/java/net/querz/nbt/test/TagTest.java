package net.querz.nbt.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Random;

import static net.querz.nbt.test.TestUtil.*;

import junit.framework.TestCase;
import net.querz.nbt.*;

public class TagTest extends TestCase {
	private static final Random RANDOM = new Random();
	
	private ByteTag maxByte = new ByteTag("byte", Byte.MAX_VALUE);
	private ShortTag maxShort = new ShortTag("short", Short.MAX_VALUE);
	private IntTag maxInt = new IntTag("int", Integer.MAX_VALUE);
	private LongTag maxLong = new LongTag("long", Long.MAX_VALUE);
	private FloatTag maxFloat = new FloatTag("float", Float.MAX_VALUE);
	private DoubleTag maxDouble = new DoubleTag("double", Double.MAX_VALUE);
	private ByteTag minByte = new ByteTag("byte", Byte.MIN_VALUE);
	private ShortTag minShort = new ShortTag("short", Short.MIN_VALUE);
	private IntTag minInt = new IntTag("int", Integer.MIN_VALUE);
	private LongTag minLong = new LongTag("long", Long.MIN_VALUE);
	private FloatTag minFloat = new FloatTag("float", Float.MIN_VALUE);
	private DoubleTag minDouble = new DoubleTag("double", Double.MIN_VALUE);
	private FloatTag decFloat = new FloatTag("decFloat", (float) Math.PI);
	private DoubleTag decDouble = new DoubleTag("decDouble", Math.PI);
	private ByteTag boolTrue = new ByteTag("boolTrue", true);
	private ByteTag boolFalse = new ByteTag("boolFalse", false);
	private ByteArrayTag byteArray = new ByteArrayTag("byteArray", new byte[] {Byte.MIN_VALUE, -2, -1, 0, 1, 2, Byte.MAX_VALUE});
	private IntArrayTag intArray = new IntArrayTag("intArray", new int[] {Integer.MIN_VALUE, -2, -1, 0, 1, 2, Integer.MAX_VALUE});
	private StringTag string = new StringTag("string0aAÂ«âˆ‘â‚¬Â®â€ Î©Â¨â�„Ã¸Ï€â€¢Â±Ã¥â€šâˆ‚Æ’Â©ÂªÂºâˆ†@Å“Ã¦â€˜Â¥â‰ˆÃ§âˆšâˆ«~Âµâˆžâ€¦", "0aAÂ«âˆ‘â‚¬Â®â€ Î©Â¨â�„Ã¸Ï€â€¢Â±Ã¥â€šâˆ‚Æ’Â©ÂªÂºâˆ†@Å“Ã¦â€˜Â¥â‰ˆÃ§âˆšâˆ«~Âµâˆžâ€¦");
	private ListTag byteList = new ListTag("byteList", TagType.BYTE);

	private void populateCompoundTag(CompoundTag tag) {
		tag.setBoolean("boolTrue", boolTrue.getBoolean());
		tag.setBoolean("boolFalse", boolFalse.getBoolean());
		tag.setByte("maxByte", maxByte.getValue());
		tag.setByte("minByte", minByte.getValue());
		tag.setShort("maxShort", maxShort.getValue());
		tag.setShort("minShort", minShort.getValue());
		tag.setInt("maxInt", maxInt.getValue());
		tag.setInt("minInt", minInt.getValue());
		tag.setLong("maxLong", maxLong.getValue());
		tag.setLong("minLong", minLong.getValue());
		tag.setFloat("maxFloat", maxFloat.getValue());
		tag.setFloat("minFloat", minFloat.getValue());
		tag.setDouble("maxDouble", maxDouble.getValue());
		tag.setDouble("minDouble", minDouble.getValue());
		tag.set(byteArray);
		tag.set(intArray);
		tag.set(string);
		tag.set(byteList);
		CompoundTag compoundClone = tag.clone();
		CompoundTag compoundClone2 = tag.clone();
		compoundClone.setName("compoundClone");
		compoundClone2.setName("compoundClone2");
		tag.set(compoundClone);
		tag.set(compoundClone2);
	}
	
	public void setUp() throws Exception {
		super.setUp();
		byteList.add(maxByte);
		byteList.addByte(minByte.getValue());
		byteList.addBoolean((boolTrue).getBoolean());
		byteList.addBoolean((boolFalse).getBoolean());
	}
	
	public void testByteTag() throws IOException {
		assertTagNotNullEquals(serializeAndDeserialize(maxByte), maxByte);
		assertTagNotNullEquals(serializeAndDeserialize(minByte), minByte);
		assertTagNotNullEquals(serializeAndDeserialize(boolTrue), boolTrue);
		assertTagNotNullEquals(serializeAndDeserialize(boolFalse), boolFalse);
		assertEquals(maxByte.toString(), "<byte:byte:127>");
		assertEquals(maxByte.toTagString(), "byte:127b");
	}
	
	public void testShortTag() throws IOException {
		assertTagNotNullEquals(serializeAndDeserialize(maxShort), maxShort);
		assertTagNotNullEquals(serializeAndDeserialize(minShort), minShort);
		assertEquals(maxShort.toString(), "<short:short:32767>");
		assertEquals(maxShort.toTagString(), "short:32767");
	}
	
	public void testIntTag() throws IOException {
		assertTagNotNullEquals(serializeAndDeserialize(maxInt), maxInt);
		assertTagNotNullEquals(serializeAndDeserialize(minInt), minInt);
		assertEquals(maxInt.toString(), "<int:int:2147483647>");
		assertEquals(maxInt.toTagString(), "int:2147483647");
	}
	
	public void testLongTag() throws IOException {
		assertTagNotNullEquals(serializeAndDeserialize(maxLong), maxLong);
		assertTagNotNullEquals(serializeAndDeserialize(minLong), minLong);
		assertEquals(maxLong.toString(), "<long:long:9223372036854775807>");
		assertEquals(maxLong.toTagString(), "long:9223372036854775807l");
	}
	
	public void testFloatTag() throws IOException {
		assertTagNotNullEquals(serializeAndDeserialize(maxFloat), maxFloat);
		assertTagNotNullEquals(serializeAndDeserialize(minFloat), minFloat);
		assertTagNotNullEquals(serializeAndDeserialize(decFloat), decFloat);
		assertEquals(maxFloat.toString(), "<float:float:3.4028235E38>");
		assertEquals(maxFloat.toTagString(), "float:3.4028235E38f");
	}
	
	public void testDoubleTag() throws IOException {
		assertTagNotNullEquals(serializeAndDeserialize(maxDouble), maxDouble);
		assertTagNotNullEquals(serializeAndDeserialize(minDouble), minDouble);
		assertTagNotNullEquals(serializeAndDeserialize(decDouble), decDouble);
		assertEquals(maxDouble.toString(), "<double:double:1.7976931348623157E308>");
		assertEquals(maxDouble.toTagString(), "double:1.7976931348623157E308d");
	}
	
	public void testStringTag() throws IOException {
		assertTagNotNullEquals(serializeAndDeserialize(string), string);
		assertTagNotNullEquals(string, string.clone());
		assertTrue(string != string.clone());
		assertEquals(string.toString(), "<string:" + string.getName() + ":" + string.getValue() + ">");
		assertEquals(string.toTagString(), string.getName() + ":\"" + string.getValue() + "\"");
	}
	
	public void testByteArrayTag() throws IOException {
		ByteArrayTag t = (ByteArrayTag) serializeAndDeserialize(byteArray);
		assertNotNull(t);
		assertEquals(t.getType(), byteArray.getType());
		assertEquals(t.getName(), byteArray.getName());
		assertTrue(Arrays.equals(t.getValue(), byteArray.getValue()));
		assertTrue(t.equals(byteArray));
		assertTrue(t != t.clone());
		assertTrue(t.getValue() != t.clone().getValue());
		assertEquals(byteArray.toString(), "<byte[]:byteArray:[-128,-2,-1,0,1,2,127]>");
		assertEquals(byteArray.toTagString(), "byteArray:[-128,-2,-1,0,1,2,127]");
	}
	
	public void testIntArrayTag() throws IOException {
		IntArrayTag t = (IntArrayTag) serializeAndDeserialize(intArray);
		assertNotNull(t);
		assertEquals(t.getType(), intArray.getType());
		assertEquals(t.getName(), intArray.getName());
		assertTrue(Arrays.equals(t.getValue(), intArray.getValue()));
		assertTrue(t.equals(intArray));
		assertTrue(t != t.clone());
		assertTrue(t.getValue() != t.clone().getValue());
		assertEquals(intArray.toString(), "<int[]:intArray:[-2147483648,-2,-1,0,1,2,2147483647]>");
		assertEquals(intArray.toTagString(), "intArray:[-2147483648,-2,-1,0,1,2,2147483647]");
	}
	
	public void testListTag() throws IOException {
		ListTag byteList2 = (ListTag) serializeAndDeserialize(byteList);
		assertNotNull(byteList2);
		assertEquals(byteList2.getType(), byteList.getType());
		assertEquals(byteList2.getListType(), byteList.getListType());
		assertEquals(byteList2.getName(), byteList.getName());
		for (int i = 0; i < byteList.size(); i++) {
			//ListTag ignores tag names, therefore only check values
			assertEquals(byteList.get(i).getValue(), byteList2.get(i).getValue());
		}
		assertTrue(byteList2.getBoolean(0));
		assertFalse(byteList2.getBoolean(1));
		assertTrue(byteList2.getBoolean(2));
		assertFalse(byteList2.getBoolean(3));
		assertEquals(byteList2.asByte(0), byteList.getByte(0));
		assertThrowsException(() -> byteList.add(maxShort), IllegalArgumentException.class);
		
		//equals should ignore tag names in list tags
		assertTrue(byteList2.equals(byteList));
		assertTrue(byteList != byteList.clone());
		assertTrue(byteList.getValue() != byteList.clone().getValue());
		
		assertEquals(byteList.toString(), TestUtil.readStringFromFile("test_list_toString.txt"));
		assertEquals(byteList.toTagString(), TestUtil.readStringFromFile("test_list_toTagString.txt"));
	}
	
	public void testCompoundTag() throws IOException {
		CompoundTag compound = new CompoundTag("compound");
		compound.clearOrdered();
		populateCompoundTag(compound);
		
		//test cloning
		assertEquals(compound, compound.clone());
		assertFalse(compound == compound.clone());
		
		CompoundTag compound2 = (CompoundTag) serializeAndDeserialize(compound);
		assertEquals(compound, compound2);

		assertEquals(compound.toString(), TestUtil.readStringFromFile("test_compound_toString.txt"));
		assertEquals(compound.toTagString(), TestUtil.readStringFromFile("test_compound_toTagString.txt"));
	}
	
	public void testNBTFileReader() {
		Tag t = new NBTFileReader(TestUtil.RESOURCES_PATH + "test_compound_gzip.dat").read();
		assertNotNull(t);
		Tag t2 = new NBTFileReader(TestUtil.RESOURCES_PATH + "test_compound.dat").read();
		assertNotNull(t2);
	}
	
	public void testNBTFileWriter() {
		CompoundTag compound = new CompoundTag("compound");
		populateCompoundTag(compound);
		new NBTFileWriter(TestUtil.RESOURCES_PATH + "test_NBTFileWriter.dat").write(compound);
		assertTrue(Files.exists(Paths.get(TestUtil.RESOURCES_PATH + "test_NBTFileWriter.dat")));
	}
	
	public void testCircularReference() {
		CompoundTag t = new CompoundTag("c");
		t.set(t);
		
		assertThrowsException(t::toString, MaxDepthReachedException.class);
		assertThrowsException(() -> wrappedSerializeAndDeserialize(t), MaxDepthReachedException.class);
		assertThrowsException(t::toTagString, MaxDepthReachedException.class);
		
		
		ListTag l = new ListTag("l", TagType.LIST);
		l.add(l);
		
		assertThrowsException(l::toString, MaxDepthReachedException.class);
		assertThrowsException(() -> wrappedSerializeAndDeserialize(l), MaxDepthReachedException.class);
		assertThrowsException(l::toTagString, MaxDepthReachedException.class);
		
		
		CompoundTag b = new CompoundTag("base");
		CompoundTag current = new CompoundTag("depth_0");
		b.set(current);
		
		//counting to a depth of Tag.MAX_DEPTH - 1
		for (int i = 1; i < Tag.MAX_DEPTH - 1; i++) {
			CompoundTag c = new CompoundTag("depth_" + i);
			current.set(c);
			c.set(new IntTag("randomInt", RANDOM.nextInt()));
			current = c;
		}

		assertThrowsNoException(b::toString);
		assertThrowsNoException(b::toTagString);
		assertThrowsNoException(() -> wrappedSerializeAndDeserialize(b));
	}
	
	public void tearDown() throws Exception {
		super.tearDown();
		Files.deleteIfExists(Paths.get(TestUtil.RESOURCES_PATH + "test_NBTFileWriter.dat"));
	}
	
	//only works with primitive tags
	private void assertTagNotNullEquals(Tag t1, Tag t2) {
		assertTrue(t1 != null && t2 != null);
		assertEquals(t1.getType(), t2.getType());
		if (t1.getName() != null && t2.getName() != null)
			assertEquals(t1.getName(), t2.getName());
		else
			assertEquals(t1.getName(), t2.getName());
		//compares reference if it's not a primitive
		assertEquals(t1.getValue(), t2.getValue());
	}
	
	private Tag serializeAndDeserialize(Tag tag) throws IOException {
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		try (NBTOutputStream nbtOut = new NBTOutputStream(byteOut)) {
			nbtOut.writeTag(tag);
		}
		ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
		try (NBTInputStream nbtIn = new NBTInputStream(byteIn)) {
			return nbtIn.readTag();
		}
	}
	
	private Tag wrappedSerializeAndDeserialize(Tag tag) {
		try {
			return serializeAndDeserialize(tag);
		} catch (IOException ex) {
			throw new UncheckedIOException(ex);
		}
	}
}
