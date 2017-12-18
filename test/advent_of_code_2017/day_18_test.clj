(ns advent-of-code-2017.day-18-test
  (:require [advent-of-code-2017.day-18 :refer :all]
            [clojure.test :refer [deftest is]]))

(deftest solve1-test
  (is (= 4
         (get-in (solve1 ["set a 1"
                          "add a 2"
                          "mul a a"
                          "mod a 5"
                          "snd a"
                          "set a 0"
                          "rcv a"
                          "jgz a -1"
                          "set a 1"
                          "jgz a -2"])
                 [:recovered 0]))))

(deftest solve2-test
  (is (= 3
         (get-in (solve2 ["snd 1"
                          "snd 2"
                          "snd p"
                          "rcv a"
                          "rcv b"
                          "rcv c"
                          "rcv d"])
                 [:sent 1]))))
