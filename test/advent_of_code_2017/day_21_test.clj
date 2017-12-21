(ns advent-of-code-2017.day-21-test
  (:require [advent-of-code-2017.day-21 :refer :all]
            [clojure.test :refer [deftest is]]))

(def lines
  ["../.# => ##./#../..."
   ".#./..#/### => #..#/..../..../#..#"])

(deftest solve-test
  (is (= 12 (solve grid 2 lines))))
