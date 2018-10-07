package gavert.ftest.CAPI.services

import com.google.inject.Singleton
import com.twitter.finagle.Redis

@Singleton
class SimpleRedisService(ip: String = "localhost", port: String = "6379") {
  val redisClient = Redis.newRichClient(ip + ":" + port)
}
