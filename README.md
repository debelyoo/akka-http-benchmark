# akka-http-benchmark
a web server based on akka-http to benchmark against Spray

The server starts on port 8080 (set in application.conf) and handles a single URI: 
GET /ping

I'm testing the server using httperf (http://www.hpl.hp.com/research/linux/httperf/).

As an example I'm running the following command:

httperf --hog --timeout=5 --server=127.0.0.1 --port=8080 --uri=/ping --rate=15000 --num-conns=200000

The above command sends 200k GET requests (/ping) at a rate of 15k/sec.
