import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * Main program used to test EventCounter functionalities
 *
 * @author Jeffrey Zhou
 */
public class Main{

    /**
     * @param args - Command line arguments, which we won't be needing in this project
     * @throws Exception - Exceptions from validation
     */
    public static void main(String[] args) throws Exception{
        EventCounter ec = new EventCounter();

        //Tests for validation
        System.out.println("----- Tests for validation -----");
        System.out.println("TEST 1: Retrieving count of events when no events have been signaled in the last 5 minutes.");
        System.out.println("Count: " + ec.getCountOfEvents(1, 0) + "\n");

        System.out.println("TEST 2: Retrieving count of events when the values for minutes and seconds are 0.");
        System.out.println("Count: " + ec.getCountOfEvents(0, 0) + "\n");

        System.out.println("TEST 3: Retrieving count of events when value of minutes is greater than 5.");
        System.out.println("Count: " + ec.getCountOfEvents(6, 0) + "\n");

        System.out.println("TEST 4: Retrieving count of events when the values of minutes and seconds combined is greater than 5 minutes or 300 seconds.");
        System.out.println("Count: " + ec.getCountOfEvents(5, 1) + "\n");

        System.out.println("TEST 5: Retrieving count of events when the value of seconds is greater than 300 seconds.");
        System.out.println("Count: " + ec.getCountOfEvents(0, 301) + "\n");

        System.out.println("TEST 6: Retrieving count of events when the value of minutes is negative.");
        System.out.println("Count: " + ec.getCountOfEvents(0, -1) + "\n");

        System.out.println("TEST 7: Retrieving count of events when the value of seconds is negative.");
        System.out.println("Count: " + ec.getCountOfEvents(-1, 0) + "\n");

        System.out.println("TEST 8: Retrieving count of events when the values of minutes and seconds are negative.");
        System.out.println("Count: " + ec.getCountOfEvents(-1, -1) + "\n");

        //Tests for signaling an event every second and getting the count every second for 5 seconds
        System.out.println("----- Tests for signaling an event every second and getting the count every second for 5 seconds -----");
        for(int i = 1; i < 6; i++){
            ec.signalEvent();
            TimeUnit.SECONDS.sleep(1);
            System.out.println("Test " + i + ": Retrieving the number of events that happened " + i + " seconds ago:");
            displayTimes(0, i);
            System.out.println("Count: " + ec.getCountOfEvents(0, i) + "\n");
        }

        //Tests for signaling 2 events, waiting 30 seconds, and getting the count for every 30 seconds for a 150-second timeframe
        System.out.println("----- Tests for signaling 2 events, waiting 30 seconds, and getting the count for every 30 seconds for a 150-second timeframe -----");
        for(int i = 1; i < 6; i++){
            ec.signalEvent();
            ec.signalEvent();
            TimeUnit.SECONDS.sleep(30);
            System.out.println("Test " + i + ": Retrieving the number of events that happened " + (i * 30) + " seconds ago:");
            displayTimes(0, i * 30);
            System.out.println("Count: " + ec.getCountOfEvents(0, i * 30) + "\n");
        }

        //Tests for signaling 3 events for the current time, waiting 3 minutes, and getting the count for every 30 seconds for a 5-minute timeframe
        System.out.println("----- Tests for signaling 3 events for the current time, waiting 3 minutes, and getting the count every 30 seconds for a 5-minute timeframe -----");
        ec.signalEvent();
        ec.signalEvent();
        ec.signalEvent();
        TimeUnit.MINUTES.sleep(3);

        System.out.println("Test 1: Retrieving the number of events that happened 30 seconds ago:");
        displayTimes(0, 30);
        System.out.println("Count: " + ec.getCountOfEvents(0, 30) + "\n");

        System.out.println("Test 2: Retrieving the number of events that happened 1 minute ago:");
        displayTimes(1, 0);
        System.out.println("Count: " + ec.getCountOfEvents(1, 0) + "\n");

        System.out.println("Test 3: Retrieving the number of events that happened 1 minute and 30 seconds ago:");
        displayTimes(1, 30);
        System.out.println("Count: " + ec.getCountOfEvents(1, 30) + "\n");

        System.out.println("Test 4: Retrieving the number of events that happened 2 minute ago:");
        displayTimes(2, 0);
        System.out.println("Count: " + ec.getCountOfEvents(2, 0) + "\n");

        System.out.println("Test 5: Retrieving the number of events that happened 2 minute and 30 seconds ago:");
        displayTimes(2, 30);
        System.out.println("Count: " + ec.getCountOfEvents(2, 30) + "\n");

        System.out.println("Test 6: Retrieving the number of events that happened 3 minute ago:");
        displayTimes(3, 0);
        System.out.println("Count: " + ec.getCountOfEvents(3, 0) + "\n");

        System.out.println("Test 7: Retrieving the number of events that happened 3 minute and 30 seconds ago:");
        displayTimes(3, 30);
        System.out.println("Count: " + ec.getCountOfEvents(3, 30) + "\n");

        System.out.println("Test 8: Retrieving the number of events that happened 4 minute ago:");
        displayTimes(4, 0);
        System.out.println("Count: " + ec.getCountOfEvents(4, 0) + "\n");

        System.out.println("Test 9: Retrieving the number of events that happened 4 minute and 30 seconds ago:");
        displayTimes(4, 30);
        System.out.println("Count: " + ec.getCountOfEvents(4, 30) + "\n");

        System.out.println("Test 10: Retrieving the number of events that happened 5 minute ago:");
        displayTimes(5, 0);
        System.out.println("Count: " + ec.getCountOfEvents(5, 0) + "\n");

        //Tests for signaling 100 events for the current time, waiting 5 minutes, and getting the count every minute for a 5-minute timeframe
        System.out.println("----- Tests for signaling 100 events for the current time, waiting 5 minutes, and getting the count every minute for a 5-minute timeframe -----");
        for(int i = 0; i < 100; i++){
            ec.signalEvent();
        }

        TimeUnit.MINUTES.sleep(5);

        System.out.println("Test 1: Retrieving the number of events that happened 1 minute ago:");
        displayTimes(1, 0);
        System.out.println("Count: " + ec.getCountOfEvents(1, 0) + "\n");

        System.out.println("Test 2: Retrieving the number of events that happened 2 minute ago:");
        displayTimes(2, 0);
        System.out.println("Count: " + ec.getCountOfEvents(2, 0) + "\n");

        System.out.println("Test 3: Retrieving the number of events that happened 3 minute ago:");
        displayTimes(3, 0);
        System.out.println("Count: " + ec.getCountOfEvents(3, 0) + "\n");

        System.out.println("Test 4: Retrieving the number of events that happened 4 minute ago:");
        displayTimes(4, 0);
        System.out.println("Count: " + ec.getCountOfEvents(4, 0) + "\n");

        System.out.println("Test 5: Retrieving the number of events that happened 5 minute ago:");
        displayTimes(5, 0);
        System.out.println("Count: " + ec.getCountOfEvents(5, 0) + "\n");

        System.out.println("Test 6: Retrieving the number of events that happened 5 minute and 10 seconds ago:");
        displayTimes(5, 10);
        System.out.println("Count: " + ec.getCountOfEvents(5, 10) + "\n");
    }

    /**
     * Method to display the current and the past times
     * @param minutes - int value representing how many minutes in the past to display past time
     * @param seconds - int value representing how many seconds in the past to display past time
     */
    public static void displayTimes(int minutes, int seconds){
        LocalDateTime timeNow = LocalDateTime.now().withNano(0);
        LocalDateTime pastTime = timeNow.minusMinutes(minutes).minusSeconds(seconds);
        System.out.println("Timestamp now: " + timeNow);
        System.out.println("Timestamp at " + minutes + " minutes and " + seconds + " seconds ago: " + pastTime);
    }

}
