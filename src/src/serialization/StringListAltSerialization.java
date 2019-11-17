package serialization;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class StringListAltSerialization {

    public static String serialize(List<String> strList) throws IOException {
        List<byte[]> byteBufferList = new ArrayList<>();
        int countTotalBytes = 0;
        for (int i=0;i<strList.size();i++) {
            byteBufferList.add(toBytes(strList.get(i).length()));
            countTotalBytes +=4;
            byte[] strBytes = strList.get(i).getBytes();
            countTotalBytes += strBytes.length;
            byteBufferList.add(strBytes);
        }

        byte[] cumulativeBuffer = new byte[countTotalBytes];
        int pos = 0;
        for (byte[] buffer: byteBufferList) {
            System.arraycopy(buffer, 0, cumulativeBuffer, pos, buffer.length);
            pos += buffer.length;
        };
        return new String(cumulativeBuffer, StandardCharsets.UTF_8);
    }

    private static byte[] toBytes(int val) {
        byte[] buffer = new byte[4];
        buffer[0] = (byte) (val >>> 24 & 0xFF);
        buffer[1] = (byte) (val >>> 16 & 0xFF);
        buffer[2] = (byte) (val >>> 8 & 0xFF);
        buffer[3] = (byte) (val & 0xFF);
        return buffer;
    }

    public static List<String> deserialize(String str) throws IOException {
        List<String> result = new ArrayList<>();
        byte[] buffer = str.getBytes();
        int readOffset = 0;
        while (readOffset < buffer.length) {
            int strByteLen = readLength(buffer, readOffset);
            String strVal = readString(buffer, readOffset+4, strByteLen);
            result.add(strVal);
            readOffset += 4+strByteLen;
        }
        return result;
    }

    private static int readLength(byte[] buffer, int readOffset) {
        byte[] result = new byte[4];
        System.arraycopy(buffer, readOffset, result, 0, 4);
        return ((result[0] << 24) + (result[1] << 16) + (result[2] << 8) + (result[3] << 0));
    }

    private static String readString(byte[] buffer, int readOffset, int len) {
        byte[] result = new byte[len];
        System.arraycopy(buffer, readOffset, result, 0, len);
        return new String(result, StandardCharsets.UTF_8);
    }

    public static void main(String[] args) throws IOException {
        List<String> list = new ArrayList<>();
        list.add("sandeep");
        list.add("and");
        list.add("parthnpari");
        list.add("loves");
        list.add("great");
        list.add("stories");
        String serializationOutput = StringListAltSerialization.serialize(list);
        System.out.println(serializationOutput);
        System.out.println(StringListAltSerialization.deserialize(serializationOutput));
    }
}
