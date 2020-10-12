package Entities

/** DBConnection class handles all methods that interact with the database */

class DBConnection(username: String, password: String) {
  def uploadCSV(path: String): Unit ={
    val source = io.Source.fromFile(path)
    for (line <- source.getLines()){
      val cols = line.split(",").map(_.trim)
      // Upload columns to database
    }
  }
}

