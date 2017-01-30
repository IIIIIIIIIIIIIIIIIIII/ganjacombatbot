package com.toliga.ganjacombatbot;

import java.io.*;

public class FileManager {
    private BufferedReader reader;
    private String fileName;

    public FileManager(String fileName) {
        this.fileName = System.getenv().get("HOME") + "DreamBot\\Scripts\\GanjaCombatBot\\" + fileName;
    }

    public void writeLineToFile(String data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false))) {
            writer.write(data + "\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readLineFromFile() {
        String line = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            line = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }

    public boolean isFileExists() {
        File file = new File(fileName);
        return file.exists();
    }
}
