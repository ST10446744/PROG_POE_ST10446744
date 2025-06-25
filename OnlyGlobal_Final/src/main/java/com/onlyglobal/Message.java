// Message.java
package com.onlyglobal;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Message {
    private String messageID;
    private static int messageCount = 0;
    private String recipient;
    private String message;
    private String hash;

    private static List<Message> sentMessages = new ArrayList<>();
    private static List<Message> disregardedMessages = new ArrayList<>();
    private static List<Message> storedMessages = new ArrayList<>();
    private static List<String> messageHashes = new ArrayList<>();
    private static List<String> messageIDs = new ArrayList<>();

    public Message(String messageID, String recipient, String message) {
        this.messageID = messageID;
        this.recipient = recipient;
        this.message = message;
        this.hash = createMessageHash();
        messageCount++;

        messageHashes.add(hash);
        messageIDs.add(messageID);
    }

    public boolean checkMessageID() {
        return messageID != null && messageID.length() == 10;
    }

    public boolean checkRecipientCell() {
        return recipient != null && recipient.matches("^\\+\\d{1,4}\\d{1,10}$");
    }

    public String createMessageHash() {
        String[] words = message.trim().split("\\s+");
        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 1 ? words[words.length - 1] : firstWord;
        return (messageID.substring(0, 2) + ":" + messageCount + ":" + firstWord + lastWord).toUpperCase();
    }

    public String sentMessage() {
        String[] options = {"Send Message", "Disregard Message", "Store Message to send later"};
        int choice = JOptionPane.showOptionDialog(null, "Choose an action", "Message Options",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        switch (choice) {
            case 0:
                sentMessages.add(this);
                return "Message sent";
            case 1:
                disregardedMessages.add(this);
                return "Message disregarded";
            case 2:
                storedMessages.add(this);
                return "Message stored for later";
            default:
                return "Invalid option";
        }
    }

    public static String printMessages() {
        StringBuilder sb = new StringBuilder();
        for (Message m : sentMessages) {
            sb.append("MessageID: ").append(m.messageID)
              .append("\nMessage Hash: ").append(m.hash)
              .append("\nRecipient: ").append(m.recipient)
              .append("\nMessage: ").append(m.message)
              .append("\n-------------------------\n");
        }
        return sb.length() == 0 ? "No sent messages." : sb.toString();
    }

    public static int returnTotalMessages() {
        return sentMessages.size();
    }

    public static void addSentMessage(Message msg) { sentMessages.add(msg); }
    public static void addDisregardedMessage(Message msg) { disregardedMessages.add(msg); }
    public static void addStoredMessage(Message msg) { storedMessages.add(msg); }
    public static void clearAllMessages() {
        sentMessages.clear();
        disregardedMessages.clear();
        storedMessages.clear();
        messageHashes.clear();
        messageIDs.clear();
        messageCount = 0;
    }

    public static List<Message> getSentMessages() { return sentMessages; }
    public static List<Message> getDisregardedMessages() { return disregardedMessages; }
    public static List<Message> getStoredMessages() { return storedMessages; }

    public String getMessageID() { return messageID; }
    public String getRecipient() { return recipient; }
    public String getMessage() { return message; }
    public String getHash() { return hash; }
}

