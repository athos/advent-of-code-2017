(ns advent-of-code-2017.day-2-test
  (:require  [advent-of-code-2017.day-2 :refer :all]
             [clojure.test :refer [deftest is]]))

(deftest solve1-test
  (is (= 18
         (solve1 ["5 1 9 5"
                  "7 5 3"
                  "2 4 6 8"]))))

(deftest solve2-test
  (is (= 9
         (solve2 ["5 9 2 8"
                  "9 4 7 3"
                  "3 8 6 5"]))))
