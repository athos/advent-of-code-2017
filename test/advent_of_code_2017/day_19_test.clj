(ns advent-of-code-2017.day-19-test
  (:require [advent-of-code-2017.day-19 :refer :all]
            [clojure.test :refer [deftest is]]))

(def lines
  ["     |          "
   "     |  +--+    "
   "     A  |  C    "
   " F---|----E|--+ "
   "     |  |  |  D "
   "     +B-+  +--+ "
   "                "])

(deftest solve1-test
  (is (= "ABCDEF" (solve1 lines))))

(deftest solve2-test
  (is (= 38 (solve2 lines))))
