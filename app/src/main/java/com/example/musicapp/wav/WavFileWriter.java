package com.example.musicapp.wav;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class WavFileWriter {

    private WavFile wavFile;
    private File file;
    private FileOutputStream writer;

    public WavFileWriter(WavFile wavFile, File file) {
        this.wavFile = wavFile;
        this.file = file;
    }

    public void writeFile(){
        try {
            writer = new FileOutputStream(file);
            writeHeader();
            writeData();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeHeader() throws IOException {
        writer.write(intToByte(wavFile.getWavHeader().chunkId, false));
        writer.write(intToByte(wavFile.getWavHeader().chunkSize, true));
        writer.write(intToByte(wavFile.getWavHeader().format, false));
        writer.write(intToByte(wavFile.getWavHeader().subChunk1Id, false));
        writer.write(intToByte(wavFile.getWavHeader().subChunk1Size, true));
        writer.write(shortToByte(wavFile.getWavHeader().audioFormat, true));
        writer.write(shortToByte(wavFile.getWavHeader().numChannels, true));
        writer.write(intToByte(wavFile.getWavHeader().sampleRate, true));
        writer.write(intToByte(wavFile.getWavHeader().byteRate, true));
        writer.write(shortToByte(wavFile.getWavHeader().blockAlign, true));
        writer.write(shortToByte(wavFile.getWavHeader().bitsPerSample, true));
        writer.write(intToByte(wavFile.getWavHeader().subChunk2Id, false));
        writer.write(intToByte(wavFile.getWavHeader().subChunk2Size, true));
    }

    private void writeData() throws IOException {
        float[] data = wavFile.getData();
        for (int i = 0; i < data.length; i++) {
            writer.write(floatToByte(data[i], true));
        }
    }

    public static byte[] intToByte(int integer, boolean inverse) {
        byte[] bytes = new byte[4];
        if (inverse)
            for (int i = 3, j = 0; i > -1; j++, i--) {
                int offset = (bytes.length - 1 - j) * 8;
                bytes[i] = (byte) ((integer >>> offset) & 0xFF);
            }
        else
            for (int i = 0; i < 4; i++) {
                int offset = (bytes.length - 1 - i) * 8;
                bytes[i] = (byte) ((integer >>> offset) & 0xFF);
            }
        return bytes;
    }

    public static byte[] shortToByte(short sh, boolean inverse) {
        byte[] bytes = new byte[2];
        if (inverse)
            for (int i = 1, j = 0; i > -1; i--, j++) {
                int offset = (bytes.length - 1 - j) * 8;
                bytes[i] = (byte) ((sh >>> offset) & 0xFF);
            }
        else
            for (int i = 0; i < 2; i++) {
                int offset = (bytes.length - 1 - i) * 8;
                bytes[i] = (byte) ((sh >>> offset) & 0xFF);
            }
        return bytes;
    }

    public static byte[] floatToByte( float fl, boolean inverse) {
        float f, fy;
        byte[] bytes = new byte[3];
        int intBits = Float.floatToIntBits(fl);
        byte[] b = {(byte) (intBits ), (byte) (intBits >> 8), (byte) (intBits >> 16), (byte) (intBits >> 24)};
        byte[] by = new byte[4];
        bytes[2] = (byte)((intBits >> 24) & 0xff);
        bytes[1] = (byte)((intBits >> 16) & 0xff);
        bytes[0] = (byte)((intBits >> 8) & 0xff);
       /* if (inverse)
            // return new byte[] {(byte) (intBits >> 24), (byte) (intBits >> 16), (byte) (intBits >> 8), (byte) (intBits)};
            //byte[] b = {(byte) (intBits ), (byte) (intBits >> 8), (byte) (intBits >> 16), (byte) (intBits >> 24)};
            for (int i = 3, j = 0; i > -1; i--, j++) {
                int offset = (bytes.length - 1 - j) * 8;
                bytes[i] = (byte) ((intBits >>> offset) & 0xFF);
            }
        else
            for (int i = 0; i < 4; i++) {
                int offset = (bytes.length - 1 - i) * 8;
                bytes[i] = (byte) ((intBits >>> offset) & 0xFF);
            }*/
        return bytes;
    }
}
