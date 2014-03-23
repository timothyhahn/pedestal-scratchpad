(ns ^:shared tutorial-client.behavior
      (:require [clojure.string :as string]
                              [io.pedestal.app.messages :as msg]
                              [io.pedestal.app :as app]))

(defn inc-transform [old-value _]
    ((fnil inc 0) old-value))

(defn swap-transform [_ message]
    (:value message))

(defn init-main [_]
    [[:transform-enable [:main :my-counter] :inc [{msg/topic [:my-counter]}]]])

(def example-app
    {:version 2
        :transform [[:inc  [:my-counter] inc-transform]
                                   [:swap [:**]         swap-transform]]
        :emit [{:init init-main}
                         [#{[:my-counter] [:other-counters :*]} (app/default-emitter [:main])]]})

