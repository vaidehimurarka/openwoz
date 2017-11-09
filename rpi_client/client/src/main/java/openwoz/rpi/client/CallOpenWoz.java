package openwoz.rpi.client;

import vyo.events.BlinkLedEvent;

import java.io.File;

public class CallOpenWoz {

    public static void main(String args[]){
        OpenWoz openWozclient = null;
        try{
            String redisIp = "10.0.0.247";
            String redisPass = "foo";
            int redisPort = 6379;
            BlinkLedEvent blinkLedEvent = new BlinkLedEvent();
            blinkLedEvent.glowled();            //blinkLedEvent.blinkLed();
            File profileFile = new File("/home/pi/Documents/workspace/openwoz/rpi_client/sample/resources/vyo_robot.js");
            openWozclient = new OpenWoz();
            openWozclient.start(profileFile,redisIp,redisPort,redisPass);
        }catch(Exception e){
            e.printStackTrace();
        }
        finally{
            if(openWozclient!=null){
                openWozclient.shutdown();
            }
        }
    }
}