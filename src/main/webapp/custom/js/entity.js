(function($, window, document) {

	"use strict"

	var EntityCount = Backbone.Model.extend({

		urlRoot: '/iPersonal/dashboard/entities/count',
		defaults: {
			'bookmarks': 0,
			'notes': 0,
			'pins': 0,
			'todos': 0,
			'diaries': 0,
			'expenses': 0
		}	
	});

	var EntityCountView = Backbone.View.extend({

		el : $('#side-menu'),
		template: $('#side-menu-template').html(),

		initialize: function() {
			this.getCount();
		},

		events: {
			'click img.add-entity-icon' : 'renderAddEntityForm'
		},

		getCount: function() {
			var self = this;
			entityCountModel.fetch().done(function(response) {
				self.render();
			});
		},

		render: function() {
			var tmpl = _.template(this.template);
			this.$el.html(tmpl(entityCountModel.toJSON()));
			return this;
		},

		renderAddEntityForm: function(e) {
			e.preventDefault();
			var entity = $(e.target).data('entity');
			backboneGlobalObj.trigger('entity:createform', entity.toUpperCase());	
		},

		refreshCount: function(entityType, relativeValue) {

			switch(entityType) {
				case 'BOOKMARK':
					var newValue = parseInt(entityCountModel.get('bookmarks')) + parseInt(relativeValue);
					entityCountModel.set({'bookmarks' : newValue});
					break;
				case 'NOTE':
					var newValue = parseInt(entityCountModel.get('notes')) + parseInt(relativeValue);
					entityCountModel.set({'notes' : newValue});
					break;
				case 'PIN':
					var newValue = parseInt(entityCountModel.get('pins')) + parseInt(relativeValue);
					entityCountModel.set({'pins' : newValue});
					break;
				case 'TODOS':
					var newValue = parseInt(entityCountModel.get('todos')) + parseInt(relativeValue);
					entityCountModel.set({'todos' : newValue});
					break;
				case 'DIARIES':
					var newValue = parseInt(entityCountModel.get('diaries')) + parseInt(relativeValue);
					entityCountModel.set({'diaries' : newValue});
					break;
				case 'EXPENSE':
					var newValue = parseInt(entityCountModel.get('expenses')) + parseInt(relativeValue);
					entityCountModel.set({'expenses' : newValue});
					break;
			}
			this.render();
		}

	});

	var EntityWrapperView = Backbone.View.extend({
		el: $('#entity-wrapper'),

		initialize: function() {
			
			var self = this;
			backboneGlobalObj.on('entity:createform', function(entity) {
				var div = entity.toLowerCase() + '-wrapper';
				self.$el.children('#' + div).removeClass('invisible').siblings().addClass('invisible');
			});
		}
	});

	window.entityCountModel = new EntityCount();
	window.entityCountView = new EntityCountView();
	window.entityWrapperView = new EntityWrapperView();

})(jQuery, window, document);