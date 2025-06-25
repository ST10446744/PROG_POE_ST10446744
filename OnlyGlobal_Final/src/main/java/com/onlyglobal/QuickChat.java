package com.onlyglobal;

import javax.swing.*;
import java.util.UUID;

public class QuickChat {
    public static void main(String[] args) {
        boolean loggedIn = true; // simulate login success
        if (!loggedIn) {
            System.out.println("You must log in first.");
            return;
        }

        // Preload test data for demo before starting
        TestDataLoader.preloadTestData();

        JOptionPane.showMessageDialog(null, "Welcome to QuickChat.");

        int messageLimit = Integer.parseInt(JOptionPane.showInputDialog("How many messages do you want to send?"));
        int messagesEntered = 0;

        while (true) {
            String[] options = {
                "Send Messages",
                "Show recently sent messages",
                "Open Message Report Page",
                "Quit"
            };

            int choice = JOptionPane.showOptionDialog(
                    null,
                    "Choose an option",
                    "Menu",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    options,
                    options[0]);

            switch (choice) {
                case 0:
                    if (messagesEntered >= messageLimit) {
                        JOptionPane.showMessageDialog(null, "Message limit reached.");
                        break;
                    }

                    String messageID = UUID.randomUUID().toString().replaceAll("[^0-9]", "");
                    if(messageID.length() > 10){
                        messageID = messageID.substring(0, 10);
                    }

                    String recipient = JOptionPane.showInputDialog("Enter recipient cell number (with international code):");
                    String message = JOptionPane.showInputDialog("Enter your message:");

                    if (message.length() > 250) {
                        JOptionPane.showMessageDialog(null, "Please enter a message of less than 250 characters.");
                        break;
                    }

                    Message msg = new Message(messageID, recipient, message);
                    if (!msg.checkMessageID()) {
                        JOptionPane.showMessageDialog(null, "Invalid message ID.");
                        break;
                    }

                    if (!msg.checkRecipientCell()) {
                        JOptionPane.showMessageDialog(null, "Invalid recipient number.");
                        break;
                    }

                    String result = msg.sentMessage();
                    if (result.equals("Message sent")) {
                        messagesEntered++;
                        JOptionPane.showMessageDialog(null,
                                "MessageID: " + messageID +
                                "\nMessage Hash: " + msg.createMessageHash() +
                                "\nRecipient: " + recipient +
                                "\nMessage: " + message);
                    } else {
                        JOptionPane.showMessageDialog(null, result);
                    }
                    break;

                case 1:
                    JOptionPane.showMessageDialog(null, Message.printMessages());
                    break;

                case 2:
                    SwingUtilities.invokeLater(() -> {
                        ReportPage.launchReportPage(null); // opens modal dialog properly
                    });
                    break;

                case 3:
                    JOptionPane.showMessageDialog(null, "Total Messages Sent: " + Message.returnTotalMessages());
                    System.exit(0);
                    break;

                default:
                    break;
            }
        }
    }
}


