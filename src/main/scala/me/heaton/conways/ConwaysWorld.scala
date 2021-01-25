package me.heaton.conways

object ConwaysWorld {
  def apply(width: Int, length: Int, lives: Set[(Int, Int)]) = Generation(width, length, lives)

  implicit class GenerationWrap(generation: Generation) {

    import generation._

    def next = Generation(width, length, tick)

    private def tick = (for {
      x <- 0 to width
      y <- 0 to length if followRules(x, y)
    } yield (x, y)).toSet

    private def followRules(x: Int, y: Int) =
      liveNeighbours(x, y) == 3 || (isLive(x, y) && liveNeighbours(x, y) == 2)

    private def liveNeighbours(x: Int, y: Int) = lives.count(neighbours(x, y).contains)

    private def isLive(x: Int, y: Int) = lives.contains((x, y))

    private def neighbours(x: Int, y: Int) = (for {
      i <- -1 to 1
      j <- -1 to 1 if i != 0 || j != 0
    } yield (flipEdge(width, x + i), flipEdge(length, y + j))).toSet

    private def flipEdge(max: Int, c: Int) = (c + max) % max

  }

}
