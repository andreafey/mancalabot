Quick and dirty Mancala bot with fairly simple strategy which can readily beat a player who
plays random moves only. Wrote for Hacker School to play against Robert Lord's Mancala web service
while having fun writing a simple Scala program.

To play:

You'll first need a player ID from the Mancala web service. 
     Visit $baseURL
     Copy ID from one player and click "Start Random Player" for the other

 Fire up sbt and run the program
     mancala [master #%] $ sbt
     [info] Set current project to mancala (in build file:/Users/andrea/workspace-scala/mancala/)
     > run $baseURL ZOQJCKIG
     [info] Compiling 1 Scala source to /Users/andrea/workspace-scala/mancala/target/scala-2.10/classes...
     [info] Running mancala.Game $baseURL ZOQJCKIG
     send: $baseURL/status?player_id=ZOQJCKIG
     move again!
     send: $baseURL/move?player_id=ZOQJCKIG&house=0
     moving from 5 will capture 6 pieces
     send: $baseURL/move?player_id=ZOQJCKIG&house=5
     lows: 0 7 7 7 7 0 => 2
     highs: 0 <= 7 7 7 7 7 7
     send: $baseURL/move?player_id=ZOQJCKIG&house=4
     move again!
     send: $baseURL/move?player_id=ZOQJCKIG&house=5
     lows: 1 7 7 7 0 0 => 4
     highs: 1 <= 0 10 9 9 9 8
     send: $baseURL/move?player_id=ZOQJCKIG&house=0
     lows: 0 8 7 7 0 0 => 4
     highs: 1 <= 0 10 9 9 9 8
     send: $baseURL/move?player_id=ZOQJCKIG&house=1
     moving from 0 will capture 9 pieces
     send: $baseURL/move?player_id=ZOQJCKIG&house=0
     move again!
     send: $baseURL/move?player_id=ZOQJCKIG&house=5
     send: $baseURL/status?player_id=ZOQJCKIG
     lows: 1 1 10 10 3 0 => 15
     highs: 8 <= 0 0 11 11 1 1
     send: $baseURL/move?player_id=ZOQJCKIG&house=0
     moving from 2 will capture 1 pieces
     send: $baseURL/move?player_id=ZOQJCKIG&house=2
     moving from 0 will capture 13 pieces
     send: $baseURL/move?player_id=ZOQJCKIG&house=0
     moving from 3 will capture 1 pieces
     send: $baseURL/move?player_id=ZOQJCKIG&house=3
     move again!
     send: $baseURL/move?player_id=ZOQJCKIG&house=1
     moving from 0 will capture 4 pieces
     send: $baseURL/move?player_id=ZOQJCKIG&house=0
     lows: 0 0 1 1 7 3 => 39
     highs: 12 <= 0 2 3 0 0 4
     send: $baseURL/move?player_id=ZOQJCKIG&house=4
     moving from 3 will capture 3 pieces
     send: $baseURL/move?player_id=ZOQJCKIG&house=3
     moving from 2 will capture 1 pieces
     send: $baseURL/move?player_id=ZOQJCKIG&house=2
     moving from 1 will capture 2 pieces
     send: $baseURL/move?player_id=ZOQJCKIG&house=1
     lows: 0 0 0 2 0 4 => 47
     highs: 15 <= 0 0 1 0 3 0
     send: $baseURL/move?player_id=ZOQJCKIG&house=5
     moving from 3 will capture 1 pieces
     send: $baseURL/move?player_id=ZOQJCKIG&house=3
     lows: 1 0 0 0 1 0 => 49
     highs: 16 <= 0 2 2 0 0 1
     send: $baseURL/move?player_id=ZOQJCKIG&house=0
     lows: 0 0 0 0 1 0 => 50
     highs: 17 <= 0 0 3 0 0 1
     send: $baseURL/move?player_id=ZOQJCKIG&house=4
     game should be over; server didn't figure that out
     Not interrupting system thread Thread[Keep-Alive-Timer,8,system]
     [success] Total time: 36 s, completed Mar 10, 2014 12:52:37 PM
     >