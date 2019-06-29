/*var instanciateUsers = function(e){
	userNamesToParam().then(function(result){
		$.ajax({
	
						type: 'POST',
						contentType: 'application/json',
						url: '/instanciateUsers?' + result,
						success: function(data, status){
							console.log(data)
						},
						error: function(xhr, status, err){
							console.log(xhr, status, err)
						}
		})
	
	})
	
}*/
/*#################################################################################################################################################################
########################################################################## Helper Methods #########################################################################
#################################################################################################################################################################*/
var userNamesToParam = function(){
	return new Promise(function(resolve, reject){
		var param = '';

		$('.usernameInput').each(function(index, elem){
		
			param += 'user=' + $(elem).val()
			if(index < $('.usernameInput').length -1){
				param += '&'

			}
			if(index == $('.usernameInput').length -1){
				return resolve(param)

			}
		})
	})
	
}