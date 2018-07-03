package util.serialization;

import java.io.*;
import java.nio.ByteBuffer;

public class SerializationHelper {
    public static Object deserializeBytes(byte[] bytes) throws IOException, ClassNotFoundException
    {
        ByteArrayInputStream bytesIn = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bytesIn);
        Object obj = ois.readObject();
        ois.close();
        return obj;
    }

    public static byte[] serializeObject(Object obj) throws IOException
    {
        ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bytesOut);
        oos.writeObject(obj);
        oos.flush();
        byte[] bytes = bytesOut.toByteArray();
        bytesOut.close();
        oos.close();
        return bytes;
    }

    public static byte[] toByteArray(int value) {
        return  ByteBuffer.allocate(4).putInt(value).array();
    }

    public static int fromByteArray(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getInt();
    }

    public static byte[] toPrimitive(Byte[] bytes){
        byte[] newBytes = new byte[bytes.length];

        for(int x = 0; x < bytes.length; x++){
            newBytes[x] = (byte) bytes[x];
        }

        return newBytes;
    }

    public static Byte[] toObjectByte(byte[] bytes){
        Byte[] newBytes = new Byte[bytes.length];

        for(int x = 0; x < bytes.length; x++){
            newBytes[x] = bytes[x];
        }
        return  newBytes;
    }

}
