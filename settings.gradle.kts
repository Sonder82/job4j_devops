rootProject.name = "DevOps"

val remoteCacheUrl = "http://192.168.0.113:5071/"
val remoteCachePush = true
val remoteCacheUsername = "user863"
val remoteCachePassword =  "qqkbjsp3263sic4bwbtawjsrme"
buildCache {
    remote<HttpBuildCache> {
        url = uri(remoteCacheUrl)
        isAllowInsecureProtocol = true
        isAllowUntrustedServer = true
        isPush = remoteCachePush
        credentials {
            username = remoteCacheUsername
            password = remoteCachePassword
        }
    }
}

