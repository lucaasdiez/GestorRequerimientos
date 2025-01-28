package com.srgi.exeptions;

public class AlreadyExistExeption extends RuntimeException {
    public AlreadyExistExeption(String s) {
        super(s);
    }
}
