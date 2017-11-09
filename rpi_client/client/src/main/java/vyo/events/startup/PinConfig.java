package vyo.events.startup;

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.impl.GpioPinImpl;

public class PinConfig {

    //public static GpioPinDigitalOutput led1 = null;
    private static GpioController gpio = GpioFactory.getInstance();
    public static  GpioPin led1 = null;

    public static void config(){
        led1 = new GpioPinImpl(gpio,null,RaspiPin.GPIO_04);
        //led1 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04, PinState.LOW);
    }

    public static void unconfig(){
        if(led1!=null){
            gpio.shutdown();
            gpio.unprovisionPin(led1);
        }
    }
}
