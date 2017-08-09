package com.msobdev.model;

import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;

import static org.junit.Assert.*;

/**
 * Created by sob1 on 09.08.2017.
 */
public class WebsiteTest {

    private Website website;

    @Before
    public void setUp() throws Exception {
        website = new Website("https://www.oracle.com/");
    }

    @Test
    public void printDomains_ShouldReturnOneDomainWithOneNumber() throws Exception {
        website.addLink("domain1");
        assertEquals("- domain1 - 1", website.toString());
    }

    @Test
    public void printDomains_ShouldReturnOneDomainWithTwoNumber() throws Exception {
        website.addLink("domain1");
        website.addLink("domain1");
        assertEquals("- domain1 - 2", website.toString());
    }

    @Test
    public void printDomains_ShouldReturnEmpty() throws Exception {
        assertEquals("", website.toString());
    }

    @Test
    public void extractDomains_ShouldReturn4Domains() throws Exception {
        website.extractDomains();
        assertEquals("- google.com - 1\n" +
                "- mozaicreader.com - 3\n" +
                "- java.com - 1\n" +
                "- youtube.com - 1", website.toString());
    }

    @Test
    public void extractDomain_ShouldReturnSameDomain() throws Exception {
        assertEquals("same domain", website.extractDomain("http://product.oracle.com"));
    }

    
}