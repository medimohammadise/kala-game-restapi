package com.codebase.codechallenge.kalagame.dto;

import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

public class GameCreatedDTO {
    int id;
   // URI uri;

    public GameCreatedDTO(int id) throws URISyntaxException {
        this.id = id;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
