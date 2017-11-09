package vyo.events;
import com.pi4j.io.gpio.*;

public class BlinkLedEvent {
    public void glowled() throws InterruptedException{
        final GpioController gpio = GpioFactory.getInstance();
        final GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(
                RaspiPin.GPIO_04, "PinLED", PinState.LOW);
        System.out.println("light is: OFF");
        pin.high();
        System.out.println("light is: ON");
        Thread.sleep(1000);
        System.out.println("light is: ON for 1 second");
        pin.low();
        pin.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
        gpio.shutdown();
        gpio.unprovisionPin(pin);
    }
}
