# Boardgame-Database

Author: Sean Tidd

Date: 10/21/2020

Project: Project 0 for Revature

##Description
This is a board game collection manager that allows the user to upload CSV
files of their board games and interact with them on a Mongo database. The 
board game data entries have the following fields: title, hours, numPlayers,
and rank. The title is the name of the board game. The hours field is the average 
amount of time to play the game. The numPlayers is the maximum number of players.
Finally, the rank is the user's personally rank of the board game in their collection
with whole numbers closer to 1 being superior. 

##Notes
- When uploading a CSV file, the first row is automatically ignored because it is assumed
to be the header row
- Also during uploading, the complete path of the CSV file is required
- When using the "update" command, format the final value arguement like so for the regex
pattern identifier: =value
- This data base allows for duplicate board games because different editions of them exist
- The source code is well documented for any further technical questions