package Entities


/** DBConnection class handles all methods that interact with the database */

class DBConnection(username: String, password: String) {
  def uploadCSV(path: String): Unit = {
    val source = io.Source.fromFile(path)
    var header = true
    for (line <- source.getLines()) {
      if (!header) {
        val cols = line.split(",")
        cols match {
          case Array(name, numPlayers, hours, rank) => println(s"This board game is called $name, it is played with $numPlayers players for an average play time of $hours hours. It is ranked $rank")
        }
        // Upload columns to database
      }
      header = false
    }
  }
}
object CSVTest extends App{
  val uploadCSV = new DBConnection("df", "df")
  uploadCSV.uploadCSV("C:\\Users\\river\\IdeaProjects\\Tidd_Project0\\src\\main\\scala\\test.csv")
}

