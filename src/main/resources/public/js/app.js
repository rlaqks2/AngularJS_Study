(function() {

    /*
        URL routing 기능: angular-route.js -> angular-ui-route.js가 더 많은 기능 제공
        표준처럼 널리 사용하고 있다.
        routing 모듈은 반드시 angular,js가 로드된 이후에 진행되어야 한다.
        ex) ui-sref="login" login 이라는 이름의 state 로 연결되어
        물리적인 페이지 이동 없이 해당 내용이 DOM 에 주입된다.
        (SPA 를 위해서 해시를 이용해 라우팅하는 기능)

        ngAnimate: angular-animate.js 모듈을 사용된 것이며,
        애니매이션 효과를 주기위해 사용된다. (해당 소스에는 사용하지 않는다.)

        ngResource: angular.js 모듈에 $http 모듈이 존재하지만,
        ngResource 를 통해 restAPI 를 훨씬 편하게 사용 가능하다.
        service script 에 사용하고 있으며,
        특정 API 에 대한 URL 을 정의해 놓고 사양할 수 있다.
        즉 ajax 호출 처럼 get, put, post, delete method 를 지정할 수 있다.
        (서버에 REST API 를 사용하기 위함)

    */
	var app = angular.module('app', ['ui.router', 'navController', 'ngAnimate', 'ui.bootstrap', 'ngResource', 'app.controllers', 'app.services', 'multipleSelect', 'LocalStorageModule'])

	// define for requirejs loaded modules
	define('app', [], function() { return app; });

	// function for dynamic load with requirejs of a javascript module for use with a view
	// in the state definition call add property `resolve: req('/views/ui.js')`
	// or `resolve: req(['/views/ui.js'])`
	// or `resolve: req('views/ui')`

	/*
	    $rootScope 객체는 문서 전체에 사용할 변수나 함수를 저장해 놓는 객체
	    $scope 객체는 controller 를 통해 벼수나 함수를 저장해 놓는 객체
	*/
	function req(deps) {
		if (typeof deps === 'string') deps = [deps];
		return {
			deps: function ($q, $rootScope) {
				var deferred = $q.defer();
				require(deps, function() {
					$rootScope.$apply(function () {
						deferred.resolve();
					});
					deferred.resolve();
				});
				return deferred.promise;
			}
		}
	}

	/*
	    localStorage 에 user 정보를 갖고 있다.
	    인증된 정보를 local 에 저장하여,
	    페이지 별로 인증 처리를 진행하기 때문!
	*/
	app.config(function($stateProvider, $urlRouterProvider, $controllerProvider, $httpProvider, localStorageServiceProvider){
		localStorageServiceProvider.setPrefix('TradersAppV2');
		$httpProvider.interceptors.push('AuthInterceptor');
		$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
		
		var origController = app.controller
		app.controller = function (name, constructor){
			$controllerProvider.register(name, constructor);
			return origController.apply(this, arguments);
		}

		var viewsPrefix = 'views/';


        /*
            없는 경로일 경우, root 경로로 redirect
        */
		$urlRouterProvider
			.otherwise("/")


        /*
            angular-ui-router 모듈을 통해 사용되며,
            ui-sref에서 지정된 값에 동작하는 state로
            해당 html을 불러주는 역할을 한다.
        */
		$stateProvider
			// you can set this to no template if you just want to use the html in the page
			.state('home', {
				url: "/",
				templateUrl: viewsPrefix + "home.html",
				data: {
					pageTitle: 'Home'
				}
			})
			.state('login', {
				url: "/login",
				templateUrl: viewsPrefix + "login.html",
				controller: 'nav'
			})
			.state('register', {
				url: "/register",
				templateUrl: viewsPrefix + "register.html",
				controller: 'RegisterController'
			})
			.state('users',{
		        url:'/users',
		        templateUrl: viewsPrefix + 'user/users.html',
		        controller:'UserListController'
		    })
		    .state('viewUser',{
			       url:'/users/:id/view',
			       templateUrl: viewsPrefix + 'user/user-view.html',
			       controller:'UserViewController'
		    })
		    .state('newUser',{
			        url:'/users/new',
			        templateUrl: viewsPrefix + 'user/user-add.html',
			        controller:'UserCreateController'
		    })
		    .state('editUser',{
			        url:'/users/:id/edit',
			        templateUrl: viewsPrefix + 'user/user-edit.html',
			        controller:'UserEditController'
		    })
			.state('suppliers',{
		        url:'/suppliers',
		        templateUrl: viewsPrefix + 'supplier/suppliers.html',
		        controller:'SupplierListController'
		    })
		    .state('viewSupplier',{
			       url:'/suppliers/:id/view',
			       templateUrl: viewsPrefix + 'supplier/supplier-view.html',
			       controller:'SupplierViewController'
		    })
		    .state('newSupplier',{
			        url:'/suppliers/new',
			        templateUrl: viewsPrefix + 'supplier/supplier-add.html',
			        controller:'SupplierCreateController'
		    })
		    .state('editSupplier',{
			        url:'/suppliers/:id/edit',
			        templateUrl: viewsPrefix + 'supplier/supplier-edit.html',
			        controller:'SupplierEditController'
		    })	    
			.state('categories',{
		        url:'/categories',
		        templateUrl: viewsPrefix + 'category/categories.html',
		        controller:'CategoryListController'
		    })
		    .state('viewCategory',{
			       url:'/categories/:id/view',
			       templateUrl: viewsPrefix + 'category/category-view.html',
			       controller:'CategoryViewController'
		    })
		    .state('newCategory',{
			        url:'/categories/new',
			        templateUrl: viewsPrefix + 'category/category-add.html',
			        controller:'CategoryCreateController'
		    })
		    .state('editCategory',{
			        url:'/categories/:id/edit',
			        templateUrl: viewsPrefix + 'category/category-edit.html',
			        controller:'CategoryEditController'
		    })
			.state('products',{
		        url:'/products',
		        templateUrl: viewsPrefix + 'product/products.html',
		        controller:'ProductListController'
		    })
		    .state('viewProduct',{
			       url:'/products/:id/view',
			       templateUrl: viewsPrefix + 'product/product-view.html',
			       controller:'ProductViewController'
		    })
		    .state('newProduct',{
			        url:'/products/new',
			        templateUrl: viewsPrefix + 'product/product-add.html',
			        controller:'ProductCreateController'
		    })
		    .state('editProduct',{
			        url:'/products/:id/edit',
			        templateUrl: viewsPrefix + 'product/product-edit.html',
			        controller:'ProductEditController'
		    })

		    .state('Excel',{
                    url:'/excel/read',
                    templateUrl: viewsPrefix + "Excel.html",
                    controller:'ExcelController'
            })

	})
	.directive('updateTitle', ['$rootScope', '$timeout',
		function($rootScope, $timeout) {
			return {
				link: function(scope, element) {
					var listener = function(event, toState) {
						var title = 'Project Name';
						if (toState.data && toState.data.pageTitle) title = toState.data.pageTitle + ' - ' + title;
						$timeout(function() {
							element.text(title);
						}, 0, false);
					};

					$rootScope.$on('$stateChangeSuccess', listener);
				}
			};
		}
	]);
}());
