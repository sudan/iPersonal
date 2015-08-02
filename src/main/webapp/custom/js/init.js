(function($, window, document){

	var Init = {

		init : function() {
			this.initDatePicker();
			this.initBackboneEvents();
			this.initChosenDropdowns();
		},

		initDatePicker: function() {
			$(document).ready(function(){
				$('input.datepicker').datepicker();
			});
		},

		initBackboneEvents: function() {
			window.backboneGlobalObj = {};
			_.extend(backboneGlobalObj, Backbone.Events);

			backboneGlobalObj.on('tag:add', function(obj){
				window.tagModel.addTags(obj.entityId, obj.entityType, obj.entityTitle, obj.tags);
			});

			backboneGlobalObj.on('entity:count', function(obj) {
				window.entityCountView.refreshCount(obj.entityType, obj.relativeValue);
			});
		},

		initChosenDropdowns: function() {

			$('#book-tag').chosen({
				width: '100%',
				no_results_text: 'Add a new tag and press enter(min 3 chars)'
			}).trigger('chosen:updated');

			$('#exp-tag').chosen({
				width: '100%',
				no_results_text: 'Add a new tag and press enter(min 3 chars)'
			}).trigger('chosen:updated');

			$('#exp-category').chosen({
				width: '100%',
				no_results_text: 'Add a new category and press enter(min 3 chars)'
			}).trigger('chosen:updated');

			$('.chosen-menu').parent().find('div.chosen-container').find('input').on('keyup', function(event){
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
		}
	};

	Init.init();

})(jQuery, window, document);