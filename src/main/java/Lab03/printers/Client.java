package Lab03.printers;

public class Client implements Runnable {
    private final PrinterMonitor monitor;

    public Client(PrinterMonitor monitor) {
        this.monitor = monitor;
    }

    public String prepareMessage() {
        return "Some prepared message";
    }

    public void run() {
        for(int i = 0; i < Main.COUNT; i++) {
            String message = prepareMessage();
            int printerId = -1;
            try {
                printerId = monitor.reserve();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(message);
            monitor.release(printerId);
        }
    }
}

