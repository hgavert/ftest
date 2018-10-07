Just a Hello World test server based on Finatra
(Well, trying to get the dependencies... you know...)


ab -n 2000 -c 100 http://localhost:8080/ping
ab -n 2000 -c 200 "http://localhost:8080/csimple?time=2018-02-01T12:23:12&account=FI123&other_party_account=FI567&other_party_name=kulta%20ja%20romu"

./sbt "project benchmarkServer" "run -http.port=:8888 -admin.port=:9990"

TODO:
Redis
1) as module
2) configuration as flags
3) WarmupHandler to initialize