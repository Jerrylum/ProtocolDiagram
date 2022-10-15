package com.jerryio.protocol_diagram;

import java.util.Collections;
import java.util.Map;

import com.beust.jcommander.internal.Maps;

public class ExistingProtocol {
    // Source: https://github.com/luismartingarcia/protocol/blob/master/specs.py

    private static final Map<String, String> protocols = Maps.newHashMap(
            "tcp", "Source Port:16,Destination Port:16,Sequence Number:32," +
                    "Acknowledgment Number:32,Offset:4,Res.:4,Flags:8,Window:16,Checksum:16," +
                    "Urgent Pointer:16,Options:24,Padding:8",
            "udp", "Source Port:16,Destination Port:16,Length:16,Checksum:16",
            "ip", "Version:4,IHL:4,Type of Service:8,Total Length:16,Identification:16," +
                    "Flags:3,Fragment Offset:13,Time to Live:8,Protocol:8,Header Checksum:16," +
                    "Source Address:32,Destination Address:32,Options:24,Padding:8",
            "ipv6", "Version:4,Traffic Class:8,Flow Label:20,Length:16,Next Header:8," +
                    "Hop Limit:8,Source Address:128,Destination Address:128",
            "icmp", "Type:8,Code:8,Checksum:16,Message Body:64",
            "icmp-destination", "Type:8,Code:8,Checksum:16,Unused:32,Internet Header + 64 bits" +
                    " of Original Data Datagram:64",
            "icmp-time", "Type:8,Code:8,Checksum:16,Unused:32,Internet Header + 64 bits" +
                    " of Original Data Datagram:64",
            "icmp-parameter", "Type:8,Code:8,Checksum:16,Pointer:32,Internet Header + 64 bits" +
                    " of Original Data Datagram:64",
            "icmp-source", "Type:8,Code:8,Checksum:16,Unused:32,Internet Header + 64 bits" +
                    " of Original Data Datagram:64",
            "icmp-redirect", "Type:8,Code:8,Checksum:16,Unused:32,Internet Header + 64 bits" +
                    " of Original Data Datagram:64",
            "icmp-echo", "Type:8,Code:8,Checksum:16,Identifier:16,Sequence Number:16,Data:64",
            "icmp-timestamp", "Type:8,Code:8,Checksum:16,Identifier:16,Sequence Number:16," +
                    "Originate Timestamp:32,Receive Timestamp:32,Transmit Timestamp:32",
            "icmp-information", "Type:8,Code:8,Checksum:16,Identifier:16,Sequence Number:16",
            "icmpv6", "Type:8,Code:8,Checksum:16,Message Body:64",
            "icmpv6-destination", "Type:8,Code:8,Checksum:16,Unused:32,Invoking packet data" +
                    " (without exceeding minimum IPv6 MTU):64",
            "icmpv6-big", "Type:8,Code:8,Checksum:16,MTU:32,Invoking packet data (without" +
                    " exceeding minimum IPv6 MTU):64",
            "icmpv6-time", "Type:8,Code:8,Checksum:16,Unused:32,Invoking packet data (without" +
                    " exceeding minimum IPv6 MTU):64",
            "icmpv6-parameter", "Type:8,Code:8,Checksum:16,Pointer:32,Invoking packet data" +
                    " (without exceeding minimum IPv6 MTU):64",
            "icmpv6-echo", "Type:8,Code:8,Checksum:16,Identifier:16,Sequence Number:16,Data:64",
            "icmpv6-rsol", "Type:8,Code:8,Checksum:16,Reserved:32,Options:64",
            "icmpv6-radv", "Type:8,Code:8,Checksum:16,Cur Hop Limit:8,M:1,O:1,Reserved:6," +
                    "Router Lifetime:16,Reachable Time:32,Retransmission Timer:32,Options:64",
            "icmpv6-nsol", "Type:8,Code:8,Checksum:16,Reserved:32,Target Address:128,Options:64",
            "icmpv6-nadv", "Type:8,Code:8,Checksum:16,R:1,S:1,O:1,Reserved:29,Target Address:128," +
                    "Options:64",
            "icmpv6-redirect", "Type:8,Code:8,Checksum:16,Reserved:32,Target Address:128," +
                    "Destination Address:128,Options:64",
            "dhcp", "Opcode:8,Hardware Type: 8,HW Addr Len:8,Hop Count:8,Transaction ID:32," +
                    "Number of Seconds:16,Flags:16,Client IP Addr:32,Your IP Addr: 32," +
                    "Server IP Addr:32,Gateway IP Addr:32,Client Hardware Addr:128," +
                    "Server Host Name (512 bytes):32,Boot Filename (1024 1024):32",
            "modbus-tcp", "Transaction ID:16,Protocol ID:16,Length:16,Unit ID:8,Function Code:8,Data:64",
            "profinet-rt", "Frame ID:16,User Data:80,Cycle Counter:16,Data Status:8,Transfer Status:8",
            "dnp3", "Start:16,Length:8,Control:8,Destination Address:16,Source Address:16,CRC:16," +
                    "User Data 1:128,CRC 1:16,User Data 2:112,CRC 2:16");

    public static String getProtocol(String name) {
        return protocols.getOrDefault(name, null);
    }

    public static Map<String, String> getProtocols() {
        return Collections.unmodifiableMap(protocols);
    }
}
