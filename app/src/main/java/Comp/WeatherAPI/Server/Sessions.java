package Comp.WeatherAPI.Server;

import java.net.SocketAddress;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Sessions {

    private static Sessions sessions = null;

    public static Sessions getInstance() {
        if (sessions == null)
            sessions = new Sessions();
        return sessions;
    }

    Map<String,Map> m = Collections.synchronizedMap(new HashMap<>());

    public void newSession(SocketAddress ip_port , String userName, String token){
        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("username", userName);
        userInfo.put("ip", ip_port.toString());
        this.m.put(token, userInfo);
        System.out.print("New session added. Sessions: ");
        System.out.println(this.m.toString());
    }

    public boolean validateSession(String token){
        return m.containsKey(token);
    }

    public void endSession(String userName){
        this.m.remove(userName);
    }
}
