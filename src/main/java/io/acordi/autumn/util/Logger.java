package io.acordi.autumn.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {

    private static final String GREEN  = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String WHITE  = "\u001B[37m";
    private static final String RED    = "\u001B[31m";
    private static final String RESET  = "\u001B[0m";
    private static final DateTimeFormatter CUSTOM_DATE = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void info(Class<?> clazz, String msg) {
        String now = LocalDateTime.now().format(CUSTOM_DATE);
        String path = clazz.getName();
        System.out.printf( GREEN+"[INFO] %15s"+WHITE+" %-50s: "+RESET+"%s\n", now, path, msg );
    }

    public static void warn(Class<?> clazz, String msg) {
        String now = LocalDateTime.now().format(CUSTOM_DATE);
        String path = clazz.getName();
        System.out.printf( YELLOW+"[WARNING] %15s"+WHITE+" %-50s: "+RESET+"%s\n", now, path, msg );
    }

    public static void error(Class<?> clazz, String msg) {
        String now = LocalDateTime.now().format(CUSTOM_DATE);
        String path = clazz.getName();
        System.out.printf( RED+"[ERROR] %15s"+WHITE+" %-50s: "+RESET+"%s\n", now, path, msg );
    }

    public static void showBanner(){
        System.out.println("__  __             ___    __  __  ______   __  __    __  ___    _   __         __  __");
        System.out.println("\\ \\ \\ \\           /   |  / / / / /_  __/  / / / /   /  |/  /   / | / /        / / / /");
        System.out.println(" \\ \\ \\ \\         / /| | / / / /   / /    / / / /   / /|_/ /   /  |/ /        / / / /      "+GREEN+"Autumn Web Framework for Java"+RESET);
        System.out.println(" / / / /        / ___ |/ /_/ /   / /    / /_/ /   / /  / /   / /|  /         \\ \\ \\ \\      "+GREEN+"For Educational Purposes only"+RESET);
        System.out.println("/_/ /_/        /_/  |_|\\____/   /_/     \\____/   /_/  /_/   /_/ |_/           \\_\\ \\_\\     "+GREEN+"by Pedro Acordi"+RESET);
        System.out.println("                                                                                     ");
    }

}
