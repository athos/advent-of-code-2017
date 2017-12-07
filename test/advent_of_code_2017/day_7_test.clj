(ns advent-of-code-2017.day-7-test
  (:require [advent-of-code-2017.day-7 :refer :all]
            [clojure.test :refer [deftest is]]))

(def lines
  ["pbga (66)"
   "xhth (57)"
   "ebii (61)"
   "havc (66)"
   "ktlj (57)"
   "fwft (72) -> ktlj, cntj, xhth"
   "qoyq (66)"
   "padx (45) -> pbga, havc, qoyq"
   "tknk (41) -> ugml, padx, fwft"
   "jptl (61)"
   "ugml (68) -> gyxo, ebii, jptl"
   "gyxo (61)"
   "cntj (57)"])

(deftest solve1-test
  (is (= "tknk" (:name (solve1 lines)))))

(deftest solve2-test
  (is (= 60 (solve2 lines))))
