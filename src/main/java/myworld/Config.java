package myworld;


import myessentials.config.api.ConfigProperty;
import myessentials.config.api.ConfigTemplate;

public class Config extends ConfigTemplate {
    private static final String[] defaultEnabledProtHandlers = {"*"};

    public static final Config instance = new Config();

    /* ----- General Config ----- */
    public ConfigProperty<String> localization = new ConfigProperty<String>(
            "Localization", "General",
            "Localization file without file extension.\\nLoaded from config/MyWorld/localization/ first, then from the jar, then finally will fallback to en_US if needed.",
            "en_US");

    public ConfigProperty<String[]> enabledProtHandlers = new ConfigProperty<String[]>(
            "EnabledProtectionHandlers", "General",
            "A list of protection handlers to load. Use * to load all.",
            defaultEnabledProtHandlers);

    public boolean isProtHandlerEnabled(String name) {
        if (enabledProtHandlers.get()[0].equals("*")) {
            return true;
        }

        for (String n : enabledProtHandlers.get()) {
            if (n.equals(name)) {
                return true;
            }
        }

        return false;
    }
}
