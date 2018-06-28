package com.codebase.codechallenge.kalagame.dto;

import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

public class GameCreatedDTO {
    int id;
    URI uri;

    public GameCreatedDTO(int id, URI uri) throws URISyntaxException {
        this.id = id;
        try {
            this.uri=new URI("http://" + InetAddress.getLocalHost().getHostAddress()+"/"+"games/"+String.valueOf(id));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }
}
