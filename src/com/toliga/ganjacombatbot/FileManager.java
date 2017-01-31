package com.toliga.ganjacombatbot;

import java.io.*;

public class FileManager {
    private BufferedReader reader;
    private BufferedWriter writer;
    private String fileName;

    public FileManager(String fileName) {
        this.fileName = System.getenv().get("HOME") + "DreamBot\\Scripts\\GanjaCombatBot\\" + fileName;
    }

    public void writeLineToFile(String data) {
        try {
            writer.write(data + "\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readLineFromFile() {
        String line = null;
        try {
            line = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }

    public void openWriter() {
        try {
            writer = new BufferedWriter(new FileWriter(fileName, false));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openReader() {
        try {
            reader = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void closeReader() {
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeWriter() {
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isFileExists() {
        File file = new File(fileName);
        return file.exists();
    }
}
