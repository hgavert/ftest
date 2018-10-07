package gavert.ftest.CAPI.modules

import com.google.inject.{Provides, Singleton}
import com.twitter.inject.TwitterModule
import gavert.ftest.CAPI.services.personalOverrideService

object personalOverrideServiceModule extends TwitterModule {

  val key = flag("key", "defaultkey", "The key to use.")

  @Singleton
  @Provides
  def providesPersonalOverrideService: personalOverrideService = {
    new personalOverrideService()
  }

}
