package utils;

import com.github.javafaker.Faker;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;


import java.util.Properties;
@Getter
@Setter

public class Utils {

    public static void setEnvVariable(String key,String value) throws ConfigurationException {
        PropertiesConfiguration config = new PropertiesConfiguration("./src/test/resources/config.properties");
        config.setProperty(key,value);
        config.save();
    }
    private String name;
    private String email;


    public void generateRandomUser(){
        Faker faker = new Faker();
        setName(faker.name().fullName());
        setEmail(faker.internet().emailAddress());

    }

    public String generatePhoneNumber(){
        String prefix = "01700";
        int min = 100000;
        int max = 999999;
        return prefix+(int) Math.round(Math.random()*(max-min)+min);

    }


}
