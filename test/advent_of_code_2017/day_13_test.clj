(ns advent-of-code-2017.day-13-test
  (:require [advent-of-code-2017.day-13 :refer :all]
            [clojure.test :refer [deftest is]]))

(def lines
  ["0: 3"
   "1: 2"
   "4: 4"
   "6: 4"])

(deftest solve1-test
  (is (= 24 (solve1 lines))))

(deftest solve2-test
  (is (= 10 (solve2 lines))))
