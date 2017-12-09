(ns advent-of-code-2017.day-9-test
  (:require [advent-of-code-2017.day-9 :refer :all]
            [clojure.test :refer [deftest is]]))

(deftest solve1-test
  (is (= 1 (solve1 "{}")))
  (is (= 6 (solve1 "{{{}}}")))
  (is (= 5 (solve1 "{{},{}}")))
  (is (= 16 (solve1 "{{{},{},{{}}}}")))
  (is (= 1 (solve1 "{<a>,<a>,<a>,<a>}")))
  (is (= 9 (solve1 "{{<ab>},{<ab>},{<ab>},{<ab>}}")))
  (is (= 9 (solve1 "{{<!!>},{<!!>},{<!!>},{<!!>}}")))
  (is (= 3 (solve1 "{{<a!>},{<a!>},{<a!>},{<ab>}}"))))

(deftest solve2-test
  (is (= 0 (solve2 "<>")))
  (is (= 17 (solve2 "<random characters>")))
  (is (= 3 (solve2 "<<<<>")))
  (is (= 2 (solve2 "<{!>}>")))
  (is (= 0 (solve2 "<!!>")))
  (is (= 0 (solve2 "<!!!>>")))
  (is (= 10 (solve2 "<{o\"i!a,<{i<a>"))))
