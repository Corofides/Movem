//package helpers
//
//import com.soywiz.korio.lang.*
//
//class FileHelper {
//    // https://stackoverflow.com/a/53018129/2895831
//    fun getResourceAsText(path: String): String? =
//        object {}.javaClass.getResource(path)?.readText()
//    // val html = getResourceAsText("/www/index.html")!!
//
//    // https://www.baeldung.com/kotlin/read-file
//    fun readFileUsingGetResource(fileName: String)
//        = this::class.java.getResource(fileName).readText(Charsets.UTF8)
//}
