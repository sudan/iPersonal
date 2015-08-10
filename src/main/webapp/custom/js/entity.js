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
			'click img.add-entity-icon' : 'renderAddEntityForm',
			'click a': 'renderListEntityForm'
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

		renderListEntityForm: function(e) {
			e.preventDefault();
			var entity = $(e.target).data('entity');
			backboneGlobalObj.trigger('entity:fetchlist', entity.toUpperCase());
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
				case 'DIARY':
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
				
				switch(entity) {
					case 'BOOKMARK': 
						bookmarkView.renderCreateTemplate(); 
						bookmarkView.prepareVariables();
						Init.initBookmark();
						break;
					case 'NOTE':
						noteView.renderCreateTemplate();
						noteView.prepareVariables();
						Init.initNote();
						break;
					case 'PIN':
						pinView.renderCreateTemplate();
						pinView.prepareVariables();
						Init.initPin();
						break;
					case 'DIARY':
						pageView.renderCreateTemplate();
						pageView.prepareVariables();
						Init.initDiary();
						break;
					case 'EXPENSE':
						expenseView.renderCreateTemplate();
						expenseView.prepareVariables();
						Init.initExpense();
						break;
				}
			});

			backboneGlobalObj.on('entity:fetchlist', function(entity){

				switch(entity) {
					case 'BOOKMARK': 
						bookmarkView.fetchBookmarks();
						break;
					case 'NOTE': 
						noteView.fetchNotes();
						break;
					case 'PIN': 
						pinView.fetchPins();
						break;
					case 'TODO': break;
					case 'DIARY': 
						pageView.fetchPages();
						break;
					case 'EXPENSE':
						expenseView.fetchExpenses(); 
						break;
					case 'ALL': break;
				}

			});
		}
	});

	window.entityCountModel = new EntityCount();
	window.entityCountView = new EntityCountView();
	window.entityWrapperView = new EntityWrapperView();

})(jQuery, window, document);