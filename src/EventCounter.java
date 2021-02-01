import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Event Counter program
 *
 * @author Jeffrey Zhou
 */
public class EventCounter{
    /**
     * int variable to limit how many minutes in the past to keep track of counts
     */
    private final int upperBoundMinutes = 5;
    /**
     * LinkedHashMap variable to track the date and time to count
     * It maintains insertion order
     * Ex: {2021-01-31T11:38:11=1, 2021-01-31T11:38:12=1, 2021-01-31T11:38:13=1}
     */
    private final LinkedHashMap<LocalDateTime, Integer> dateTimeToCountMap = new LinkedHashMap<>();
    /**
     * LocalDateTime variable to track the current date and time
     */
    private LocalDateTime timeNow = LocalDateTime.now().withNano(0);

    /**
     * Method to signal an event and keep track of the count of events at the current time, excluding nanoseconds
     * This method will also remove any old entries in the map that are greater than 5 minutes in the past
     */
    public void signalEvent(){
        //Get current time, excluding nanoseconds
        timeNow = LocalDateTime.now().withNano(0);

        //Remove old entries in the map that are more than 5 minutes ago
        removeOldEntries();

        //If timeNow doesn't exist in the map, add date and time as key with value as 1 to the map
        //Else if timeNow exist in the map, increment existing value by 1
        dateTimeToCountMap.merge(timeNow, 1, Integer::sum);
    }

    /**
     * Method to get the count of events from the the user-specified time in the past until now
     * This method will also remove any old entries in the map that are more greater 5 minutes in the past
     * @param minutes - int value representing how many minutes in the past to retrieve the count of events
     * @param seconds - int value representing how many seconds in the past to retrieve the count of events
     * @return - The count of events from the the user-specified time in the past until now
     */
    public int getCountOfEvents(int minutes, int seconds){
        int count = 0;

        //Validate values of minutes and seconds
        //Catch and print the exception messages if there are any
        //and return count; which is 0 at this point
        try{
            validateFields(minutes, seconds);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return count;
        }

        //If dateTimeToCountMap is empty, return count; which is 0 at this point
        if(dateTimeToCountMap.isEmpty()){
            return count;
        }

        //Get current time, excluding nanoseconds
        timeNow = LocalDateTime.now().withNano(0);

        //Get the time in the past determined by the values of minutes and seconds
        LocalDateTime pastTime = timeNow.minusMinutes(minutes).minusSeconds(seconds);

        //Remove old entries in the map that are older than 5 minutes ago
        removeOldEntries();

        //Traverse the entry set of dateTimeToCountMap
        //If the key(date and time) of the current entry is equal or after pastTime
        //Add the value of the entry to count
        for(Map.Entry<LocalDateTime, Integer> entry : dateTimeToCountMap.entrySet()){
            if(entry.getKey().isEqual(pastTime) || entry.getKey().isAfter(pastTime)){
                count += entry.getValue();
            }
        }

        return count;
    }

    /**
     * Method to validate values of minutes and seconds
     * @param minutes - int value representing how many minutes in the past to retrieve the count of events
     * @param seconds - int value representing how many seconds in the past to retrieve the count of events
     * @throws Exception - Any exceptions that will occur with the values of minutes and seconds passed in
     */
    private void validateFields(int minutes, int seconds) throws Exception{
        //If user specifies 0 for both minutes and seconds
        //or if minutes or seconds are negative
        //Throw an error message
        if((minutes == 0 && seconds == 0) || minutes < 0 || seconds < 0){
            throw new Exception("ERROR: The values of minutes or seconds must be greater than 0. Both values cannot be a negative.");
        }

        //If value of minutes is greater than the upper bound minutes defined, 5
        //Throw an error message
        if(minutes > upperBoundMinutes){
            throw new Exception("ERROR: The value of minutes cannot be greater than " + upperBoundMinutes + ".");
        }

        int totalSeconds = (minutes * 60) + seconds;
        int upperBoundSeconds = upperBoundMinutes * 60;

        //If the total seconds of both minutes and seconds combined are greater than 300 seconds(5 minutes)
        //Throw an error message
        if(totalSeconds > upperBoundSeconds){
            throw new Exception("ERROR: The total value of minutes and seconds cannot be greater than " + upperBoundSeconds + " seconds.");
        }
    }

    /**
     * Method to remove any old entries in the map that are greater than 5 minutes in the past
     */
    private void removeOldEntries(){
        //Lambda expression which will traverse the entry set of timeToCountMap
        //and removes the entry from the map if the key(date and time) is older than 5 minutes in the past
        dateTimeToCountMap.entrySet().removeIf(entry -> entry.getKey().isBefore(timeNow.minusMinutes(upperBoundMinutes)));
    }

}
