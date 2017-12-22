(ns advent-of-code-2017.day-22-test
  (:require [advent-of-code-2017.day-22 :refer :all]
            [clojure.test :refer [deftest is]]))

(def lines
  ["..#"
   "#.."
   "..."])

(deftest run-test
  (is (= 5587 (solve1 lines))))

(deftest solve2-test
  (is (= 26 (solve2 100 lines))))
