package zadc;

import java.util.LinkedList;
class Buffer {
    private final int maxBufferSize = 10;
    private final LinkedList<Integer> buffList = new
            LinkedList<>();
    public boolean isEmpty(){
        return this.buffList.isEmpty();
    }
    public synchronized void put(int i) {
        while(buffList.size() >= maxBufferSize){
            try{
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        buffList.add(i);
        notifyAll();
    }
    public synchronized int get() {
        while(buffList.isEmpty()){
            try{
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        int result = buffList.removeFirst();
        notifyAll();
        return result;
    }
}

