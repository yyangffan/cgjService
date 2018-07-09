package   com.lhkj.cgjservice.base.ui.lock;

import java.util.HashMap;

/**
 * Created by 浩琦 on 2017/6/19.
 */

public class BaseLock {
    private Event event;
    private boolean isRun;
    private HashMap<Long,Event> eventList;
    private long index;
    public BaseLock(){
        eventList=new HashMap();
    }
    protected void subEvent(Event event){
        index++;
        if (isRun){
            eventList.put(index,event);
        }else {
            isRun=true;
            this.event=event;
            event.event();
        }
    }

    protected void startEvent(){

    }

    protected void overEvent(long index){

    }

    protected interface Event{
         void event();
    }
}
