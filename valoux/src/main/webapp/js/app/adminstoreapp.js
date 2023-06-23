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
                templateUrl : 'store-list.html',
                controller  : 'adminManagement'
            })
            
           
            .when('/merge', {
                templateUrl : 'store-merge.html',
                controller  : 'adminManagement'
            })
			.when('/edit/:storeId', {
                templateUrl : 'edit-store.html',
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
             