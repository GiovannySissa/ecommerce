package io.gs.ecommerce.suite

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks

trait TestSuite extends AnyFunSuite with ScalaCheckDrivenPropertyChecks
