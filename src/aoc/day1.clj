(ns aoc.day1
  (:require [clojure.string :as str]))

(defn get-numbers [text]
  (->> text
       str/split-lines
       (map (fn [l] (map Integer/parseInt (re-seq #"\d+" l))))
       (reduce (fn [[list1 list2] [num1 num2]]
                 [(conj list1 num1) (conj list2 num2)])
               [[] []])))

(defn part1 [input]
  (let [[list1 list2] (get-numbers input)
        sorted1 (sort list1)
        sorted2 (sort list2)]
    (->>
     (map #(Math/abs (- %1 %2)) sorted1, sorted2)
     (apply +))))

(defn part2 [input]
  (let [[list1 list2] (get-numbers input)
        freqs (frequencies list2)]
    (reduce (fn [total n]
              (+ total (* n (get freqs n 0))))
            0
            list1)))

(comment
  (def input (str/trim (slurp "./inputs/day1.txt")))

  (part1 input)
  ;; => 2580760
  (part2 input)
  ;; => 25358365

  ;;
  )
