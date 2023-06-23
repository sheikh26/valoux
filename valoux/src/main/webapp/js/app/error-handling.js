app.factory(
    "stacktraceService",
    function() {

            // "printStackTrace" is a global object.
            return({
                    print: printStackTrace
            });

    }
);

app.provider(
        "$exceptionHandler",
        {
                $get: function( errorLogService ) {

                        return( errorLogService );

                }
        }
);
app.factory(
        "errorLogService",
        function( $log, $window, stacktraceService ) {
                
                function log( exception, cause ) {
                        $log.error.apply( $log, arguments );
                        var savedItem = sessionStorage.getItem("saveImdData");
                        if( savedItem ){
                            var d = JSON.parse(savedItem);
                           savedItem = d.resData;
                        }
                        try {

                                var errorMessage = exception.toString();
                                var stackTrace = stacktraceService.print({ e: exception });
                              /*  $.ajax({
                                        type: "POST",
                                        //url: "javascript-errors",
                                        url:"/valoux/rest/logError/saveErrorLogs.csv",
                                        contentType: "application/json",
                                        data: angular.toJson({
                                            "reqPaparam": {
                                                "errorUrl": $window.location.href,
                                                "errorMessage": errorMessage,
                                                "stackTrace": stackTrace,
                                                "cause": ( cause || "" ),
                                                "userInfo": savedItem
                                            }    
                                        })
                                });*/

                        } catch ( loggingError ) {

                                $log.warn( "Error logging failed" );
                                $log.log( loggingError );

                        }

                }
                return( log );

        }
);