(ns aoc.day6
  (:require [clojure.string :as str]))


(defn index->loc [index line-length]
  [(quot index line-length) (dec (rem index line-length))])

(defn get-starting-point [input]
  (let [right-start (str/index-of input ">")
        left-start (str/index-of input "<")
        up-start (str/index-of input "^")
        down-start (str/index-of input "v")
        line-length (count (first (str/split-lines input)))]
    (cond
      right-start {:loc (index->loc right-start line-length) :direction :right}
      left-start {:loc (index->loc left-start line-length) :direction :left}
      up-start {:loc (index->loc up-start line-length) :direction :up}
      down-start {:loc (index->loc down-start line-length) :direction :down})))

(comment
  (def input (str/trim (slurp "./inputs/day5.txt")))
  (def input "....#.....
....^....#
..........
..#.......
.......#..
..........
.#........
........#.
#.........
......#...")

  (def grid (->> input
                 str/split-lines
                 (mapv (fn [l] (into [] (str/split l #""))))))

  (str/index-of input "^")

  ;; (part1 input)
  ;; (part2 input)

  ;;
  )
