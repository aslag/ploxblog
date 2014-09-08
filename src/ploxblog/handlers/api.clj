(ns ploxblog.handlers.api
  (:require [ring.util.response :refer :all]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [taoensso.timbre :as timbre]))
(timbre/refer-timbre)

(def api-routes
  (routes
    (context "/api" []
      (context "/post" []
        (GET "/:id" [id]
          (info (str "GET /api/post/" id))
          (response {:message "foof"}))
        (POST "/" {body :body}
          (info (str "POST /api/post/ with body" body))
          (response {:message "goof"})
              ))
      ; removed b/c I hope to handle these more generally
      ;
      ; (route/not-found (response {:message "not found"}))
             )))
