(ns ploxblog.wrappers
  (:require [clojure.pprint :refer [pprint]]
            [taoensso.timbre :as timbre]))
(timbre/refer-timbre)

(defn req-log [handler]
  (fn [req]
    (debug "HTTP Request:\n" (with-out-str (pprint req)))
      (handler req)))

(defn resp-log [handler]
  (fn [req]
    (let [resp (handler req)]
      (debug "HTTP Response:\n" (with-out-str (pprint resp)))
      resp)))
