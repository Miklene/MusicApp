package com.example.musicapp.wav;

public abstract class WavHeader {

    protected final int chunkId = 0x52494646;    //"RIFF"
    protected int chunkSize;                     //весь размер файла -8. data+44-8;
    //int shortMonoChunkSize = 176036;
    //int shortStereoChunkSize = 352036;
    //int floatChunkSize = 352036;
    protected final int format = 0x57415645;      //"WAVE"
    protected final int subChunk1Id = 0x666d7420; //"fmt"
    protected final int subChunk1Size = 16;       //16 для PCM
    protected final short audioFormat = 1;        //Для PCM = 1
    protected short numChannels;                  //
    protected final int sampleRate = 44100;
    protected int byteRate;                       //Количество байт, переданных за секунду воспроизведения.
    //int shortByteRate = 88200;
    //int floatByteRate = 176400;
    protected short blockAlign;                   //Количество байт для одного сэмпла, включая все каналы.
    // short shortBlockAlign = 2;
    // short floatBlockAlign = 4;
    protected short bitsPerSample;
    //short shortBitsPerSample = 16;
    //short floatBitsPerSample = 32;
    protected int subChunk2Id = 0x64617461;     //"data"
    protected int subChunk2Size;                          //Количество байт в области данных.
    //int shortMonoSubChunk2Size = 176000;
    //int shortStereoSubChunk2Size = 352000;
    //int floatSubChunk2Size = 352000;
    protected int dataSize;

    public WavHeader(short numChannels, int dataSize) {
        this.numChannels = numChannels;
        this.dataSize = dataSize;
    }

    protected void calculateChunkSize(){
        chunkSize =  dataSize*blockAlign + 44 - 8;
    }

    protected void calculateSubChunk2Size() {
        subChunk2Size = dataSize * bitsPerSample/8 ;
    }

    protected void calculateByteRate(){
        byteRate = sampleRate*numChannels*bitsPerSample/8;
    }
}
