package com.example.demo.constants;

public class DockerComands {

    public static String DOCKER = "docker";
    public static String RM     = "rm";
    public static String RUN    = "run";
    public static String STOP   = "stop";
    public static String START  = "start";


    // Listar
    public static String IMAGES = "images";
    public static String PS = "ps";

    // Flags
    public static String ALL = "-a";
    public static String QUIET = "-q";
    public static String DETACHED = "-d";
    public static String ENVIRONMENT = "-e";
    public static String FORMAT = "--format";
    public static String INTERACTIVE = "-i";
    public static String NAME = "--name";
    public static String PUBLISH = "-p";
    public static String REMOVE = "--rm";
    public static String SIGNAL = " --signal";
    public static String TERMINAL = "-t";
    public static String TIMEOUT = " --timeout";
    public static String VOLUME = "-v";
    
    public static String CPUS = "--cpus";
    public static String MEMORY = "-m";

    // Formatos de retorno
    public static String FORMATO_JSON = "json";
    public static String FORMATO_IMAGE_1 = "{{.Containers}};{{.CreatedAt}};{{.CreatedSince}};{{.Digest}};{{.ID}};{{.Repository}};{{.SharedSize}};{{.Size}};{{.Tag}};{{.UniqueSize}}";
    public static String FORMATO_IMAGE_2 = "{{.Containers}}\\;{{.CreatedAt}}\\;{{.CreatedSince}}\\;{{.Digest}}\\;{{.ID}}\\;{{.Repository}}\\;{{.SharedSize}}\\;{{.Size}}\\;{{.Tag}}\\;{{.UniqueSize}}";
    public static String FORMATO_CONTAINER_1 = "{{.ID}}\\;{{.Image}}\\;{{.Names}}\\;{{.RunningFor}}\\;{{.State}}\\;{{.Status}}";
    public static String FORMATO_CONTAINER_2 = "{{.ID}}";

}