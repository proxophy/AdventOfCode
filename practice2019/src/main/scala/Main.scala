import scala.io.Source

object Main {

  def main(args: Array[String]): Unit = {
    val ex = "1,9,10,3,2,3,11,0,99,30,40,50"
    val input = Source.fromFile(".//inputs//input2").getLines.toList
    val d = new DayTwo(input(0))
    println(d.task1)
  }

  def test(x: Int): Int = {
    x + 1
  }
}

trait Task {
  def task1(): String
  def task2(): String
}

class DayTwo(args: String) extends Task {
  var arr = {
    val splargs = args.split(",")
    splargs.map(_.toInt)
  }

  def run(): Unit = {
    var i: Int = 0
    var f = false
    while (i < arr.length && arr(i) != 99 && !f) {
      val op = arr(i)
      if (op == 1){
        arr(arr(i+3)) = arr(arr(i+1)) + arr(arr(i+2))
      } else if (op == 2){
        arr(arr(i+3)) = arr(arr(i+1)) * arr(arr(i+2))
      } else if(op == 99){
        f = true
      }

      i += 4
    }
  }

  def task1(): String = {
    arr(1) = 12
    arr(2) = 2
    run()
    "" + arr(0)
  }

  def task2(): String = ???
}

class DayOne(args: Array[String]) extends Task {
  var arr: Array[Int] = {
    args.map(_.toInt)
  }

  def task1(): String = {
    var sum: Int = 0
    for (el <- arr) {
      sum += el / 3 - 2
    }

    "" + sum
  }

  def task2(): String = {
    var sum: Int = 0
    for (el <- arr) {
      var curfuel = el / 3 - 2
      while (curfuel > 0) {
        sum += curfuel
        curfuel = curfuel / 3 - 2
      }
    }

    "" + sum
  }
}
