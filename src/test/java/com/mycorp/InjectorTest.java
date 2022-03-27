package com.mycorp;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class InjectorTest {

    private final ByteArrayOutputStream output = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(output));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
    }

    @Test
    public void injectAC() {
        SomeBean sb = (new Injector().inject(new SomeBean()));
        sb.foo();
        String lineSeparator = System.getProperty("line.separator");
        Assert.assertEquals("A"+lineSeparator+"C"+lineSeparator,output.toString());
    }

    @Test
    public void injectBC() {
        Injector injector = new Injector();
        injector.setFilenameOfProperties("var2.properties");
        SomeBean sb = (injector.inject(new SomeBean()));
        sb.foo();
        String lineSeparator = System.getProperty("line.separator");
        Assert.assertEquals("B"+lineSeparator+"C"+lineSeparator,output.toString());
    }
}