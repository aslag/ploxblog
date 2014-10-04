(ns ploxblog.handlers.api
  (:require [ring.util.response :refer :all]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ploxblog.repositories.any :as any]
            [ploxblog.repositories.post :as post]
            [taoensso.timbre :as timbre]))
(timbre/refer-timbre)

(defn -handle-not-found [resp]
  (if (or (nil? resp) (empty? resp))
    (route/not-found (response {:message "not found"}))
    (response resp)))

(defn -bodyless [resp]
  (if (associative? resp)
    (dissoc resp :body)
    resp))

(defn -single [id]
  (->
    (post/by-id id)
    (post/resolve)
    (-handle-not-found)))

(def api-routes
  (routes
    (context "/api" []
      (context "/post" []

        ; get all
        (GET "/" []
          (info "GET /api/post")
           (-> (post/by-label)
               (post/resolve)
               (-handle-not-found)))

        ; get meta about one by id
        (HEAD "/:id" [id]
          (info "HEAD" (str "/api/post/" id))
          (-> (-single id)
              (-bodyless)))

        ; get one by id
        (GET "/:id" [id]
          (info "GET" (str "/api/post/" id))
          (-single id))

        ; create new, returns 201 w/ Location header, 409 w/ Location header to determined duplicate (possible b/c IDs are derived), not idempotent
        (POST "/" {body :body}
          (info "POST /api/post" body)
          (response {:id "some_id"}))

        ; delete one by id, returns deleted
        (DELETE "/:id" [id]
          (info "DELETE" (str "/api/post/" id))
          (-> (post/delete id)
              (response)
              (-bodyless)))

        ; entire update of one by id, returns 204 with no body if successful; idempotent

        (PUT "/:id" [id :as {body :body}]
          (info "PUT" (str "/api/post/" id) body)
          (response {:id "some_id"}))
        ; partial update of one by id, returns 204 w/ no content if successful, 400 if malformed, 415 if unsupported media type, or 422 if requested patch screws up state; not idempotent

        (PATCH "/:id" [id :as {body :body}]
          (info "PATCH" (str "/api/post/" id) body)
          (response {:id "some_id"}))

        ; available methods on resource; note, should return Accept-Patch header with patch format (see http://tools.ietf.org/html/rfc5789#section-3.1)
        (OPTIONS "/:id" [id]
          (info "OPTIONS" (str "/api/post/" id))
          ; TODO: fix!
          (header {} "Allow" "HEAD,<others particular to resource>"))
      )
      ; removed b/c I hope to handle these more generally
      ;
      ; (route/not-found (response {:message "not found"}))
             )))
