(ns ploxblog.handlers.api
  (:require [ring.util.response :refer :all]
            [compojure.core :refer :all]
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
