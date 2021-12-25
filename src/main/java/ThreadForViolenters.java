import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThreadForViolenters implements Runnable{

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
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
        Map<String, Double> mapForAllViolators = new HashMap<>();
        Map<String, Integer> mapForCount = new HashMap<>();
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
                String fullName =
                        violent.getFirst_name() + " "+ violent.getLastName();

                if(mapForAllViolators.containsKey(fullName)) {
                    mapForAllViolators.computeIfPresent(fullName,
                            (k,v) -> violent.getFineAmount()+ v);
                }
                else{
                    mapForAllViolators.put(fullName, violent.getFineAmount());
                }
                if(mapForCount.containsKey(fullName))
                {
                    mapForCount.compute(fullName,
                            (k,v) -> v+1);
                }
                else{
                    mapForCount.put(fullName, 1);
                }
            }
        }
        Map<String, Double> mapForAverageFine = new HashMap<>();
        for (Map.Entry<String,Double> entry: mapForAllViolators.entrySet()) {
            for (Map.Entry<String, Integer> entry1: mapForCount.entrySet()) {
                if(entry.getKey() == entry1.getKey())
                {
                    mapForAverageFine.put(entry.getKey(), entry.getValue() / entry1.getValue());
                }
            }
        }
        try(BufferedWriter bufferedWriter = new BufferedWriter(
                new FileWriter("src/main/resources/listOfViolenters.txt")))
        {
            try {
                bufferedWriter.write("Name and average fine of violator \n");
            } catch (IOException e) {
                e.printStackTrace();
            }
            mapForAverageFine.forEach((k,v) -> {
                try {
                    bufferedWriter.write(k + " " + v +'\n');
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            try {
                bufferedWriter.write("Name and sum of all violations \n");
            } catch (IOException e) {
                e.printStackTrace();
            }
            mapForAllViolators.forEach((k,v) -> {
                try {
                    bufferedWriter.write(k + " " + v +'\n');
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            try {
                bufferedWriter.write("Name and quantity of violations \n");
            } catch (IOException e) {
                e.printStackTrace();
            }
            mapForCount.forEach((k,v) -> {
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
