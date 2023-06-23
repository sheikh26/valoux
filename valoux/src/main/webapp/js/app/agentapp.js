var app = angular.module('valouxApp', [
                                    'valouxApp.controllers',
                                    'valouxApp.valouxServices',
                                    'valouxApp.valouxDirectives',
                                    'ngRoute'                                    
                                    ]);
;
app.config(function($routeProvider) {
        $routeProvider
            // route for the about page
           /*.when('/grid', {
                templateUrl : 'include/agentShare-list-grid.html',
                controller  : 'userManagement'
            })*/
            .when('/', {
                templateUrl : 'include/owner-agents-list.html',
                controller  : 'userManagement'
            })
    });