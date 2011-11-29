(ns httpserver.httpserver-spec
  (:use [speclj.core])
  (:import (httpserver HttpServer)))

(defn- port-available [port]
   (try
     (java.net.ServerSocket. port)
     (java.net.DatagramSocket. port)
     true
     (catch java.io.IOException e false)
     )
   )

(describe "Http Server"

  (with server (HttpServer.))
  (after (.stop @server))

  (context "Constructors"

    (it "defaults to port 8080"
      (should= 8080 (.getPort (HttpServer.)))
      )

    (it "defaults to the directory '.'"
      (should= "." (.getDirectory (HttpServer.)))
      )

    (it "can be created with a different port"
      (should= 90 (.getPort (HttpServer. 90)))
      )

    (it "can be created with a different directory"
      (should= "some other directory" (.getDirectory (HttpServer. "some other directory")))
      )

    )

  (context "Starting the Server"

    (it "the port is unavailable after the server has been started"
      (.start @server)
      (should-not (port-available (.getPort @server)))
      )

    )

  (context "Stopping the Server"

    (it "the port is available again after the server has been stopped"
      (.start @server)
      (should-not (port-available (.getPort @server)))
      (.stop @server)
      (should (port-available (.getPort @server)))
      )

    )
  )