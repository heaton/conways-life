package me.heaton.conways

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class ConwaysLife extends AnyWordSpec with Matchers {

  import ConwaysWorld._

  "Conways life" should {
    val emptyWorld = world55()

    "create a generation by the initial state" in {
      emptyWorld shouldEqual emptyWorld
    }

    "make a live cell die when no lives around" in {
      world55((1, 1)).next shouldEqual emptyWorld
    }

    "make a live cell die when only 1 live around" in {
      world55((1, 1), (0, 1)).next shouldEqual emptyWorld
      world55((1, 1), (0, 3), (2, 1)).next.lives should not contain(1, 1)
    }

    "make a live cell still alive when 2 lives around" in {
      world55((1, 1), (0, 1), (2, 1)).next.lives should contain((1, 1))
      world55((1, 1), (1, 0), (1, 2)).next.lives should contain((1, 1))
      world55((1, 1), (0, 0), (2, 2)).next.lives should contain((1, 1))
      world55((1, 1), (2, 0), (0, 2)).next.lives should contain((1, 1))
    }

    "make a live cell still alive when 3 lives around" in {
      world55((1, 1), (0, 0), (0, 2), (2, 0)).next.lives should contain((1, 1))
    }

    "keep a dead cell dead when only 2 lives around" in {
      world55((0, 0), (0, 2)).next shouldEqual world55()
    }

    "make a dead cell alive when 3 lives around" in {
      world55((0, 0), (0, 2), (2, 0)).next shouldEqual world55((1, 1))
      world55((2, 2), (1, 1), (2, 3), (3, 1)).next shouldEqual world55((2, 1), (2, 2), (1, 2), (3, 2))
    }

    "make a live cell dead when more than 3 lives around" in {
      world55((1, 1), (0, 0), (0, 2), (2, 0), (2, 2)).next.lives should not contain(1, 1)
    }

    "count other side if the cell is on the edge" in {
      world55((0, 2), (4, 1), (1, 3)).next shouldEqual world55((0, 2))
      world55((2, 0), (1, 1), (3, 4)).next shouldEqual world55((2, 0))
    }
  }

  private def world55(lives: (Int, Int)*) = ConwaysWorld(5, 5, lives.toSet)
}
