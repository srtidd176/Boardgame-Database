package Entities

import org.bson.types.ObjectId

case class BoardGame(_id: ObjectId, title: String, hours: Float, numPlayers: Int, rank: Int ){}

object BoardGame{
  def apply(title: String, hours: Float, numPlayers: Int, rank: Int): BoardGame = BoardGame(new ObjectId(), title, hours, numPlayers, rank)
}
