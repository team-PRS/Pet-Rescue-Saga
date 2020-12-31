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

    public String getLevelValue(Integer level, String NameValue)
    {
        String key = "L"+ level.toString() + "_" + NameValue;
        if (this.prop != null)
        {
            return this.prop.getProperty(key);
        }
        return null;
    }
}
