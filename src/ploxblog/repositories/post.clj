(ns ploxblog.repositories.post
    (:require [ploxblog.repositories.any :as any]
              [clojurewerkz.neocons.rest.transaction :as tx]
              [taoensso.timbre :as timbre]))
(timbre/refer-timbre)

(def label "Post")

(def delete (partial any/delete label))

(def by-label (partial any/by-label label))

; resolves post data to make up full record
(defn resolve [posts]
  (map (fn [post]
         (let [id (:id post)]
           (-> post
             (assoc :tags (any/resp-single
               (any/trans (tx/statement (str "MATCH (p:" label ")-[:has_tag]->(t:Tag) WHERE p.id={id} return t") {:id id}))))
             (assoc :author (any/resp-single
               (any/trans (tx/statement (str "MATCH (p:" label ")-[:authored_by]->(a:Author) WHERE p.id={id} return a") {:id id}))))
           ))) posts))

; (defn -well-formed [data])

; strategy: transform title to url-safe id and return that id (in URL) with 201. if user wants to retitle a post, will need to delete and recreate post with new id. Furnish way to store a redirect b/n old post id and new one (user picks)
; (defn new [data])
