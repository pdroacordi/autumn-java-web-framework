package io.acordi.autumn.web.server;


import io.acordi.autumn.util.ClassPathScanner;
import io.acordi.autumn.util.Logger;

import java.util.List;

public class AutumnWebApplication {

    public static void run(Class<?> origin){
        Logger.showBanner();


        EmbeddedTomcat.start();
    }

}
