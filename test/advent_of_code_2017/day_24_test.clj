(ns advent-of-code-2017.day-24-test
  (:require [advent-of-code-2017.day-24 :refer :all]
            [clojure.test :refer [deftest is]]))

(def lines
  ["0/2"
   "2/2"
   "2/3"
   "3/4"
   "3/5"
   "0/1"
   "10/1"
   "9/10"])

(deftest solve1-test
  (is (= 31 (solve1 lines))))

(deftest solve2-test
  (is (= 19 (solve2 lines))))
