/**
 * Author: Phumin Walaipatchara
 */

package fancybank.io;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Take input from stdin and handle invalid inputs
 */

public class StdinWrapper {
    private String message;
    private String token;
    private BufferedReader br;
    private boolean special; // Is special key?
    private Character specialChar;

    /* Special key */
    //private static final Character END = 'e';
    private static final Character QUIT = 'q';
    //private static final Character INFO = 'i';
    //private static final Character TELEPORT = 't';
    //private static final Character BACKNEXUS = 'b';

    public StdinWrapper(String message) {
        if (message == null)
            throw new IllegalArgumentException("Illegal Argument for StdinWrapper");
        this.message = message;
        br = new BufferedReader(new InputStreamReader(System.in));
        clear();
    }

    private void clear() {
        token = null;
        specialChar = null;
        special = false;
    }

    public void setMessage(String message) {
        if (message == null)
            throw new IllegalArgumentException("Illegal Argument for StdinWrapper");
        this.message = message;
    }

    /**
     * Read input from stdin and set flag if received special key
     */

    private void getInput() {
        clear();
        boolean success;

        do {
            System.out.printf(message);

            try {
                token = br.readLine();
                success = true;
            } catch (Exception e) {
                success = false;
            }
            System.out.println();
        } while (!success);

        if (token != null && ((token.length()==1 && Character.toLowerCase(token.charAt(0)) == 'q') || token.toLowerCase().equals("quit")))
        {
            special = true;
            specialChar = QUIT;
            token = null;
            return;
        }
        // else if (token != null && ((token.length()==1 && Character.toLowerCase(token.charAt(0)) == 'e') || token.toLowerCase().equals("end")))
        // {
        //     special = true;
        //     specialChar = END;
        //     token = null;
        //     return;
        // }
        // else if (token != null && ((token.length()==1 && Character.toLowerCase(token.charAt(0)) == 'i') || token.toLowerCase().equals("info")))
        // {
        //     special = true;
        //     specialChar = INFO;
        //     token = null;
        //     return;
        // }
        // else if (token != null && ((token.length()==1 && Character.toLowerCase(token.charAt(0)) == 't') || token.toLowerCase().equals("teleport")))
        // {
        //     special = true;
        //     specialChar = TELEPORT;
        //     token = null;
        //     return;
        // }
        // else if (token != null && ((token.length()==1 && Character.toLowerCase(token.charAt(0)) == 'b') || token.toLowerCase().equals("back")))
        // {
        //     special = true;
        //     specialChar = BACKNEXUS;
        //     token = null;
        //     return;
        // }

        special = false;
        specialChar = null;
    }

    public boolean isQuit() {
        return (special && specialChar.equals(QUIT)) ? true : false;
    }

    public boolean isEnd() {
        return (special && specialChar.equals(END)) ? true : false;
    }

    public boolean isInfo() {
        return (special && specialChar.equals(INFO)) ? true : false;
    }

    public boolean isTeleport() {
        return (special && specialChar.equals(TELEPORT)) ? true : false;
    }

    public boolean isBackNexus() {
        return (special && specialChar.equals(BACKNEXUS)) ? true : false;
    }

    public String next() {
        getInput();
        return (!special) ? token : null;
    }

    public Character nextChar() {
        getInput();
        try {
            return (!special) ? token.charAt(0) : null;
        } catch (Exception e) {
            return null;
        }
    }
    
    public Integer nextInt() {
        getInput();
        try {
            return (!special) ? Integer.parseInt(token) : null;
        } catch (Exception e) {
            return null;
        }
    }
}