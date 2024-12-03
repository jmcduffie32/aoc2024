(ns aoc.day3
  (:require [clojure.string :as str]
            [clojure.core.match :refer [match]]))

(defn get-muls[text]
  (re-seq #"mul\((\d+),(\d+)\)" text))

(defn get-instructions[text]
  (re-seq #"(?:do(?:n't)?\(\)|mul\((\d+),(\d+)\))" text))

(defn process-instruction [state [command n1 n2]]
  (match [command n1 n2]
         ["do()" _ _] (assoc state :enabled true)
         ["don't()" _ _] (assoc state :enabled false)
         :else (if (:enabled state)
                 (update state :sum (fn [s] (+ s (* (read-string n1)
                                                    (read-string n2)))))
                 state)))

(defn solve [input inst-fn]
  (->> input
       inst-fn
       (reduce process-instruction {:enabled true
                                    :sum     0})
       :sum))

(comment
  (def input (str/trim (slurp "./inputs/day3.txt")))

  (solve input get-muls)
  (solve input get-instructions)

  ;;
  )
