(ns ploxblog.app
  (:use ring.util.response)
  (:require [ploxblog.wrappers :as plx-wrappers]
            [ploxblog.handlers.api :as plx-api]
            [ploxblog.handlers.static :as plx-static]
            [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.middleware.resource :as ring-rs]
            [ring.middleware.json :as ring-js]
            [taoensso.timbre :as timbre]))
(timbre/refer-timbre)

(defn init []
  (info "ploxblox is starting..."))

(defn destroy []
  (info "ploxblox is shutting down..."))

; combine api and static routes, apply middleware to each selectively
(defroutes app-routes
  (-> plx-api/api-routes
    (ring-js/wrap-json-body)
    (ring-js/wrap-json-response))
  (-> plx-static/static-routes
    (ring-rs/wrap-resource "resources/public")))

(def app-handler
  (-> (handler/api app-routes)
      ; TODO: add a wrapper like (plx-wrappers/error) for 500s, 404s; differentiate b/n static responses (pages in text/html) and those for api (application/json)
      (plx-wrappers/req-log)
      (plx-wrappers/resp-log)))
