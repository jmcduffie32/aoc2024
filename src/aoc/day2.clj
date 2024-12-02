(ns aoc.day2
  (:require [clojure.string :as str]))

(defn get-numbers [text]
  (->> text
       str/split-lines
       (mapv (fn [l] (map #(Integer/parseInt %) (re-seq #"\d+" l))))))

(defn safe-diff [numbers]
  (->> numbers
       (partition 2 1)
       (map (fn [[n1 n2]] (<= (abs (- n2 n1)) 3)))
       (every? true?)))

(defn drop-combos [numbers]
  (for [i (range (count numbers))]
    (concat (subvec numbers 0 i)
            (subvec numbers (inc i)))))

(defn safe? [numbers]
  (and
   (or (apply > numbers)
       (apply < numbers))
   (safe-diff numbers)))

(defn part1 [input]
  (->> input
       get-numbers
       (map safe?)
       (filter true?)
       count))

(defn part2 [input]
  (->> input
       get-numbers
       (map vec)
       (map drop-combos)
       (map #(some true? (map safe? %)))
       (filter true?)
       count))

(comment
  (def input
    (str/trim (slurp "./inputs/day2.txt"))
    )

  (part1 input)
  (part2 input)

  ;;
  )
