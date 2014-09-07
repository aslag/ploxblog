(ns ploxblog.handlers.api
  (:use ring.util.response)
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [taoensso.timbre :as timbre]))
(timbre/refer-timbre)

(def api-routes
  (routes
    (context "/api" []
      (GET "/post" []
           (info "handling /api/post")
           (response {:message "foof"}))
      ; removed b/c I hope to handle these more generally
      ;
      ; (route/not-found (response {:message "not found"}))
             )))
