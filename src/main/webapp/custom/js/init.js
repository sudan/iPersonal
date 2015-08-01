(function($, window, document){

	$('#book-tag').chosen({
		width: '100%',
		no_results_text: 'Add a new tag'
	}).trigger('chosen:updated');

	$('.tag-menu').parent().find('div.chosen-container').find('input').on('keyup', function(event){
		if(event.keyCode == 13) {
			var value = $(event.target).val();
			if(value.length >= 3) {
				$(window.currentTagEntity).append('<option>' + value + '</option>');
				var chosenValues = $(window.currentTagEntity).val();
				if (!chosenValues)
					chosenValues = []
				chosenValues.push(value);
				
				$(window.currentTagEntity).val(chosenValues);
				$(window.currentTagEntity).trigger('chosen:updated');
			}
		}	
	});

})(jQuery, window, document);