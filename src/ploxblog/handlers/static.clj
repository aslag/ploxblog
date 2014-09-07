(ns ploxblog.handlers.static
  (:use ring.util.response)
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [taoensso.timbre :as timbre]))
(timbre/refer-timbre)

(def static-routes
  (routes
    (GET "/" []
      (route/resources "/"))
      ; removed b/c I hope to handle these more generally
      ;
      ; (route/not-found (response "<html><head><title>Not Found</title></head><body><p>Not Found</p></body></html>"))
   ))
