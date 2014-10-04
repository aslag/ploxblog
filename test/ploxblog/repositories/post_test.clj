(ns ploxblog.repositories.post_test
  (:require [clojure.test :refer :all]
            [ploxblog.repositories.post :refer :all]))

(defn -to-post [title]
  {:title title})

(deftest test-label
  (testing "label function returns nil on invalid input"
    (is (= (label {}) nil))
    (is (= (label nil) nil))
    (is (= (label "") nil)))

  (testing "label function replaces special chars and spaces"
    (is (= (label (-to-post "fooꝊgoo")) "foogoo"))
    (is (= (label (-to-post "foo Ꝋ goo")) "foo-goo"))
    )

  (testing "label function lowercases title"
    (is (= (label (-to-post "FooFooFoo")) "foofoofoo"))
    ))
