(ns advent-of-code-2017.day-8-test
  (:require [advent-of-code-2017.day-8 :refer :all]
            [clojure.test :refer [deftest is]]))

(def lines
  ["b inc 5 if a > 1"
   "a inc 1 if b < 5"
   "c dec -10 if a >= 1"
   "c inc -20 if c == 10"])

(deftest solve1-test
  (is (= 1 (solve1 lines))))

(deftest solve2-test
  (is (= 10 (solve2 lines))))
