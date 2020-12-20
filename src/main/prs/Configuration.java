package prs;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class Configuration
{
    private Properties prop;

    public Configuration(String ConfigPath)
    {
        try
        {
            InputStream file = Files.newInputStream(Path.of(ConfigPath));
            this.prop = new Properties();
            this.prop.load(file);
        }
        catch (Exception e)
        {
            this.prop = null;
            System.out.println("Error: can't load file: " + ConfigPath);
        }
    }

    public String getValue (Integer level, String NameValue)
    {
        String Key = "L"+ level.toString() + "_" + NameValue;
        if (this.prop != null)
        {
            return this.prop.getProperty(Key);
        }
        return null;
    }


    public static void main (String[] args)
    {
        Configuration conf = new Configuration("/home/nata/Documents/Projets/Pet-Rescue-Saga/Pet_Rescue/Pet-Rescue-Saga/config.txt");
        System.out.println(conf.getValue(1, "additionalBlocs"));
    }
}
