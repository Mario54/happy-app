package com.mariolamontagne.happy.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import android.content.Context;

public class RemindersJSONSerializer {
    
    private Context mContext;
    private String mFilename;
    
    public RemindersJSONSerializer(Context context, String filename) {
        mContext = context;
        mFilename = filename;
    }
    
    public ArrayList<Reminder> loadReminders() throws IOException {
        ArrayList<Reminder> reminders = new ArrayList<Reminder>();
        
        BufferedReader reader = null;
        try {
            InputStream in = mContext.openFileInput(mFilename);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }
            
            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
            for (int i = 0; i < array.length(); i++) {
                reminders.add(new Reminder(array.getJSONObject(i)));
            }
        } catch (Exception e) {
            // ignore
        } finally {
            if (reader != null) 
                reader.close();
        }
        
        return reminders;
    }
    
    public void saveReminders(ArrayList<Reminder> reminders) throws JSONException, IOException {
        JSONArray array = new JSONArray();

        for (Reminder reminder : reminders) {
            array.put(reminder.toJSON());
        }
        
        Writer writer = null;
        try {
            OutputStream output = mContext.openFileOutput(mFilename, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(output);
            writer.write(array.toString());
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

}
