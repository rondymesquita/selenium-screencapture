window.onload = function(){
	// console.log(document);
	var fieldSearch = document.getElementById('search_field');
	var fieldButton = document.getElementById('search_button');
	var result = document.getElementById('result');


	fieldButton.addEventListener('click',function(){
		if(result.classList.contains('hide')){
			result.classList.remove('hide');
		}else{
			result.classList.add('hide');
		}

	});

}
