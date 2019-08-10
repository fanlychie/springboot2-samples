package org.fanlychie.jdbc.sample.exception;

/**
 * Created by fanlychie on 2019/6/25.
 */
public class TransactionException extends RuntimeException {

    public TransactionException(String message) {
        super(message);
    }

}