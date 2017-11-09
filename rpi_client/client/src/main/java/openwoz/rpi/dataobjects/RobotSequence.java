package openwoz.rpi.dataobjects;

import java.util.ArrayList;
import java.util.List;

/**
 * Class which defines the data structure of a robot sequence
 * Yet to be implemented
 * 
 * @author Amiraj Dhawan (amirajdhawan@gmail.com)
 */
public class RobotSequence {
    List<RobotEvent> events;
    RobotSequence(){
        this.events = new ArrayList<RobotEvent>();
    }

    public List<RobotEvent> getEvents() {
        return events;
    }

    public void setEvents(List<RobotEvent> events) {
        this.events = events;
    }

    public void addEvent(RobotEvent event){
        this.events.add(event);
    }

    public void removeEvent(RobotEvent event){
        this.events.remove(event);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RobotSequence that = (RobotSequence) o;
        return events != null ? events.equals(that.events) : that.events == null;
    }

    @Override
    public int hashCode() {
        return events != null ? events.hashCode() : 0;
    }
}
