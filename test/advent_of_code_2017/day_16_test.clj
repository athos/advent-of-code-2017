(ns advent-of-code-2017.day-16-test
  (:require [advent-of-code-2017.day-16 :refer :all]
            [clojure.test :refer [deftest is]]))

(def instrs
  [[:s 1]
   [:x 3 4]
   [:p "e" "b"]])

(deftest run-test
  (is (= (mapv str "baedc")
         (run (mapv str "abcde") instrs))))

(deftest run-ntimes-test
  (is (= (mapv str "ceadb")
         (run-ntimes 2 (mapv str "abcde") instrs))))
