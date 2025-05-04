package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static Request nextReq(int reqId){
        return new Request(reqId);
    }
    public static void main(String[] args) throws InterruptedException {

        CircuitBreaker circuitBreaker = new CircuitBreaker(3,10);
        BackendService service = new BackendService(circuitBreaker);
        RequestQueue requestQueue = new RequestQueue(circuitBreaker,service);
        RequestHandler handler = new RequestHandler(circuitBreaker,requestQueue,service);
        HealthCheck healthCheck = new HealthCheck(circuitBreaker,1);

        healthCheck.start();

        int reqId = 1;

        while(reqId<=10){
           Request request = nextReq(reqId);
           reqId++;
           handler.handleRequest(request);
           Thread.sleep(1);
        }
    }
}