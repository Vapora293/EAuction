package com.worwafi.others;

import java.io.*;

public class FileOperation {
    PrintWriter pw;
    public void change(File changing, String toChange) {
        // PrintWriter object for output.txt
            try {
                pw = new PrintWriter(changing);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        // BufferedReader object for input.txt
        BufferedReader br1 = null;
        try {
            br1 = new BufferedReader(new FileReader("input.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String line1 = null;
            try {
                line1 = br1.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        // loop for each line of input.txt
        while (line1 != null) {
            boolean flag = false;
            // BufferedReader object for delete.txt
            BufferedReader br2 = null;
            try {
                br2 = new BufferedReader(new FileReader("delete.txt"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            String line2 = null;
            try {
                line2 = br2.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // loop for each line of delete.txt
            while (line2 != null) {
                if (line1.equals(line2)) {
                    flag = true;
                    break;
                }
                try {
                    line2 = br2.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // if flag = false
            // write line of input.txt to output.txt
            if (!flag)
                pw.println(line1);
            try {
                line1 = br1.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        pw.flush();

        // closing resources
        try {
            br1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        pw.close();
        System.out.println("File operation performed successfully");
    }
}
