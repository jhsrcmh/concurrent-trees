package com.googlecode.concurrenttrees.examples.shakespeare.util;

import com.googlecode.concurrenttrees.examples.shakespeare.BuildShakespeareRadixTree;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Utility methods used only by unit tests. Not especially efficient or robust.
 *
 * @author Niall Gallagher
 */
public class IOUtil {

    public static Set<String> loadWordsFromTextFileOnClasspath(String resourceName, boolean convertToLowerCase) {
        BufferedReader in = null;
        try {
            Set<String> results = new TreeSet<String>();
            InputStream is = BuildShakespeareRadixTree.class.getResourceAsStream(resourceName);
            if (is == null) {
                throw new IllegalStateException("File not found on classpath");
            }
            in = new BufferedReader(new InputStreamReader(is));
            String line;
            while (true) {
                line = in.readLine();
                if (line == null) {
                    break;
                }
                if (convertToLowerCase) {
                    line = line.toLowerCase();
                }
                results.addAll(Arrays.asList(line.split("\\W+", 0)));
            }
            results.remove("");
            return results;

        }
        catch (Exception e) {
            throw new IllegalStateException("Failed to load file from classpath: " + resourceName, e);
        }
        finally {
            if (in != null) {
                try {
                    in.close();
                }
                catch (Exception ignore) {
                    // Ignore
                }
            }
        }
    }

    public static String loadTextFileFromClasspath(String resourceName, boolean stripPunctuation, boolean stripLineBreaks, boolean convertToLowerCase) {
        BufferedReader in = null;
        try {
            StringBuilder sb = new StringBuilder();
            InputStream is = BuildShakespeareRadixTree.class.getResourceAsStream(resourceName);
            if (is == null) {
                throw new IllegalStateException("File not found on classpath");
            }
            in = new BufferedReader(new InputStreamReader(is));
            String line;
            final String lineBreak = System.getProperty("line.separator");
            while (true) {
                line = in.readLine();
                if (line == null) {
                    break;
                }
                if (stripPunctuation) {
                    line = line.replaceAll("[^\\w\\s]", "");
                    line = line.replaceAll("\\s+", " ");
                    line = line.trim();
                }
                if (convertToLowerCase) {
                    line = line.toLowerCase();
                }
                if (stripLineBreaks) {
                    if (line.equals("")) {
                        // Skip blank lines...
                        continue;
                    }
                    // Insert a space instead of line break...
                    sb.append(line);
                    sb.append(" ");
                }
                else {
                    sb.append(line);
                    sb.append(lineBreak);
                }
            }
            return sb.toString();

        }
        catch (Exception e) {
            throw new IllegalStateException("Failed to load file from classpath: " + resourceName, e);
        }
        finally {
            if (in != null) {
                try {
                    in.close();
                }
                catch (Exception ignore) {
                    // Ignore
                }
            }
        }
    }
}
