import org.junit.jupiter.api.Test;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for EventCounter
 *
 * @author Jeffrey Zhou
 */
class EventCounterTest{

    /**
     * Test method to test all the failure test cases
     */
    @Test
    void getCountOfEventsFailureTest(){
        EventCounter ec = new EventCounter();

        //TEST 1: Retrieving count of events while passing 0 for minutes and 0 for seconds.
        assertEquals(0, ec.getCountOfEvents(0, 0));

        //TEST 2: Retrieving count of events when the value of minutes is greater than 5.
        assertEquals(0, ec.getCountOfEvents(10, 0));

        //TEST 3: Retrieving count of events when the values of minutes and seconds combined is greater than 5 minutes or 300 seconds.
        assertEquals(0, ec.getCountOfEvents(5, 30));

        //TEST 4: Retrieving count of events when the value of seconds is greater than 300 seconds.
        assertEquals(0, ec.getCountOfEvents(0, 301));

        //TEST 5: Retrieving count of events when the value of minutes is negative.
        assertEquals(0, ec.getCountOfEvents(-100, 0));

        //TEST 6: Retrieving count of events when the value of seconds is negative.
        assertEquals(0, ec.getCountOfEvents(0, -100));

        //TEST 7: Retrieving count of events when the values of minutes and seconds are negative.
        assertEquals(0, ec.getCountOfEvents(-100, -100));
    }

    /**
     * Test method to test all the succesful test cases
     * @throws InterruptedException - No exceptions will be expected
     */
    @Test
    public void getCountOfEventsSuccessTest() throws InterruptedException{
        EventCounter ec = new EventCounter();

        //TEST 1: Retrieving count of events when no events have been signaled in the last 5 minutes.
        assertEquals(0, ec.getCountOfEvents(5, 0));

        //Test 2: Signaling an event every second for 5 seconds
        for(int i = 0; i < 5; i++){
            ec.signalEvent();
            TimeUnit.SECONDS.sleep(1);
        }

        assertEquals(1, ec.getCountOfEvents(0, 1));
        assertEquals(2, ec.getCountOfEvents(0, 2));
        assertEquals(3, ec.getCountOfEvents(0, 3));
        assertEquals(4, ec.getCountOfEvents(0, 4));
        assertEquals(5, ec.getCountOfEvents(0, 5));

        //Test 3: Signaling an event every 30 seconds for 150 seconds
        for(int i = 1; i < 6; i++){
            ec.signalEvent();
            TimeUnit.SECONDS.sleep(30);
        }

        assertEquals(1, ec.getCountOfEvents(0, 30));
        assertEquals(2, ec.getCountOfEvents(1, 0));
        assertEquals(3, ec.getCountOfEvents(1, 30));
        assertEquals(4, ec.getCountOfEvents(2, 0));
        assertEquals(5, ec.getCountOfEvents(2, 30));
        assertEquals(10, ec.getCountOfEvents(3, 0));
        assertEquals(10, ec.getCountOfEvents(3, 30));
        assertEquals(10, ec.getCountOfEvents(4, 0));
        assertEquals(10, ec.getCountOfEvents(4, 30));
        assertEquals(10, ec.getCountOfEvents(5, 0));

        //Test 4: Signaling an event every minute for 5 minutes
        for(int i = 1; i < 6; i++){
            ec.signalEvent();
            TimeUnit.MINUTES.sleep(1);
        }

        assertEquals(1, ec.getCountOfEvents(1, 0));
        assertEquals(2, ec.getCountOfEvents(2, 0));
        assertEquals(3, ec.getCountOfEvents(3, 0));
        assertEquals(4, ec.getCountOfEvents(4, 0));
        assertEquals(5, ec.getCountOfEvents(5, 0));

        //Test 5: Getting count of events 6 minutes in the past
        assertEquals(0, ec.getCountOfEvents(6, 0));
    }

}