package com.resumeanalyzer.resume_analyzer;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;

import java.util.Scanner;

public class GeminiTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Client client = new Client();
        String consent = "y";
        String ask = "";
        while(consent.equals("y")){
            System.out.print("Ask anything: ");
            ask = sc.nextLine();
            long start = System.currentTimeMillis();
            GenerateContentResponse response =
                    client.models.generateContent(
                            "gemini-3.5-flash-lite",
                            ask,
                            null
                    );
            System.out.println(response.text());
            long end = System.currentTimeMillis();
            System.out.println("Time: " + (end - start) + " ms");
            System.out.println("Want to continue asking?(y/n)");
            consent = sc.nextLine();
        }


    }
}
