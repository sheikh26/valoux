'use strict';

// See https://datatables.net/extras/tabletools/
angular.module('datatables.tabletools', ['datatables'])
    .config(dtTableToolsConfig);

/* @ngInject */
function dtTableToolsConfig($provide, DT_DEFAULT_OPTIONS) {
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
            options.withTableTools = withTableTools;
            options.withTableToolsOption = withTableToolsOption;
            options.withTableToolsButtons = withTableToolsButtons;
            return options;

            /**
             * Add table tools compatibility
             * @paparam sSwfPath the path to the swf file to export in csv/xls
             * @returns {DTOptions} the options
             */
            function withTableTools(sSwfPath) {
                console.warn('The tabletools extension has been retired. Please use the select and buttons extensions instead: https://datatables.net/extensions/select/ and https://datatables.net/extensions/buttons/');
                var tableToolsPrefix = 'T';
                options.dom = options.dom ? options.dom : DT_DEFAULT_OPTIONS.dom;
                if (options.dom.indexOf(tableToolsPrefix) === -1) {
                    options.dom = tableToolsPrefix + options.dom;
                }
                options.hasTableTools = true;
                if (angular.isString(sSwfPath)) {
                    options.withTableToolsOption('sSwfPath', sSwfPath);
                }
                return options;
            }

            /**
             * Add option to "oTableTools" option
             * @paparam key the key of the option to add
             * @paparam value an object or a function of the function
             * @returns {DTOptions} the options
             */
            function withTableToolsOption(key, value) {
                if (angular.isString(key)) {
                    options.oTableTools = options.oTableTools && options.oTableTools !== null ? options.oTableTools : {};
                    options.oTableTools[key] = value;
                }
                return options;
            }

            /**
             * Set the table tools buttons to display
             * @paparam aButtons the array of buttons to display
             * @returns {DTOptions} the options
             */
            function withTableToolsButtons(aButtons) {
                if (angular.isArray(aButtons)) {
                    options.withTableToolsOption('aButtons', aButtons);
                }
                return options;
            }
        }
    }
}
