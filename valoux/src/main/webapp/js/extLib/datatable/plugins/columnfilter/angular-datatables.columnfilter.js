'use strict';

// See http://jquery-datatables-column-filter.googlecode.com/svn/trunk/index.html
angular.module('datatables.columnfilter', ['datatables'])
    .config(dtColumnFilterConfig)
    .run(initColumnFilterPlugin);

/* @ngInject */
function dtColumnFilterConfig($provide) {
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
            options.withColumnFilter = withColumnFilter;
            return options;

            /**
             * Add column filter support
             * @paparam columnFilterOptions the plugins options
             * @returns {DTOptions} the options
             */
            function withColumnFilter(columnFilterOptions) {
                options.hasColumnFilter = true;
                if (columnFilterOptions) {
                    options.columnFilterOptions = columnFilterOptions;
                }
                return options;
            }
        }
    }
}

/* @ngInject */
function initColumnFilterPlugin(DTRendererService) {
    var columnFilterPlugin = {
        postRender: postRender
    };
    DTRendererService.registerPlugin(columnFilterPlugin);

    function postRender(options, result) {
        if (options && options.hasColumnFilter) {
            result.dataTable.columnFilter(options.columnFilterOptions);
        }
    }
}
