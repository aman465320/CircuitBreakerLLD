package org.example;

import lombok.Data;

@Data
public class Request {
    private int reqId;
    public Request(int id){
        this.reqId = id;
    }

}
