package io.github.persey86;


import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by Anastasia on 19.07.2016.
 */
public class App {

    private static final HttpClient httpClient = HttpClientBuilder.create().build();

    public static void main(String... args) {
        System.out.println("Maven page test");
        App.mvnrepositoryTest();
        System.out.println();
        System.out.println("Google Page test");
        App.googleSearchTest();
    }

    private static HttpResponse getResponseByUrl(String url){
        try {
            HttpGet request = new HttpGet(url);
            HttpResponse response = httpClient.execute(request);
            return response;
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    private static void mvnrepositoryTest(){
        HttpResponse response = getResponseByUrl("https://mvnrepository.com/artifact/org.apache.httpcomponents/httpclient/4.5.2");
        if (response != null) {
            Header[] allHeaders = response.getAllHeaders();
            StatusLine statusLine = response.getStatusLine();
            System.out.println("Is http request successful: " + (statusLine.getStatusCode() == 200));

            HttpEntity entity = response.getEntity();
            try {
                String responseString = EntityUtils.toString(entity, "UTF-8");
                System.out.println("Is html page: " + responseString.contains("<!DOCTYPE html>"));
            } catch (IOException e) {
                System.out.println("IOException: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private static void googleSearchTest(){

        HttpResponse response = getResponseByUrl("https://www.google.com.ua/search?sclient=psy-ab&q=samsung+ukraine&oq=samsung+ukraine&gs_l=serp.3..0i71l4.0.0.0.14402.0.0.0.0.0.0.0.0..0.0....0...1c..64.psy-ab..0.0.0.NZGZetoR0MI&pbx=1&bav=on.2,or.r_cp.&bvm=bv.127521224,d.bGs&fp=47b070b57407517e&biw=195&bih=658&tch=1&ech=1&psi=33OOV-r7LsyS6ASo15GYDw.1468953566731.3");

        if (response != null) {
            HttpEntity entity = response.getEntity();
            Header[] allHeaders = response.getAllHeaders();
            StatusLine statusLine = response.getStatusLine();
            System.out.println("Is http request successful: " + (statusLine.getStatusCode() == 200));
            for (Header header : allHeaders) {
                if (header.getName().equals("Content-Type")){
                    System.out.println("Is Content-Type JSON: " + header.getValue().contains("json"));
                }
            }

            try {
                String responseString = EntityUtils.toString(entity, "UTF-8");
            } catch (IOException e) {
                System.out.println("IOException: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
