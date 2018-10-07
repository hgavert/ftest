package gavert.ftest.events

import gavert.ftest.scala_misc.Helpers.get1stOrElse

object Transaction {
  // ei tätä tarvinnutkaan
}

case class Transaction(time: String, account: String, otherPartyAccount: String, otherPartyName: String)


object TransactionFuncs {
  /**
    * Helper function - could be nested in categorize, but here so that it can be tested...
    * This takes in the string, turns it then into regex and wraps into function that detects the string and then returns optionally the category
    * @param str String to be found
    * @param category Category to be returned if string is found
    * @return function that searches for those strings
    */
  def createMatcherFunctions(str: String, category: String): String => Option[String] = (stringToCategorize: String) => {
    val reg = ("(.* )?" + str + " .*").r
    reg.findFirstIn(stringToCategorize) match {
      case Some(_) => Some(category)
      case None => None
    }
  }

  // tuo näytti tyhmältä regexiltä, entäs simppelimpi...
  // huomattavasti nopeampi...
  def createMatcherFunctions2(str: String, category: String): String => Option[String] = (stringToCategorize: String) => {
    if (stringToCategorize.contains(str))
      Some(category)
    else
      None
  }

  def getStringsAndCategories() = List(
    List("foo", "bar")
      .map((_, "foobar")),
    List("as oy", "valion eläkekassa", "vuokra", "helen oy", "energia 247 oy", "shknmyynti", "jtehuolto", "asunto oy", "shk", "vesi", "fortum", "saunalahti", "dna oy", "sonera", "telia")
      .map((_, "asuminen")),
    List("prisma", "s market", "k market", "siwa", "citymarket", "sale", "valintatalo", "super market", "lidl", "supermarket", "sokos herkku", "halpahalli", "tokmanni", "uniresta", "alepa")
      .map((_, "paivittaistavarakauppa")),
    List("vakuutukset", "fennia", "op pohjola", "kesk vak yht", "axa", "vakuutusvahinkoyhti", "vahinkovakuutusyhti", "vakuutus")
      .map((_, "vakuutukset")),
    List("apteekki", "mehilinen", "sairaala", "terveystalo", "sairaanhoito", "hieroja", "hieronta", "fysioterapia", "fysioterapeutti")
      .map((_, "terveydenhoito")),
    List("touhula", "tenava", "pivkoti", "pivhoito", "thtipivkodit", "lastentarha", "lastenhoito")
      .map((_, "lastenhoito")),
    List("vr", "shell", "pyskinninvalvonta", "metro", "neste oil", "liikenne", "pyskinti", "finnair", "teboil", "autokorjaamo", "taksi", "norwegian", "liikenteen turvallisuusvirasto", "kuljetus", "auto", "katsastus", "autofix", "matkahuolto", "koskilinjat", "bussi", "hsl", "huoltoasema", "st1")
      .map((_, "liikkuminen")), // TODO: NOT LIKE "tyntekijliitto"")
    List("lainat", "seb kort bank", "luoton maksu", "visa luotto%", "american express", "lainanhoito", "klarna", "bank norwegian credit cards" )
      .map((_, "luottojenhoito")),
    List("arvopaperi", "snn sst", "nordnet", "seligson", "ssttili", "nordea rahoitus", "accunia", "rahastoyhti", "varainhoito", "alexander corporate finance", "alexandria markets", "pankkiiriliike", "omaisuudenhoito", "asset management", "capital", "dividend house", "rahastohallinto", "em finance", "enegia portfolio services", "estlander partners", "evervest", "finlandia group", "fourton", "index helsinki", "innovestor", "invesdor", "investium", "jam advisors", "kansalaisrahoitus", "lago kapital", "sijoituspalvelut", "nordea funds", "northern star partners", "obsido oy", "op silytys", "ahola maliniemi partners", "wasa stock adviser", "pareto securities", "power deriva", "privanet securities", "salkunhallinta", "sp kapitaali", "ub securities", "varainhallinta", "zenito oy", "euroclear finland")
      .map((_, "sijoittaminen")),
    List("puuilo", "miele", "kalustaja", "starkki", "teknikmagasinet", "plantagen", "ikea", "jysk", "gigantti", "matto", "kaluste", "bauhaus", "clas ohlson", "sisustus", "rakentaminen", "rautakauppa", "k rauta" )
      .map((_, "sisustaminen ja rakentaminen")),
    List("zara", "cos", "vima", "polarn o pyret", "jack jones", "suutari", "lindex", "hennes", "intersport", "reima oy", "stadium", "cubus", "shoes", "kappahl", "din sko", "filippa k", "vi ma oy", "dressmann", "pukumies", "gina tricot", "vero moda", "victorias secret", "zalando", "name it" )
      .map((_, "vaatetus")),
    List("tallink silja", "viking line", "flow festival", "teatteri", "tivoli", "jungle juice bar", "noodle", "games", "munkkikah", "kakkukahvila", "bakery", "ruoka", "sushibar", "coffee", "sodexo", "woltfin", "pancho villa", "grilli", "ravintola", "pancho villa", "mcdonalds", "starbucks", "subway", "pizzarium", "friends burge", "burger", "bbq", "antell", "food", "cafe", "hesburger", "restaurant", "bar", "rosso", "leipomo", "kotipizza", "kebab", "berry center", "sushi", "burger king", "kahvila", "diner", "pub" )
      .map((_, "viihde ja ravintolat")),
    List("ray", "veikkaus" )
      .map((_, "rahapelit")),
    List("kello ja", "koru", "glitter", "kulta ja", "kultasepp")
      .map((_, "kellot ja korut")),
    List("kampaamo", "kampaaja", "parturi", "make up", "beauty", "solarium", "jhalli", "urheiluhalli", "liikunta", "syke", "uimahalli", "tanssi", "silmlasi", "optikko", "wellness", "makeup")
      .map((_, "kauneus, liikunta ja hyvinvointi"))
  ).flatten

  /**
    * This is just modelled after the weird SQL used long way back...
    * Yeah, fix and make good..
    *
    * Could be faster:
    * 1 000 000 is between 22sec and 3min - with regex
    *
    * @param tr
    * @return The category as string
    */
  def categorize(tr: Transaction): String = {
    // So, the SQL was like this:
    /*
    FROM category_view WHERE after_nimi2 LIKE 'as oy %' OR after_nimi2 LIKE '% as oy %' \
    OR after_nimi2 LIKE 'valion eläkekassa %' OR after_nimi2 LIKE '% valion eläkekassa %' \
    OR after_nimi2 LIKE 'vuokra %' OR after_nimi2 LIKE '% vuokra %' \
    OR after_nimi2 LIKE 'telia %' OR after_nimi2 LIKE '% telia %'")
    asuminen = category_asuminen.withColumn("category", lit('asuminen'))
    */

    // So, to mimic this, we generate list of regex -> category String
    // You should read this from file... but first version here...
    // We simplify regex to string first and then turn it to regex

    // BTW, to optimize this, have the most common categories on top
    // Or make a better object that can order it internally based on hits
    val stringsAndCategories = getStringsAndCategories()

    val matchers = stringsAndCategories
      .map{case (s, c) => createMatcherFunctions(s, c)}
        .toStream

    get1stOrElse(matchers, "unknown")(tr.otherPartyName)

  }
}
