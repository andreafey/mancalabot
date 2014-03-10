package mancala

import io.Source
import util.Random
import java.io.IOException

object Game {
    val rand = new Random
    def main(args: Array[String]): Unit = {
    	val baseURL = args(0)
        val id = args(1)
        var status = read(statusURL(id, baseURL))
        while (!gameOver(status)) {
//    		println("begin loop: " + status)
        	// wait for game to start or just ignore errors b/c why not
        	if (waiting(status) || error(status)) {
        		Thread.sleep(1000)
        		status = read(statusURL(id, baseURL))
        	}
        	else {
        		val ints:Array[Int] = status.split(" ").map(s => s.toInt)
        		val captMove = captureMove(ints.zipWithIndex)
        		captMove match {
        		    case Some(house) => status = moveNow(id, house, baseURL)
        		    case None => {
        		    	val mancMove = mancalaMove(ints.take(6).zipWithIndex)
        		    		status = mancMove match {
        		    			case None => read(moveURL(id, randomMove(ints), baseURL))
        		    			case Some(house) => read(moveURL(id, house, baseURL))
        		    	}
        		        
        		    }
        		}
        	}
//    		println("end loop: " + status)
    		// need this b/c opponent move is not immediate; we want to 
    		// start loop following opponent's move
    		Thread.sleep(1000)
//    		println("new end loop status: " + status)
        }
    }
    def read(url:String):String = {
        println("send: " + url)
        try {
            Source.fromURL(url).mkString
        } catch {
            case e: IOException => "error"
        }
    }
    // move pieces in this id's house
    def moveNow(id:String, house:Int, url:String):String = read(moveURL(id, house, url))
	//$baseURL/status?player_id=YOURPLAYERID
    def statusURL(id:String, url:String):String = url + "/status?player_id=" + id
	//$baseURL/move?player_id=YOURPLAYERID&house=YOURMOVEHERE
    def moveURL(id:String, house:Int, url:String) = url + "/move?player_id=" + id + "&house=" + house.toString

    // STATUS OPTIONS
    def waiting(status:String):Boolean = "waiting".equals(status)
    def error(status:String):Boolean = "error".equals(status)
    def gameOver(status:String):Boolean =
        // this first case is what we expect when the game is over
        if ("win".equals(status) || "lose".equals(status) || "tie".equals(status)) {
            println("game over!")
            true
        }  
    	// this second case is an kluge to handle cases where the game is 
        // over but the response is unexpected
        else if (statusIndicatesOver(status)) {
    	    println("game should be over; server didn't figure that out")
    		true
    	}
        else false
    def statusIndicatesOver(status:String):Boolean = status.contains("0 0 0 0 0 0")
    
    def houseEmpty(house:List[Char]):Boolean =
        (house.foldLeft(0)((acc, c) => acc + c.asDigit)) == 0
    // random strategy
    def randomMove(ints:Array[Int]):Int = {
        // only consider first 6 houses with seeds in the house
        val intsWithIndex:Array[(Int,Int)] = 
            ints.take(6).zipWithIndex.filter(tp => tp._1 != 0)
        println("lows: " + ints.slice(0, 6).mkString(" ") + " => " + ints(6).toString)
        println("highs: " + ints(13).toString + " <= "+ ints.slice(7, 13).mkString(" "))
        // randomly select one of non-empty houses and return its index
        intsWithIndex(rand.nextInt(intsWithIndex.length))._2
    }
    
    // try to put seeds in mancala; arr is a zipWithIndexed Array[count, index]
    def mancalaMove(arr:Array[(Int, Int)]):Option[Int] = {
    	arr.length match {
        	case 0 => None
        	case _ => {
        		val item = arr(arr.length - 1)
				if (6 - item._2 == item._1) {
					println("move again!")
					Some(item._2)
				}
				else mancalaMove(arr.slice(0, arr.length - 1))
        	}
    	}
    }
    
    def captureMove(arr:Array[(Int, Int)]):Option[Int] = {
		def oppositeCount(seeds:Int, index:Int):Option[(Int, Int)] = {
		    // only consider your own house to start from
		    if (index > 5) None
		    // only valid moves are where there are seeds
		    else if (seeds == 0) None
		    else {
		    	// the house you will land on if you move
		    	val last = lastHouse(seeds, index)
    			// either your mancala or your opponent's house
    			if (last > 5) None
    			// house is empty - capture something if there are seeds!
    			else if (last == index || arr(last)._1 == 0) {
    				val captchaSeeds = arr(oppositeHouse(last))._1
					if (captchaSeeds == 0) None
					// there are seeds to capture
					else Some((captchaSeeds, index))
    			}
		    	// house not empty
    			else None
		        
		    }
		}
    
		def lastHouse(seeds:Int, index:Int):Int = {
		    (seeds + index) % 12 // not 13, because 13 is opponent's mancala
		}
		def oppositeHouse(house:Int):Int = 12 - house
		// an array of all moves where capturing happens in the form (# pieces capture, house to move)
        val captcha:Array[(Int,Int)] = 
            (arr.map(item => oppositeCount(item._1, item._2))).flatten
        if (captcha.length == 0) None
        else {
            var max:Int = 0
            var i = -1
            for (item <- captcha) {
                if (item._1 > max) {
                    max = item._1
                    i = item._2
                }
            } 
            if (max > 0) {
                println("moving from " + i + " will capture " + max + " pieces")
                Some(i)
            }
            else None
        }
    }

}