/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javathreads;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author human
 */
public class IPScanner implements Runnable {

    private final Properties ports;
    private final InetAddress address;
    private boolean reachable;
    private boolean onlyReachable = true;
    private ExecutorService threadPool;

    public IPScanner(Properties ports, InetAddress address) {
        this.ports = ports;
        this.address = address;
    }

    @Override
    public void run() {
        try {
            if (reachable = address.isReachable(5000)) {
                System.out.println(address.getHostAddress());
            }
        } catch (IOException ex) {
//            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        threadPool = Executors.newFixedThreadPool(32);

        if (reachable || onlyReachable) {
            for (int i = 0; i < 65536; i++) {
                threadPool.execute(new PortScanner(address, i));
            }
        }

        threadPool.shutdown();

    }

    private class PortScanner implements Runnable {

        private InetAddress address;
        private int port;

        public PortScanner(InetAddress address, int port) {
            this.address = address;
            this.port = port;
        }

        @Override
        public void run() {
            try {
                new Socket(address, port);

                String name = String.format("[%s]", ports.getProperty(Integer.toString(port), "UNKNOWN"));

                System.out.printf("%s:%d %s\n", address.getHostAddress(), port, name);
            } catch (IOException ex) {
//                    Logger.getLogger(IPScanner.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

}
