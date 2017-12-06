(ns advent-of-code-2017.day-6-test
  (:require [advent-of-code-2017.day-6 :refer :all]
            [clojure.test :refer [deftest is]]))

(deftest solve1-test
  (is (= 5 (solve1 [0 2 7 0]))))

(deftest solve2-test
  (is (= 4 (solve2 [2 4 1 2]))))
