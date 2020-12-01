package com.example.demo.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import com.example.demo.service.ICommonService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CommonService implements ICommonService {

    private static final Logger log = LoggerFactory.getLogger(CommonService.class);

    @Override
    public Map<String, Object> readLetters() {
        Map<String, Object> returnMap = new HashMap<String, Object>();

        File staticFolder = new File(
                Paths.get(System.getProperty("user.dir").toString()).resolve("Letters").toString());

        try {
            File[] files = staticFolder.listFiles();
            StringBuffer content = new StringBuffer();
            String line;

            if (files.length > 0) {
                for (File file : files) {
                    try (BufferedReader reader = new BufferedReader(
                            new InputStreamReader(new FileInputStream(file)));) {
                        while ((line = reader.readLine()) != null) {
                            content.append(line);
                            content.append(System.getProperty("line.separator")); // 換行
                        }
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    } finally {
                        System.out.println(content);
                    }
                }

            }

            returnMap.put("returnCode", "200");
            returnMap.put("content", content);
            return returnMap;
        } catch (Exception exception) {
            returnMap.put("returnCode", "400");
            returnMap.put("content", "No files found in static");
        }
        return returnMap;
    }

}
