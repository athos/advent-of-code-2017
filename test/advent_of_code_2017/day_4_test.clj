(ns advent-of-code-2017.day-4-test
  (:require [advent-of-code-2017.day-4 :refer :all]
            [clojure.test :refer [deftest is]]))

(deftest valid1?-test
  (is (valid1? "aa bb cc dd ee"))
  (is (not (valid1? "aa bb cc dd aa")))
  (is (valid? "aa bb cc dd aaa")))

(deftest valid2?-test
  (is (valid2? "abcde fghij"))
  (is (not (valid2? "abcde xyz ecdab")))
  (is (valid2? "a ab abc abd abf abj"))
  (is (valid2? "iiii oiii ooii oooi oooo"))
  (is (not (valid2? "oiii ioii iioi iiio"))))
