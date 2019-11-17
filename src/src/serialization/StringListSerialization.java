package serialization;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class StringListSerialization {

    public static String serialize(List<String> strList) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        for (String str: strList) {
            dataOutputStream.writeInt(str.length());
            dataOutputStream.write(str.getBytes());
        }
        dataOutputStream.flush();
        return new String(outputStream.toByteArray(), StandardCharsets.UTF_8);
    }

    public static List<String> deserialize(String str) throws IOException {
        List<String> result = new ArrayList<>();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(str.getBytes());
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        while (dataInputStream.available() > 0) {
            int strLen = dataInputStream.readInt();
            byte[] strBytes = new byte[strLen];
            dataInputStream.read(strBytes);
            result.add(new String(strBytes, StandardCharsets.UTF_8));
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        List<String> list = new ArrayList<>();
        list.add("sandeep");
        list.add("and");
        list.add("parthnpari");
        list.add("loves");
        list.add("great");
        list.add("stories");
        String serializationOutput = StringListSerialization.serialize(list);
        System.out.println(serializationOutput);
        System.out.println(StringListSerialization.deserialize(serializationOutput));
    }
}
