package de.berlin.htw.kbe.gruppe1.maumau;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * Hello world!
 *
 */
@SpringBootApplication(exclude= {SecurityAutoConfiguration.class})
public class App 
{
    public static void main( String[] args )
    {
    	SpringApplication.run(App.class, args);
    }
}
