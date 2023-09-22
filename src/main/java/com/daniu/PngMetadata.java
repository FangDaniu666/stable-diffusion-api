package com.daniu;

public class PngMetadata {
    private static final int TEXT_CHUNK_TYPE = 0x74455874;

    private byte[] bytes;

    public PngMetadata(byte[] bytes) {
        this.bytes = bytes;
    }

    public void addText(String key, String value) {
        byte[] keyBytes = key.getBytes();
        byte[] valueBytes = value.getBytes();

        byte[] chunkData = new byte[keyBytes.length + valueBytes.length + 1];
        System.arraycopy(keyBytes, 0, chunkData, 0, keyBytes.length);
        chunkData[keyBytes.length] = 0;
        System.arraycopy(valueBytes, 0, chunkData, keyBytes.length + 1, valueBytes.length);

        int crc = computeCrc(chunkData);

        byte[] lengthBytes = intToBytes(chunkData.length);
        byte[] crcBytes = intToBytes(crc);

        byte[] chunkBytes = new byte[chunkData.length + 12];
        System.arraycopy(lengthBytes, 0, chunkBytes, 0, 4);
        System.arraycopy("tEXt".getBytes(), 0, chunkBytes, 4, 4);
        System.arraycopy(chunkData, 0, chunkBytes, 8, chunkData.length);
        System.arraycopy(crcBytes, 0, chunkBytes, 8 + chunkData.length, 4);

        byte[] updatedBytes = new byte[bytes.length + chunkBytes.length];
        System.arraycopy(bytes, 0, updatedBytes, 0, bytes.length);
        System.arraycopy(chunkBytes, 0, updatedBytes, bytes.length, chunkBytes.length);

        this.bytes = updatedBytes;
    }

    public byte[] getBytes() {
        return bytes;
    }

    private int computeCrc(byte[] data) {
        int crc = 0xFFFFFFFF;
        for (byte b : data) {
            crc ^= b;
            for (int i = 0; i < 8; i++) {
                if ((crc & 1) != 0) {
                    crc = (crc >>> 1) ^ 0xEDB88320;
                } else {
                    crc >>>= 1;
                }
            }
        }
        return ~crc;
    }

    private byte[] intToBytes(int value) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (value >> 24);
        bytes[1] = (byte) (value >> 16);
        bytes[2] = (byte) (value >> 8);
        bytes[3] = (byte) value;
        return bytes;
    }
}
