package myworld.handlers;

import myworld.Config;
import myworld.api.BaseHandler;

public abstract class AbstractHandler extends BaseHandler {
    public boolean enabled() {
        return Config.instance.isProtHandlerEnabled(getName());
    }
}
