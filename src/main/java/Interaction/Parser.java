package Interaction;

import java.io.*;

/**
 * Class for parsing data for transfer or usage.
 */
public class Parser {
    /**
     * Parses object from byte array.
     *
     * @param data byte array from byte stream.
     * @return object
     */
    public static Object parseFrom(byte[] data) {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data); ObjectInput in =
                new ObjectInputStream(byteArrayInputStream)) {
            return in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Parses class object to byte array for transfer.
     *
     * @param object object to be serialized
     * @param <T>    object type
     * @return byte array
     */
    public static <T> byte[] parseTo(T object) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(6400); ObjectOutput out =
                new ObjectOutputStream(byteArrayOutputStream)) {
            out.writeObject(object);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
