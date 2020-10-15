package Entities
import org.mongodb.scala.{MongoClient, MongoCollection, Observable}
import org.mongodb.scala.bson.codecs.Macros._
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.bson.codecs.configuration.CodecRegistry
import org.mongodb.scala.model.Filters.{equal, exists, regex}
import org.mongodb.scala.model.Indexes.ascending
import org.mongodb.scala.model.Updates.set

import scala.concurrent.Await
import scala.concurrent.duration.{Duration, SECONDS}


/** DBConnection class handles all methods that interact with the database */

class DBConnection(mongoClient : MongoClient) {

  val codecRegistry: CodecRegistry = fromRegistries(fromProviders(classOf[BoardGame]), MongoClient.DEFAULT_CODEC_REGISTRY)
  val collection: MongoCollection[BoardGame] = mongoClient.getDatabase("boardgamesdb").withCodecRegistry(codecRegistry).getCollection("boardgames")

  /**
   * getResults allows a database command to be executed by waiting until termination
   * or 10 seconds. Whichever comes first.
   * @param obs : The query
   * @tparam T : The datatype
   * @return : A sequence
   */
  def getResults[T](obs: Observable[T]): Seq[T] = {
    Await.result(obs.toFuture(), Duration(10, SECONDS))
  }

  /**
   * printResults prints the result to the console
   * @param obs : The query
   * @tparam T :  The datatype
   */
  def printResults[T](obs: Observable[T]): Unit = {
    getResults(obs).foreach(println(_))
  }

  /**
   * Adds a new board game Document to the database
   * @param title : The title of the board game
   * @param hours : The average playtime in hours
   * @param numPlayers : The maximum number of players
   * @param rank : The user's rank for the board game
   */
  def addBoardGame(title: String, hours: Float, numPlayers: Int, rank: Int): Unit ={
    getResults(collection.insertOne(BoardGame(title, hours, numPlayers, rank)))
  }


  /**
   * Deletes the first Document that has the listed title from the database
   * @param title : The title of the board game to be deleted
   */
  def deleteBoardGame(title: String): Unit ={
    getResults(collection.deleteOne(equal("title", title)))
  }


  /**
   * Uploads the rows from a CSV as Documents to the database
   * @param path : The path to the file
   */
  def uploadCSV(path: String): Unit = {
    val source = io.Source.fromFile(path)

    var header = true
    for (line <- source.getLines()) {
      if (!header) {
        val cols = line.split(",")
        cols match {
          case Array(name, numPlayers, hours, rank) => addBoardGame(name, hours.toFloat, numPlayers.toInt, rank.toInt)
        }
      }
      header = false
    }
    source.close()
  }

  /**
   * Updates the field of the first Document with the given name
   * @param title : The title of the board game
   * @param field : The desired field to be updated
   * @param value : The new field value
   */
  def updateBoardGame[T](title: String, field: String, value: T): Unit = {
    getResults(collection.updateOne(equal("title", title), set(field, value)))
  }

  /**
   * Show all Documents that have a title similar to the one provided
   * in alphabetical order
   * @param title : The similar title to search by
   */
  def search(title: String): Unit = {
    printResults(collection.find(regex("title", title)).sort(ascending("title")))
  }


  /**
   * Shows all the board games on the database to the console according to rank
   */
  def showAll(): Unit = {
    printResults(collection.find(exists("title")).sort(ascending("rank")))
  }

  /**
   * Clears all Documents from the Collection
   */
  def clearDB(): Unit = {
    getResults(collection.deleteMany(exists("title")))
  }

  /**
   * Closes the connection to the Mongo client
   */
  def closeConnection(): Unit = {
    mongoClient.close()
  }

}

