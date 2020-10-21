package Entities

import java.io.FileNotFoundException

import scala.io.StdIn
import scala.util.matching.Regex

/** CLUI class is the Command Line User Interface */

class CLUI(db : DBConnection) {

  val commandLine: Regex = "(\\w+)\\s*(.*)".r
  val updateLine: Regex = "^(update)\\s+(\\w*.*)(\\s(\\w*.*)+\\s(\\w*.*))$".r
  val addLine : Regex = "^(add)+\\s+(\\w*.*)\\s+((\\d*\\.?\\d*)\\s+(\\d*)\\s+(\\d*)$)".r
  val unknownLine : Regex  = "(\\w*.*)".r

  def printWelcome(): Unit = {
    println("Welcome to the Board Game Collection Interface")
  }

  def printOptions(): Unit ={
    println("_______________________________________OPTIONS______________________________________________________")
    println("upload [filename] : uploads a CSV file to the database")
    println("clear : empties the database")
    println("add [board game name] [hours] [numPlayers] [rank] : adds a new board game with the given parameters")
    println("delete [board game name] : deletes a board game with the given name")
    println("search [board game name] : returns all boardgames with a similar name")
    println("update [board game name] [field] [new value] : updates the field of the board game with the given name")
    println("show : shows all the board games in the database")
    println("exit : exit from menu")
    println("____________________________________________________________________________________________________")
  }


  def menu(): Unit = {
    var continueMainLoop = true
    printWelcome()
    var isEmpty=false
    while(continueMainLoop){
      if(isEmpty==false){
        printOptions()
      }
      else{
        isEmpty = false
      }
      try {
        StdIn.readLine() match {
          case commandLine(cmd, arg) if cmd.equalsIgnoreCase("upload") => {
            try {
              db.uploadCSV(arg)
              println(s"I just uploaded the CSV file called $arg")
            } catch {
              case e: FileNotFoundException => println(s"'$arg' file not found")
            }
          }
          case commandLine(cmd, arg) if cmd.equalsIgnoreCase("clear") => {
            println("Are you sure? Data can not be recovered once deleted")
            StdIn.readLine() match {
              case answer: String if answer.equalsIgnoreCase("yes") =>
                db.clearDB()
                println("deleted all data from database")
              case answer: String if answer.equalsIgnoreCase("no") =>
              case answer: String => println(s"${answer} is not a valid answer. Aborting ")
            }
          }

          case addLine(cmd, title, group3, hours, numPlayers, rank) if cmd.equalsIgnoreCase("add") => {
            db.addBoardGame(title, hours.toFloat, numPlayers.toInt, rank.toInt)
            println(s"I just added a new board game called $title")
          }

          case commandLine(cmd, arg) if cmd.equalsIgnoreCase("delete") => {
            db.deleteBoardGame(arg)
            println(s"I just deleted a board game called $arg")
          }
          case commandLine(cmd, arg) if cmd.equalsIgnoreCase("search") => {
            db.search(arg)
            println(s"I just searched for board games similar to: $arg")
          }
          case updateLine(cmd, title, group3, fieldVal, value) if cmd.equalsIgnoreCase("update") => {
            val field = group3.split(" ")(1)
            if (field == "hours") {
              db.updateBoardGame(title, field, value.toFloat)
            }
            else if (field != "title") {
              db.updateBoardGame(title, field, value.toInt)
            }
            else {
              db.updateBoardGame(title, field, value)
            }
            println(s"I just updated the ${field} field to $value for the game called $title")
          }
          case commandLine(cmd, arg) if cmd.equalsIgnoreCase("show") => {
            db.showAll()
            println(s"I just showed all the board games in the database")
          }
          case commandLine(cmd, arg) if cmd.equalsIgnoreCase("exit") =>
            db.closeConnection()
            continueMainLoop = false

          case "" => {
            isEmpty = true
          }

          case unknownLine(unknown) => {
            println(s" '${unknown}' is not a valid command")
          }
        }
      }catch{
        case e:ArrayIndexOutOfBoundsException => println("You used too many arguements")
      }
    }

  }


}
