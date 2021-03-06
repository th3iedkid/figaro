/*
 * StructuredVE.scala
 * A structured variable elimination algorithm.
 *
 * Created By:      Avi Pfeffer (apfeffer@cra.com)
 * Creation Date:   March 1, 2015
 *
 * Copyright 2015 Avrom J. Pfeffer and Charles River Analytics, Inc.
 * See http://www.cra.com or email figaro@cra.com for information.
 *
 * See http://www.github.com/p2t2/figaro for a copy of the software license.
 */

package com.cra.figaro.algorithm.structured.algorithm.structured

import com.cra.figaro.language._
import com.cra.figaro.algorithm.structured._
import com.cra.figaro.algorithm.structured.strategy._
import com.cra.figaro.algorithm.structured.solver._
import com.cra.figaro.algorithm.structured.strategy.solve.ConstantStrategy
import com.cra.figaro.algorithm.structured.algorithm._
import com.cra.figaro.algorithm.structured.strategy.decompose._
import com.cra.figaro.algorithm.factored.factors.factory._
import com.cra.figaro.algorithm.factored.factors.MaxProductSemiring


class StructuredMPEVE(universe: Universe) extends StructuredMPEAlgorithm(universe) {

  val semiring = MaxProductSemiring()

  def run() {    
    val strategy = DecompositionStrategy.recursiveStructuredStrategy(problem, new ConstantStrategy(mpeVariableElimination), defaultRangeSizer, Lower, false)
    strategy.execute(initialComponents)    
  }
}

object StructuredMPEVE {
  /** Create a structured variable elimination algorithm with the given query targets. */
  def apply()(implicit universe: Universe) = {        
    new StructuredMPEVE(universe)
  }

  /**
   * Use VE to compute the probability that the given element satisfies the given predicate.
   */
  def mostLikelyValue[T](target: Element[T]): T = {
    val alg = new StructuredMPEVE(target.universe)
    alg.start()
    val result = alg.mostLikelyValue(target)
    alg.kill()
    result
  }
}
