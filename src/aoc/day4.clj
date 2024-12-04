(ns aoc.day4
  (:require [clojure.string :as str]))

(defn get-spans [matrix]
  (for [i (range (count matrix))
        j (range (count (get matrix 0)))]
    [(str/join (map (fn [span] (get-in matrix span ""))
                    [[i j] [(+ i 1) j] [(+ i 2) j] [(+ i 3) j]]))
     (str/join (map (fn [span] (get-in matrix span ""))
                    [[i j] [i (+ j 1)] [i (+ j 2)] [i (+ j 3)]]))
     (str/join (map (fn [span] (get-in matrix span ""))
                    [[i j] [(+ i 1) (+ j 1)] [(+ i 2) (+ j 2)] [(+ i 3) (+ j 3)]]))
     (str/join (map (fn [span] (get-in matrix span ""))
                    [[i j] [(+ i 1) (- j 1)] [(+ i 2) (- j 2)] [(+ i 3) (- j 3)]]))]))

(defn get-x-spans [matrix]
  (for [i (range (count matrix))
        j (range (count (get matrix 0)))]
    [(str/join (map (fn [span] (get-in matrix span ""))
                    [[i j] [(+ i 1) (+ j 1)] [(+ i 2) (+ j 2)]]))
     (str/join (map (fn [span] (get-in matrix span ""))
                    [[i (+ j 2)] [(+ i 1) (+ j 1)] [(+ i 2) j]]))]))

(defn part1 [input]
  (->> input
       str/split-lines
       (mapv #(into [] %))
       get-spans
       flatten
       (filter #(or (= % "XMAS")
                    (= % "SAMX")))
       count))

(defn part2 [input]
  (->> input
       str/split-lines
       (mapv #(into [] %))
       get-x-spans
       (filter (fn [[s1 s2]] (and (#{"MAS" "SAM"} s1)
                                  (#{"MAS" "SAM"} s2))))
       count))

(comment
  (def input (str/trim (slurp "./inputs/day4.txt")))

  (part1 input)
  (part2 input)

;;
  )
