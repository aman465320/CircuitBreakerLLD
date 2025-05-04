package org.example;

public class HealthCheck extends Thread{

    private final CircuitBreaker circuitBreaker;
    private final long checkFreq;


    public HealthCheck(CircuitBreaker circuitBreaker, long checkFreq) {
        this.circuitBreaker = circuitBreaker;
        this.checkFreq = checkFreq*1000;
    }

    @Override
    public void run() {
        circuitBreaker.getState();
        if(circuitBreaker.getState() == CircuitState.OPEN){
            circuitBreaker.attemptReset();
        }
        try {
            Thread.sleep(checkFreq);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException();
        }
    }
}
