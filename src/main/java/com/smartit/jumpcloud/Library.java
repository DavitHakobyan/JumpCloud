package com.smartit.jumpcloud;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static javax.swing.UIManager.put;

public class Library {

    private static final Logger log = LoggerFactory.getLogger(Library.class);

    // action container map, where durations are kept in the list
    final HashMap<String, ArrayList<Integer>> counter;

    public Library() {
        this.counter = new HashMap<>();
    }

    /**
     * add action to the library
     * @param jsonString String
     */
    public synchronized void addAction(String jsonString) {
        if (jsonString == null || jsonString.isEmpty())
            return;

        // convert the json string into a ActionTime bean
        ObjectMapper objectMapper = new ObjectMapper();
        ActionTime actionTime = null;
        try {
            actionTime = objectMapper.readValue(jsonString, ActionTime.class);
        } catch (IOException e) {
            log.error("Error parsing input string: ", e);
        }

        // if the counter contains action
        if (counter.containsKey(actionTime.getAction())) {
            // append another duration to the counter list
            counter.get(actionTime.getAction()).add(actionTime.getTime());
        } else {
            // add the new action name and initialize the array with first time
            ArrayList<Integer> list = new ArrayList<>();
            list.add(actionTime.getTime());
            counter.put(actionTime.getAction(), list);
        }
    }

    /**
     * get action average time
     * @return String
     */
    public synchronized String getStats() {
        if (this.counter.isEmpty())
            return "";

        // calculate the average for each action and save in the arrayList
        ArrayList<ActionAvg> avgs = new ArrayList<>();
        this.counter.forEach((action, list) -> {
            int count = 0;
            for (Integer val : list) {
                count += val;
            }
            avgs.add(new ActionAvg(action, count / list.size()));
        });

        // parse the arrayList into a json string
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(avgs);
        } catch (JsonProcessingException e) {
            log.error("Error converting the list of objects to json string: ", e);
        }
        return null;
    }

    public static void main(String[] args) {
        // input parameters
        String json1 = "{\"action\":\"jump\",\"time\":100}";
        String json2 = "{\"action\":\"run\",\"time\":75}";
        String json3 = "{\"action\":\"jump\",\"time\":200}";

        // add json action string to the library
        Library library = new Library();
        library.addAction(json1);
        library.addAction(json2);
        library.addAction(json3);

        // get the Status and print the values
        String stats = library.getStats();
        System.out.printf("stats: %s", stats);
    }
}
