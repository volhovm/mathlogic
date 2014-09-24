package com.volhovm.mathlogic

/**
 * @author volhovm
 *         Created on 9/10/14
 */

object ProofChecker {
  var curtime = System.currentTimeMillis()
  var timeList = List[String]()
  def memtime(a: String = "") = {
    timeList = (a + ": " + (System.currentTimeMillis() - curtime)) :: timeList
    curtime = System.currentTimeMillis()
  }
  def dumptime: Unit = {
    for (i <- timeList.reverse) println(i)
  }
  def main(args: Array[String]) {
    val time0 = System.currentTimeMillis()
    var maxlength = 30
    var lst: List[Expr] = List.empty[Expr]
//    val iterator = scala.io.Source.fromFile("simpletest.in").getLines()
    val iterator = scala.io.Source.fromFile("maxtest.in").getLines()
    var curr = ""
    while (iterator.hasNext){
      curr = iterator.next()
      if (curr.length > maxlength) maxlength = curr.length
      lst = new ExpressionParser(curr).inputLine.run().get :: lst
    }
    memtime("Reading + parsing")
    lst = lst.reverse
    memtime("Reversing")
    val list = Verificator.verificate(lst, maxlength)
    memtime("Verificating")
    list.reverse.foreach(println)
    memtime("Reversing + writing")
    dumptime
  }
}
