package Entities

import scala.io.StdIn
import scala.util.matching.Regex

/** CLUI class is the Command Line User Interface */

class CLUI {

  val commandLine: Regex = "(\\w+)\\s*(.*)".r
  val updateLine: Regex = "(\\w+)\\s+(\\w*)\\s+(\\w+)\\s+(.*)".r

  def printWelcome(): Unit ={
    println("Welcome to the Board Game Collection Interface")
  }

  def printOptions(): Unit ={
    println("_______________________________________OPTIONS______________________________________________________")
    println("upload [filename] : uploads a CSV file to the database")
    println("clear : empties the database")
    println("delete [board game name] : deletes a board game with the given name")
    println("search [board game name] : returns all boardgames with a similar name")
    println("update [field] [new value] [board game name] : updates the field of the board game with the given name")
    println("show : shows all the board games in the database")
    println("exit : exit from menu")
  }


  def menu(): Unit = {
    var continueMainLoop = true
    printWelcome()
    while(continueMainLoop){
      printOptions()
      StdIn.readLine() match {
        case commandLine(cmd, arg) if cmd.equalsIgnoreCase("upload") => {
          println(s"I just uploaded the CSV file called $arg")
        }
        case commandLine(cmd, arg) if cmd.equalsIgnoreCase("clear") => {
          println("Are you sure? Data can not be recovered once deleted")
          StdIn.readLine() match{
            case answer:String if answer.equalsIgnoreCase("yes") =>
              println("deleted all data from database")
            case answer:String if answer.equalsIgnoreCase("no") =>
          }

        }
        case commandLine(cmd, arg) if cmd.equalsIgnoreCase("delete") => {
          println(s"I just deleted a board game called $arg")
        }
        case commandLine(cmd, arg) if cmd.equalsIgnoreCase("search") => {
          println(s"I just searched for board games similar to: $arg")
        }
        case updateLine(cmd, field, value, arg) if cmd.equalsIgnoreCase("update") => {
          println(s"I just updated the $field field to $value for the game called $arg")
        }
        case commandLine(cmd, arg) if cmd.equalsIgnoreCase("show") => {
          println(s"I just showed all the board games in the database")
        }
        case commandLine(cmd, arg) if cmd.equalsIgnoreCase("exit") =>
          continueMainLoop = false
      }
    }

  }


}
