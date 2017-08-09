package com.msobdev.model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by sob1 on 09.08.2017.
 * Counts number of links to domain on website
 */
public class Website {

    /** Domain with number of links*/
    private Map<String, Integer> domainCounter;
    /** Website name*/
    private String websiteName;
    /** Website links*/
    private List<String> linkList;

    public Website(String websiteName) {
        this.websiteName = websiteName;
        domainCounter = new HashMap<>();
        linkList = new ArrayList<>();
    }
    /**
     * Add number to link domain counter
     * @param domain Domain name
     * */
    public void addLink(String domain){
        /** Domain exists*/
        if(domainCounter.containsKey(domain)){
            int counter = domainCounter.get(domain);
            domainCounter.put(domain, (counter + 1));
        }else{
            domainCounter.put(domain, 1);
        }
    }

    /**
     * Connect to the website and get links
     * using Jsoup
     * */
    public void findHtmlLinks(){

        try {
            /** Get website html*/
            Document doc = Jsoup.parse(new URL(websiteName), 6000);
            /** Select a tags with http attribute having "http"*/
            Elements links = doc.select("a[href*=http]");
            /** Add http attributes to the linkList*/
            links.forEach(i -> linkList.add(i.attr("abs:href")));
        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Get domains and count links
     * */
    public void extractDomains(){
        String domain;

        findHtmlLinks();

        for(int i = 0; i < linkList.size(); i++){
            /** Exctract domain*/
            domain = extractDomain(linkList.get(i));
            /** Error control*/
            if((domain != "wrong url") && (domain != "same domain") && (domain != "wrong domain")){
                        addLink(domain);
            }
        }
    }

    /**
     * Get domain from url using regex
     * @param httpString Full url link
     * @return Domain or error
     * */
    public String extractDomain(String httpString){

        /** regex  - full http pattern. RFX 3986*/
        String pattern1 = "^(([^:/?#]+):)?(//([^/?#]*))?([^?#]*)(\\?([^#]*))?(#(.*))?";
        /** regex only domain pattern*/
        String pattern2 = "([a-zA-Z1-9]*[.]{1}[a-zA-Z1-9]*)$";

        Pattern p = Pattern.compile(pattern1);
        Matcher m = p.matcher(httpString);
        /** ex. www.product.oracle.com*/
        String relativeDomain;

        if(m.find()) {
            relativeDomain = m.group(4);
        }else{
            return "wrong url";
        }

        p = Pattern.compile(pattern2);
        m = p.matcher(relativeDomain);

        if(m.find()) {
            /** Ignore main domain*/
            if(websiteName.contains(m.group(1))) {
                return "same domain";
            }
            /** ex. oracle.com*/
            return m.group(1);
        }else{
            return "wrong domain";
        }

    }

    public String getWebsiteName() {
        return websiteName;
    }

    public String getLinkList() {
        AtomicInteger k = new AtomicInteger();
        String result = linkList.stream()
                .map(i -> k.incrementAndGet() +" "+ i)
                .collect(Collectors.joining("\n"));
        return result;
    }

    public void setWebsiteName(String websiteName) {
        this.websiteName = websiteName;
    }

    @Override
    public String toString() {
        String result = domainCounter.entrySet()
                .stream()
                .map(entry -> "- " + entry.getKey() + " - " + entry.getValue())
                .collect(Collectors.joining("\n"));
        return result;
    }
}
