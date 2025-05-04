package org.example;

public class RequestHandler {
    private CircuitBreaker breaker;
    private BackendService service;
    private RequestQueue requestQueue;

    public RequestHandler(CircuitBreaker breaker, RequestQueue requestQueue, BackendService service) {
        this.breaker = breaker;
        this.requestQueue = requestQueue;
        this.service = service;
    }

    public void handleRequest(Request request){
//        if((request.getReqId()%4) == 0){
//        System.out.println(request.getReqId());
//            breaker.setState(CircuitState.OPEN);
//        }
//        else if((request.getReqId()%3) == 0){
//            breaker.setState(CircuitState.HAF_OPEN);
//        }
//        else {
//            breaker.setState(CircuitState.CLOSED);
//        }
        if(breaker.getState() == CircuitState.CLOSED){
            try{
                System.out.println("Request success , id = " + request.getReqId());
                service.processRequest(request);
                breaker.success();
            } catch(Exception e){
                System.out.println("Request failure , id = " + request.getReqId());
                breaker.recordFailure();
                requestQueue.addRequest(request);
            }
        }
        else if(breaker.getState() == CircuitState.OPEN){
            System.out.println("Request failrue , id = " + request.getReqId());
            requestQueue.addRequest(request);
        }
        else if(breaker.getState() == CircuitState.HAF_OPEN) {
            try{
                System.out.println("Request success , id = " + request.getReqId());
                service.processRequest(request);
                breaker.success();
            }
            catch(Exception e){
                System.out.println("Request failure , id = " + request.getReqId());
                breaker.failure();
            }
        }
    }
}
