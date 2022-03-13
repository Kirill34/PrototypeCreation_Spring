package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tutor")
public class TutorController {

    @GetMapping("/a")
    public String getA()
    {
        return "a";
    }

    @GetMapping("/")
    public List<String> getPage() throws IOException {
        //List<String> lines = FileUtils.readLines(new File("website/index.html"), Charset.defaultCharset());
        //return lines;
        List<String> list = new ArrayList<>();
        try {
            RandomAccessFile file = new RandomAccessFile("static/index.html", "r");
            String str;
            while ((str = file.readLine()) != null) {
                //System.out.println(str);
                list.add(str);
            }
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
