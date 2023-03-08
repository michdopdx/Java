//package edu.pdx.cs410J.michdo;
//
//import java.io.PrintWriter;
//import java.io.Writer;
//import java.util.Map;
//
///**
// * Creates a dumper object which allows for writing text.
// * @author
// */
//
//public class TextDumper {
//  private final Writer writer;
//
//  /**
//   * TextDumper constructor taking in writer
//   * @param writer text dumper writer
//   */
//  public TextDumper(Writer writer) {
//    this.writer = writer;
//  }
//
//  /**
//   * Dumps the content of map
//   * @param dictionary the dictionary
//   */
//  public void dump(Map<String, String> dictionary) {
//    try (
//      PrintWriter pw = new PrintWriter(this.writer)
//    ){
//      for (Map.Entry<String, String> entry : dictionary.entrySet()) {
//        pw.println(entry.getKey() + " : " + entry.getValue());
//      }
//
//      pw.flush();
//    }
//  }
//}
