package raycaster

class Scene(val ceilingHeight: Double, val grid: Array[Array[Boolean]]) {
  
  var player: Option[Character] = None
  
  // Return true if the grid has a wall
  // at the given CoordsGrid
  def hasWall(at: CoordsGrid): Boolean = {
    if ((grid.length - 1 < at.x ) ||
        (grid(0).length - 1 < at.y) ||
        (at.x < 0) ||
        (at.y < 0) ||
        (at.x > grid.length - 1) ||
        (at.y > grid(0).length - 1)) {
      true
    } else {
      this.grid(at.x)(at.y)
    }
  }
}