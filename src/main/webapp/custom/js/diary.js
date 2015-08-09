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

    var Pages = Backbone.Collection.extend({
        model: Page
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

            this.model = new Diary();
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
                        var tags = self.searchTag.val();
                        self.postCreation(pageId, "DIARIES", title, 1, tags);
                        var page = new Page({
                            'pageId': pageId,
                            'title': title,
                            'content': content,
                            'createdOn': Math.floor(Date.now()),
                            'modifiedAt': Math.floor(Date.now()),
                            'summary': content.replace(/<(?:.|\n)*?>/gm, ''),
                            'tags': tags 
                        });
                        self.collection.unshift(page);
                        var entityList = self.buildEntityList();
                        backboneGlobalObj.trigger('entity:displaylist', entityList);
                    }
                });
            }
        },

        fetchPages: function(e) {

            var self = this;

            if (e) {
                e.preventDefault();
            }

            if (this.collection.length  == parseInt(entityCountModel.attributes.diaries)) {
                var entityList = this.buildEntityList();
                backboneGlobalObj.trigger('entity:displaylist', entityList);
                return;
            }
             
            this.model = new Diary();   
            this.model.fetch({data: {offset: this.collection.length, limit : 20} }).complete(function(response){
                if (response.status == 200) {
                    var diary = JSON.parse(response.responseText)['diary'];
                    var pages = diary.pages;

                    if (pages instanceof Array) {

                        for (var index in pages) {
                            var page = new Page(pages[index])
                            self.collection.push(page);
                        }

                    } else if( pages) {
                        var page = new Page(pages);
                        self.collection.push(page);
                    }

                    var entityList = self.buildEntityList();
                    backboneGlobalObj.trigger('entity:displaylist', entityList);
                }
            });
        },

        buildEntityList: function() {

            var entityList = [];

            for (var i = 0; i < this.collection.length; i++) {
                var summary = this.collection.models[i].attributes.summary;
                var entity = {
                    'entityId' : this.collection.models[i].attributes.pageId,
                    'entityTitle' : this.collection.models[i].attributes.title,
                    'entitySummary': summary ? summary.substring(0,100) : summary,
                    'entityType': 'diary',
                    'modifiedAt': this.collection.models[i].attributes.modifiedAt,
                };
                entityList.push(entity);
            }
            return entityList;
        }

	});

	window.diaryView = new DiaryView({ collection: new Pages() });
})(jQuery, window, document);