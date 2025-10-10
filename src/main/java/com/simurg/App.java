package com.simurg;

import com.simurg.Parser.Parser;
import org.htmlunit.html.*;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
       // String url = "jdbc:sqlite:";
        try {
            Parser.collectItems("Семавик","42");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
