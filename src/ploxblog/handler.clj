(ns ploxblog.handler
  (:use ring.util.response)
  (:require [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.middleware.json :as middleware]
            [taoensso.timbre :as timbre]))
(timbre/refer-timbre)

(defn init []
  (info "ploxblox is starting..."))

(defn destroy []
  (info "ploxblox is shutting down..."))

(defroutes app-routes
  (context "/api" []
    (GET "/post" []
         (info "handling /post")
         (response {:content "foof"}))
    (route/resources "/")
    (route/not-found "not found")))

(def app
  (-> (handler/api app-routes)
      (middleware/wrap-json-body)
      (middleware/wrap-json-response)))
