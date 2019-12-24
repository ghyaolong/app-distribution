package com.cube.utils;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * IP工具类
 */
public class IPUtils {


    public static void main(String[] args) {
//        try {
//            // 正确的IP拿法
//            System.out.println("get LocalHost LAN Address : " + getLocalHostLANAddress().getHostAddress());
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
        getLocalIPList().stream().forEach(System.out::println);
    }

    // 正确的IP拿法，即优先拿site-local地址
    private static InetAddress getLocalHostLANAddress() throws UnknownHostException {
        try {
            InetAddress candidateAddress = null;
            // 遍历所有的网络接口
            for (Enumeration ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements();) {
                NetworkInterface iface = (NetworkInterface) ifaces.nextElement();
                // 在所有的接口下再遍历IP
                for (Enumeration inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements();) {
                    InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
                    System.out.println(inetAddr.getHostAddress());
                    if (!inetAddr.isLoopbackAddress()) {// 排除loopback类型地址
                        if (inetAddr.isSiteLocalAddress()) {
                            // 如果是site-local地址，就是它了
                            return inetAddr;
                        } else if (candidateAddress == null) {
                            // site-local类型的地址未被发现，先记录候选地址
                            candidateAddress = inetAddr;
                        }
                    }
                }
            }
            if (candidateAddress != null) {
                return candidateAddress;
            }
            // 如果没有发现 non-loopback地址.只能用最次选的方案
            InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
            if (jdkSuppliedAddress == null) {
                throw new UnknownHostException("The JDK InetAddress.getLocalHost() method unexpectedly returned null.");
            }
            return jdkSuppliedAddress;
        } catch (Exception e) {
            UnknownHostException unknownHostException = new UnknownHostException(
                    "Failed to determine LAN address: " + e);
            unknownHostException.initCause(e);
            throw unknownHostException;
        }
    }

    public static List<String> getLocalIPList() {
        List<String> ipList = new ArrayList<String>();
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            NetworkInterface networkInterface;
            Enumeration<InetAddress> inetAddresses;
            InetAddress inetAddress;
            String ip;
            while (networkInterfaces.hasMoreElements()) {
                networkInterface = networkInterfaces.nextElement();
                inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    inetAddress = inetAddresses.nextElement();
                    System.out.println(inetAddress.toString());

                    if(inetAddress.isAnyLocalAddress() && inetAddress instanceof Inet4Address){
                        System.out.println("isAnyLocalAddress:"+inetAddress.getHostAddress());
                    }
                    if(inetAddress.isLinkLocalAddress() && inetAddress instanceof Inet4Address){
                        System.out.println("isLinkLocalAddress:"+inetAddress.getHostAddress());
                    }
                    if(inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address){
                        System.out.println("isLoopbackAddress:"+inetAddress.getHostAddress());
                    }
                    if(inetAddress.isSiteLocalAddress() && inetAddress instanceof Inet4Address){
                        System.out.println("isSiteLocalAddress:"+inetAddress.getHostAddress());
                    }
                    if (inetAddress != null && inetAddress instanceof Inet4Address) { // IPV4
                        ip = inetAddress.getHostAddress();
                        ipList.add(ip);
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return ipList;
    }
}
