package com.megasystem.suitepayment.entity;

import java.io.Serializable;


public class Enumerators implements Serializable {

    public static final class Status {
        public static Long Cancel = 58L;
        public static Long Success = 57L;
        public static Long Pending = 56L;
        public static Long InProcess = 60L;
    }

    public static final Long createTypeIdc = 54L;
    public static final Long TypeIdc = 52L;
}
