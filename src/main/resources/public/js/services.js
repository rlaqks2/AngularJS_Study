/*
    서비스 vs 팩토리
    이 둘의 차이는 객체를 생성하는 방법의 차이에 있다.
    서비스: 생성자 함수와 같이 동작하고 new 키워드를 사용해 인스턴스를 초기화한다.
    (상속 데이터 핸들링, 모델링)
    팩토리: 비즈니스 로직 또는 모듈 제공자로 사용한다.
    (정적 메소드에 활용)
*/
angular.module('app.services', [])
.service('AuthService', function() {
	return {
		user : null,
		authorization: null,
	}
})
.factory('AuthInterceptor', function($rootScope, AuthService, localStorageService) {  
	var service = this;
    service.request = function(config) {
    	if(!AuthService.user){
    		auth = localStorageService.get('AuthService');
    		if (auth){
    			AuthService.user = auth.user;
    			$rootScope.user = auth.user;
    			AuthService.authorization = auth.authorization;
    		}
    	}

        if (AuthService.authorization) {
        	if (!config.headers.Authorization)
        		config.headers.Authorization = AuthService.authorization;
        }
        return config;
    };
    return service;
})

/*
    User, Supplier, Category, Product 객체로 활용해서 사용.
*/
.factory('User', function($resource, AuthService) {
	return $resource('/api/v2/users/:id', { id: '@id' }, {update: { method:'PUT'}});
})
.factory('Supplier', function($resource, AuthService) {
	return $resource('/api/v2/suppliers/:id', { id: '@id' }, {update: { method:'PUT'}});
})
.factory('Category', function($resource) {
	return $resource('/api/v2/categories/:id', { id: '@id' }, {update: {method: 'PUT'}});
})
.factory('Product', function($resource) {
	return $resource('/api/v2/products/:id', { id: '@id' }, {update: {method: 'PUT'}});
})
.service('popupService',function($window){
    this.showPopup=function(message){
        return $window.confirm(message);
    }
})