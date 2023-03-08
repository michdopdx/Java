//package edu.pdx.cs410J.michdo;
//
//import edu.pdx.cs410J.ParserException;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.Reader;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
///**
// * Creates a text parser object which allows for parsing text.
// */
//public class TextParser {
//  private final Reader reader;
//
//  /**
//   * Text parser constructor taking in a reader
//   * @param reader text reader
//   */
//  public TextParser(Reader reader) {
//    this.reader = reader;
//  }
//
//  /**
//   * Parses dictionary from text
//   * @return A dictionary
//   * @throws ParserException throws when parsing error occurs
//   */
//  public Map<String, String> parse() throws ParserException {
//    Pattern pattern = Pattern.compile("(.*) : (.*)");
//
//    Map<String, String> map = new HashMap<>();
//
//    try (
//      BufferedReader br = new BufferedReader(this.reader)
//    ) {
//
//      for (String line = br.readLine(); line != null; line = br.readLine()) {
//        Matcher matcher = pattern.matcher(line);
//        if (!matcher.find()) {
//          throw new ParserException("Unexpected text: " + line);
//        }
//
//        String word = matcher.group(1);
//        String definition = matcher.group(2);
//
//        map.put(word, definition);
//      }
//
//    } catch (IOException e) {
//      throw new ParserException("While parsing dictionary", e);
//    }
//
//    return map;
//  }
//}
