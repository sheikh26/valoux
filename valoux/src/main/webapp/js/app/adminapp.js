var app = angular.module('valouxApp', [
                                    'valouxApp.controllers',
                                    'valouxApp.valouxServices',
                                    'valouxApp.valouxDirectives',
                                    'datatables'
                                    ]);
 app.config(function($routeProvider) {
        $routeProvider
            // route for the about page
            .when('/', {
                templateUrl : 'ads-list.html',
                controller  : 'adminManagement'
            })
            
           
            .when('/add', {
                templateUrl : 'ads-add.html',
                controller  : 'adminManagement'
            })
			.when('/edit/:storeImageId', {
                templateUrl : 'ads-add.html',
                controller  : 'adminManagement'
            })
            
            /*.when('/success/:itemId', {
                templateUrl : 'include/add-item-success.html',
                controller  : 'collectionManagement'
            })*/
            .otherwise({
                redirectTo: '/'
            });
    });
             