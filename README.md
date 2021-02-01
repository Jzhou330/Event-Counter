Project: EventCounter
Author: Jeffrey Zhou
Date: 2/1/2021

# EventCounter

 EventCounter is a Java class which helps to track the number of events that happened during a specified window of time. This project was developed with IntelliJ IDEA.

 The class supports two operations:
 1. Single event happened (e.g. a page was served)
 2. Request the number of events that happened over a user-specified amount of time until the current time. For this project, the upper bound will be 5 minutes and the lower bound will be 1 second. The idea of this is a rolling 5-minute counter.

 This program is only expected to keep track of counts and processing will be in memory only and that it will not persist data to longer term storage. Any counts that are greater than 5 minutes in the past will not counted towards the number of events requested by the user and will be deleted from memory.


### Class Variables
```java
	/**
     * int variable to limit how many minutes in the past to keep track of counts
     */
    private final int upperBoundMinutes = 5;
```

```java
    /**
     * LinkedHashMap variable to track the date and time to count
     * It maintains insertion order
     * Ex: {2021-01-31T11:38:11=1, 2021-01-31T11:38:12=1, 2021-01-31T11:38:13=1}
     */
	private final int upperBoundMinutes = 5;
```

```java
	/**
     * LocalDateTime variable to track the current date and time
     */
	private final int upperBoundMinutes = 5;
```

### Methods

#### Utility Methods
```java
    /**
     * Method to signal an event and keep track of the count of events at the current time, excluding nanoseconds
     * This method will also remove any old entries in the map that are greater than 5 minutes in the past
     */
    public void signalEvent(){
```

```java
    /**
     * Method to get the count of events from the the user-specified time in the past until now
     * This method will also remove any old entries in the map that are greater than 5 minutes in the past
     * @param minutes - int value representing how many minutes in the past to retrieve the count of events
     * @param seconds - int value representing how many seconds in the past to retrieve the count of events
     * @return - The count of events from the the user-specified time in the past until now
     */
    public int getCountOfEvents(int minutes, int seconds){
```


#### Helper Methods
```java
    /**
     * Method to validate values of minutes and seconds
     * @param minutes - int value representing how many minutes in the past to retrieve the count of events
     * @param seconds - int value representing how many seconds in the past to retrieve the count of events
     * @throws Exception - Any exceptions that will occur with the values of minutes and seconds passed in
     */
    private void validateFields(int minutes, int seconds) throws Exception{
```
See Exceptions later on.


```java
    /**
     * Method to remove any old entries in the map that are greater than 5 minutes in the past
     */
    private void removeOldEntries(){
```


### Sleeping Mechanism

To simulate passing of time in my tests, I used the following sleep methods:

1.)
```java
TimeUnit.SECONDS.sleep()
```
The following example will pause the program for 1 second:
```java
TimeUnit.SECONDS.sleep(1);
```

2.)
```java
TimeUnit.MINUTES.sleep()
```
The following example will pause the program for 1 minute:
```java
TimeUnit.MINUTES.sleep(1);
```


### Usage

```java
public static void main(String[] args) throws Exception{
    EventCounter ec = new EventCounter();
    ec.signalEvent();

    int count = ec.getCountOfEvents(5, 0);
    System.out.println("Before sleep count: " + count);

    TimeUnit.MINUTES.sleep(1);

    ec.signalEvent();
    count = ec.getCountOfEvents(5, 0);
    System.out.println("After sleep count: " + count);
}
```
Output:
```
Before sleep count: 1
After sleep count: 2
```


### Exceptions
There will be some exceptions that will be thrown if the users enter invalid values for the parameters, int minutes and int seconds, of getCountofEvents(). The following are some scenarios:

1.) Retrieving count of events when the values for minutes and seconds are 0.
```java
System.out.println("Count: " + ec.getCountOfEvents(0, 0) + "\n");
```
Output:
```
ERROR: The values of minutes or seconds must be greater than 0. Both values cannot be a negative.
Count: 0
```

2.) Retrieving count of events when minutes value is greater than 5.
```java
System.out.println("Count: " + ec.getCountOfEvents(6, 0) + "\n");
```
Output:
```
ERROR: The value of minutes cannot be greater than 5.
Count: 0
```

3.) Retrieving count of events when the values of minutes and seconds combined is greater than 5 minutes or 300 seconds.
```java
System.out.println("Count: " + ec.getCountOfEvents(5, 1) + "\n");
```
Output:
```
ERROR: The total value of minutes and seconds cannot be greater than 300 seconds.
Count: 0
```

4.) Retrieving count of events when the value of seconds is greater than 300 seconds.
```java
System.out.println("Count: " + ec.getCountOfEvents(0, 301) + "\n");
```
Output:
```
ERROR: The total value of minutes and seconds cannot be greater than 300 seconds.
Count: 0
```

5.) Retrieving count of events when the value of minutes is negative.
```java
System.out.println("Count: " + ec.getCountOfEvents(0, -1) + "\n");
```
Output:
```
ERROR: The values of minutes or seconds must be greater than 0. Both values cannot be a negative.
Count: 0
```

6.) Retrieving count of events when the value of minutes is negative.
```java
System.out.println("Count: " + ec.getCountOfEvents(-1, 0) + "\n");
```
Output:
```
ERROR: The values of minutes or seconds must be greater than 0. Both values cannot be a negative.
Count: 0
```

7.) Retrieving count of events when the values of minutes and seconds are negative.
```java
System.out.println("Count: " + ec.getCountOfEvents(-1, -1) + "\n");
```
Output:
```
ERROR: The values of minutes or seconds must be greater than 0. Both values cannot be a negative.
Count: 0
```

### Testing

1.) Main class - This class simulates how a user would use the EventCounter class. It will run through many different test cases and display the counts of events, exceptions(if any), and current and past times for analysis. TimeUnit.SECONDS.sleep() and TimeUnit.MINUTES.sleep() will be used to simulate the passing of time. The total run time is around 11 minutes.

2.) EventCounterTest class - This test class that handles unit testing with assertions of count of events in test methods. TimeUnit.SECONDS.sleep() and TimeUnit.MINUTES.sleep() will be used to simulate the passing of time. The total run time is around 7.5 minutes.
