import java.security.MessageDigest;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.security.NoSuchAlgorithmException;
/**********************************************************
 * @Author: Siddharth Bhardwaj 
 * If the SHA-256 signature of the password being tested, 
 * returned as a bytes array, is equal to the bytes array 
 * contained in the BYTES_SHA_256_TO_FIND variable, 
 * then we have successfully cracked the password.
 * 
 * If cracked, then password as well as the time taken
 * will be displayed.
 **********************************************************/
public class core implements Runnable{
    
    private int start;
    private int end;
    private final MessageDigest digest = MessageDigest.getInstance("SHA-256");
    private static boolean DONE = false;
    private String found;

    public core(int start, int end) throws NoSuchAlgorithmException {
        this.start = start;
        this.end = end;
    }

    public void generate(StringBuilder sb, int n){
        if(DONE)
            return;

        if(n == sb.length()){
            String canidate = sb.toString();
            //check password
            byte[] bytes = digest.digest(canidate.getBytes());
            if(Arrays.equals(bytes, pCracker.BYTES_SHA_TO_FIND)){
                found = canidate;
                DONE = true;
                return;
            }
        }
        //continue to generate...
        for(int i = 0; i < pCracker.ALPHABET.length && !DONE; i++){
            char letter = pCracker.ALPHABET[i];
            sb.setCharAt(n, letter);
            generate(sb, n+1);
        }  
    }
    @Override
    public void run() {
        for(int length = this.start; length <= this.end && !DONE; length++){
            StringBuilder sb = new StringBuilder();
            sb.setLength(length);
            generate(sb, 0);
        }
        if (DONE){
            long duration = System.currentTimeMillis() - pCracker.START_TIME;
            System.out.println("Password Cracked in " + TimeUnit.MILLISECONDS.toSeconds(duration));
        }else{
            System.out.println("Password was not cracked for subset [" + start + end + "]");
        }
    }
}