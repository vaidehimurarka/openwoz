package openwoz.rpi.comm;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;

//import openwoz.rpi.dataobjects.RobotSequence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import openwoz.rpi.dataobjects.RobotEvent;
import redis.clients.jedis.JedisPubSub;

/**
 * Class which describes all the event handlers for the Jedis Server
 * 
 * @author Amiraj Dhawan (amirajdhawan@gmail.com)
 */
public class RobotProfileSubscriber extends JedisPubSub {
	
	private static Logger logger = LoggerFactory.getLogger(RobotProfileSubscriber.class);
	private static String loggingPrefix = "";

	public RobotProfileSubscriber(){
	}

	/**
	 * Event handler for onUnsubscribe
	 * 
	 * @param  channel: Name of the channel
	 * @param  subscribedChannels: total number of subscribed channels
	 * @return void
	 * @author Amiraj Dhawan (amirajdhawan@gmail.com)
	 */
	@Override
	public void onUnsubscribe(String channel, int subscribedChannels) {
		logger.info(loggingPrefix  + " onUnsubscribe function");
	}
	
	/**
	 * Event handler for onSubscribe
	 * 
	 * @param  channel: Name of the channel
	 * @param  subscribedChannels: total number of subscribed channels
	 * @return void
	 * @author Amiraj Dhawan (amirajdhawan@gmail.com)
	 */
	@Override
	public void onSubscribe(String channel, int subscribedChannels) {
		logger.info(loggingPrefix + "onSubscribe");
	}
	
	/**
	 * Event handler for onPUnsubscribe
	 * 
	 * @param pattern: pattern matched against a channel
	 * @param  subscribedChannels: total number of subscribed channels
	 * @return void
	 * @author Amiraj Dhawan (amirajdhawan@gmail.com)
	 */
	@Override
	public void onPUnsubscribe(String pattern, int subscribedChannels) {
	}

	/**
	 * Event handler for onPSubscribe
	 * 
	 * @param pattern: pattern matched against a channel
	 * @param  subscribedChannels: total number of subscribed channels
	 * @return void
	 * @author Amiraj Dhawan (amirajdhawan@gmail.com)
	 */
	@Override
	public void onPSubscribe(String pattern, int subscribedChannels) {
	}

	/**
	 * Event handler for onPMessage
	 * 
	 * @param pattern: pattern matched against a channel
	 * @param  channel: name of the channel
	 * @param  message: the actual message received
	 * @return void
	 * @author Amiraj Dhawan (amirajdhawan@gmail.com)
	 */
	@Override
	public void onPMessage(String pattern, String channel, String message) {
	}

	/**
	 * Event handler for onMessage. Executes the code to be executed when a message on the channel is
	 * received. Currently moves the first motor in the motor configuration with the value received.
	 * In the future, this will do a reflection based function call based on an event.
	 * 
	 * @param channel: name of the channel
	 * @param message: the actual message received
	 * @return void
	 * @author Amiraj Dhawan (amirajdhawan@gmail.com)
	 */
	@Override
	public void onMessage(String channel, String message) {
		logger.info(loggingPrefix + "Message received: " + message);
		
		try{
			ObjectMapper mapper = new ObjectMapper();
			RobotEvent[] events = mapper.readValue(message, RobotEvent[].class);
			logger.info(loggingPrefix + "size of object mapper"+events.length);
			long previousTime = 0;
			if(events!=null){
				Arrays.sort(events,new RobotEventComparator());
				for(int i = 0;i<events.length;i++){
					RobotEvent event = events[i];
					long currentTime = event.getTime();
					long sleepTime = currentTime-previousTime;
					Thread.sleep(sleepTime);
					Class<?> classInstance = Class.forName(event.getClassName());
					Object objectReflect = classInstance.newInstance();
					Method methodInstance = classInstance.getDeclaredMethod(event.getMethodName());
					logger.info(loggingPrefix + "Invoking method: " + event.getMethodName() + " from class: " + event.getClassName());
					methodInstance.invoke(objectReflect);
					previousTime = currentTime;
				}
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}	
	}

	public class RobotEventComparator implements Comparator<RobotEvent>{

		@Override
		public int compare(RobotEvent o1, RobotEvent o2) {
			return (int) (o1.getTime()-o2.getTime());
		}
	}
}
