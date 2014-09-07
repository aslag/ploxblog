(ns ploxblog.handler
  (:use ring.util.response)
  (:require [ploxblog.wrappers :as wrappers]
            [compojure.core :refer :all]
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
         (info "handling /api/post")
         (response {:message "foof"}))
    (route/resources "/")
    (route/not-found (response {:message "not found"}))))

(def app
  (-> (handler/api app-routes)
      (middleware/wrap-json-body)
      (middleware/wrap-json-response)
      (wrappers/req-log)
      (wrappers/resp-log)))
