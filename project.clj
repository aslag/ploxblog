(defproject ploxblog "0.1.0-SNAPSHOT"
  :description "Blog engine for lulzotron.com"
  :url "http://github.com/aslag/ploxblog"
  :license {:name "GPLv3"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [ring "1.3.1"]
                 [ring-server "0.3.1"]
                 [compojure "1.1.8"]
                 ; uses jetty 7; must replace ring-jetty-adapter to use newer jetty
                 [ring/ring-json "0.3.1"]
                 [com.taoensso/timbre "3.3.0"]]
  :plugins [[lein-ring "0.8.11"]
            [lein-deps-tree "0.1.2"]]
  :ring {:handler ploxblog.handler/app
         :init ploxblog.handler/init
         :destroy ploxblog.handler/destroy
         :auto-reload? true}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-devel "1.3.1"]]}})
