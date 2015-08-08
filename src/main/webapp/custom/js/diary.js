(function($, window, document){

	"use strict"

	var DIARY_URL_ROOT = '/iPersonal/dashboard/diaries';

	var Page = Base.extend({

		initialize: function() {
			this.mandatory = {
                'title': true, 'content': true, 'date': true
            },
            this.maxLength = {
                'title': 50, 'content': 3000
            }
		}
	});

	var Diary = Base.extend({

		urlRoot: DIARY_URL_ROOT,


		initialize: function() {

			this.formAttributes = ['title', 'content', 'date'];
		}
	});

	var DiaryView = BaseView.extend({

		el: $('#diary-wrapper'),
        createTemplate: $('#diary-create-template').html(),

        events : {
            'click #diary-submit': 'createDiary',
            'click #diary-cancel': 'resetValues',
            'click #diary-tag-img': 'displayTagSelection',
        },

        initialize: function() {
            this.model = new Diary();
        },

        prepareVariables: function() {
            
            this.saveForm =  $('#diary-form');
            this.tagImage = $('#diary-tag-img');
            this.searchTag = $('#diary-tag');
            this.diaryRTE =  $('#diary');
        },

        resetValues: function(e) {

            if (e) {
                e.preventDefault();
            }
            BaseView.prototype.resetValues.apply(this, arguments);
            this.diaryRTE.empty();
        },

        createDiary: function(e) {

            var self = this;
            e.preventDefault();

            var date = self.saveForm.find('[name=date]').val();
            var content = self.saveForm.find('div#diary').cleanHtml();
            var title = self.saveForm.find('[name=title]').val();

            var dateArr = []
            if (date) {
            	dateArr = date.split('-');
            }

            var page = new Page({
            	content: content,
            	title: title,
            	month: dateArr[1],
            	date: dateArr[2]
            });

            var errors = page.validate(page.attributes);
            if (errors) {
            	this.renderErrors(errors);
            }

            this.model.pages = [];
            this.model.pages.push(page);

            self.model.set({
                pages: this.model.pages,
                year: dateArr[0]
            });

        
            var result = self.model.save({
                success: function(response) {},
                error: function(error) {}
            });

            if (result) {
                result.complete(function(response){
                    if (response.status != 201) {
                        var errors = self.buildErrorObject(response, self);
                        self.renderErrors(errors);
                    } else {
                        var pageId = response.responseText;
                        self.model.set({ pageId: pageId});
                        var tags = self.searchTag.val();
                        self.postCreation(pageId, "DIARIES", title, 1, tags);
                    }
                });
            }
        }

	});

	window.diaryView = new DiaryView();
})(jQuery, window, document);