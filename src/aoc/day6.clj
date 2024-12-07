(ns aoc.day6
  (:require [clojure.string :as str]
            [clojure.core.match :refer [match]]))


(defn index->loc [index line-length]
  [(quot index line-length) (dec (rem index line-length))])

(defn init-state [input]
  (let [right-start (str/index-of input ">")
        left-start (str/index-of input "<")
        up-start (str/index-of input "^")
        down-start (str/index-of input "v")
        line-length (count (first (str/split-lines input)))
        state (cond
          right-start {:loc (index->loc right-start line-length) :direction :right}
          left-start {:loc (index->loc left-start line-length) :direction :left}
          up-start {:loc (index->loc up-start line-length) :direction :up}
          down-start {:loc (index->loc down-start line-length) :direction :down})]
    (assoc state :visited #{(:loc state)})))

(defn move [state]
  (match state
    {:direction :right} (update-in state [:loc 1] inc)
    {:direction :left} (update-in state [:loc 1] dec)
    {:direction :up} (update-in state [:loc 0] dec)
    {:direction :down} (update-in state [:loc 0] inc)))

(defn turn-if-needed [grid state]
  (if (= (get-in grid (:loc (move state))) "#")
    (match state
      {:direction :right} (assoc state :direction :down)
      {:direction :left} (assoc state :direction :up)
      {:direction :up} (assoc state :direction :right)
      {:direction :down} (assoc state :direction :left))
    state))

(defn mark-visited [state]
  (update state :visited conj (:loc state)))

(defn step [grid state]
  (->> state
       (turn-if-needed grid)
       move
       mark-visited))

(defn create-grid [input]
  (->> input
       str/split-lines
       (mapv (fn [l] (into [] (str/split l #""))))))

(comment
  (def input (str/trim (slurp "./inputs/day6.txt")))

  (defn part1 [input]
    (let [grid (create-grid input)]
      (-> (loop [state (init-state input)]
            (if (get-in grid (:loc state))
              (recur (step grid state))
              state))
          :visited
          count
          dec)))

  (part1 input)
  ;;
  )
