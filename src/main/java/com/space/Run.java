package com.space;

public class Run implements Runnable  {

    private Thread t;
    private String threadName;

    Run( String name) {
        threadName = name;
        System.out.println("Creating " +  threadName );
    }

    @Override
    public void run() {


    }

    public void start () {
        System.out.println("Starting " +  threadName );
        if (t == null) {
            t = new Thread (this, threadName);
            t.start ();
        }
    }

}

class TestThread {

    public static void main(String args[]) {
        Run R1 = new Run( "Thread-1");
        R1.start();

        Run R2 = new Run( "Thread-2");
        R2.start();
    }
}