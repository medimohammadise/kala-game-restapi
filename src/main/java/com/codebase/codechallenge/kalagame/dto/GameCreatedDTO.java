package com.codebase.codechallenge.kalagame.dto;

import java.net.URI;
import java.net.URISyntaxException;

public class GameCreatedDTO {
    int id;
    URI uri;

    public GameCreatedDTO(int id, URI uri) throws URISyntaxException {
        this.id = id;
        this.uri = new URI("/games/"+String.valueOf(id));
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
