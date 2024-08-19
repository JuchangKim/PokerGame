/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Poker_Game;

/**
 *
 * @author user
 */
import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class FileManager {
    private final String filePath;
   

    public FileManager() {
        // Construct the file path to the resources folder within the package folder
        this.filePath = "./resources/UserRecord.txt";
    }

    // Save a user's record (username and balance) to the file
    public void saveRecord(String username, int balance) {
        try (OutputStream os = new FileOutputStream(filePath, true);
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os))) {
            writer.write(username + ":" + balance);
            writer.newLine();
            System.out.println("Game data saved successfully. Username: " + username);
        } catch (IOException e) {
        }
    }

    // Load all records from the file into a LinkedList
    public List<String[]> loadRecords() {
        List<String[]> records = new LinkedList<>();
        try (InputStream is = new FileInputStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(":");
                records.add(data);
            }
        } catch (IOException e) {
        }
        return records;
    }

    // Find a user's record by username
    public String findRecord(String username) {
        List<String[]> records = loadRecords();
        for (String[] record : records) {
            if (record[0].equals(username)) {
                return "Username: " + record[0] + ", Balance: " + record[1];
            }
        }
        return "Record not found.";
    }
    
    
}