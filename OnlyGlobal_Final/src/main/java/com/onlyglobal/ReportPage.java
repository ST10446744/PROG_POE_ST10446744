

// ReportPage.java
package com.onlyglobal;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;

public class ReportPage extends JDialog {
    public ReportPage(Frame owner) {
        super(owner, "OnlyGlobal - Message Report", true);
        setSize(600, 500);
        setLayout(new BorderLayout());

        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(outputArea);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        JButton btnSenders = new JButton("\uD83D\uDCCE View Senders & Recipients");
        JButton btnLongest = new JButton("\uD83D\uDCCF Longest Sent Message");
        JButton btnSearchID = new JButton("\uD83D\uDD0D Search by Message ID");
        JButton btnSearchRecipient = new JButton("\uD83D\uDCEC Search by Recipient");
        JButton btnDeleteHash = new JButton("\u274C Delete by Hash");
        JButton btnFullReport = new JButton("\uD83D\uDCCB Full Sent Message Report");
        JButton btnDisregarded = new JButton("\uD83D\uDEAB View Disregarded Messages");

        buttonPanel.add(btnSenders);
        buttonPanel.add(btnLongest);
        buttonPanel.add(btnSearchID);
        buttonPanel.add(btnSearchRecipient);
        buttonPanel.add(btnDeleteHash);
        buttonPanel.add(btnFullReport);
        buttonPanel.add(btnDisregarded);

        add(buttonPanel, BorderLayout.NORTH);

        btnSenders.addActionListener(e -> {
            outputArea.setText("");
            for (Message m : Message.getSentMessages()) {
                outputArea.append("Sender: You, Recipient: " + m.getRecipient() + "\n");
            }
            if (Message.getSentMessages().isEmpty()) {
                outputArea.setText("No sent messages found.");
            }
        });

        btnLongest.addActionListener(e -> {
            outputArea.setText("");
            Message longest = null;
            for (Message m : Message.getSentMessages()) {
                if (longest == null || m.getMessage().length() > longest.getMessage().length()) {
                    longest = m;
                }
            }
            if (longest != null) {
                outputArea.setText("Longest Message:\nTo: " + longest.getRecipient() +
                        "\nMessage: " + longest.getMessage());
            } else {
                outputArea.setText("No sent messages found.");
            }
        });

        btnSearchID.addActionListener(e -> {
            String searchID = JOptionPane.showInputDialog(this, "Enter Message ID:");
            outputArea.setText("");
            boolean found = false;
            if (searchID != null) {
                for (Message m : Message.getSentMessages()) {
                    if (m.getMessageID().equals(searchID)) {
                        outputArea.setText("Recipient: " + m.getRecipient() +
                                "\nMessage: " + m.getMessage());
                        found = true;
                        break;
                    }
                }
            }
            if (!found) {
                outputArea.setText("Message ID not found.");
            }
        });

        btnSearchRecipient.addActionListener(e -> {
            String recipient = JOptionPane.showInputDialog(this, "Enter Recipient Number (+code):");
            outputArea.setText("");
            boolean found = false;
            if (recipient != null) {
                for (Message m : Message.getSentMessages()) {
                    if (m.getRecipient().equals(recipient)) {
                        outputArea.append("Message: " + m.getMessage() + "\n\n");
                        found = true;
                    }
                }
            }
            if (!found) {
                outputArea.setText("No messages found for recipient.");
            }
        });

        btnDeleteHash.addActionListener(e -> {
            String hash = JOptionPane.showInputDialog(this, "Enter Message Hash to Delete:");
            outputArea.setText("");
            boolean deleted = false;
            if (hash != null) {
                Iterator<Message> iterator = Message.getSentMessages().iterator();
                while (iterator.hasNext()) {
                    Message m = iterator.next();
                    if (m.getHash().equals(hash)) {
                        iterator.remove();
                        outputArea.setText("Message deleted.");
                        deleted = true;
                        break;
                    }
                }
            }
            if (!deleted) {
                outputArea.setText("Hash not found.");
            }
        });

        btnFullReport.addActionListener(e -> {
            outputArea.setText("");
            if (Message.getSentMessages().isEmpty()) {
                outputArea.setText("No sent messages to display.");
                return;
            }
            for (Message m : Message.getSentMessages()) {
                outputArea.append("Message ID: " + m.getMessageID() +
                        "\nHash: " + m.getHash() +
                        "\nTo: " + m.getRecipient() +
                        "\nMessage: " + m.getMessage() +
                        "\n-------------------------\n");
            }
        });

        btnDisregarded.addActionListener(e -> {
            outputArea.setText("");
            java.util.List<Message> disregarded = Message.getDisregardedMessages();
            if (disregarded.isEmpty()) {
                outputArea.setText("No disregarded messages found.");
            } else {
                for (Message m : disregarded) {
                    outputArea.append("Message ID: " + m.getMessageID() +
                            "\nRecipient: " + m.getRecipient() +
                            "\nMessage: " + m.getMessage() +
                            "\n-------------------------\n");
                }
            }
        });

        setLocationRelativeTo(owner);
    }

    public static void launchReportPage(Frame owner) {
        ReportPage dialog = new ReportPage(owner);
        dialog.setVisible(true);
    }
}



