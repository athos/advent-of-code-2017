(ns advent-of-code-2017.day-5-test
  (:require [advent-of-code-2017.day-5 :refer :all]
            [clojure.test :refer [deftest is]]))

(deftest solve1-test
  (is (= 5 (solve1 [0 3 0 1 -3]))))

(deftest solve2-test
  (is (= 10 (solve2 [0 3 0 1 -3]))))
