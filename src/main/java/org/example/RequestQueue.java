package org.example;

import java.util.LinkedList;
import java.util.Queue;

public class RequestQueue {
    private Queue<Request> requestQ;
    private CircuitBreaker breaker;
    private BackendService service;

    public RequestQueue(CircuitBreaker breaker, BackendService backendService) {
        this.requestQ = new LinkedList<>();
        this.breaker = breaker;
        this.service =backendService;
    }

    public void addRequest(Request request){
        requestQ.offer(request);
    }

    public void processRequest(Request request){
        while(!requestQ.isEmpty()){
            if(requestQ.contains(request)){
             service.processRequest(request);
            }
        }
    }

}
