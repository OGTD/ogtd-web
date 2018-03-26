(ns ogtd-web-2.test-runner
  (:require
   [doo.runner :refer-macros [doo-tests]]
   [ogtd-web-2.core-test]
   [ogtd-web-2.common-test]))

(enable-console-print!)

(doo-tests 'ogtd-web-2.core-test
           'ogtd-web-2.common-test)
