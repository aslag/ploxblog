(ns ploxblog.handlers.api
  (:require [ring.util.response :refer :all]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ploxblog.repositories.post :as post]
            [taoensso.timbre :as timbre]))
(timbre/refer-timbre)

(defn nfound []
  (route/not-found (response {:message "not found"})))

(defn -handle-not-found [resp]
  (if (or (nil? resp) (empty? resp))
    (nfound)
    (response resp)))

(def api-routes
  (routes
    (context "/api" []
      (context "/post" []
        ; get all
        (GET "/" []
          (info "GET /api/post")
          (-handle-not-found (post/resolve (post/by-label "Post"))))
        ; get meta about one by id
        (HEAD "/:id" [id]
          (info "HEAD" (str "/api/post/" id))
          (dissoc (-handle-not-found (post/resolve (post/by-id id))) :body))
        ; get one by id
        (GET "/:id" [id]
          (info "GET" (str "/api/post/" id))
          (-handle-not-found (post/resolve (post/by-id id))))
        ; create new, returns new w/ id
        (POST "/" {body :body}
          (info "POST /api/post" body)
          (response {:id "some_id"}))
        ; delete one by id, returns deleted
        (DELETE "/:id" [id]
          (info "DELETE" (str "/api/post/" id))
          (response {:id "some_id"}))
        ; entire update of one by id, returns updated
        (PUT "/:id" [id :as {body :body}]
          (info "PUT" (str "/api/post/" id) body)
          (response {:id "some_id"}))
        ; partial update of one by id, returns updated
        (PATCH "/:id" [id :as {body :body}]
          (info "PATCH" (str "/api/post/" id) body)
          (response {:id "some_id"}))
        ; available methods on resource
        (OPTIONS "/:id" [id]
          (info "OPTIONS" (str "/api/post/" id))
          ; TODO: fix!
          (header {} "Allow" "HEAD,<others particular to resource>"))
      )
      ; removed b/c I hope to handle these more generally
      ;
      ; (route/not-found (response {:message "not found"}))
             )))
