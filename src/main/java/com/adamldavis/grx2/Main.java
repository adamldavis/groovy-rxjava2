package com.adamldavis.grx2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {

    public static final String VERSION = "1.0-alpha1";
    public static final String GRADLE_BUILD = "plugins {\n" +
            "    id \"groovy\"\n" +
            "    id \"application\"\n" +
            "}\n" +
            "repositories {\n" +
            "    jcenter()\n" +
            "}\n" +
            "dependencies {\n" +
            "    compile \"com.adamldavis:groovy-rxjava2:" + VERSION + "\"\n" +
            "    testCompile \"org.spockframework:spock-core:1.3-groovy-2.5\"\n" +
            "}";

    public static void main(String[] args) {
        if (args.length != 1 || args[0].equals("-h")) {
            System.out.println("Usage: groovy-rxjava2 [option]\n-v (version)\n-h (help)\n-i (inits a project)");
        }
        else if (args[0].equals("-v")) {
            System.out.println(VERSION);
        }
        else if (args[0].equals("-i")) {
            try (FileOutputStream out = new FileOutputStream("build.gradle");
                 PrintWriter pw = new PrintWriter(out)) {
                pw.write(GRADLE_BUILD);
                new File("src/main/groovy").mkdirs();
                new File("src/test/groovy").mkdirs();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
