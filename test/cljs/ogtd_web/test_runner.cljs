(ns ogtd-web.test-runner
  (:require
   [doo.runner :refer-macros [doo-tests]]
   [ogtd-web.core-test]
   [ogtd-web.common-test]))

(enable-console-print!)

(doo-tests 'ogtd-web.core-test
           'ogtd-web.common-test)
