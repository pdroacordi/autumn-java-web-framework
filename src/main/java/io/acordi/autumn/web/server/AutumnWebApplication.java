package io.acordi.autumn.web.server;


import io.acordi.autumn.core.context.ApplicationContext;
import io.acordi.autumn.core.scanner.ComponentScanner;
import io.acordi.autumn.core.scanner.ControllerScanner;
import io.acordi.autumn.util.Logger;

public class AutumnWebApplication {

    public static void run(Class<?> origin){
        Logger.showBanner();

        ApplicationContext context = ApplicationContext.getInstance();
        context.init(origin);

        EmbeddedTomcat.start();
    }

}
