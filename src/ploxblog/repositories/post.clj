(ns ploxblog.repositories.post
    (:require [ploxblog.repositories.any :as any]
              [clojurewerkz.neocons.rest.transaction :as tx]
              [taoensso.timbre :as timbre]
              [clojure.string :as string]))
(timbre/refer-timbre)

(def ^:const label "Post")

(def delete (partial any/delete label))

(def by-id (partial any/by-id label))

(def by-label (partial any/by-label label))

; resolves post data to make up full record
(defn hydrate [posts]
  (map (fn [post]
         (let [id (:id post)]
           (-> post
             (assoc :tags (any/resp-single
               (any/trans (tx/statement (format "MATCH (p:%s)-[:has_tag]->(t:Tag) WHERE p.id={id} return t" label) {:id id}))))
             (assoc :author (any/resp-single
               (any/trans (tx/statement (format "MATCH (p:%s)-[:authored_by]->(a:Author) WHERE p.id={id} return a" label) {:id id}))))
           ))) posts))

; (defn -well-formed [data])

(defn label [post]
  (when-let [title (:title post)]
    (-> title
        (string/replace #"[^\d\w\s]+" "")
        (string/replace #"[ ]+" "-")
        (string/lower-case))))


; strategy: transform title to url-safe id and return that id (in URL) with 201. if user wants to retitle a post, will need to delete and recreate post with new id. Furnish way to store a redirect b/n old post id and new one (user picks)
; return values:
; - consider using maybe monad for various return values (success (needs URL), duplicate record, malformed, other server error)
; (defn new [data])
