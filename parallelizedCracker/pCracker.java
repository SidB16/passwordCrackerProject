//package com.ssaurel.passwordcracker;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**********************************************************
 * @Author: Siddharth Bhardwaj 
 **********************************************************/
public class pCracker{
    //Feilds- are what the object knows/has.
    public static final String PASSWORD_SHA_TO_FIND = "8c9713d70976a2d1ea0c07a6d70998dc7da3ebca901f5b55cce470191bc86332";
    public static final byte[] BYTES_SHA_TO_FIND = pCracker.hexStringtoByteArray(PASSWORD_SHA_TO_FIND);
    public static final int PASSWORD_MAX_LENGTH = 5;
    public static char[] ALPHABET = "abcdefghijklmnopqrstuvwxyz0123456789@#&!()^=+/:.;,".toCharArray();
    public static long START_TIME;

    private static byte[] hexStringtoByteArray(String data){
        int n = data.length();
        byte[] rtn = new byte[n/2];

        for(int i = 0; i < n; i += 2){
            rtn[i/2] = (byte)((Character.digit(data.charAt(i+1), 16) << 4) + Character.digit(data.charAt(i+1), 16));
        }
        return rtn;
    }

    public static void main(String[] args) throws NoSuchAlgorithmException{
        int cores = Runtime.getRuntime().availableProcessors();
        int lenghtSet = PASSWORD_MAX_LENGTH / cores;
        int end = 0;

        START_TIME = System.currentTimeMillis();

        ExecutorService executorService = Executors.newFixedThreadPool(cores);

        while(end < PASSWORD_MAX_LENGTH){
            int start = end + 1;
            end = start + lenghtSet;

            if(end > PASSWORD_MAX_LENGTH)
                end = PASSWORD_MAX_LENGTH;
            
            executorService.submit(new core(start, end));
        }
        executorService.shutdown();
    }
}