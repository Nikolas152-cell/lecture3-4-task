import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.*;
import java.util.*;


public class Main {
    public static void main(String[] args) throws IOException {
        ThreadForViolenters threadForViolenters = new ThreadForViolenters();
        Thread allViolenters = new Thread(threadForViolenters);
        allViolenters.start();
        ThreadForTypesOfViolents threadForTypesOfViolents = new ThreadForTypesOfViolents();
        Thread allTypes = new Thread(threadForTypesOfViolents);
        allTypes.start();
        /*File path = new File("src/main/resources/traffic violents");
        File[] list = path.listFiles();
        String[] jsons = new String[list.length];
        for (int i = 0; i < list.length; i++) {
            File element = list[i];
            BufferedReader br
                    = new BufferedReader(new FileReader(element));
            String temp;
            jsons[i] = "";
            while((temp= br.readLine())!=null)
            {
                jsons[i] += '\n'+(temp);
            }

        }
        File[] jsonsViolents = new File[jsons.length];
        for (int i = 0; i < jsons.length; i++) {
            File element = new File("src/main/resources/"+list[i].getName() +".json");
            element.createNewFile();
            jsonsViolents[i] = element;
            try(BufferedWriter bufferedWriter = new BufferedWriter(
                    new FileWriter(element.getAbsolutePath())
            ))
            {
                bufferedWriter.write(jsons[i]);
            }
        }

        Map<String, Double> sumTypeMap = new TreeMap<>(Collections.reverseOrder());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
        //all types of violations
        for (int i = 0; i < jsons.length; i++) {
            List<Violent> violentsList = objectMapper.readValue(jsons[i],
                    new TypeReference<List<Violent>>() {
                    });
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
        }
        //all violators
        //string[] for name, surname,
        Map<String, Double> mapForAllViolators = new HashMap<>();
        Map<String, Integer> mapForCount = new HashMap<>();
        for (int i = 0; i < jsons.length; i++) {
            List<Violent> violentsList = objectMapper.readValue(jsons[i],
                    new TypeReference<List<Violent>>() {
                    });

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
        }*/
    }




}
