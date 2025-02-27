package io.acordi.autumn.web.server;


import io.acordi.autumn.core.scanner.ComponentScanner;
import io.acordi.autumn.core.scanner.ControllerScanner;
import io.acordi.autumn.util.ClassPathScanner;
import io.acordi.autumn.util.Logger;
import io.acordi.autumn.web.routing.RouteRegistry;

import java.util.List;

public class AutumnWebApplication {

    public static void run(Class<?> origin){
        Logger.showBanner();

        ControllerScanner.scanControllers(origin);

        EmbeddedTomcat.start();
    }

}
