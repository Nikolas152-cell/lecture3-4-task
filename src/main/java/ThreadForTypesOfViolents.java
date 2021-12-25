import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ThreadForTypesOfViolents implements Runnable{
    @Override
    public void run() {
        File path = new File("src/main/resources/traffic violents");
        File[] list = path.listFiles();
        String[] jsons = new String[list.length];
        for (int i = 0; i < list.length; i++) {
            File element = list[i];
            BufferedReader br
                    = null;
            try {
                br = new BufferedReader(new FileReader(element));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            String temp = "";
            jsons[i] = "";
            while(true)
            {
                try {
                    if (!((temp= br.readLine())!=null)) break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                jsons[i] += '\n'+(temp);
            }

        }
        File[] jsonsViolents = new File[jsons.length];
        for (int i = 0; i < jsons.length; i++) {
            File element = new File("src/main/resources/"+list[i].getName() +".json");
            try {
                element.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            jsonsViolents[i] = element;
            try(BufferedWriter bufferedWriter = new BufferedWriter(
                    new FileWriter(element.getAbsolutePath())
            ))
            {
                bufferedWriter.write(jsons[i]);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        Map<String, Double> sumTypeMap = new TreeMap<>(Collections.reverseOrder());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
        for (int i = 0; i < jsons.length; i++) {
            List<Violent> violentsList = null;
            try {
                violentsList = objectMapper.readValue(jsons[i],
                        new TypeReference<List<Violent>>() {
                        });
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            for (Violent violent: violentsList) {
                if(sumTypeMap.containsKey(violent.getType())) {
                    sumTypeMap.computeIfPresent(violent.getType(),
                            (k,v) -> violent.getFineAmount()+ v);
                }
                else{
                    sumTypeMap.put(violent.getType(), violent.getFineAmount());
                }
            }
        }
        try(BufferedWriter bufferedWriter = new BufferedWriter(
                new FileWriter("src/main/resources/statisticFileSumFireAmount.txt")))
        {
            sumTypeMap.forEach((k,v) -> {
                try {
                    bufferedWriter.write(k + " " + v +'\n');
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }catch (IOException e)
        {
            e.printStackTrace();
        }

    }
}
