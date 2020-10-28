package com.example.musicapp.wav;

public class WavHeader16Bit extends WavHeader {

    public WavHeader16Bit(short numChannels, int dataSize) {
        super(numChannels,dataSize);
        bitsPerSample = 16;
        blockAlign = (short) (numChannels*bitsPerSample/8);
        chunkSize =  dataSize*bitsPerSample/8 + 44 - 8;
        subChunk2Size = dataSize * bitsPerSample/8 ;
        byteRate = sampleRate*bitsPerSample/8*numChannels;
    }
}
