(ns advent-of-code-2017.day-15-test
  (:require [advent-of-code-2017.day-15 :refer :all]
            [clojure.test :refer [deftest is]]))

(deftest solve1-test
  (is (= 1 (solve1 5 65 8921))))

(deftest solve2-test
  (is (= 1 (solve2 1057 65 8921))))
