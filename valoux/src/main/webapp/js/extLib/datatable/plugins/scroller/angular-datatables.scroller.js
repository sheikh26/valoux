'use strict';

// See http://datatables.net/extensions/scroller/
angular.module('datatables.scroller', ['datatables'])
    .config(dtScrollerConfig);

/* @ngInject */
function dtScrollerConfig($provide, DT_DEFAULT_OPTIONS) {
    $provide.decorator('DTOptionsBuilder', dtOptionsBuilderDecorator);

    function dtOptionsBuilderDecorator($delegate) {
        var newOptions = $delegate.newOptions;
        var fromSource = $delegate.fromSource;
        var fromFnPromise = $delegate.fromFnPromise;

        $delegate.newOptions = function() {
            return _decorateOptions(newOptions);
        };
        $delegate.fromSource = function(ajax) {
            return _decorateOptions(fromSource, ajax);
        };
        $delegate.fromFnPromise = function(fnPromise) {
            return _decorateOptions(fromFnPromise, fnPromise);
        };

        return $delegate;

        function _decorateOptions(fn, paparams) {
            var options = fn(paparams);
            options.withScroller = withScroller;
            return options;

            /**
             * Add scroller compatibility
             * @returns {DTOptions} the options
             */
            function withScroller() {
                var scrollerSuffix = 'S';
                options.dom = options.dom ? options.dom : DT_DEFAULT_OPTIONS.dom;
                if (options.dom.indexOf(scrollerSuffix) === -1) {
                    options.dom = options.dom + scrollerSuffix;
                }
                return options;
            }
        }
    }
}
