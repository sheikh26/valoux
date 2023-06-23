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
                templateUrl : 'include/collection-list-agent.html',
                controller  : 'collectionManagement'
            })
            .when('/add', {
                templateUrl : 'include/add-collection.html',
                controller  : 'collectionManagement'
            })
            .when('/edit/:collectionId', {
                templateUrl : 'include/add-collection.html',
                controller  : 'collectionManagement'
            })
            .when('/grid', {
                templateUrl : 'include/collection-grid-agent.html',
                controller  : 'collectionManagement'
            })
            .when('/detail/:collectionId', {
                templateUrl : 'include/collection-details.html',
                controller  : 'collectionManagement'
            })
            /*.when('/success/:itemId', {
                templateUrl : 'include/add-item-success.html',
                controller  : 'collectionManagement'
            })*/
            .otherwise({
                redirectTo: '/'
            });
    });
                                    
     