var app = angular.module('valouxApp', [
                                    'valouxApp.controllers',
                                    'valouxApp.valouxServices',
                                    'valouxApp.valouxDirectives',
                                    'ngRoute'
                                    ]);
     app.config(function($routeProvider) {
        $routeProvider
            // route for the about page
            .when('/', {
                templateUrl : 'include/appraisal-list-agent.html',
                controller  : 'appraisalManagement'
            })
            .when('/add', {
                templateUrl : 'include/add-appraisal.html',
                controller  : 'appraisalManagement'
            })
            .when('/edit/:appraisalId', {
                templateUrl : 'include/add-appraisal.html',
                controller  : 'appraisalManagement'
            })
            .when('/grid', {
                templateUrl : 'include/appraisal-grid-agent.html',
                controller  : 'appraisalManagement'
            })
            .when('/detail/:appraisalId', {
                templateUrl : 'include/appraisal-details.html',
                controller  : 'appraisalManagement'
            })
            /*.when('/success/:itemId', {
                templateUrl : 'include/add-item-success.html',
                controller  : 'collectionManagement'
            })*/
            .otherwise({
                redirectTo: '/'
            });
    });
                                    
     