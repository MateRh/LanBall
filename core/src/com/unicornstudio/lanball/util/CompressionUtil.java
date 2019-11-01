package com.unicornstudio.lanball.util;

import java.nio.charset.StandardCharsets;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class CompressionUtil {

    public static byte[] compress(String input) {
        byte[] output = new byte[65507];
        Deflater deflater = new Deflater();
        deflater.setInput(input.getBytes(StandardCharsets.UTF_8));
        deflater.finish();
        int length = deflater.deflate(output);
        deflater.end();
        return getTruncatedBytes(output, length);
    }

    public static String decompress(byte[] input) {
        try {
            byte[] result = new byte[376800];
            Inflater inflater = new Inflater();
            inflater.setInput(input);
            int length = inflater.inflate(result);
            inflater.end();
            return new String(result, 0, length, StandardCharsets.UTF_8);
        } catch (DataFormatException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static byte[] getTruncatedBytes(byte[] input, int length) {
        byte[] result = new byte[length];
        System.arraycopy(input, 0, result, 0, length);
        return result;
    }

}
