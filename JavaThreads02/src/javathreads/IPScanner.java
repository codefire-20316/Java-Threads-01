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

/**
 *
 * @author human
 */
public class IPScanner implements Runnable {

    private final Properties ports;
    private final InetAddress address;
    private boolean reachable;
    private boolean onlyReachable = true;

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

        if (reachable || onlyReachable) {
            for (int i = 0; i < 65536; i++) {
                try {
                    new Socket(address, i);

                    String name = String.format("[%s]", ports.getProperty(Integer.toString(i), "UNKNOWN"));

                    System.out.printf("%s:%d %s\n", address.getHostAddress(), i, name);
                } catch (IOException ex) {
//                    Logger.getLogger(IPScanner.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

}
