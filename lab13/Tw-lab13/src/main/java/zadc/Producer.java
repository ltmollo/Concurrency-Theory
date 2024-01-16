package zadc;

class Producer extends Thread {
    private final Buffer _buf;
    private int nbOfOperations;
    Producer(Buffer buffer, int nbOfOperations){
        this._buf = buffer;
        this.nbOfOperations = nbOfOperations;
    }
    public void run() {
        for (int i = 0; i < nbOfOperations; ++i) {
            var item = (int) (Math.random() * 100) + 1;
            _buf.put(item);
        }
    }
}