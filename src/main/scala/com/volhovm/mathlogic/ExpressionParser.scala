package com.volhovm.mathlogic
import org.parboiled2._

class ExpressionParser(val input: ParserInput) extends Parser {

  // S = (A {"," A}* "|-" A ) | A

  // Grammar
  // A = B "->" A | B
  // B = C {"|" C}*
  // C = D {"&" D}*
  // D = Var | "!" D | "(" A ")"

  def derivationInputLine: Rule1[(List[Expr], Expr)]
  = rule { ((zeroOrMore(A).separatedBy(",") ~> ((a: Seq[Expr]) => a.toList)) ~ (spaces ~ "|-") ~ A) ~> ((a: List[Expr], b: Expr) => (a, b))}
  def inputLine: Rule1[Expr] = rule { A ~ EOI }
  private def A: Rule1[Expr] = rule { oneOrMore(B).separatedBy("->") ~> ((a: Seq[Expr]) => a.reduceRight(-->)) }
//  private def A: Rule1[Expr] = rule { B ~ zeroOrMore("->" ~ )}
  private def B: Rule1[Expr] = rule { C ~ zeroOrMore("|" ~ C ~> |||) }
  private def C: Rule1[Expr] = rule { D ~ zeroOrMore("&" ~ D ~> &&&) }
  private def D: Rule1[Expr] = rule { spaces ~ (variable | negate | parenth)}
  private def spaces: Rule0 = rule { zeroOrMore(" ") }
  private def variable: Rule1[Expr] = rule { capture(upper) ~> ((a: String) => Var(a)) }
  private def upper: Rule0 = rule { anyOf("ABCPYFGRLOEUIDHTNSQJKXMWVZ") }
  private def negate: Rule1[Expr] = rule { "!" ~ D ~> !! }
  private def parenth: Rule1[Expr] = rule { "(" ~ A ~ ")" }
}


