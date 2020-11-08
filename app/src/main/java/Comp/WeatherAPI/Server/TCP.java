package Comp.WeatherAPI.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TCP {

    public static void writeAuthMessage(DataOutputStream outputStream, AuthenticationMessages auth, String payload) throws IOException {

        outputStream.writeByte(0); // Auth Phase
        outputStream.writeByte(auth.getValue()); // Auth Challenge
        int question_len = payload.getBytes().length; // Size of payload
        outputStream.writeInt(question_len); // write size
        outputStream.writeBytes(payload); // Write question
        outputStream.flush();
    }

    public static String readAuthUsernameOrAnswer(DataInputStream inputStream) throws IOException {
        boolean valid = true;
        if(inputStream.readByte() != 0){
            // If not auth phase not valid
            System.out.println("here");
            inputStream.readAllBytes();
            return null;
        }
        if(inputStream.readByte() != 0){
            // If not auth request not valid
            inputStream.readAllBytes();
            return null;
        }
        int payload_size = inputStream.readInt();
        return new String(inputStream.readNBytes(payload_size));
    }

    public static String readQuery(DataInputStream inputStream) throws IOException {
        if(inputStream.readByte() != 1){
            // If not query not valid
            inputStream.readAllBytes();
            return null;
        }
        int token = inputStream.readInt();
        if(!Authentication.getInstance().validateSession(Integer.toString(token))){
            inputStream.readAllBytes();
            return null;
        }
        int payload_size = inputStream.readInt();
        return new String(inputStream.readNBytes(payload_size));
    }

    public static void passDataSocketInfo(DataOutputStream outputStream, int localPort) throws IOException {
        outputStream.writeInt(localPort);
    }
}
