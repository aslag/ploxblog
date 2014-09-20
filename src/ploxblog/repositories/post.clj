(ns ploxblog.repositories.post
    (:require [clojurewerkz.neocons.rest :as nr]
              [clojurewerkz.neocons.rest.transaction :as tx]
              [taoensso.timbre :as timbre]))
(timbre/refer-timbre)

(def conn (nr/connect "http://127.0.0.1:7474/db/data"))

; gen
(defn -resp-single [tx_resp]
  (debug "raw response", tx_resp)
  (map (fn [ob] (first (:row ob)))
               (:data (first tx_resp))))

; gen
(defn -trans [& stmts]
  (let [st (map #(if (coll? %1) %1 (tx/statement %1)) stmts)]
    (debug "in-transaction" st)
      (apply (partial tx/in-transaction conn) st)))

; resolves post data to make up full record
(defn resolve [posts]
  (map (fn [post]
         (let [id (:id post)]
           (-> post
             (assoc :tags (-resp-single
               (-trans (tx/statement "MATCH (p:Post)-[:has_tag]->(t:Tag) WHERE p.id={id} return t" {:id id}))))
             (assoc :author (-resp-single
               (-trans (tx/statement "MATCH (p:Post)-[:authored_by]->(a:Author) WHERE p.id={id} return a" {:id id}))))
           ))) posts))

; gen
(defn by-label [label]
  (-resp-single
    (-trans (str "MATCH n WHERE n:" label " RETURN n"))))

; gen
(defn by-id [id]
  (-resp-single
    (-trans (tx/statement "MATCH n WHERE n.id={id} return n" {:id id}))))
