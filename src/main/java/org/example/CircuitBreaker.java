package org.example;

import lombok.Data;

import java.time.Instant;

@Data
public class CircuitBreaker {
    private CircuitState state;
    private int failrueCount;
    private int failureThreshold;
    private int timeout;
    private Instant lastFailureTime;

    public CircuitBreaker(int failureThreshold, int timeout){
        this.state = CircuitState.CLOSED;
        this.failrueCount = 0;
        this.failureThreshold = failureThreshold;
        this.timeout = timeout*1000;
        this.lastFailureTime = Instant.now();
    }

    public void attemptReset(){
        if(state == CircuitState.OPEN && lastFailureTime!= null && Instant.now().toEpochMilli() - lastFailureTime.toEpochMilli() >= timeout){
            this.state = CircuitState.HALF_OPEN;
        }
    }

    public void reset(){
        if(state == CircuitState.HALF_OPEN){
            failrueCount = 0;
            state = CircuitState.CLOSED;
        }
    }

    public void success(){
        if(state == CircuitState.HALF_OPEN){
            reset();
        }
    }

    public void recordFailure(){
        failrueCount += 1;
        lastFailureTime = Instant.now();
        if(failrueCount >= failureThreshold){
            state = CircuitState.OPEN;
        }
    }

    public void failure(){
        if(state == CircuitState.HALF_OPEN){
            state = CircuitState.OPEN;
            lastFailureTime = Instant.now();
        }
    }


}
