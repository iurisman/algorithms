package igorurisman.algorithms

import java.util.concurrent.ForkJoinPool
import java.util.concurrent.TimeUnit.*
import java.util.concurrent.atomic.AtomicInteger
import scala.concurrent.{ExecutionContext, Future}
object ReadWriteLocks extends App {

  private val pool = new ForkJoinPool(20)
  private implicit val ec: ExecutionContext = ExecutionContext.fromExecutor(pool)
  private val threads = 2
  private val readers = new AtomicInteger(0)
  private val writers = new AtomicInteger(0)

  private def async(bloc: => Unit): Unit = {
    new Thread {
      override def run(): Unit = bloc
    }.start()
  }

  for (i <- 1 to threads) async {
    val rl = Locker.getReadLock()
    println(s"Reader $i " + readers.incrementAndGet())
    rl.release()
  }

  for (i <- 1 to threads) async {
    val wl = Locker.getWriteLock()
    println(s"Writer $i " + writers.incrementAndGet())
    wl.release()
  }

  //pool.shutdown();
  //pool.awaitTermination(20, SECONDS)
  Thread.sleep(2000)
  println(s"Readers: ${readers.get()}, Writers: ${writers.get()}")
}

trait Lock {
  def release(): Unit
}
trait ReadLock extends Lock
trait WriteLock extends Lock

object Locker {

  private val locker = AnyRef
  @volatile private var activeWriter = false
  @volatile private var activeReaders = 0

  def getReadLock(): ReadLock = locker.synchronized {
    println("? read")
    while (activeWriter) locker.wait()
    activeReaders += 1
    println("! read")
    return new ReadLock {
      override def release(): Unit = {
        locker.synchronized {
          activeReaders -= 1
          locker.notifyAll()
        }
      }
    }
  }

  def getWriteLock(): WriteLock = locker.synchronized {
    println("? write")
    while (activeWriter || activeReaders > 0) locker.wait()
    activeWriter = true
    println("! write")
    return new WriteLock {
      override def release(): Unit = {
        locker.synchronized {
          activeWriter = false
          locker.notifyAll()
        }
      }
    }
  }
}