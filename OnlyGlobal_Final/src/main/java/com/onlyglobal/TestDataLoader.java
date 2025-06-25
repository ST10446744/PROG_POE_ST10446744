package com.onlyglobal;

public class TestDataLoader {

    public static void preloadTestData() {
        // Clear any existing data if needed
        Message.clearAllMessages();

        // Add test message 1 (Sent)
        Message.addSentMessage(new Message("1000000001", "+27834557896", "Did you get the cake?"));
        
       
        Message.addSentMessage(new Message("1000000098", "+27662365821", "My car broken, help me"));
        
         Message.addSentMessage(new Message("1000000950", "+27845842020", "School is out, come fetch me"));

        // Add test message 2 (Stored)
        Message.addStoredMessage(new Message("1000000002", "+27838884567", "Where are you? You are late! I have asked you to be on time."));

        // Add test message 3 (Disregard)
        Message.addDisregardedMessage(new Message("1000000003", "+27834484567", "Yohoooo, I am at your gate."));

        // Add test message 4 (Sent)
        Message.addSentMessage(new Message("1000000004", "0838884567", "It is dinner time !"));

        // Add test message 5 (Stored)
        Message.addStoredMessage(new Message("1000000005", "+27838884567", "Ok, I am leaving without you."));
    }
}


