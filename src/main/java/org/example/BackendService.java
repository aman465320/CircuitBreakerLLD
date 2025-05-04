package org.example;

public class BackendService {
    CircuitBreaker circuitBreaker;
    public BackendService(CircuitBreaker circuitBreaker){
        this.circuitBreaker = circuitBreaker;
    }
    public void processRequest(Request request){
        System.out.println("Processing requst with ID = " + request.getReqId());

    }
}
