package zadc;

class Consumer extends Thread {
    private final Buffer _buf;
    private final int nbOfOperations;
    Consumer(Buffer buffer, int nbOfOperations){
        this._buf = buffer;
        this.nbOfOperations = nbOfOperations;
    }
    public void run() {
        for (int i = 0; i < nbOfOperations; ++i) {
            _buf.get();
        }
    }
}