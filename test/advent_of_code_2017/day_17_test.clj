(ns advent-of-code-2017.day-17-test
  (:require [advent-of-code-2017.day-17 :refer :all]
            [clojure.test :refer [deftest is]]))

(deftest solve1-test
  (is (= 638 (solve1 3))))

(deftest solve2-test
  (is (= 33601318 (solve2 301))))
