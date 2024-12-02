(ns aoc.day2
  (:require [clojure.string :as str]))

(defn get-numbers [text]
  (->> text
       str/split-lines
       (map (fn [l] (map #(Integer/parseInt %) (re-seq #"\d+" l))))))

(defn safe-diff [numbers]
  (->> numbers
       (partition 2 1)
       (map (fn [[n1 n2]] (<= (abs (- n2 n1)) 3)))
       (every? true?)))

(defn part1 [input]
  (->> input
       get-numbers
       (map (fn [numbers]
              (and
                (or (apply > numbers)
                    (apply < numbers))
                (safe-diff numbers))))
       (filter true?)
       count))

(defn part2 [input]
  (let [[list1 list2] (get-numbers input)
        freqs (frequencies list2)]
    (reduce (fn [total n]
              (+ total (* n (get freqs n 0))))
            0
            list1)))

(comment
  (def input (str/trim (slurp "./inputs/day2.txt")))

  (part1 input)
  (part2 input)

  ;;
  )
