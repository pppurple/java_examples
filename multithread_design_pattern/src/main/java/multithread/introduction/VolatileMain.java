package multithread.introduction;

public class VolatileMain {
    public static void main(String[] args) {
        while (true) {
            Volatile vol = new Volatile();

            Runnable setTask = vol::setValues;
            Runnable readTask = vol::readValues;

            new Thread(setTask).start();
            new Thread(readTask).start();
        }
    }

    public static class Volatile {
        private volatile int number = 0;
        private volatile String str = "empty";

        public void setValues() {
            number = 123;
            str = "abc";
        }

        public void readValues() {
            System.out.println(number);
            System.out.println(str);
            System.out.println("-------------------");
        }
    }
}
