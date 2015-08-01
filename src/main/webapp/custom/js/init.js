(function($, window, document){

	$('#book-tag').chosen({
		width: '100%',
		no_results_text: 'Add a new tag and press enter(min 3 chars)'
	}).trigger('chosen:updated');

	$('.tag-menu').parent().find('div.chosen-container').find('input').on('keyup', function(event){
		if(event.keyCode == 13) {
			var value = $(event.target).val();
			if(value.length >= 3) {
				var tagDropDown = $(window.currentTagEntity);
				tagDropDown.append($('<option></option>').attr('value', value).text(value));
				var chosenValues = $(window.currentTagEntity).val();
				if (!chosenValues)
					chosenValues = []
				chosenValues.push(value);
				
				tagDropDown.val(chosenValues);
				tagDropDown.trigger('chosen:updated');
			}
		}	
	});

})(jQuery, window, document);