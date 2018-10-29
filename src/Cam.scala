package raycaster
import scala.math._
import java.awt.image._

class Cam(val character: Character, 
          val heightLoc: Double,
          val fov: Int, 
          val resWidth: Int, 
          val resHeight: Int,
          val renderLimit: Double) {
  // Color used to represent the floor and ceiling, currently black
  val floorColor = (0 << 16) | (0 << 8) | 0
  
  // Creates the limits used in converting distances to colors
  val distanceToColorLimits = {  
    var limits: Array[Double] = (0 to 255).toArray.map(x => 0.0)
    val limit: Double = renderLimit.toDouble / limits.length
    var counter: Int = 0
    for (c <- 0 until limits.length) {
      limits(c) = counter * limit
      counter += 1
    }
    limits.reverse
  }
  
  // Calculates the angle between two adjacent pixels
  val angleBetweenColumns: Double = fov.toDouble / resWidth
  
  // These precalculate pixel column angles
  val columnAnglesN: Array[Double] = columnAnglesCalc(270)
  val columnAnglesE: Array[Double] = columnAnglesCalc(0)
  val columnAnglesS: Array[Double] = columnAnglesCalc(90)
  val columnAnglesW: Array[Double] = columnAnglesCalc(180)

  // Colors to represent distances, from black to white
  val colors: Array[Int] = (0 to 255).toArray
  
  // Returns the pixel column angles for a given angle
  def columnAnglesCalc(a: Double): Array[Double] = {
    val leftAngle: Double = a - (fov / 2)
    val res: Array[Double] = Array.fill(resWidth)(0)
    for (i <- 0 until resWidth) {
      res(i) = leftAngle + (i * angleBetweenColumns) + ( angleBetweenColumns / 2 )
    }
    res.map(fixAngle(_))
  }
  
  // Returns tangent from degrees
  def tanD(a: Double): Double = {
    tan(a.toRadians)
  }
  
  // Return arctan from degrees
  def atanD(a: Double): Double = {
    atan(a).toDegrees
  }
  
  // Modifies the given BufferedImage to represent the current view
  def render(g: BufferedImage) = {
    val columnsDist:   Array[Double] = calcDistances
    val ceilingPixels: Array[Int] = columnsDist.map(this.ceilingPixels(_))
    val floorPixels:   Array[Int] = columnsDist.map(this.floorPixels(_))
    val pixelsDist:    Array[Array[Double]] = columnsDist.map(Array.fill(resHeight)(_))
    val pixels: Array[Array[Int]] = pixelsDist.map(_.map(distanceToColor(_)))
    for (i <- 0 until pixels.length) {
      for (c <- 0 until ceilingPixels(i)) {
        pixels(i)(c) = floorColor
      }
      for (f <- 0 until floorPixels(i)) {
        pixels(i)(resHeight - 1 - f) = floorColor
      }
    }
    for (x <- 0 until resWidth ; y <- 0 until resHeight) g.setRGB(x, y, pixels(x)(y))
  }
  
  // Converts a distance to a color
  def distanceToColor(d: Double): Int = {
    var value: Int = colors.head
    if (d < renderLimit) {
      value = colors(distanceToColorLimits.indexWhere(d >= _))
    }
    (value << 16) | (value << 8) | value
  }
  
  // Creates rays for each pixel column and returns 
  // an Array with the calculated distances
  def calcDistances: Array[Double] = {
    val rays: Array[Ray] = Array.ofDim(this.resWidth)
    val angles: Array[Double] = this.character.rot match {
      case 0  => columnAnglesE
      case 90 => columnAnglesS
      case 180 => columnAnglesW
      case 270 => columnAnglesN
    }
    for (i <- 0 until rays.length) {
      rays(i) = new Ray(this.character.loc, angles(i), character.scene)
    }
    rays.map(_.intersectionDistance)
  }
  
  // Fixes angles under 0 and over 360
  def fixAngle(a: Double): Double = {
    if (a < 0) return a +  360
    if (a == 360) return 0
    a
  }
  
  // Returns the amount of pixels from the bottom that are floor 
  def floorPixels(dist: Double): Int = { 
    val angle = atanD(this.heightLoc / dist)
    val ratio = ( fov / 2 - angle ) / fov
    (resHeight / 2 * ratio ).toInt
  }
  // Returns the amount of pixels from the bottom that are ceiling
  def ceilingPixels(dist: Double): Int = {
    val angle = atanD( (this.character.scene.ceilingHeight - this.heightLoc)/ dist)
    val ratio = ( fov / 2 - angle ) / fov
    (resHeight / 2 * ratio ).toInt
  }
}