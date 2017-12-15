(ns advent-of-code-2017.day-12-test
  (:require [advent-of-code-2017.day-12 :refer :all]
            [clojure.test :refer [deftest is]]))

(def lines
  ["0 <-> 2"
   "1 <-> 1"
   "2 <-> 0, 3, 4"
   "3 <-> 2, 4"
   "4 <-> 2, 3, 6"
   "5 <-> 6"
   "6 <-> 4, 5"])

(deftest solve1-test
  (is (= 6 (solve1 lines))))

(deftest solve2-test
  (is (= 2 (solve2 lines))))
