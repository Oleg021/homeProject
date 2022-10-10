package com.nix.vyrvykhvost;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Request {
    private String ipAddress;
    private String userAgent;
    private LocalDateTime created;

    public Request(String ipAddress, String userAgent) {
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
        this.created = LocalDateTime.now();
    }
}
