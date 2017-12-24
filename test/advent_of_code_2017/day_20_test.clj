(ns advent-of-code-2017.day-20-test
  (:require [advent-of-code-2017.day-20 :refer :all]
            [clojure.test :refer [deftest is]]))

(deftest solve1-test
  (is (= 0 (:id (solve1 ["p=< 3,0,0>, v=< 2,0,0>, a=<-1,0,0>"
                         "p=< 4,0,0>, v=< 0,0,0>, a=<-2,0,0>"])))))

(deftest solve2-test
  (is (= 1 (solve2 ["p=<-6,0,0>, v=< 3,0,0>, a=< 0,0,0>"
                    "p=<-4,0,0>, v=< 2,0,0>, a=< 0,0,0>"
                    "p=<-2,0,0>, v=< 1,0,0>, a=< 0,0,0>"
                    "p=< 3,0,0>, v=<-1,0,0>, a=< 0,0,0>"]))))
