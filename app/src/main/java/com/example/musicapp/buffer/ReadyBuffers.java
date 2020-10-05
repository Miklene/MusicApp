package com.example.musicapp.buffer;

import java.util.*;

public class ReadyBuffers {

    Queue<float[]> queue;
    private int numberOfOperations;
    Stack<float[]> stack;
    ArrayList<float[]> arrayList;
    int pointer;


    public ReadyBuffers(int numberOfHarmonics){
        this.numberOfOperations = (numberOfHarmonics+1)*2-1;
        queue = new LinkedList<>();
        //stack = new Stack<>();
        /*arrayList = new ArrayList<>(numberOfHarmonics+1);
        pointer = -1;*/
    }

    public int getNumberOfOperations() {
        return numberOfOperations;
    }

    public synchronized void putBuffer(float[] buffer){
        /*arrayList.add(buffer);
        pointer++;*/
        //stack.push(buffer);
        queue.add(buffer);
        numberOfOperations--;
    }

    public synchronized float[] getBuffer(){
        /*float[] buffer;
        buffer = arrayList.get(pointer);*/
        try{

            //stack.peek();
            queue.element();
        } catch (NoSuchElementException e){
           System.out.println(e);
        }
        /*arrayList.remove(pointer);
        pointer--;
        return buffer;*/
        //return stack.pop();
        return queue.remove();
    }

    public int getSize(){
        //return arrayList.size();
        //return stack.size();
        return queue.size();
    }

}
