package raycaster.tests
import java.io._
import org.scalatest._
import org.scalatest.FlatSpec
import org.scalatest.Matchers
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import raycaster._
import raycaster.io._
import org.scalautils.Equality._
import scala.math._

@RunWith(classOf[JUnitRunner])
class RayTest extends FlatSpec with Matchers{
  /*
  def tanD(a: Double): Double = {
    tan(a.toRadians)
  }
  
  val testFile: String = {
    "ceiling height = 2\n" +
    "field of view     = 90\n" +
    "resolution width  = 1\n" +
    "resolution height = 1\n" +
    "camera height     = 1\n" +
    "\n" + 
    "MAP =\n" +
    "XXX\n" +
    "XEX\n" +
    "XXX\n"
  }
  val sc: Scene = GridReader.readFile(testFile.lines)
  val grid = sc.grid
      
  "raycaster.Ray" should "have zero horizontal intersections with angle 0" in {
      val ray = new Ray(new Coords(1.5, 1.5), 0, grid)
      ray.checkHorizontalIntersection shouldBe None
      ray.checkHorizontalIntersection shouldBe None
  }
  "it" should "get right horizontal intersections with ortho values" in {
    val ray1 = new Ray(new Coords(1.5, 1.5), 0, grid)
    ray1.checkHorizontalIntersection shouldBe None
    ray1.checkHorizontalIntersection shouldBe None
      
    val ray2 = new Ray(new Coords(1.5, 1.5), 90, grid)
    ray2.checkHorizontalIntersection.get.x shouldBe 1.5
    ray2.checkHorizontalIntersection.get.y shouldBe 1.0
      
    val ray3 = new Ray(new Coords(1.5, 1.5), 180, grid)
    ray3.checkHorizontalIntersection shouldBe None
    ray3.checkHorizontalIntersection shouldBe None
      
    val ray4 = new Ray(new Coords(1.5, 1.5), 270, grid)
    ray4.checkHorizontalIntersection.get.x shouldBe 1.5
    ray4.checkHorizontalIntersection.get.y shouldBe 2.0
  }
  "it" should "have zero vertical intersections with angle 90" in {
    val ray = new Ray(new Coords(1.5, 1.5), 90, grid)
    ray.checkVerticalIntersection shouldBe None
    ray.checkVerticalIntersection shouldBe None
  }
  "it" should "get right vertical intersections with ortho values" in {
    val ray1 = new Ray(new Coords(1.5, 1.5), 0, grid)
    ray1.checkVerticalIntersection.get.x shouldBe 2.0
    ray1.checkVerticalIntersection.get.y shouldBe 1.5
      
    val ray2 = new Ray(new Coords(1.5, 1.5), 90, grid)
    ray2.checkVerticalIntersection shouldBe None
    ray2.checkVerticalIntersection shouldBe None
      
    val ray3 = new Ray(new Coords(1.5, 1.5), 180, grid)
    ray3.checkVerticalIntersection.get.x shouldBe 1.0
    ray3.checkVerticalIntersection.get.y shouldBe 1.5
      
    val ray4 = new Ray(new Coords(1.5, 1.5), 270, grid)
    ray4.checkVerticalIntersection shouldBe None
    ray4.checkVerticalIntersection shouldBe None
  }
    

  "it" should "calculate vert.int. angle 15" in {
    val a = new Ray(new Coords(1.5, 1.5), 15, grid)     
    val av = a.checkVerticalIntersection
    val x = av.get.x; val y = av.get.y
    x shouldBe 2.0
    y shouldBe 1.5 - 0.5 * tanD(15)
  }
  "it" should "calculate vert.int. angle 45" in {
    val a = new Ray(new Coords(1.5, 1.5), 45, grid)     
    val av = a.checkVerticalIntersection
    val x = av.get.x; val y = av.get.y
    x shouldBe 2.0
    y shouldBe 1.0
  }
  "it" should "calculate vert.int. angle 160" in {
    val a = new Ray(new Coords(1.5, 1.5), 160, grid)     
    val av = a.checkVerticalIntersection
    val x = av.get.x; val y = av.get.y
    x shouldBe 1.0
    y shouldBe 1.5 - 0.5 * (tanD(20))
  }
  "it" should "calculate vert.int. angle 190" in {
    val a = new Ray(new Coords(1.5, 1.5), 190, grid)     
    val av = a.checkVerticalIntersection
    val x = av.get.x; val y = av.get.y
    x shouldBe 1.0
    y shouldBe 1.5 + 0.5 * tanD(10)
  }
  "it" should "calculate vert.int. angle 330" in {
    val a = new Ray(new Coords(1.5, 1.5), 330, grid)     
    val av = a.checkVerticalIntersection
    val x = av.get.x; val y = av.get.y
    x shouldBe 2.0
    y shouldBe 1.5 + 0.5 * tanD(30)
  }
    
  "it" should "calculate hori.int. angle 80" in {
    val a = new Ray(new Coords(1.5, 1.5), 80, grid)     
    val av = a.checkHorizontalIntersection
    val x = av.get.x; val y = av.get.y
    x shouldBe 1.5 + 0.5 * tanD(10)
    y shouldBe 1.0
  }
  "it" should "calculate hori.int. angle 140" in {
    val a = new Ray(new Coords(1.5, 1.5), 140, grid)     
    val av = a.checkHorizontalIntersection
    val x = av.get.x; val y = av.get.y
    x shouldBe 1.5 - 0.5 * tanD(50)
    y shouldBe 1.0
  }
  "it" should "calculate hori.int. angle 230" in {
    val a = new Ray(new Coords(1.5, 1.5), 230, grid)     
    val av = a.checkHorizontalIntersection
    val x = av.get.x; val y = av.get.y
    x shouldBe 1.5 - 0.5 * tanD(40)
    y shouldBe 2.0
  }
  "it" should "calculate hori.int. angle 300" in {
    val a = new Ray(new Coords(1.5, 1.5), 300, grid)     
    val av = a.checkHorizontalIntersection
    val x = av.get.x; val y = av.get.y
    x shouldBe 1.5 + 0.5 * tanD(30)
    y shouldBe 2.0
  }

  "it" should "calc distances between points" in {
    val a = new Coords(4, 4)
    val b = new Coords(4, 8)
    val c = new Coords(5, 7)
    val d = new Coords(7, 6)
    a.distance(b) shouldBe 4.0
    a.distance(d) shouldBe b.distance(d)
    a.distance(c) should be < 3.163
    a.distance(c) should be > 3.162
    b.distance(c) should be < 1.415
    b.distance(c) should be > 1.414
  }
  * */
}